package ua.ivan909020.scheduler.queue.kafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

import ua.ivan909020.scheduler.queue.kafka.configuration.properties.KafkaQueueProperties;
import ua.ivan909020.scheduler.queue.kafka.service.KafkaQueueConsumer;
import ua.ivan909020.scheduler.queue.kafka.service.KafkaQueueProducer;

@Configuration
@EnableConfigurationProperties
public class KafkaQueueAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KafkaQueueProperties kafkaQueueProperties() {
        return new KafkaQueueProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaQueueProducer kafkaQueueProducer(
            KafkaQueueProperties kafkaQueueProperties,
            ProducerFactory<String, String> kafkaProducerFactory) {

        return new KafkaQueueProducer(kafkaQueueProperties, kafkaProducerFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaQueueConsumer kafkaQueueConsumer(
            KafkaQueueProperties kafkaQueueProperties,
            ConsumerFactory<String, String> kafkaConsumerFactory) {

        return new KafkaQueueConsumer(kafkaQueueProperties, kafkaConsumerFactory);
    }

    @Bean
    public DefaultKafkaProducerFactoryCustomizer kafkaProducerCustomizer() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100_000);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4");

        return producerFactory -> producerFactory.updateConfigs(properties);
    }

}
