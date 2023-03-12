package ua.ivan909020.scheduler.core.service.core;

import static ua.ivan909020.scheduler.core.model.domain.instance.Instance.SCHEDULER_MODE;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.event.EventListener;

import jakarta.annotation.PreDestroy;
import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;

public class SchedulerService {

    private final SchedulerProperties schedulerProperties;

    private final InstanceRegistry instanceRegistry;

    private final Set<WorkerService> workerServices;

    public SchedulerService(
            SchedulerProperties schedulerProperties,
            InstanceRegistry instanceRegistry,
            Set<WorkerService> workerServices) {

        this.schedulerProperties = schedulerProperties;
        this.instanceRegistry = instanceRegistry;
        this.workerServices = workerServices;
    }

    @EventListener(InstanceRegisteredEvent.class)
    public void start() {
        instanceRegistry.updateCurrentInstanceMetadata(Map.of(SCHEDULER_MODE, schedulerProperties.getMode().name()));

        workerServices.forEach(WorkerService::start);
    }

    @PreDestroy
    public void stop() {
        workerServices.forEach(WorkerService::stop);
    }

}
