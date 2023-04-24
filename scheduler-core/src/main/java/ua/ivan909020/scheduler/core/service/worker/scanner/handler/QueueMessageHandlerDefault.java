package ua.ivan909020.scheduler.core.service.worker.scanner.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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

    private final ThreadPoolTaskExecutor taskHandlerExecutor;

    public QueueMessageHandlerDefault(
            TaskRepository taskRepository,
            QueueConsumer queueConsumer,
            JsonConverter jsonConverter,
            TaskHandlerRegistry taskHandlerRegistry,
            ThreadPoolTaskExecutor taskHandlerExecutor) {

        this.taskRepository = taskRepository;
        this.queueConsumer = queueConsumer;
        this.jsonConverter = jsonConverter;
        this.taskHandlerRegistry = taskHandlerRegistry;
        this.taskHandlerExecutor = taskHandlerExecutor;
    }

    @Override
    public void start() {
        logger.info("Starting");

        queueConsumer.subscribe(message -> {
            logger.info("Received a message: {}", message);

            taskHandlerExecutor.execute(() -> {
                try {
                    Task task = jsonConverter.convertToObject(message.getValue(), Task.class);

                    handle(task);
                } catch (Exception e) {
                    logger.warn("Failed to handle a message: {}", message);
                }
            });
        });
        queueConsumer.start();
    }

    private void handle(Task task) {
        TaskHandler taskHandler = taskHandlerRegistry.findTaskHandler(task);

        Exception exception = null;
        try {
            taskHandler.handle(task);

            logger.info("Task successfully handled: {}", task);
        } catch (Exception e) {
            exception = e;
            logger.warn("Failed to handle a task: {}", task, e);
        }

        if (exception == null) {
            task.setStatus(TaskStatus.SUCCEEDED);
            taskRepository.updateStatus(task);
        } else {
            task.setStatus(TaskStatus.FAILED);
            taskRepository.updateStatus(task);
        }
    }

    @Override
    public void stop() {
        logger.info("Stopping");

        queueConsumer.stop();
        taskHandlerExecutor.shutdown();
    }

}
