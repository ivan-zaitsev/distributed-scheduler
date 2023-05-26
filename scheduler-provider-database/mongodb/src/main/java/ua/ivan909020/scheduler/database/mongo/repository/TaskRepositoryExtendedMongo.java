package ua.ivan909020.scheduler.database.mongo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ua.ivan909020.scheduler.core.model.domain.page.KeysetPage;
import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepositoryExtended;

public class TaskRepositoryExtendedMongo extends TaskRepositoryMongo implements TaskRepositoryExtended {

    public TaskRepositoryExtendedMongo(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public PagedList<Task> findAll(List<Integer> partitions, List<TaskStatus> statuses, KeysetPage page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("partition").in(partitions));
        query.addCriteria(Criteria.where("status").in(statuses));
        if (page.cursor() != null) {
            query.addCriteria(Criteria.where("_id").gt(page.cursor()));
        }
        query.limit(page.size());
        query.with(Sort.by(Sort.Order.asc("executeAt")));

        List<Task> result = mongoTemplate.find(query, Task.class);

        String nextCursor = result.size() > 1 ? result.get(result.size() - 1).getId() : null;
        return new PagedList<>(result, nextCursor);
    }

}
