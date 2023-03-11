package ua.ivan909020.scheduler.core.service.core;

import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;

public class SchedulerService {

    private final Set<WorkerService> workerServices;

    public SchedulerService(Set<WorkerService> workerServices) {
        this.workerServices = workerServices;
    }

    @PostConstruct
    public void start() {
        workerServices.forEach(WorkerService::start);
    }

    @PreDestroy
    public void stop() {
        workerServices.forEach(WorkerService::stop);
    }

}
