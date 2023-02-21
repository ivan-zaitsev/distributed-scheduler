package ua.ivan909020.scheduler.rest.service.instance;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.leader.LeaderRegistry;
import ua.ivan909020.scheduler.rest.model.dto.InstanceDto;

@Service
public class InstanceServiceDefault implements InstanceService {

    private InstanceRegistry instanceRegistry;

    private Optional<LeaderRegistry> leaderRegistry;

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

        if (leaderRegistry.isPresent()) {
            instanceDto.setLeader(serviceInstance.getInstanceId().equals(leaderRegistry.get().getLeaderInstanceId()));
        }
        return instanceDto;
    }

}
