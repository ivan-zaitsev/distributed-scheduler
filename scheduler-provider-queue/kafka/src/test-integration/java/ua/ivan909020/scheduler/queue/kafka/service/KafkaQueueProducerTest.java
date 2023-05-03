package ua.ivan909020.scheduler.queue.kafka.service;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;
import ua.ivan909020.scheduler.queue.kafka.configuration.properties.KafkaQueueProperties;

class KafkaQueueProducerTest extends ServiceTestBase {

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Autowired
    private KafkaQueueProperties kafkaQueueProperties;

    @Autowired
    private KafkaQueueProducer kafkaQueueProducer;

    @Test
    void send_shouldConsumeSentMessage() {
        QueueMessage message = new QueueMessage();
        message.setKey("key");
        message.setValue("value");

        Consumer<String, String> consumer = consumerFactory.createConsumer();
        consumer.subscribe(List.of(kafkaQueueProperties.getQueueTopic()));

        kafkaQueueProducer.send(message);

        AtomicReference<ConsumerRecord<String, String>> consumerRecord = new AtomicReference<>();

        await().atMost(Duration.ofSeconds(10)).until(() -> {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            if (consumerRecords.isEmpty()) {
                return false;
            }
            consumerRecord.set(consumerRecords.iterator().next());
            return true;
        });

        assertEquals(message.getKey(), consumerRecord.get().key());
        assertEquals(message.getValue(), consumerRecord.get().value());
    }

}
