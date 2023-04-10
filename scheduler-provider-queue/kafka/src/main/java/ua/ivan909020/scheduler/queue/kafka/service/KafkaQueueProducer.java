package ua.ivan909020.scheduler.queue.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;
import ua.ivan909020.scheduler.core.queue.QueueProducer;
import ua.ivan909020.scheduler.queue.kafka.configuration.properties.KafkaQueueProperties;

public class KafkaQueueProducer implements QueueProducer {

    private final KafkaQueueProperties kafkaQueueProperties;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaQueueProducer(
            KafkaQueueProperties kafkaQueueProperties,
            ProducerFactory<String, String> kafkaProducerFactory) {

        this.kafkaQueueProperties = kafkaQueueProperties;
        this.kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
    }

    @Override
    public void send(QueueMessage message) {
        kafkaTemplate.send(kafkaQueueProperties.getQueueTopic(), message.getKey(), message.getValue());
    }

}
