package ua.ivan909020.scheduler.rest.controller.endpoint.task;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.rest.exception.EntityValidationException;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;
import ua.ivan909020.scheduler.rest.service.task.TaskService;

@RestController
@RequestMapping("/api")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/v1/tasks")
    public PagedList<TaskDto> findAll(
            @RequestParam List<TaskStatus> statuses,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String pageCursor) {

        if (pageSize < 1 || pageSize > 1000) {
            throw new EntityValidationException("Parameter 'pageSize' must be >= 1 and < 1000");
        }

        return taskService.findAll(statuses, new KeysetPage(pageSize, pageCursor));
    }

}
