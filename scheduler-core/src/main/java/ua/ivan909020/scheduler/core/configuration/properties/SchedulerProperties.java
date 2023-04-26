package ua.ivan909020.scheduler.core.configuration.properties;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("scheduler")
public class SchedulerProperties {

    private String groupId;

    private String instanceId;

    private int maxPartitions;

    private Duration taskFetchInterval;

    private int taskFetchLimit;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getMaxPartitions() {
        return maxPartitions;
    }

    public void setMaxPartitions(int maxPartitions) {
        this.maxPartitions = maxPartitions;
    }

    public Duration getTaskFetchInterval() {
        return taskFetchInterval;
    }

    public void setTaskFetchInterval(Duration taskFetchInterval) {
        this.taskFetchInterval = taskFetchInterval;
    }

    public int getTaskFetchLimit() {
        return taskFetchLimit;
    }

    public void setTaskFetchLimit(int taskFetchLimit) {
        this.taskFetchLimit = taskFetchLimit;
    }

}
