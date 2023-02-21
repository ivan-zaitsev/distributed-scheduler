package ua.ivan909020.scheduler.core.configuration;

import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerPartitioningProperties;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.worker.partitioning.PartitionWorkerService;
import ua.ivan909020.scheduler.core.service.worker.partitioning.policy.DiscoveryPartitionPolicy;
import ua.ivan909020.scheduler.core.service.worker.partitioning.policy.PartitionPolicy;
import ua.ivan909020.scheduler.core.service.worker.partitioning.policy.StaticPartitionPolicy;

@Configuration
@EnableConfigurationProperties
public class PartitioningAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SchedulerPartitioningProperties partitioningProperties() {
        return new SchedulerPartitioningProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty("scheduler.partitioning.static-partitions")
    public PartitionPolicy staticPartitionPolicy(SchedulerPartitioningProperties partitioningProperties) {
        return new StaticPartitionPolicy(partitioningProperties.getStaticPartitions());
    }

    @Bean
    @ConditionalOnMissingBean
    public PartitionPolicy discoveryPartitionPolicy(
            SchedulerPartitioningProperties partitioningProperties,
            InstanceRegistry instanceRegistry) {

        return new DiscoveryPartitionPolicy(partitioningProperties, instanceRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public PartitionWorkerService partitionWorkerService(PartitionPolicy partitionPolicy) {
        return new PartitionWorkerService(partitionPolicy, Executors.newScheduledThreadPool(1));
    }

}
