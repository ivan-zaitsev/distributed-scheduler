package ua.ivan909020.scheduler.rest.service.task;

import java.util.List;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;

public interface TaskService {

    PagedList<TaskDto> findAll(List<TaskStatus> statuses, KeysetPage page);

}
