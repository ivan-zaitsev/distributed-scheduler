package ua.ivan909020.scheduler.rest.service.instance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.rest.model.dto.instance.InstanceDto;

@ExtendWith(MockitoExtension.class)
class InstanceServiceDefaultTest {

    @Mock
    private InstanceRegistry instanceRegistry;

    @InjectMocks
    private InstanceServiceDefault instanceServiceDefault;

    @Test
    void findAll_shouldReturnEmptyList_whenRegistryIsEmpty() {
        List<InstanceDto> expectedInstanceDtos = List.of();

        List<InstanceDto> actualInstanceDtos = instanceServiceDefault.findAll();

        assertEquals(expectedInstanceDtos, actualInstanceDtos);
    }

    @Test
    void findAll_shouldReturnInstances() {
        Instant timestamp = Instant.now();
        List<InstanceDto> expectedInstanceDtos = List.of(buildInstanceDto(timestamp));

        List<Instance> instances = List.of(buildInstance(timestamp));
        doReturn(instances).when(instanceRegistry).getAllInstances();

        List<InstanceDto> actualInstanceDtos = instanceServiceDefault.findAll();

        assertEquals(expectedInstanceDtos, actualInstanceDtos);
    }

    private InstanceDto buildInstanceDto(Instant timestamp) {
        InstanceDto instanceDto = new InstanceDto();
        instanceDto.setId("instanceId");
        instanceDto.setUri(URI.create("http://localhost:8080"));
        instanceDto.setRegisteredAt(timestamp);
        instanceDto.setUpdatedAt(timestamp);
        instanceDto.setStatus(InstanceStatus.UP);
        instanceDto.setPartitions(List.of(1));
        return instanceDto;
    }

    private Instance buildInstance(Instant timestamp) {
        ServiceInstance serviceInstance = new DefaultServiceInstance(
                "instanceId", 
                "serviceId",
                "localhost",
                8080,
                false);

        Instance instance = new Instance();
        instance.setServiceInstance(serviceInstance);
        instance.setRegisteredAt(timestamp);
        instance.setUpdatedAt(timestamp);
        instance.setStatus(InstanceStatus.UP);
        instance.setPartitions(List.of(1));
        return instance;
    }

}
