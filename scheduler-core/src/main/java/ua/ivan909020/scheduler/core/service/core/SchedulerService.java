package ua.ivan909020.scheduler.core.service.core;

import ua.ivan909020.scheduler.core.model.domain.task.ScheduleTaskRequest;

public interface SchedulerService {

    void start();

    void stop();

    void schedule(ScheduleTaskRequest request);

}
