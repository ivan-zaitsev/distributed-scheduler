package ua.ivan909020.scheduler.core.service.handler;

import java.util.List;

import ua.ivan909020.scheduler.core.model.entity.Task;

public interface TaskHandlerRegistry {

    TaskHandler findTaskHandler(Task task);

    List<TaskHandler> findAllTaskHandlers();

}
