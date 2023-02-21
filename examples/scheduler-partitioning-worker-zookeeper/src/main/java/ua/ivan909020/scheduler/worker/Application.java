package ua.ivan909020.scheduler.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.service.handler.TaskHandler;

@SpringBootApplication
public class Application {

    @Bean
    public TaskHandler taskHandler() {
        return new TaskHandler() {

            @Override
            public boolean supports(Task task) {
                return true;
            }

            @Override
            public void handle(Task task) {
                System.out.println(task);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
