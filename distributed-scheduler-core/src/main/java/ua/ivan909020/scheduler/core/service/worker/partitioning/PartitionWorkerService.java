package ua.ivan909020.scheduler.core.service.worker.partitioning;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.ivan909020.scheduler.core.model.domain.SchedulerMode;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;
import ua.ivan909020.scheduler.core.service.worker.partitioning.policy.PartitionPolicy;

public class PartitionWorkerService implements WorkerService {

    private static final Duration REPARTITION_INTERVAL = Duration.ofSeconds(10);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PartitionPolicy partitionPolicy;

    private final ScheduledExecutorService executorService;

    private final List<Integer> currentPartitions;

    public PartitionWorkerService(PartitionPolicy partitionPolicy, ScheduledExecutorService executorService) {
        this.partitionPolicy = partitionPolicy;
        this.executorService = executorService;
        this.currentPartitions = new ArrayList<>();
    }

    @Override
    public SchedulerMode getMode() {
        return SchedulerMode.PARTITIONING;
    }

    @Override
    public void start() {
        logger.info("Starting PartitionWorkerService");

        executorService.scheduleWithFixedDelay(
                this::repartition, 0, REPARTITION_INTERVAL.toSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        logger.info("Stopping PartitionWorkerService");

        executorService.shutdownNow();
    }

    private void repartition() {
        logger.info("Starting repartition");

        List<Integer> newPartitions = partitionPolicy.computePartitions();

        if (!currentPartitions.equals(newPartitions)) {
            System.out.println("newPartitions=" + newPartitions);
        } else {
            System.out.println("currentPartitions=" + currentPartitions);
        }
    }

}
