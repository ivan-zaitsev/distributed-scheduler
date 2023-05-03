package ua.ivan909020.scheduler.database.mongo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.uuid.Generators;

import ua.ivan909020.scheduler.core.model.entity.Task;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;

class TaskRepositoryMongoTest extends RepositoryTestBase {

    @Autowired
    private TaskRepositoryMongo taskRepositoryMongo;

    @AfterEach
    void teardown() {
        taskRepositoryMongo.mongoTemplate.remove(Task.class).all();
    }
    
    @Test
    void create_shouldThrowException_whenTaskWasNotCreated() {
        Task task = buildTask(generateId(), Instant.now());

        taskRepositoryMongo.create(task);
        Executable executable = () -> taskRepositoryMongo.create(task);

        IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertEquals("Task with id " + task.getId() + " was not inserted", exception.getMessage());
    }

    @Test
    void create_shouldCreateTask() {
        Task task = buildTask(generateId(), Instant.now());

        taskRepositoryMongo.create(task);

        List<Task> tasks = taskRepositoryMongo.findAllOverdue(
                List.of(task.getPartition()), List.of(task.getStatus()), task.getExecuteAt(), 1);

        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    void updateStatus_shouldThrowException_whenTaskWasNotUpdated() {
        Task task = buildTask(generateId(), Instant.now());
        taskRepositoryMongo.create(task);

        task.setStatus(TaskStatus.SUBMITTED);
        task.setVersion(task.getVersion() + 1);
        Executable executable = () -> taskRepositoryMongo.updateStatus(task);

        IllegalStateException exception = assertThrows(IllegalStateException.class, executable);
        assertEquals("Task with id " + task.getId() + " was not updated", exception.getMessage());
    }

    @Test
    void updateStatus_shouldUpdateTask() {
        Task task = buildTask(generateId(), Instant.now());
        taskRepositoryMongo.create(task);

        task.setStatus(TaskStatus.SUBMITTED);
        taskRepositoryMongo.updateStatus(task);

        List<Task> tasks = taskRepositoryMongo.findAllOverdue(
                List.of(task.getPartition()), List.of(task.getStatus()), task.getExecuteAt(), 1);

        assertEquals(1, tasks.size());
        assertEquals(task, tasks.get(0));
    }

    @Test
    void findAllOverdue_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        List<Task> tasks = taskRepositoryMongo.findAllOverdue(
                List.of(1), List.of(TaskStatus.values()), Instant.now(), 50);

        assertEquals(List.of(), tasks);
    }

    @Test
    void findAllOverdue_shouldReturnTasks() {
        Task task_1 = buildTask(generateId(), Instant.now().plus(Duration.ofMinutes(1)));
        taskRepositoryMongo.create(task_1);

        Task task_2 = buildTask(generateId(), Instant.now().minus(Duration.ofSeconds(1)));
        taskRepositoryMongo.create(task_2);

        List<Task> expectedTasks = List.of(task_2);

        List<Task> actualTasks = taskRepositoryMongo.findAllOverdue(
                List.of(1), List.of(TaskStatus.values()), Instant.now(), 50);

        assertEquals(expectedTasks, actualTasks);
    }

    private String generateId() {
        return Generators.timeBasedEpochGenerator().generate().toString();
    }

    private Task buildTask(String id, Instant timestamp) {
        Task task = new Task();
        task.setPartition(1);
        task.setId(id);
        task.setVersion(1L);
        task.setStatus(TaskStatus.SCHEDULED);
        task.setExecuteAt(timestamp.truncatedTo(ChronoUnit.MILLIS));
        task.setName("name");
        task.setData("data");
        return task;
    }

}
