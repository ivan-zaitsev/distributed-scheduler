package ua.ivan909020.scheduler.core.repository;

import java.time.Instant;
import java.util.List;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;

public interface TaskRepository {

    boolean create(Task task);

    boolean update(Task task);

    List<Task> findAllOverdue(List<TaskStatus> statuses, Instant timestamp, int limit);

}
