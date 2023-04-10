package ua.ivan909020.scheduler.database.mongo.model.entity;

import java.time.Instant;

public class TaskLock {

    private Integer partition;

    private String taskId;

    private Instant lockAtMost;

    public Integer getPartition() {
        return partition;
    }

    public TaskLock setPartition(Integer partition) {
        this.partition = partition;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskLock setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Instant getLockAtMost() {
        return lockAtMost;
    }

    public TaskLock setLockAtMost(Instant lockAtMost) {
        this.lockAtMost = lockAtMost;
        return this;
    }

}
