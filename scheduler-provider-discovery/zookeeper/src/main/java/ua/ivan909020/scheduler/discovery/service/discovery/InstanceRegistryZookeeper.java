package ua.ivan909020.scheduler.discovery.service.discovery;

import static org.springframework.cloud.zookeeper.support.StatusConstants.INSTANCE_STATUS_KEY;

import java.time.Instant;
import java.util.List;

import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.support.StatusConstants;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;
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
    public Instance getCurrentInstance() {
        setPortIfNeeded();
        return buildInstance(new ZookeeperServiceInstance(instanceName, instanceRegistration.getServiceInstance()));
    }

    private void setPortIfNeeded() {
        if (instanceRegistration.getServiceInstance().getPort() == null) {
            instanceRegistration.setPort(0);
        }
    }

    @Override
    public List<Instance> getAllInstances() {
        return discoveryClient.getInstances(instanceName).stream()
                .map(ZookeeperServiceInstance.class::cast)
                .map(this::buildInstance)
                .toList();
    }

    private Instance buildInstance(ZookeeperServiceInstance serviceInstance) {
        Instance instance = new Instance();
        instance.setServiceInstance(serviceInstance);
        instance.setStatus(buildInstanceStatus(serviceInstance));
        instance.setRegisteredAt(Instant.ofEpochMilli(serviceInstance.getServiceInstance().getRegistrationTimeUTC()));
        return instance;
    }

    private InstanceStatus buildInstanceStatus(ZookeeperServiceInstance serviceInstance) {
        String status = serviceInstance.getMetadata().get(INSTANCE_STATUS_KEY);
        return switch (status) {
            case StatusConstants.STATUS_UP -> InstanceStatus.UP;
            case StatusConstants.STATUS_OUT_OF_SERVICE -> InstanceStatus.DOWN;
            default -> throw new IllegalStateException("Failed to determine instance status for = " + status);
        };
    }

}
