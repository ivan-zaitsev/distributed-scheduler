package ua.ivan909020.scheduler.discovery.service.discovery;

import static org.springframework.cloud.zookeeper.support.StatusConstants.INSTANCE_STATUS_KEY;
import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.SCHEDULER_MODE;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.util.ReflectionUtils;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceMode;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

public class InstanceRegistryZookeeper implements InstanceRegistry {

    private final String instanceName;

    private final ServiceInstanceRegistration instanceRegistration;

    private final ZookeeperDiscoveryClient discoveryClient;

    private final ServiceDiscovery<ZookeeperInstance> serviceDiscovery;

    public InstanceRegistryZookeeper(
            String instanceName,
            ServiceInstanceRegistration instanceRegistration,
            ZookeeperDiscoveryClient discoveryClient,
            ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {

        this.instanceName = instanceName;
        this.instanceRegistration = instanceRegistration;
        this.discoveryClient = discoveryClient;
        this.serviceDiscovery = serviceDiscovery;
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
    public void updateCurrentInstanceMetadata(Map<String, String> metadata) {
        ServiceInstance<ZookeeperInstance> serviceInstance = instanceRegistration.getServiceInstance();

        Map<String, String> currentMetadata = serviceInstance.getPayload().getMetadata();
        currentMetadata.putAll(metadata);

        try {
            serviceDiscovery.updateService(serviceInstance);
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
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
        instance.setRegisteredAt(Instant.ofEpochMilli(serviceInstance.getServiceInstance().getRegistrationTimeUTC()));
        instance.setStatus(InstanceStatus.valueOf(serviceInstance.getMetadata().get(INSTANCE_STATUS_KEY)));
        instance.setMode(InstanceMode.valueOf(serviceInstance.getMetadata().get(SCHEDULER_MODE)));
        return instance;
    }

}
