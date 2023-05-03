package ua.ivan909020.scheduler.discovery.zookeeper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.cloud.zookeeper.support.StatusConstants.INSTANCE_STATUS_KEY;
import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.PARTITIONS;
import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.UPDATED_AT;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.support.StatusConstants;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;

@ExtendWith(MockitoExtension.class)
class InstanceRegistryZookeeperTest {

    private MockedStatic<Instant> instant;

    @Mock
    private SchedulerProperties schedulerProperties;

    @Mock
    private ServiceInstanceRegistration instanceRegistration;

    @Mock
    private ZookeeperDiscoveryClient discoveryClient;

    @Mock
    private ServiceDiscovery<ZookeeperInstance> serviceDiscovery;

    @InjectMocks
    private InstanceRegistryZookeeper instanceRegistryZookeeper;

    @AfterEach
    void teardown() {
        if (instant != null) {
            instant.close();
            instant = null;
        }
    }

    @Test
    void getCurrentInstance_shouldReturnInstance() throws Exception {
        Instant timestamp = Instant.now();

        String groupId = "groupId";
        doReturn(groupId).when(schedulerProperties).getGroupId();

        Map<String, String> metadata = Map.of(
                UPDATED_AT, timestamp.toString(),
                INSTANCE_STATUS_KEY, StatusConstants.STATUS_UP.toString(),
                PARTITIONS, "1,2,3");
        ServiceInstanceBuilder<ZookeeperInstance> builder = ServiceInstance.<ZookeeperInstance>builder();
        builder.name("name").port(8080).payload(new ZookeeperInstance("id", "name", metadata));
        doReturn(builder.build()).when(instanceRegistration).getServiceInstance();

        Instance actualInstance = instanceRegistryZookeeper.getCurrentInstance();

        assertEquals(timestamp, actualInstance.getUpdatedAt());
        assertEquals(InstanceStatus.UP, actualInstance.getStatus());
        assertEquals(List.of(1, 2, 3), actualInstance.getPartitions());
    }

    @Test
    void updateCurrentInstanceMetadata() throws Exception {
        Instant timestamp = Instant.now();
        instant = mockStatic(Instant.class);
        instant.when(Instant::now).thenReturn(timestamp);

        ServiceInstance<ZookeeperInstance> serviceInstance = mock(ServiceInstance.class);
        doReturn(serviceInstance).when(instanceRegistration).getServiceInstance();
        doReturn(new ZookeeperInstance("id", "name", new HashMap<>())).when(serviceInstance).getPayload();

        Map<String, String> metadata = Map.of(PARTITIONS, "1,2,3");
        instanceRegistryZookeeper.updateCurrentInstanceMetadata(metadata);

        ArgumentCaptor<ServiceInstance<ZookeeperInstance>> serviceInstanceCaptor = 
                ArgumentCaptor.forClass(ServiceInstance.class);
        verify(serviceDiscovery, times(1)).updateService(serviceInstanceCaptor.capture());

        assertEquals("1,2,3", serviceInstanceCaptor.getValue().getPayload().getMetadata().get(PARTITIONS));
        assertEquals(timestamp.toString(), serviceInstanceCaptor.getValue().getPayload().getMetadata().get(UPDATED_AT));
    }

    @Test
    void getAllInstances_shouldReturnEmptyList_whenDiscoveryClientIsEmpty() {
        List<Instance> expectedInstances = List.of();

        String groupId = "groupId";
        doReturn(groupId).when(schedulerProperties).getGroupId();
        doReturn(List.of()).when(discoveryClient).getInstances(groupId);

        List<Instance> actualInstances = instanceRegistryZookeeper.getAllInstances();

        assertEquals(expectedInstances, actualInstances);
    }

}
