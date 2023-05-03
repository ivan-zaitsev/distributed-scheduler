package ua.ivan909020.scheduler.queue.kafka.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import ua.ivan909020.scheduler.testdata.TestApplication;
import ua.ivan909020.scheduler.testdata.initializer.KafkaContainerInitializer;

@SpringBootTest
@ContextConfiguration(classes = TestApplication.class, initializers = KafkaContainerInitializer.class)
public class ServiceTestBase {

}
