package ua.ivan909020.scheduler.core.service.core;

import java.util.Set;

import com.fasterxml.uuid.Generators;

import ua.ivan909020.scheduler.core.model.domain.task.ScheduleTaskRequest;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;

public class SchedulerServiceDefault implements SchedulerService {

    private final PartitionService partitionService;

    private final Set<WorkerService> workerServices;

    private final TaskRepository taskRepository;

    public SchedulerServiceDefault(
            PartitionService partitionService,
            Set<WorkerService> workerServices,
            TaskRepository taskRepository) {

        this.partitionService = partitionService;
        this.workerServices = workerServices;
        this.taskRepository = taskRepository;
    }

    @Override
    public void start() {
        workerServices.forEach(WorkerService::start);
    }

    @Override
    public void stop() {
        workerServices.forEach(WorkerService::stop);
    }

    @Override
    public void schedule(ScheduleTaskRequest request) {
        Task task = new Task();
        task.setPartition(partitionService.generate());
        task.setId(Generators.timeBasedEpochGenerator().generate().toString());
        task.setVersion(1L);
        task.setStatus(TaskStatus.SCHEDULED);
        task.setExecuteAt(request.getExecuteAt());
        task.setName(request.getName());
        task.setData(request.getData());

        taskRepository.create(task);
    }

}
