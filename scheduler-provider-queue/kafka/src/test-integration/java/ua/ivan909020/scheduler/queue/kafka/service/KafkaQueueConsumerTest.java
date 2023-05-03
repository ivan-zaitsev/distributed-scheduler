package ua.ivan909020.scheduler.queue.kafka.service;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ProducerFactory;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;
import ua.ivan909020.scheduler.queue.kafka.configuration.properties.KafkaQueueProperties;

class KafkaQueueConsumerTest extends ServiceTestBase {

    @Autowired
    private ProducerFactory<String, String> producerFactory;

    @Autowired
    private KafkaQueueProperties kafkaQueueProperties;

    @Autowired
    private KafkaQueueConsumer kafkaQueueConsumer;

    @BeforeEach
    public void setup() {
        kafkaQueueConsumer.start();
    }

    @AfterEach
    public void teardown() {
        kafkaQueueConsumer.stop();
    }

    @Test
    void subscribe_shouldConsumeSentMessage() {
        AtomicReference<QueueMessage> queueMessage = new AtomicReference<>();
        kafkaQueueConsumer.subscribe(queueMessage::set);

        QueueMessage message = new QueueMessage();
        message.setKey("key");
        message.setValue("value");

        Producer<String, String> producer = producerFactory.createProducer();
        producer.send(new ProducerRecord<>(kafkaQueueProperties.getQueueTopic(), message.getKey(), message.getValue()));

        await().atMost(Duration.ofSeconds(10)).until(() -> queueMessage.get() != null);

        assertEquals(message, queueMessage.get());
    }

}
