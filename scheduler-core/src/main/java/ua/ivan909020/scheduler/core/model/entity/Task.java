package ua.ivan909020.scheduler.core.model.entity;

import java.time.Instant;

public class Task {

    private Integer partition;

    private String id;

    private Long version;

    private TaskStatus status;

    private Instant executeAt;

    private String name;

    private String data;

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Task [partition=" + partition +
                ", id=" + id +
                ", version=" + version +
                ", status=" + status +
                ", executeAt=" + executeAt +
                ", name=" + name +
                ", data=" + data + "]";
    }

}
