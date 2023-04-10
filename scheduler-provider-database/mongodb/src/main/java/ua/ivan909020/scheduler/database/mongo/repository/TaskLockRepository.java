package ua.ivan909020.scheduler.database.mongo.repository;

import java.time.Instant;

import ua.ivan909020.scheduler.database.mongo.model.entity.TaskLock;

public interface TaskLockRepository {

    boolean acquire(TaskLock taskLock);

    boolean release(TaskLock taskLock);

    default boolean executeWithLock(Runnable command, TaskLock taskLock) {
        if (!acquire(taskLock)) {
            return false;
        }
        try {
            command.run();
        } finally {
            release(new TaskLock().setTaskId(taskLock.getTaskId()).setLockAtMost(Instant.now()));
        }
        return true;
    }

}
