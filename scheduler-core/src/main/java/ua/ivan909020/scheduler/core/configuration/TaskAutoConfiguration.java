package ua.ivan909020.scheduler.core.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.service.handler.TaskHandlerRegistry;
import ua.ivan909020.scheduler.core.service.handler.TaskHandlerRegistryDefault;

@Configuration
public class TaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskHandlerRegistry taskHandlerRegistry(ApplicationContext applicationContext) {
        return new TaskHandlerRegistryDefault(applicationContext);
    }

}
