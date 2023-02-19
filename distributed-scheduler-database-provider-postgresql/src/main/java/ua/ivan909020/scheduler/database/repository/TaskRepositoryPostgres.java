package ua.ivan909020.scheduler.database.repository;

import java.sql.Types;
import java.time.Instant;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.uuid.Generators;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepository;

public class TaskRepositoryPostgres implements TaskRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public TaskRepositoryPostgres(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(Task task) {
        String sql = """
                INSERT INTO task(
                    id,
                    partition,
                    version, name,
                    status,
                    data,
                    created_at,
                    execute_at,
                    processing_started_at,
                    processing_retries_count
                )
                VALUES (
                    :id,
                    :partition,
                    :version,
                    :name
                    :status,
                    :data,
                    :createdAt,
                    :executeAt,
                    :processingStartedAt,
                    :processingRetriesCount
                )
                """;

        task.setId(Generators.timeBasedEpochGenerator().generate());

        int insertedCount = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(task));
        return insertedCount == 1;
    }

    @Override
    public boolean update(Task task) {
        String sql = """
                UPDATE task SET
                    partition = :partition,
                    version = :version + 1,
                    name = :name
                    status = :status,
                    data = :data,
                    createdAt = :createdAt,
                    executeAt = :executeAt,
                    processingStartedAt = :processingStartedAt,
                    processingRetriesCount = :processingRetriesCount
                WHERE
                    id = :id AND
                    version = :version
                """;

        int updatedCount = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(task));
        return updatedCount == 1;
    }

    @Override
    public List<Task> findAllOverdue(List<TaskStatus> statuses, Instant timestamp, int limit) {
        String sql = """
                SELECT * FROM task
                WHERE execute_at <= :timestamp AND
                      status IN (:statuses)
                ORDER BY execute_at
                LIMIT :limit
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("timestamp", timestamp);
        parameters.addValue("statuses", statuses.stream().map(TaskStatus::name).toList(), Types.OTHER);
        parameters.addValue("limit", limit);

        return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<Task>());
    }

}
