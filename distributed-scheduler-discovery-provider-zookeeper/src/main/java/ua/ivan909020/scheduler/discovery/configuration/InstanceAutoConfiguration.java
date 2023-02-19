package ua.ivan909020.scheduler.discovery.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.discovery.service.discovery.InstanceRegistryZookeeper;

@Configuration
public class InstanceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstanceRegistry instanceRegistry(
            @Value("${spring.application.name}") String instanceName,
            ServiceInstanceRegistration instanceRegistration,
            ZookeeperDiscoveryClient discoveryClient) {

        return new InstanceRegistryZookeeper(instanceName, instanceRegistration, discoveryClient);
    }

}
