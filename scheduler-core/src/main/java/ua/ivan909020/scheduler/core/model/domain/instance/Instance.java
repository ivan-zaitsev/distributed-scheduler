package ua.ivan909020.scheduler.core.model.domain.instance;

import java.time.Instant;

import org.springframework.cloud.client.ServiceInstance;

public class Instance {

    public static final String SCHEDULER_MODE = "scheduler_mode";
    public static final String CURRENT_PARTITIONS = "current_partitions";

    private ServiceInstance serviceInstance;

    private Instant registeredAt;

    private InstanceStatus status;

    private InstanceMode mode;

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(ServiceInstance serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public void setStatus(InstanceStatus status) {
        this.status = status;
    }

    public InstanceMode getMode() {
        return mode;
    }

    public void setMode(InstanceMode mode) {
        this.mode = mode;
    }

}
