package ua.ivan909020.scheduler.core.service.worker;

import ua.ivan909020.scheduler.core.model.domain.instance.InstanceMode;

public interface WorkerService {

    InstanceMode getMode();

    void start();

    void stop();

}
