package ua.ivan909020.scheduler.core.configuration;

import java.util.Set;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.service.core.SchedulerService;
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
    @ConditionalOnMissingBean
    public SchedulerService schedulerService(
            SchedulerProperties schedulerProperties,
            Set<WorkerService> workerServices) {

        return new SchedulerService(schedulerProperties, workerServices);
    }

}
