package ua.ivan909020.scheduler.leader.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua.ivan909020.scheduler.leader.configuration.properties.LeaderZookeeperProperties;

@Configuration
@EnableConfigurationProperties
@AutoConfigureBefore(org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration.class)
public class ZookeeperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LeaderZookeeperProperties leaderZookeeperProperties() {
        ZookeeperProperties zookeeperConnectionProperties = new ZookeeperProperties();

        LeaderZookeeperProperties zookeeperProperties = new LeaderZookeeperProperties();
        zookeeperProperties.setConnectionProperties(zookeeperConnectionProperties);
        return zookeeperProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperProperties zookeeperProperties(LeaderZookeeperProperties zookeeperProperties) {
        return zookeeperProperties.getConnectionProperties();
    }

}
