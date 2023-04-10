package ua.ivan909020.scheduler.rest.service.instance;

import java.util.Comparator;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.rest.model.dto.instance.InstanceDto;

@Service
public class InstanceServiceDefault implements InstanceService {

    private final InstanceRegistry instanceRegistry;

    public InstanceServiceDefault(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public List<InstanceDto> findAll() {
        return instanceRegistry.getAllInstances().stream()
                .map(this::buildInstanceDto)
                .sorted(Comparator.comparing(InstanceDto::getRegisteredAt))
                .toList();
    }

    private InstanceDto buildInstanceDto(Instance instance) {
        ServiceInstance serviceInstance = instance.getServiceInstance();

        InstanceDto instanceDto = new InstanceDto();
        instanceDto.setId(serviceInstance.getInstanceId());
        instanceDto.setUri(serviceInstance.getUri());
        instanceDto.setRegisteredAt(instance.getRegisteredAt());
        instanceDto.setUpdatedAt(instance.getUpdatedAt());
        instanceDto.setStatus(instance.getStatus());
        instanceDto.setPartitions(instance.getPartitions());
        return instanceDto;
    }

}
