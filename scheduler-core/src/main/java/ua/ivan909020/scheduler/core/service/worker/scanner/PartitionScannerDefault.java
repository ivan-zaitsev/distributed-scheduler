package ua.ivan909020.scheduler.core.service.worker.scanner;

import static java.util.concurrent.TimeUnit.SECONDS;
import static ua.ivan909020.scheduler.core.model.entity.TaskStatus.SCHEDULED;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.queue.QueueProducer;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.core.service.converter.JsonConverter;

public class PartitionScannerDefault implements PartitionScanner {

    private static final Duration SCAN_INTERVAL = Duration.ofSeconds(5);

    private static final int TASK_FETCH_LIMIT = 500;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TaskRepository taskRepository;

    private final QueueProducer queueProducer;

    private final JsonConverter jsonConverter;

    private final ScheduledExecutorService partitionExecutor = Executors.newScheduledThreadPool(1);

    private final List<Integer> partitions;

    public PartitionScannerDefault(
            TaskRepository taskRepository,
            QueueProducer queueProducer,
            JsonConverter jsonConverter,
            List<Integer> partitions) {

        this.taskRepository = taskRepository;
        this.queueProducer = queueProducer;
        this.jsonConverter = jsonConverter;
        this.partitions = partitions;
    }

    @Override
    public List<Integer> getPartitions() {
        return partitions;
    }

    @Override
    public void start() {
        logger.info("Starting, partitions: {}", partitions);

        partitionExecutor.scheduleWithFixedDelay(() -> {
            try {
                scan(partitions);
            } catch(Exception e) {
                logger.warn("Failed to scan partitions", e);
            }
        }, 0, SCAN_INTERVAL.toSeconds(), SECONDS);
    }

    @Override
    public void stop() {
        logger.info("Stopping, partitions: {}", partitions);

        partitionExecutor.shutdownNow();
    }

    private void scan(List<Integer> partitions) {
        List<Task> tasks =
                taskRepository.findAllOverdue(partitions, List.of(SCHEDULED), Instant.now(), TASK_FETCH_LIMIT);

        logger.info("Scanning, partitions: {}, found tasks: {}", partitions, tasks.size());

        for (Task task : tasks) {
            try {
                send(task);
            } catch (Exception e) {
                logger.warn("Failed to send a task: {}", task, e);
            }
        }
    }

    private void send(Task task) {
        task.setStatus(TaskStatus.SUBMITTED);
        taskRepository.updateStatus(task);

        QueueMessage message = new QueueMessage();
        message.setKey(String.valueOf(task.getId()));
        message.setValue(jsonConverter.convertToString(task));

        logger.info("Sending a message: {}", message);

        queueProducer.send(message);
    }

}
