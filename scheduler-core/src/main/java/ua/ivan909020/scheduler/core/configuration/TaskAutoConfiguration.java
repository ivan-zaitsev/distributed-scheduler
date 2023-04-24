package ua.ivan909020.scheduler.core.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.util.CallerBlocksPolicy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import ua.ivan909020.scheduler.core.service.handler.TaskHandlerRegistry;
import ua.ivan909020.scheduler.core.service.handler.TaskHandlerRegistryDefault;

@Configuration
public class TaskAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolTaskExecutor taskHandlerExecutor() {
        ThreadPoolTaskExecutor taskHandlerExecutor = new ThreadPoolTaskExecutor();
        taskHandlerExecutor.setCorePoolSize(8);
        taskHandlerExecutor.setMaxPoolSize(8);
        taskHandlerExecutor.setQueueCapacity(8);
        taskHandlerExecutor.setRejectedExecutionHandler(new CallerBlocksPolicy(Integer.MAX_VALUE));
        taskHandlerExecutor.afterPropertiesSet();
        return taskHandlerExecutor;
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskHandlerRegistry taskHandlerRegistry(ApplicationContext applicationContext) {
        return new TaskHandlerRegistryDefault(applicationContext);
    }

}
