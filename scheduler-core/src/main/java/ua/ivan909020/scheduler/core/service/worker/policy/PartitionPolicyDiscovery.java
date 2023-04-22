package ua.ivan909020.scheduler.core.service.worker.policy;

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
        int currentInstanceIndex =
                instanceIds.indexOf(instanceRegistry.getCurrentInstance().getServiceInstance().getInstanceId()) + 1;

        if (areInstancesEmpty(instancesCount, currentInstanceIndex)) {
            return List.of();
        }

        if (areThereMoreInstancesThanPartitions(currentInstanceIndex)) {
            return List.of();
        }

        int currentPartitionsCount = calculateCurrentInstancePartitionsCount(instancesCount);
        int start = calculatePartitionStartIndex(currentInstanceIndex, currentPartitionsCount);
        int end = calculatePartitionEndIndex(instancesCount, currentInstanceIndex, currentPartitionsCount);

        return IntStream.rangeClosed(start, end).boxed().toList();
    }

    private boolean areInstancesEmpty(int instancesCount, int currentInstanceIndex) {
        return instancesCount == 0 || currentInstanceIndex == 0;
    }

    private boolean areThereMoreInstancesThanPartitions(int currentInstanceIndex) {
        return currentInstanceIndex > schedulerProperties.getMaxPartitions();
    }

    private int calculateCurrentInstancePartitionsCount(int instancesCount) {
        return (int) Math.ceil((double) schedulerProperties.getMaxPartitions() / instancesCount);
    }

    private int calculatePartitionStartIndex(int currentInstanceIndex, int currentInstancePartitionsCount) {
        return ((currentInstanceIndex - 1) * currentInstancePartitionsCount) + 1;
    }

    private int calculatePartitionEndIndex(int instancesCount, int currentInstanceIndex, int currentPartitionsCount) {
        if (currentInstanceIndex == instancesCount) {
            return schedulerProperties.getMaxPartitions();
        } else {
            return currentInstanceIndex * currentPartitionsCount;
        }
    }

}
