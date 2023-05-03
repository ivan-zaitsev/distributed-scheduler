package ua.ivan909020.scheduler.testdata.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String DOCKER_IMAGE_NAME = "postgres:15.2";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        PostgreSQLContainer container = new PostgreSQLContainer(DockerImageName.parse(DOCKER_IMAGE_NAME));
        container.start();

        TestPropertyValues values = TestPropertyValues.of(
                "spring.datasource.url=" + container.getJdbcUrl(),
                "spring.datasource.username=" + container.getUsername(),
                "spring.datasource.password=" + container.getPassword());

        values.applyTo(applicationContext);
    }

}
