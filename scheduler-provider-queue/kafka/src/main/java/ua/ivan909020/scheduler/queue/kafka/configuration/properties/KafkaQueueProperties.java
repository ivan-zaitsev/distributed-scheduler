package ua.ivan909020.scheduler.queue.kafka.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("scheduler")
public class KafkaQueueProperties {

    private String queueTopic;

    public String getQueueTopic() {
        return queueTopic;
    }

    public void setQueueTopic(String queueTopic) {
        this.queueTopic = queueTopic;
    }

}
