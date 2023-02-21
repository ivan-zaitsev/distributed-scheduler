package ua.ivan909020.scheduler.core.service.discovery;

import java.util.List;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;

public interface InstanceRegistry {

    public Instance getCurrentInstance();

    public List<Instance> getAllInstances();

}
