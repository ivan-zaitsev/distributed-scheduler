package ua.ivan909020.scheduler.database.mongo.repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import ua.ivan909020.scheduler.database.mongo.model.entity.TaskLock;

public class TaskLockRepositoryMongo implements TaskLockRepository {

    private MongoTemplate mongoTemplate;

    public TaskLockRepositoryMongo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean acquire(TaskLock taskLock) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(taskLock.getTaskId()));
        query.addCriteria(Criteria.where("lockUntil").lte(taskLock.getLockAtMost()));

        Update update = new Update();
        update.set("_id", taskLock.getTaskId());
        update.set("lockUntil", taskLock.getLockAtMost());

        UpdateResult result = mongoTemplate.upsert(query, update, TaskLock.class);
        return result.getUpsertedId() != null || result.getModifiedCount() == 1;
    }

    @Override
    public boolean release(TaskLock taskLock) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(taskLock.getTaskId()));
        query.addCriteria(Criteria.where("lockUntil").gte(taskLock.getLockAtMost()));

        Update update = new Update();
        update.set("_id", taskLock.getTaskId());
        update.set("lockUntil", taskLock.getLockAtMost());

        UpdateResult result = mongoTemplate.updateFirst(query, update, TaskLock.class);
        return result.getModifiedCount() == 1;
    }

}
