package ua.ivan909020.scheduler.core.service.discovery;

import java.util.List;
import java.util.Map;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;

public interface InstanceRegistry {

    Instance getCurrentInstance();

    void updateCurrentInstanceMetadata(Map<String, String> metadata);

    List<Instance> getAllInstances();

}
