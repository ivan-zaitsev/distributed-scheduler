package ua.ivan909020.scheduler.core.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import ua.ivan909020.scheduler.core.model.domain.SchedulerMode;

@ConfigurationProperties("scheduler")
public class SchedulerProperties {

    private SchedulerMode mode;

    public SchedulerMode getMode() {
        return mode;
    }

    public void setMode(SchedulerMode mode) {
        this.mode = mode;
    }

}
