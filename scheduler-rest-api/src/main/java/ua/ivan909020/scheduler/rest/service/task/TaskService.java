package ua.ivan909020.scheduler.rest.service.task;

import ua.ivan909020.scheduler.rest.model.dto.PageDto;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;

public interface TaskService {

    PageDto<TaskDto> findAll(Integer pageNumber, Integer pageSize);

}
