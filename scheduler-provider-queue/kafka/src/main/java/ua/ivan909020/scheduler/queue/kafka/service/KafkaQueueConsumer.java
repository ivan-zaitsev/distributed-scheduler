package ua.ivan909020.scheduler.queue.kafka.service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;
import ua.ivan909020.scheduler.core.queue.QueueConsumer;
import ua.ivan909020.scheduler.queue.kafka.configuration.properties.KafkaQueueProperties;

public class KafkaQueueConsumer implements QueueConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final KafkaQueueProperties kafkaQueueProperties;

    private final ConsumerFactory<String, String> kafkaConsumerFactory;

    private KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer;

    private Set<Consumer<QueueMessage>> subsribers;

    public KafkaQueueConsumer(
            KafkaQueueProperties kafkaQueueProperties,
            ConsumerFactory<String, String> kafkaConsumerFactory) {

        this.kafkaQueueProperties = kafkaQueueProperties;
        this.kafkaConsumerFactory = kafkaConsumerFactory;
        this.subsribers = new HashSet<>();
    }

    @Override
    public void start() {
        if (kafkaMessageListenerContainer != null) {
            return;
        }
        kafkaMessageListenerContainer = createMessageListenerContainer(kafkaQueueProperties, kafkaConsumerFactory);
        kafkaMessageListenerContainer.start();
    }

    @Override
    public void stop() {
        if (kafkaMessageListenerContainer == null) {
            return;
        }
        kafkaMessageListenerContainer.stop();
    }

    @Override
    public void subscribe(Consumer<QueueMessage> handler) {
        subsribers.add(handler);
    }

    private KafkaMessageListenerContainer<String, String> createMessageListenerContainer(
            KafkaQueueProperties kafkaQueueProperties, ConsumerFactory<String, String> kafkaConsumerFactory) {

        ContainerProperties containerProperties = new ContainerProperties(kafkaQueueProperties.getQueueTopic());
        containerProperties.setMessageListener(new MessageListener<String, String>() {

            @Override
            public void onMessage(ConsumerRecord<String, String> data) {
                for (Consumer<QueueMessage> subscriber : subsribers) {
                    try {
                        QueueMessage message = new QueueMessage();
                        message.setKey(data.key());
                        message.setValue(data.value());

                        subscriber.accept(message);
                    } catch (Exception e) {
                        logger.warn("Failed to consume a message: {}", data, e);
                    }
                }
            }

        });
        return new KafkaMessageListenerContainer<>(kafkaConsumerFactory, containerProperties);
    }

}
