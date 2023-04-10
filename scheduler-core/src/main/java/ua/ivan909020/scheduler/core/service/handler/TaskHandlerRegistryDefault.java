package ua.ivan909020.scheduler.core.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import ua.ivan909020.scheduler.core.model.entity.Task;

public class TaskHandlerRegistryDefault implements TaskHandlerRegistry {

    private ApplicationContext applicationContext;

    public TaskHandlerRegistryDefault(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public TaskHandler findTaskHandler(Task task) {
        List<TaskHandler> taskHandlers = findAllTaskHandlers().stream()
                .filter(taskHandler -> taskHandler.supports(task))
                .toList();

        if (taskHandlers.isEmpty()) {
            throw new IllegalStateException("Found none task handlers for task: " + task.getId());
        }
        if (taskHandlers.size() > 1) {
            throw new IllegalStateException("Found too many task handlers for task: " + task.getId());
        }

        return taskHandlers.get(0);
    }

    @Override
    public List<TaskHandler> findAllTaskHandlers() {
        return new ArrayList<>(applicationContext.getBeansOfType(TaskHandler.class).values());
    }

}
