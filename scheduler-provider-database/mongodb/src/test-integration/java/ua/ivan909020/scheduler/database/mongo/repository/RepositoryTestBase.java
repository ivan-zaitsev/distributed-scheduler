package ua.ivan909020.scheduler.database.mongo.repository;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import ua.ivan909020.scheduler.database.mongo.configuration.MongoDatabaseAutoConfiguration;
import ua.ivan909020.scheduler.testdata.TestApplication;
import ua.ivan909020.scheduler.testdata.initializer.MongoContainerInitializer;

@DataMongoTest
@Import(MongoDatabaseAutoConfiguration.class)
@ContextConfiguration(classes = TestApplication.class, initializers = MongoContainerInitializer.class)
public class RepositoryTestBase {

}
