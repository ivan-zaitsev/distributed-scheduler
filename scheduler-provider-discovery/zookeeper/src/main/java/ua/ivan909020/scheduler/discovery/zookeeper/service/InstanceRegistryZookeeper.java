package ua.ivan909020.scheduler.discovery.zookeeper.service;

import static org.springframework.cloud.zookeeper.support.StatusConstants.INSTANCE_STATUS_KEY;
import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.PARTITIONS;
import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.UPDATED_AT;

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
import ua.ivan909020.scheduler.core.service.converter.PartitionConverter;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

public class InstanceRegistryZookeeper implements InstanceRegistry {

    private final String groupId;

    private final ServiceInstanceRegistration instanceRegistration;

    private final ZookeeperDiscoveryClient discoveryClient;

    private final ServiceDiscovery<ZookeeperInstance> serviceDiscovery;

    public InstanceRegistryZookeeper(
            String groupId,
            ServiceInstanceRegistration instanceRegistration,
            ZookeeperDiscoveryClient discoveryClient,
            ServiceDiscovery<ZookeeperInstance> serviceDiscovery) {

        this.groupId = groupId;
        this.instanceRegistration = instanceRegistration;
        this.discoveryClient = discoveryClient;
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public Instance getCurrentInstance() {
        setPortIfNeeded();
        return buildInstance(new ZookeeperServiceInstance(groupId, instanceRegistration.getServiceInstance()));
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
        currentMetadata.put(Instance.UPDATED_AT, Instant.now().toString());
        currentMetadata.putAll(metadata);

        try {
            serviceDiscovery.updateService(serviceInstance);
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
    }

    @Override
    public List<Instance> getAllInstances() {
        return discoveryClient.getInstances(groupId).stream()
                .map(ZookeeperServiceInstance.class::cast)
                .map(this::buildInstance)
                .toList();
    }

    private Instance buildInstance(ZookeeperServiceInstance serviceInstance) {
        Instance instance = new Instance();
        instance.setServiceInstance(serviceInstance);
        instance.setRegisteredAt(Instant.ofEpochMilli(serviceInstance.getServiceInstance().getRegistrationTimeUTC()));

        if (serviceInstance.getMetadata().containsKey(UPDATED_AT)) {
            instance.setUpdatedAt(Instant.parse(serviceInstance.getMetadata().get(UPDATED_AT)));
        }
        if (serviceInstance.getMetadata().containsKey(INSTANCE_STATUS_KEY)) {
            instance.setStatus(InstanceStatus.valueOf(serviceInstance.getMetadata().get(INSTANCE_STATUS_KEY)));
        }
        if (serviceInstance.getMetadata().containsKey(PARTITIONS)) {
            instance.setPartitions(PartitionConverter.toList(serviceInstance.getMetadata().get(PARTITIONS)));
        }
        return instance;
    }

}
