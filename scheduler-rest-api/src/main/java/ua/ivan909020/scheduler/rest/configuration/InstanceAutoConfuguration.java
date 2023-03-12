package ua.ivan909020.scheduler.rest.configuration;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.discovery.LeaderRegistry;
import ua.ivan909020.scheduler.rest.controller.instance.InstanceRestController;
import ua.ivan909020.scheduler.rest.service.instance.InstanceService;
import ua.ivan909020.scheduler.rest.service.instance.InstanceServiceDefault;

@Configuration
public class InstanceAutoConfuguration {

    @Bean
    @ConditionalOnMissingBean
    public InstanceService instanceService(
            InstanceRegistry instanceRegistry,
            Optional<LeaderRegistry> leaderRegistry) {

        return new InstanceServiceDefault(instanceRegistry, leaderRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstanceRestController instanceRestController(InstanceService instanceService) {
        return new InstanceRestController(instanceService);
    }

}
