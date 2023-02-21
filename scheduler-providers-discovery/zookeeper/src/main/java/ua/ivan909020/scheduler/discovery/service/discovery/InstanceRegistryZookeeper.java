package ua.ivan909020.scheduler.discovery.service.discovery;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;

import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

public class InstanceRegistryZookeeper implements InstanceRegistry {

    private final String instanceName;

    private final ServiceInstanceRegistration instanceRegistration;

    private final ZookeeperDiscoveryClient discoveryClient;

    public InstanceRegistryZookeeper(
            String instanceName,
            ServiceInstanceRegistration instanceRegistration,
            ZookeeperDiscoveryClient discoveryClient) {

        this.instanceName = instanceName;
        this.instanceRegistration = instanceRegistration;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public ServiceInstance getCurrentInstance() {
        setPortIfNeeded();
        return new ZookeeperServiceInstance(instanceName, instanceRegistration.getServiceInstance());
    }

    private void setPortIfNeeded() {
        if (instanceRegistration.getServiceInstance().getPort() == null) {
            instanceRegistration.setPort(0);
        }
    }

    @Override
    public List<ServiceInstance> getAllInstances() {
        return discoveryClient.getInstances(instanceName);
    }

}
