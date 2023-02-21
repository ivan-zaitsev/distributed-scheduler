package ua.ivan909020.scheduler.rest.service.task;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.rest.model.dto.PageDto;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;

@Service
public class TaskServiceDefault implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceDefault(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public PageDto<TaskDto> findAll(Integer pageNumber, Integer pageSize) {
        return new PageDto<>(List.of(), 0);
    }

}
