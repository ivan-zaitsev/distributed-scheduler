package ua.ivan909020.scheduler.leader.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.zookeeper.ZookeeperProperties;

@ConfigurationProperties("scheduler.zookeeper")
public class LeaderZookeeperProperties {

    private ZookeeperProperties connectionProperties;

    public ZookeeperProperties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(ZookeeperProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

}
