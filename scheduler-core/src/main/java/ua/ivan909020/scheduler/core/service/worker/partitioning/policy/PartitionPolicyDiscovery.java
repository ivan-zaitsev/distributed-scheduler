package ua.ivan909020.scheduler.core.service.worker.partitioning.policy;

import java.util.List;
import java.util.stream.IntStream;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerPartitioningProperties;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

public class PartitionPolicyDiscovery implements PartitionPolicy {

    private final SchedulerPartitioningProperties partitioningProperties;

    private final InstanceRegistry instanceRegistry;

    public PartitionPolicyDiscovery(
            SchedulerPartitioningProperties partitioningProperties,
            InstanceRegistry instanceRegistry) {

        this.partitioningProperties = partitioningProperties;
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public List<Integer> computePartitions() {
        List<String> instanceIds = instanceRegistry.getAllInstances().stream()
                .map(instance -> instance.getServiceInstance().getInstanceId())
                .sorted()
                .toList();

        int instancesCount = instanceIds.size();
        int currentInstanceIndex =
                instanceIds.indexOf(instanceRegistry.getCurrentInstance().getServiceInstance().getInstanceId());

        if (areInstancesEmpty(instancesCount, currentInstanceIndex)) {
            return List.of();
        }

        int currentPartitionsCount = calculateCurrentInstancePartitionsCount(instancesCount);

        if (areThereMoreInstancesThanPartitions(currentInstanceIndex)) {
            return List.of();
        }

        int start = calculatePartitionStartIndex(currentInstanceIndex, currentPartitionsCount);
        int end = calculatePartitionEndIndex(instancesCount, currentInstanceIndex, currentPartitionsCount);

        return IntStream.rangeClosed(start, end).boxed().toList();
    }

    private boolean areInstancesEmpty(int instancesCount, int currentInstanceIndex) {
        return instancesCount == 0 || currentInstanceIndex == -1;
    }

    private int calculateCurrentInstancePartitionsCount(int instancesCount) {
        if (partitioningProperties.getMaxPartitions() > instancesCount) {
            return partitioningProperties.getMaxPartitions() / instancesCount;
        } else {
            return partitioningProperties.getMaxPartitions();
        }
    }

    private boolean areThereMoreInstancesThanPartitions(int currentInstanceIndex) {
        return currentInstanceIndex >= partitioningProperties.getMaxPartitions() - 1;
    }

    private int calculatePartitionStartIndex(int currentInstanceIndex, int currentInstancePartitionsCount) {
        return currentInstanceIndex * currentInstancePartitionsCount;
    }

    private int calculatePartitionEndIndex(int instancesCount, int currentInstanceIndex, int currentPartitionsCount) {
        if (currentInstanceIndex == instancesCount - 1) {
            return partitioningProperties.getMaxPartitions() - 1;
        } else {
            return ((currentInstanceIndex + 1) * currentPartitionsCount) - 1;
        }
    }

}
