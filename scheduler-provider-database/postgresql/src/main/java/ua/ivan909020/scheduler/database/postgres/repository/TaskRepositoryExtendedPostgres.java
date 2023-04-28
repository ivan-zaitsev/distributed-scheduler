package ua.ivan909020.scheduler.database.postgres.repository;

import java.util.List;

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
    }

    @Override
    public PagedList<Task> findAll(List<Integer> partitions, List<TaskStatus> statuses, KeysetPage page) {
        return new PagedList<>(List.of(), null);
    }

}
