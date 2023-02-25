package ua.ivan909020.scheduler.core.service.worker.partitioning.policy;

import java.util.List;

public class PartitionPolicyStatic implements PartitionPolicy {

    private final List<Integer> staticPartitions;

    public PartitionPolicyStatic(List<Integer> staticPartitions) {
        this.staticPartitions = staticPartitions;
    }

    @Override
    public List<Integer> computePartitions() {
        return staticPartitions;
    }

}
