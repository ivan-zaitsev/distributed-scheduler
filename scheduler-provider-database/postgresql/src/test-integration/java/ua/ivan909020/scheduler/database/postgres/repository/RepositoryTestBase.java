package ua.ivan909020.scheduler.database.postgres.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import ua.ivan909020.scheduler.database.postgres.configuration.PostgresDatabaseAutoConfiguration;
import ua.ivan909020.scheduler.testdata.TestApplication;
import ua.ivan909020.scheduler.testdata.initializer.PostgresContainerInitializer;

@JdbcTest
@Import(PostgresDatabaseAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class, initializers = PostgresContainerInitializer.class)
public class RepositoryTestBase {

}
