package ua.ivan909020.scheduler.core.service.worker.scanner.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.queue.QueueConsumer;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.core.service.converter.JsonConverter;
import ua.ivan909020.scheduler.core.service.handler.TaskHandler;
import ua.ivan909020.scheduler.core.service.handler.TaskHandlerRegistry;

public class QueueMessageHandlerDefault implements QueueMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TaskRepository taskRepository;

    private final QueueConsumer queueConsumer;

    private final JsonConverter jsonConverter;

    private final TaskHandlerRegistry taskHandlerRegistry;

    public QueueMessageHandlerDefault(
            TaskRepository taskRepository,
            QueueConsumer queueConsumer,
            JsonConverter jsonConverter,
            TaskHandlerRegistry taskHandlerRegistry) {

        this.taskRepository = taskRepository;
        this.queueConsumer = queueConsumer;
        this.jsonConverter = jsonConverter;
        this.taskHandlerRegistry = taskHandlerRegistry;
    }

    @Override
    public void start() {
        logger.info("Starting");

        queueConsumer.subscribe(message -> {
            logger.info("Received a message: {}", message);

            try {
                Task task = jsonConverter.convertToObject(message.getValue(), Task.class);
                handle(task);
            } catch (Exception e) {
                logger.warn("Failed to handle a message: {}", message);
            }
        });
        queueConsumer.start();
    }

    private void handle(Task task) {
        TaskHandler taskHandler = taskHandlerRegistry.findTaskHandler(task);

        try {
            taskHandler.handle(task);

            task.setStatus(TaskStatus.SUCCEEDED);
            taskRepository.updateStatus(task);

            logger.warn("Task successfully handled: {}", task);
        } catch (Exception e) {
            task.setStatus(TaskStatus.FAILED);
            taskRepository.updateStatus(task);

            logger.warn("Failed to handle a task: {}", task, e);
        }
    }

    @Override
    public void stop() {
        logger.info("Stopping");

        queueConsumer.stop();
    }

}
