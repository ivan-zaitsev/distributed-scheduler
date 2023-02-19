package ua.ivan909020.scheduler.core.service.core;

import java.util.List;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;

public class SchedulerService {

    private SchedulerProperties schedulerProperties;

    private final Set<WorkerService> workerServices;

    public SchedulerService(SchedulerProperties schedulerProperties, Set<WorkerService> workerServices) {
        this.schedulerProperties = schedulerProperties;
        this.workerServices = workerServices;
    }

    @PostConstruct
    public void start() {
        List<WorkerService> services = filterWorkerServices();

        if (services.isEmpty()) {
            throw new IllegalStateException("Found none worker services with mode = " + schedulerProperties.getMode());
        }

        services.forEach(WorkerService::start);
    }

    @PreDestroy
    public void stop() {
        List<WorkerService> services = filterWorkerServices();
        services.forEach(WorkerService::stop);
    }

    private List<WorkerService> filterWorkerServices() {
        return workerServices.stream()
                .filter(workerService -> schedulerProperties.getMode().equals(workerService.getMode()))
                .toList();
    }

}
