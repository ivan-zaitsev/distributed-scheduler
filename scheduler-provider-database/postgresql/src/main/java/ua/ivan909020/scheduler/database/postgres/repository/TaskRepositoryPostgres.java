package ua.ivan909020.scheduler.database.postgres.repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepository;

public class TaskRepositoryPostgres implements TaskRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public TaskRepositoryPostgres(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Task task) {
        String sql = """
                INSERT INTO task(
                    partition, id, version, status, execute_at, name, data
                )
                VALUES (
                    :partition, :id, :version, :status, :executeAt, :name, :data
                )
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("partition", task.getPartition());
        parameters.addValue("id", UUID.fromString(task.getId()));
        parameters.addValue("version", task.getVersion());
        parameters.addValue("status", task.getStatus(), Types.OTHER);
        parameters.addValue("executeAt", Timestamp.from(task.getExecuteAt()), Types.TIMESTAMP);
        parameters.addValue("name", task.getName());
        parameters.addValue("data", task.getData());

        try {
            int insertedCount = jdbcTemplate.update(sql, parameters);
            if (insertedCount != 1) {
                throw new IllegalStateException("Task with id " + task.getId() + " was not inserted");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Task with id " + task.getId() + " was not inserted", e);
        }
    }

    @Override
    public void updateStatus(Task task) {
        String sql = """
                UPDATE task SET
                    version = :version + 1,
                    status = :status
                WHERE
                    partition = :partition AND
                    id = :id AND
                    version = :version
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("partition", task.getPartition());
        parameters.addValue("id", UUID.fromString(task.getId()));
        parameters.addValue("version", task.getVersion());
        parameters.addValue("status", task.getStatus(), Types.OTHER);

        int updatedCount = jdbcTemplate.update(sql, parameters);
        if (updatedCount != 1) {
            throw new IllegalStateException("Task with id " + task.getId() + " was not updated");
        }

        task.setVersion(task.getVersion() + 1);
    }

    @Override
    public List<Task> findAllOverdue(
            List<Integer> partitions, List<TaskStatus> statuses, Instant timestamp, int limit) {

        String sql = """
                SELECT 
                  partition, id, version, status, execute_at AS executeAt, name, data
                FROM task
                WHERE
                  partition IN (:partitions) AND
                  status IN (:statuses) AND
                  execute_at <= :executeAt
                ORDER BY execute_at ASC
                LIMIT :limit
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("partitions", partitions);
        parameters.addValue("statuses", statuses.stream().map(TaskStatus::name).toList(), Types.OTHER);
        parameters.addValue("executeAt", Timestamp.from(timestamp), Types.TIMESTAMP);
        parameters.addValue("limit", limit);

        return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(Task.class));
    }

}
