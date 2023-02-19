package ua.ivan909020.scheduler.core.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.service.leader.LeaderRegistry;
import ua.ivan909020.scheduler.core.service.worker.leadership.LeaderWorkerService;

@Configuration
public class LeadershipAutoConfiguration {

    @Bean
    @ConditionalOnBean(LeaderRegistry.class)
    @ConditionalOnMissingBean
    public LeaderWorkerService leaderWorkerService(LeaderRegistry leaderRegistry) {
        return new LeaderWorkerService(leaderRegistry);
    }

}
