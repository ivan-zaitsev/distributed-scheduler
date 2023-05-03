package ua.ivan909020.scheduler.rest.model.dto.task;

import java.time.Instant;
import java.util.Objects;

import ua.ivan909020.scheduler.core.model.entity.TaskStatus;

public class TaskDto {

    private String id;

    private TaskStatus status;

    private Instant executeAt;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Instant executeAt) {
        this.executeAt = executeAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(executeAt, id, name, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TaskDto other = (TaskDto) obj;
        return Objects.equals(executeAt, other.executeAt) && 
                Objects.equals(id, other.id) && 
                Objects.equals(name, other.name) && 
                status == other.status;
    }

    @Override
    public String toString() {
        return "TaskDto [" + 
                "id=" + id + 
                ", status=" + status + 
                ", executeAt=" + executeAt + 
                ", name=" + name + "]";
    }

    
}
