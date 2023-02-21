package ua.ivan909020.scheduler.core.service.worker.partitioning.policy;

import java.util.List;

public interface PartitionPolicy {

    List<Integer> computePartitions();

}
