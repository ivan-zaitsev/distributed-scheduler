package ua.ivan909020.scheduler.testdata;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.queue.QueueConsumer;
import ua.ivan909020.scheduler.core.queue.QueueProducer;
import ua.ivan909020.scheduler.core.repository.TaskRepositoryExtended;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

@SpringBootApplication(scanBasePackages = "ua.ivan909020.scheduler")
public class TestApplication {

    @Bean
    public InstanceRegistry instanceRegistry() {
        return new InstanceRegistry() {

            @Override
            public Instance getCurrentInstance() {
                return null;
            }

            @Override
            public void updateCurrentInstanceMetadata(Map<String, String> metadata) {

            }

            @Override
            public List<Instance> getAllInstances() {
                return List.of();
            }

        };
    }

    @Bean
    public QueueProducer queueProducer() {
        return new QueueProducer() {

            @Override
            public void send(QueueMessage message) {

            }
        };
    }
    
    @Bean
    public QueueConsumer queueConsumer() {
        return new QueueConsumer() {

            @Override
            public void start() {

            }

            @Override
            public void stop() {

            }

            @Override
            public void subscribe(Consumer<QueueMessage> handler) {

            }
            
        };
    }

    @Bean
    public TaskRepositoryExtended taskRepository() {
        return new TaskRepositoryExtended() {

            @Override
            public void create(Task task) {

            }

            @Override
            public void updateStatus(Task task) {

            }

            @Override
            public List<Task> findAllOverdue(
                    List<Integer> partitions, List<TaskStatus> statuses, Instant timestamp, int limit) {

                return List.of();
            }

            @Override
            public PagedList<Task> findAll(List<Integer> partitions, List<TaskStatus> statuses, KeysetPage page) {
                return new PagedList<>(List.of(), null);
            }

        };
    }

}
