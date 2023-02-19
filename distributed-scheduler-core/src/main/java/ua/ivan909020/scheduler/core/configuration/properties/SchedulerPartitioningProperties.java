package ua.ivan909020.scheduler.core.configuration.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("scheduler.partitioning")
public class SchedulerPartitioningProperties {

    private int maxPartitions;

    private List<Integer> staticPartitions;

    public int getMaxPartitions() {
        return maxPartitions;
    }

    public void setMaxPartitions(int maxPartitions) {
        this.maxPartitions = maxPartitions;
    }

    public List<Integer> getStaticPartitions() {
        return staticPartitions;
    }

    public void setStaticPartitions(List<Integer> staticPartitions) {
        this.staticPartitions = staticPartitions;
    }

}
