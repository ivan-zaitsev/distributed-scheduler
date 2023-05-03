package ua.ivan909020.scheduler.rest.service.task;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepositoryExtended;
import ua.ivan909020.scheduler.core.service.core.PartitionService;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;

@Service
public class TaskServiceDefault implements TaskService {

    private final TaskRepositoryExtended taskRepository;

    private final PartitionService partitionService;

    public TaskServiceDefault(TaskRepositoryExtended taskRepository, PartitionService partitionService) {
        this.taskRepository = taskRepository;
        this.partitionService = partitionService;
    }

    @Override
    public PagedList<TaskDto> findAll(List<TaskStatus> statuses, KeysetPage page) {
        PagedList<Task> tasks = taskRepository.findAll(partitionService.getAll(), statuses, page);
        List<TaskDto> result = tasks.content().stream().map(this::convert).toList();
        return new PagedList<>(result, tasks.nextCursor());
    }

    private TaskDto convert(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setStatus(task.getStatus());
        taskDto.setExecuteAt(task.getExecuteAt());
        taskDto.setName(task.getName());
        return taskDto;
    }

}
