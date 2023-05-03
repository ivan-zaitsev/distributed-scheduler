package ua.ivan909020.scheduler.core.model.entity;

import java.time.Instant;
import java.util.Objects;

public class Task {

    private Integer partition;

    private String id;

    private Long version;

    private TaskStatus status;

    private Instant executeAt;

    private String name;

    private String data;

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Instant executeAt) {
        this.executeAt = executeAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, executeAt, id, name, partition, status, version);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        return Objects.equals(data, other.data) && 
                Objects.equals(executeAt, other.executeAt) && 
                Objects.equals(id, other.id) && 
                Objects.equals(name, other.name) && 
                Objects.equals(partition, other.partition) && 
                status == other.status && 
                Objects.equals(version, other.version);
    }

    @Override
    public String toString() {
        return "Task [" + 
                "partition=" + partition +
                ", id=" + id +
                ", version=" + version +
                ", status=" + status +
                ", executeAt=" + executeAt +
                ", name=" + name +
                ", data=" + data + "]";
    }

}
