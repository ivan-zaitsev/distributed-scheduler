package ua.ivan909020.scheduler.discovery.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;

@ConfigurationProperties("scheduler.zookeeper")
public class DiscoveryZookeeperProperties {

    private ZookeeperProperties connectionProperties = new ZookeeperProperties();

    private ZookeeperDiscoveryProperties discoveryProperties = new ZookeeperDiscoveryProperties(
            new InetUtils(new InetUtilsProperties()));

    public ZookeeperProperties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(ZookeeperProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public ZookeeperDiscoveryProperties getDiscoveryProperties() {
        return discoveryProperties;
    }

    public void setDiscoveryProperties(ZookeeperDiscoveryProperties discoveryProperties) {
        this.discoveryProperties = discoveryProperties;
    }

}
