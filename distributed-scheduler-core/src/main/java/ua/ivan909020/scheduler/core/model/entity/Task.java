package ua.ivan909020.scheduler.core.model.entity;

import java.time.Instant;
import java.util.UUID;

public class Task {

    private UUID id;

    private Integer partition;

    private Integer version;

    private String name;

    private TaskStatus status;

    private String data;

    private Instant createdAt;

    private Instant executeAt;

    private Instant processingStartedAt;

    private Instant processingRetriesCount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Instant executeAt) {
        this.executeAt = executeAt;
    }

    public Instant getProcessingStartedAt() {
        return processingStartedAt;
    }

    public void setProcessingStartedAt(Instant processingStartedAt) {
        this.processingStartedAt = processingStartedAt;
    }

    public Instant getProcessingRetriesCount() {
        return processingRetriesCount;
    }

    public void setProcessingRetriesCount(Instant processingRetriesCount) {
        this.processingRetriesCount = processingRetriesCount;
    }

}
