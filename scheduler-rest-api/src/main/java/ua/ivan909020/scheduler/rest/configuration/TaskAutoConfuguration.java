package ua.ivan909020.scheduler.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.rest.controller.task.TaskRestController;
import ua.ivan909020.scheduler.rest.service.task.TaskService;
import ua.ivan909020.scheduler.rest.service.task.TaskServiceDefault;

@Configuration
public class TaskAutoConfuguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskService taskService(TaskRepository taskRepository) {
        return new TaskServiceDefault(taskRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskRestController instanceRestController(TaskService taskService) {
        return new TaskRestController(taskService);
    }

}
