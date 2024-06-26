package ua.ivan909020.scheduler.database.postgres.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.database.postgres.repository.TaskRepositoryExtendedPostgres;

@Configuration
public class PostgresDatabaseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TaskRepository taskRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new TaskRepositoryExtendedPostgres(jdbcTemplate);
    }

}
