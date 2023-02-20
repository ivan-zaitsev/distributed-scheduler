package ua.ivan909020.scheduler.core.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import ua.ivan909020.scheduler.core.model.domain.SchedulerMode;

@ConfigurationProperties("scheduler")
public class SchedulerProperties {

    private String instanceId;

    private SchedulerMode mode;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public SchedulerMode getMode() {
        return mode;
    }

    public void setMode(SchedulerMode mode) {
        this.mode = mode;
    }

}
