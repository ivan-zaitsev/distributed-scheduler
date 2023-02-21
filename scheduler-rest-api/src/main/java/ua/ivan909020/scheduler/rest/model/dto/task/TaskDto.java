package ua.ivan909020.scheduler.rest.model.dto.task;

import java.util.UUID;

import ua.ivan909020.scheduler.core.model.entity.TaskStatus;

public class TaskDto {

    private UUID id;

    private TaskStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
