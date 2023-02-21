package ua.ivan909020.scheduler.core.service.worker;

import ua.ivan909020.scheduler.core.model.domain.SchedulerMode;

public interface WorkerService {

    SchedulerMode getMode();

    void start();

    void stop();

}