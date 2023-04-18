package ua.ivan909020.scheduler.core.repository;

import java.util.List;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;

public interface TaskRepositoryExtended extends TaskRepository {

    PagedList<Task> findAll(List<Integer> partitions, List<TaskStatus> statuses, KeysetPage page);

}
