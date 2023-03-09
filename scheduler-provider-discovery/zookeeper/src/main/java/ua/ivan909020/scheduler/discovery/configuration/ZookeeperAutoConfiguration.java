package ua.ivan909020.scheduler.discovery.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.discovery.configuration.properties.DiscoveryZookeeperProperties;

@Configuration
@EnableConfigurationProperties
@AutoConfigureBefore({
        org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration.class,
        org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryAutoConfiguration.class })
public class ZookeeperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryZookeeperProperties discoveryZookeeperProperties(
            SchedulerProperties schedulerProperties,
            InetUtils inetUtils) {

        ZookeeperProperties zookeeperConnectionProperties = new ZookeeperProperties();

        ZookeeperDiscoveryProperties zookeeperDiscoveryProperties = new ZookeeperDiscoveryProperties(inetUtils);
        zookeeperDiscoveryProperties.setInstanceId(schedulerProperties.getInstanceId());

        DiscoveryZookeeperProperties zookeeperProperties = new DiscoveryZookeeperProperties();
        zookeeperProperties.setConnectionProperties(zookeeperConnectionProperties);
        zookeeperProperties.setDiscoveryProperties(zookeeperDiscoveryProperties);
        return zookeeperProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperProperties zookeeperProperties(DiscoveryZookeeperProperties zookeeperProperties) {
        return zookeeperProperties.getConnectionProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperDiscoveryProperties zookeeperDiscoveryProperties(DiscoveryZookeeperProperties zookeeperProperties) {
        return zookeeperProperties.getDiscoveryProperties();
    }

}
