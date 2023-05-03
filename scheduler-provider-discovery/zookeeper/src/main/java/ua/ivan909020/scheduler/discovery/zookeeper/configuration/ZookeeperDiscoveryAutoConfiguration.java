package ua.ivan909020.scheduler.discovery.zookeeper.configuration;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.discovery.zookeeper.service.InstanceRegistryZookeeper;

@Configuration
public class ZookeeperDiscoveryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstanceRegistry instanceRegistry(
            SchedulerProperties schedulerProperties,
            ServiceInstanceRegistration instanceRegistration,
            ZookeeperDiscoveryClient discoveryClient,
            ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {

        return new InstanceRegistryZookeeper(schedulerProperties, instanceRegistration,
                discoveryClient, serviceDiscovery);
    }

}
