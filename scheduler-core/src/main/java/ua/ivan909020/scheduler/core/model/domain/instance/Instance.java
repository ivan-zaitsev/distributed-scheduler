package ua.ivan909020.scheduler.core.model.domain.instance;

import java.time.Instant;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

public class Instance {

    public static final String UPDATED_AT = "updated_at";
    public static final String PARTITIONS = "partitions";

    private ServiceInstance serviceInstance;

    private Instant registeredAt;

    private Instant updatedAt;

    private InstanceStatus status;

    private List<Integer> partitions;

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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public void setStatus(InstanceStatus status) {
        this.status = status;
    }

    public List<Integer> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Integer> partitions) {
        this.partitions = partitions;
    }

    @Override
    public String toString() {
        return "Instance [" + 
                "serviceInstance=" + serviceInstance + 
                ", registeredAt=" + registeredAt + 
                ", updatedAt=" + updatedAt + 
                ", status=" + status + 
                ", partitions=" + partitions + "]";
    }

}
