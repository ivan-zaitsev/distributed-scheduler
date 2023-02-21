package ua.ivan909020.scheduler.rest.controller.task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.ivan909020.scheduler.rest.model.dto.PageDto;
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
    public PageDto<TaskDto> findAll(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return taskService.findAll(pageNumber, pageSize);
    }

}
