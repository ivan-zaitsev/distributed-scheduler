package ua.ivan909020.scheduler.core.service.worker;

import static java.util.concurrent.TimeUnit.SECONDS;
import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.PARTITIONS;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.ivan909020.scheduler.core.service.converter.PartitionConverter;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.worker.policy.PartitionPolicy;
import ua.ivan909020.scheduler.core.service.worker.scanner.PartitionScanner;
import ua.ivan909020.scheduler.core.service.worker.scanner.PartitionScannerFactory;
import ua.ivan909020.scheduler.core.service.worker.scanner.handler.QueueMessageHandler;

public class WorkerServiceDefault implements WorkerService {

    private static final Duration REPARTITION_INTERVAL = Duration.ofSeconds(10);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PartitionPolicy partitionPolicy;

    private final InstanceRegistry instanceRegistry;

    private final QueueMessageHandler queueMessageHandler;

    private final PartitionScannerFactory partitionScannerFactory;

    private final ScheduledExecutorService executorService;

    private List<PartitionScanner> partitionScanners;

    public WorkerServiceDefault(
            PartitionPolicy partitionPolicy,
            InstanceRegistry instanceRegistry,
            PartitionScannerFactory partitionScannerFactory,
            QueueMessageHandler queueMessageHandler,
            ScheduledExecutorService executorService) {

        this.partitionPolicy = partitionPolicy;
        this.executorService = executorService;
        this.instanceRegistry = instanceRegistry;
        this.partitionScannerFactory = partitionScannerFactory;
        this.queueMessageHandler = queueMessageHandler;
        this.partitionScanners = new ArrayList<>();
    }

    @Override
    public void start() {
        logger.info("Starting");

        queueMessageHandler.start();

        executorService.scheduleWithFixedDelay(() -> {
            try {
                updatePartitions();
            } catch(Exception e) {
                logger.warn("Failed to update partitions", e);
            }
        }, 0, REPARTITION_INTERVAL.toSeconds(), SECONDS);
    }

    @Override
    public void stop() {
        logger.info("Stopping");

        consumePartitionScanners(PartitionScanner::stop);
        queueMessageHandler.stop();

        executorService.shutdownNow();
    }

    private void updatePartitions() {
        List<Integer> newPartitions = partitionPolicy.computePartitions();
        List<Integer> currentPartitions = getCurrentPartitions();

        logger.info("Starting repartition, current: {}, new: {}", currentPartitions, newPartitions);

        if (!newPartitions.equals(currentPartitions)) {
            rectratePartitions(newPartitions);
        }

        instanceRegistry.updateCurrentInstanceMetadata(Map.of(PARTITIONS, PartitionConverter.toString(newPartitions)));
    }

    private List<Integer> getCurrentPartitions() {
        return partitionScanners.stream()
                .map(PartitionScanner::getPartitions)
                .flatMap(Collection::stream)
                .toList();
    }

    private void rectratePartitions(List<Integer> newPartitions) {
        logger.info("Recreate partitions");

        consumePartitionScanners(PartitionScanner::stop);
        if (newPartitions.isEmpty()) {
            partitionScanners = List.of();
        } else {
            partitionScanners = List.of(partitionScannerFactory.create(newPartitions));
        }
        consumePartitionScanners(PartitionScanner::start);
    }

    private void consumePartitionScanners(Consumer<PartitionScanner> consumer) {
        partitionScanners.forEach(scanner -> {
            try {
                consumer.accept(scanner);
            } catch (Exception e) {
                logger.warn("Failed to consume scanner, {}", scanner, e);
            }
        });
    }

}
