package ua.ivan909020.scheduler.core.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import ua.ivan909020.scheduler.core.model.domain.instance.InstanceMode;

@ConfigurationProperties("scheduler")
public class SchedulerProperties {

    private String instanceId;

    private InstanceMode mode;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public InstanceMode getMode() {
        return mode;
    }

    public void setMode(InstanceMode mode) {
        this.mode = mode;
    }

}
