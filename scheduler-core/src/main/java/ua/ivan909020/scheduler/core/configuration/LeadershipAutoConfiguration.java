package ua.ivan909020.scheduler.core.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.model.domain.instance.InstanceMode;
import ua.ivan909020.scheduler.core.service.discovery.LeaderRegistry;
import ua.ivan909020.scheduler.core.service.worker.leadership.LeaderWorkerService;

@Configuration
public class LeadershipAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "scheduler.mode", havingValue = InstanceMode.Fields.LEADERSHIP)
    public LeaderWorkerService leaderWorkerService(LeaderRegistry leaderRegistry) {
        return new LeaderWorkerService(leaderRegistry);
    }

}
