package ua.ivan909020.scheduler.testdata.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String DOCKER_IMAGE_NAME = "confluentinc/cp-kafka:7.3.0";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        KafkaContainer container = new KafkaContainer(DockerImageName.parse(DOCKER_IMAGE_NAME));
        container.start();

        TestPropertyValues values = TestPropertyValues.of(
                "spring.kafka.bootstrap-servers=" + container.getBootstrapServers(),
                "spring.kafka.consumer.group-id=${scheduler.group-id}",
                "spring.kafka.consumer.auto-offset-reset=earliest");

        values.applyTo(applicationContext);
    }

}
