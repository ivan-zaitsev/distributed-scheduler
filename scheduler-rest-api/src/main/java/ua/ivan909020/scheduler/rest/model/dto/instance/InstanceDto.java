package ua.ivan909020.scheduler.rest.model.dto.instance;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;

@JsonInclude(Include.NON_NULL)
public class InstanceDto {

    private String id;

    private URI uri;

    private Instant registeredAt;

    private Instant updatedAt;

    private InstanceStatus status;

    private List<Integer> partitions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Instant registeredAt) {
        this.registeredAt = registeredAt;
    }

    public InstanceStatus getStatus() {
        return status;
    }

    public void setStatus(InstanceStatus status) {
        this.status = status;
    }

    public List<Integer> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Integer> partitions) {
        this.partitions = partitions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, partitions, registeredAt, status, updatedAt, uri);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InstanceDto other = (InstanceDto) obj;
        return Objects.equals(id, other.id) && 
                Objects.equals(partitions, other.partitions) && 
                Objects.equals(registeredAt, other.registeredAt) && 
                status == other.status && 
                Objects.equals(updatedAt, other.updatedAt) && 
                Objects.equals(uri, other.uri);
    }

    @Override
    public String toString() {
        return "InstanceDto [" + 
                "id=" + id + 
                ", uri=" + uri + 
                ", registeredAt=" + registeredAt + 
                ", updatedAt=" + updatedAt + 
                ", status=" + status + 
                ", partitions=" + partitions + "]";
    }

}
