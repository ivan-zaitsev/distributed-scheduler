package ua.ivan909020.scheduler.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.repository.TaskRepositoryExtended;
import ua.ivan909020.scheduler.core.service.core.PartitionService;
import ua.ivan909020.scheduler.rest.controller.endpoint.task.TaskRestController;
import ua.ivan909020.scheduler.rest.service.task.TaskService;
import ua.ivan909020.scheduler.rest.service.task.TaskServiceDefault;

@Configuration
public class TaskAutoConfuguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskService taskService(TaskRepositoryExtended taskRepository, PartitionService partitionService) {
        return new TaskServiceDefault(taskRepository, partitionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskRestController taskRestController(TaskService taskService) {
        return new TaskRestController(taskService);
    }

}
