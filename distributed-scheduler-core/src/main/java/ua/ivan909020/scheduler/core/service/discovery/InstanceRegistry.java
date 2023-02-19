package ua.ivan909020.scheduler.core.service.discovery;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

public interface InstanceRegistry {

    public ServiceInstance getCurrentInstance();

    public List<ServiceInstance> getAllInstances();

}
