package ua.ivan909020.scheduler.database.postgres.repository;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepositoryExtended;

public class TaskRepositoryExtendedPostgres extends TaskRepositoryPostgres implements TaskRepositoryExtended {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public TaskRepositoryExtendedPostgres(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PagedList<Task> findAll(List<Integer> partitions, List<TaskStatus> statuses, KeysetPage page) {
        String sql = """
                SELECT 
                  partition, id, version, status, execute_at AS executeAt, name, data
                FROM task
                WHERE
                  partition IN (:partitions) AND
                  status IN (:statuses)
                ORDER BY execute_at ASC
                LIMIT :limit
                """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("partitions", partitions);
        parameters.addValue("statuses", statuses.stream().map(TaskStatus::name).toList(), Types.OTHER);
        parameters.addValue("limit", page.size());

        List<Task> result = jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<>(Task.class));

        String nextCursor = result.size() > 1 ? result.get(result.size() - 1).getId() : null;
        return new PagedList<>(result, nextCursor);
    }

}
