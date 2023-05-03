package ua.ivan909020.scheduler.rest.service.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepositoryExtended;
import ua.ivan909020.scheduler.core.service.core.PartitionService;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;

@ExtendWith(MockitoExtension.class)
class TaskServiceDefaultTest {

    @Mock
    private TaskRepositoryExtended taskRepository;

    @Mock
    private PartitionService partitionService;

    @InjectMocks
    private TaskServiceDefault taskServiceDefault;

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        PagedList<TaskDto> expectedTaskDtos = new PagedList<>(List.of(), null);

        PagedList<Task> tasks = new PagedList<>(List.of(), null);
        doReturn(tasks).when(taskRepository).findAll(any(), any(), any());

        PagedList<TaskDto> actualTaskDtos = taskServiceDefault.findAll(List.of(TaskStatus.values()),
                new KeysetPage(50, null));

        assertEquals(expectedTaskDtos, actualTaskDtos);
    }

    @Test
    void findAll_shouldReturnTasks() {
        Instant timestamp = Instant.now();
        PagedList<TaskDto> expectedTaskDtos = new PagedList<>(List.of(buildTaskDto(timestamp)), null);

        PagedList<Task> tasks = new PagedList<>(List.of(buildTask(timestamp)), null);
        doReturn(tasks).when(taskRepository).findAll(any(), any(), any());

        PagedList<TaskDto> actualTaskDtos = taskServiceDefault.findAll(List.of(TaskStatus.values()),
                new KeysetPage(50, null));

        assertEquals(expectedTaskDtos, actualTaskDtos);
    }

    private TaskDto buildTaskDto(Instant timestamp) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId("id");
        taskDto.setStatus(TaskStatus.SCHEDULED);
        taskDto.setExecuteAt(timestamp);
        taskDto.setName("name");
        return taskDto;
    }

    private Task buildTask(Instant timestamp) {
        Task task = new Task();
        task.setId("id");
        task.setStatus(TaskStatus.SCHEDULED);
        task.setExecuteAt(timestamp);
        task.setName("name");
        return task;
    }

}
