package ua.ivan909020.scheduler.core.model.domain.instance;

import java.time.Instant;

import org.springframework.cloud.client.ServiceInstance;

public class Instance {

    private ServiceInstance serviceInstance;

    private Instant registeredAt;

    private InstanceStatus status;

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

}
