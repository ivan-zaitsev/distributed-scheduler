package ua.ivan909020.scheduler.leader.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.service.leader.LeaderRegistry;
import ua.ivan909020.scheduler.leader.service.LeaderEventPublisherZookeeper;
import ua.ivan909020.scheduler.leader.service.LeaderRegistryZookeeper;

@Configuration
public class LeaderAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DefaultCandidate candidate(SchedulerProperties schedulerProperties) {
        return new DefaultCandidate(schedulerProperties.getInstanceId(), "worker-node");
    }

    @Bean
    @ConditionalOnMissingBean
    public LeaderInitiator leaderInitiator(CuratorFramework client, Candidate candidate) {
        return new LeaderInitiator(client, candidate, "worker-node/leader");
    }

    @Bean
    @ConditionalOnMissingBean
    public LeaderRegistry leaderRegistry(LeaderInitiator leaderInitiator) {
        return new LeaderRegistryZookeeper(leaderInitiator, new LeaderEventPublisherZookeeper());
    }

}
