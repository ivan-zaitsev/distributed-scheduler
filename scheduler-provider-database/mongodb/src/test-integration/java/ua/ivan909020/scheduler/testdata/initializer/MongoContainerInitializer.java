package ua.ivan909020.scheduler.testdata.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String DOCKER_IMAGE_NAME = "mongo:6.0.5";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        MongoDBContainer container = new MongoDBContainer(DockerImageName.parse(DOCKER_IMAGE_NAME));
        container.start();

        TestPropertyValues values = TestPropertyValues.of(
                "spring.data.mongodb.uri=" + container.getReplicaSetUrl());

        values.applyTo(applicationContext);
    }

}
