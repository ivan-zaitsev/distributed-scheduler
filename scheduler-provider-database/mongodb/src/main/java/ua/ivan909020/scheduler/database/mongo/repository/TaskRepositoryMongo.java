package ua.ivan909020.scheduler.database.mongo.repository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.core.repository.TaskRepository;
import ua.ivan909020.scheduler.database.mongo.model.entity.TaskLock;

public class TaskRepositoryMongo implements TaskRepository {

    protected MongoTemplate mongoTemplate;

    protected TaskLockRepository taskLockRepository;

    public TaskRepositoryMongo(MongoTemplate mongoTemplate, TaskLockRepository taskLockRepository) {
        this.mongoTemplate = mongoTemplate;
        this.taskLockRepository = taskLockRepository;
    }

    @Override
    public Task find(Integer partition, String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("partition").in(partition));
        query.addCriteria(Criteria.where("_id").in(id));

        return mongoTemplate.findOne(query, Task.class);
    }

    @Override
    public void create(Task task) {
        Runnable command = () -> {
            if (find(task.getPartition(), task.getId()) != null) {
                throw new IllegalStateException("Task with id " + task.getId() + " already exists");
            }

            mongoTemplate.insert(task);
        };

        TaskLock taskLock = new TaskLock()
                .setPartition(task.getPartition())
                .setTaskId(task.getId())
                .setLockAtMost(Instant.now().plus(Duration.ofSeconds(10)));

        boolean result = taskLockRepository.executeWithLock(command, taskLock);
        if (!result) {
            throw new IllegalStateException("Task with id " + task.getId() + " is locked");
        }
    }

    @Override
    public void updateStatus(Task task) {
        Query query = new Query();
        query.addCriteria(Criteria.where("partition").is(task.getPartition()));
        query.addCriteria(Criteria.where("_id").is(task.getId()));
        query.addCriteria(Criteria.where("version").is(task.getVersion()));

        Update update = new Update();
        update.set("version", task.getVersion() + 1);
        update.set("status", task.getStatus());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Task.class);

        if (result.getModifiedCount() != 1) {
            throw new IllegalStateException("Task with id " + task.getId() + " was not updated");
        }

        task.setVersion(task.getVersion() + 1);
    }

    @Override
    public List<Task> findAllOverdue(
            List<Integer> partitions, List<TaskStatus> statuses, Instant timestamp, int limit) {

        Query query = new Query();
        query.addCriteria(Criteria.where("partition").in(partitions));
        query.addCriteria(Criteria.where("status").in(statuses));
        query.addCriteria(Criteria.where("executeAt").lte(timestamp));
        query.limit(limit);

        return mongoTemplate.find(query, Task.class);
    }

}
