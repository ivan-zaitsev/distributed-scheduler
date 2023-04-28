package ua.ivan909020.scheduler.database.mongo.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.database.mongo.repository.TaskRepositoryExtendedMongo;

@Configuration
public class MongoAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskRepository taskRepository(MongoTemplate mongoTemplate) {
        return new TaskRepositoryExtendedMongo(mongoTemplate);
    }

}
