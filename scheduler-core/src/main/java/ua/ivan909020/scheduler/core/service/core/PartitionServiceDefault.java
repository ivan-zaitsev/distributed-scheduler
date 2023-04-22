package ua.ivan909020.scheduler.core.service.core;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;

public class PartitionServiceDefault implements PartitionService {

    private final Random random = new Random();

    private final SchedulerProperties schedulerProperties;

    public PartitionServiceDefault(SchedulerProperties schedulerProperties) {
        this.schedulerProperties = schedulerProperties;
    }

    @Override
    public List<Integer> getAll() {
        return IntStream.rangeClosed(1, schedulerProperties.getMaxPartitions()).boxed().toList();
    }

    @Override
    public int generate() {
        return random.nextInt(schedulerProperties.getMaxPartitions() + 1);
    }

}
