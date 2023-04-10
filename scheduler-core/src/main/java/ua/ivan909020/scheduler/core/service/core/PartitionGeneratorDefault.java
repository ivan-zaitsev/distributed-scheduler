package ua.ivan909020.scheduler.core.service.core;

import java.util.Random;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;

public class PartitionGeneratorDefault implements PartitionGenerator {

    private final Random random = new Random();

    private final SchedulerProperties schedulerProperties;

    public PartitionGeneratorDefault(SchedulerProperties schedulerProperties) {
        this.schedulerProperties = schedulerProperties;
    }

    @Override
    public int generate() {
        return random.nextInt(schedulerProperties.getMaxPartitions() + 1);
    }

}
