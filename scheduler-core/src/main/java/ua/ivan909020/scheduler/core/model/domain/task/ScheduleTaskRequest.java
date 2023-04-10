package ua.ivan909020.scheduler.core.model.domain.task;

import java.time.Instant;

public class ScheduleTaskRequest {

    private Instant executeAt;

    private String name;

    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
