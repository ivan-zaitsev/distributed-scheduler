package ua.ivan909020.scheduler.core.service.worker.policy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

public class PartitionPolicyDiscovery implements PartitionPolicy {

    private final SchedulerProperties schedulerProperties;

    private final InstanceRegistry instanceRegistry;

    public PartitionPolicyDiscovery(SchedulerProperties schedulerProperties, InstanceRegistry instanceRegistry) {
        this.schedulerProperties = schedulerProperties;
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public List<Integer> computePartitions() {
        List<String> instanceIds = instanceRegistry.getAllInstances().stream()
                .sorted(Comparator.comparing(Instance::getRegisteredAt))
                .map(instance -> instance.getServiceInstance().getInstanceId())
                .toList();

        int instancesCount = instanceIds.size();
        int currentInstanceIndex = instanceIds
                .indexOf(instanceRegistry.getCurrentInstance().getServiceInstance().getInstanceId());

        if (instancesCount == 0 || currentInstanceIndex == -1) {
            return List.of();
        }

        List<Integer> partitions = IntStream.rangeClosed(1, schedulerProperties.getMaxPartitions()).boxed().toList();
        return partition(partitions, instanceIds.size()).get(currentInstanceIndex);
    }

    private List<List<Integer>> partition(List<Integer> partitions, int partitionSize) {
        List<List<Integer>> result = new ArrayList<>(partitionSize);

        int quotient = partitions.size() / partitionSize;
        int reminder = partitions.size() % partitionSize;

        int offset = 0;
        for (int i = 0; i < partitionSize; i++) {
            int size = quotient + (i < reminder ? 1 : 0);
            result.add(partitions.subList(offset, offset + size));
            offset += size;
        }
        return result;
    }

}
