package ua.ivan909020.scheduler.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

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
//        new Thread(() -> {
//            for (int i = 0; i < 100000; i++) {
//                ScheduleTaskRequest request = new ScheduleTaskRequest();
//                request.setExecuteAt(Instant.now());
//                request.setName("TEST_TASK");
//                request.setData("");
//
//                schedulerService.schedule(request);
//            }
//        }).start();

        schedulerService.start();
    }

    @Bean
    public TaskHandler taskHandler() {
        return new TaskHandler() {

            @Override
            public boolean supports(Task task) {
                return "TEST_TASK".equals(task.getName());
            }

            @Override
            public void handle(Task task) {
                System.out.println("HANDLED: " + task);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
