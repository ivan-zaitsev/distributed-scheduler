package ua.ivan909020.scheduler.core.configuration;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.core.service.core.PartitionService;
import ua.ivan909020.scheduler.core.service.core.PartitionServiceDefault;
import ua.ivan909020.scheduler.core.service.core.SchedulerService;
import ua.ivan909020.scheduler.core.service.core.SchedulerServiceDefault;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;

@Configuration
@EnableConfigurationProperties
public class SchedulerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SchedulerProperties schedulerProperties() {
        return new SchedulerProperties();
    }

    @Bean
    public PartitionService partitionService(SchedulerProperties schedulerProperties) {
        return new PartitionServiceDefault(schedulerProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SchedulerService schedulerService(
            PartitionService partitionService,
            Set<WorkerService> workerServices,
            TaskRepository taskRepository) {

        return new SchedulerServiceDefault(partitionService, workerServices, taskRepository);
    }

}
