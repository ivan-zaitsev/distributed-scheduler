package ua.ivan909020.scheduler.core.service.worker.scanner;

import java.util.List;

import ua.ivan909020.scheduler.core.queue.QueueProducer;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.core.service.converter.JsonConverter;

public class PartitionScannerFactoryDefault implements PartitionScannerFactory {

    private final TaskRepository taskRepository;

    private final QueueProducer queueProducer;

    private final JsonConverter jsonConverter;

    public PartitionScannerFactoryDefault(
            TaskRepository taskRepository,
            QueueProducer queueProducer,
            JsonConverter jsonConverter) {

        this.taskRepository = taskRepository;
        this.queueProducer = queueProducer;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public PartitionScanner create(List<Integer> partitions) {
        return new PartitionScannerDefault(taskRepository, queueProducer, jsonConverter, partitions);
    }

}
