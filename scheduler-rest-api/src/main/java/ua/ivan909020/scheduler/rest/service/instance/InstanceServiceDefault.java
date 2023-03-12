package ua.ivan909020.scheduler.rest.service.instance;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceMode;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.discovery.LeaderRegistry;
import ua.ivan909020.scheduler.rest.model.dto.instance.InstanceDto;
import ua.ivan909020.scheduler.rest.model.dto.instance.InstanceLeadershipDto;
import ua.ivan909020.scheduler.rest.model.dto.instance.InstancePartitioningDto;

@Service
public class InstanceServiceDefault implements InstanceService {

    private final InstanceRegistry instanceRegistry;

    private final Optional<LeaderRegistry> leaderRegistry;

    public InstanceServiceDefault(
            InstanceRegistry instanceRegistry, 
            Optional<LeaderRegistry> leaderRegistry) {

        this.instanceRegistry = instanceRegistry;
        this.leaderRegistry = leaderRegistry;
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
        instanceDto.setStatus(instance.getStatus());
        instanceDto.setMode(instance.getMode());

        if (InstanceMode.LEADERSHIP.equals(instance.getMode()) && leaderRegistry.isPresent()) {
            instanceDto.setLeadership(buildInstanceLeadershipPropertiesDto(serviceInstance));
        }
        if (InstanceMode.PARTITIONING.equals(instance.getMode())) {
            instanceDto.setPartitioning(buildInstancePartitioningPropertiesDto(serviceInstance));
        }
        return instanceDto;
    }

    private InstanceLeadershipDto buildInstanceLeadershipPropertiesDto(ServiceInstance serviceInstance) {
        InstanceLeadershipDto result = new InstanceLeadershipDto();
        result.setLeader(serviceInstance.getInstanceId().equals(leaderRegistry.get().getLeaderInstanceId()));
        return result;
    }

    private InstancePartitioningDto buildInstancePartitioningPropertiesDto(ServiceInstance serviceInstance) {
        InstancePartitioningDto result = new InstancePartitioningDto();
        result.setPartitions(serviceInstance.getMetadata().get(Instance.CURRENT_PARTITIONS));
        return result;
    }

}
