package ua.ivan909020.scheduler.rest.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.rest.controller.endpoint.instance.InstanceRestController;
import ua.ivan909020.scheduler.rest.service.instance.InstanceService;
import ua.ivan909020.scheduler.rest.service.instance.InstanceServiceDefault;

@Configuration
public class InstanceAutoConfuguration {

    @Bean
    @ConditionalOnMissingBean
    public InstanceService instanceService(InstanceRegistry instanceRegistry) {
        return new InstanceServiceDefault(instanceRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstanceRestController instanceRestController(InstanceService instanceService) {
        return new InstanceRestController(instanceService);
    }

}
