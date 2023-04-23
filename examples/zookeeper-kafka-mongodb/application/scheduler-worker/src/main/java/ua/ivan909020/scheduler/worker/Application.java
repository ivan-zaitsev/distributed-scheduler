package ua.ivan909020.scheduler.worker;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import co.elastic.apm.attach.ElasticApmAttacher;
import ua.ivan909020.scheduler.core.model.domain.task.ScheduleTaskRequest;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.service.core.SchedulerService;
import ua.ivan909020.scheduler.core.service.handler.TaskHandler;

@SpringBootApplication
public class Application {

    private final SchedulerService schedulerService;

    public Application(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @EventListener(InstanceRegisteredEvent.class)
    public void handleInstanceRegisteredEvent() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Instant timestamp = Instant.now().plus(Duration.ofMinutes(1));

        for (AtomicInteger i = new AtomicInteger(0); i.get() < 10000; i.incrementAndGet()) {
            executorService.submit(() -> {
                ScheduleTaskRequest request = new ScheduleTaskRequest();
                request.setExecuteAt(timestamp);
                request.setName(String.format("TEST_TASK_%d", i.get()));
                request.setData("");

                schedulerService.schedule(request);
            });
        }

        schedulerService.start();
    }

    @Bean
    public TaskHandler taskHandler() {
        return new TaskHandler() {

            @Override
            public boolean supports(Task task) {
                return task.getName().startsWith("TEST_TASK");
            }

            @Override
            public void handle(Task task) {
                System.out.println("HANDLED: " + task);
            }

        };
    }

    public static void main(String[] args) {
        ElasticApmAttacher.attach();

        SpringApplication.run(Application.class, args);
    }

}
