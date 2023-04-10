package ua.ivan909020.scheduler.core.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.queue.QueueConsumer;
import ua.ivan909020.scheduler.core.queue.QueueProducer;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.core.service.converter.JsonConverter;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;
import ua.ivan909020.scheduler.core.service.handler.TaskHandlerRegistry;
import ua.ivan909020.scheduler.core.service.worker.WorkerServiceDefault;
import ua.ivan909020.scheduler.core.service.worker.policy.PartitionPolicy;
import ua.ivan909020.scheduler.core.service.worker.policy.PartitionPolicyDiscovery;
import ua.ivan909020.scheduler.core.service.worker.scanner.PartitionScannerFactory;
import ua.ivan909020.scheduler.core.service.worker.scanner.PartitionScannerFactoryDefault;
import ua.ivan909020.scheduler.core.service.worker.scanner.handler.QueueMessageHandler;
import ua.ivan909020.scheduler.core.service.worker.scanner.handler.QueueMessageHandlerDefault;

@Configuration
public class WorkerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JsonConverter jsonConverter(ObjectMapper objectMapper) {
        return new JsonConverter(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public PartitionPolicy partitionPolicyDiscovery(
            SchedulerProperties schedulerProperties,
            InstanceRegistry instanceRegistry) {

        return new PartitionPolicyDiscovery(schedulerProperties, instanceRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public PartitionScannerFactory partitionScannerFactory(
            TaskRepository taskRepository,
            QueueProducer queueProducer,
            JsonConverter jsonConverter) {

        return new PartitionScannerFactoryDefault(taskRepository, queueProducer, jsonConverter);
    }

    @Bean
    @ConditionalOnMissingBean
    public QueueMessageHandler queueMessageHandler(
            TaskRepository taskRepository,
            QueueConsumer queueConsumer,
            JsonConverter jsonConverter,
            TaskHandlerRegistry taskHandlerRegistry) {

        return new QueueMessageHandlerDefault(taskRepository, queueConsumer, jsonConverter, taskHandlerRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public WorkerServiceDefault workerServiceDefault(
            PartitionPolicy partitionPolicy,
            InstanceRegistry instanceRegistry,
            PartitionScannerFactory partitionScannerFactory,
            QueueMessageHandler queueMessageHandler) {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        return new WorkerServiceDefault(partitionPolicy, instanceRegistry, 
                partitionScannerFactory, queueMessageHandler, executorService);
    }

}
