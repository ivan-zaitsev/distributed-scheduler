package ua.ivan909020.scheduler.core.service.handler;

import ua.ivan909020.scheduler.core.model.entity.Task;

public interface TaskHandler {

    boolean supports(Task task);

    void handle(Task task);

}
