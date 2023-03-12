package ua.ivan909020.scheduler.rest.model.dto.instance;

import java.net.URI;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;
import ua.ivan909020.scheduler.core.model.domain.instance.InstanceMode;

@JsonInclude(Include.NON_NULL)
public class InstanceDto {

    private String id;

    private URI uri;

    private Instant registeredAt;

    private InstanceStatus status;

    private InstanceMode mode;

    private InstancePartitioningDto partitioning;

    private InstanceLeadershipDto leadership;

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

    public InstanceMode getMode() {
        return mode;
    }

    public void setMode(InstanceMode mode) {
        this.mode = mode;
    }

    public InstancePartitioningDto getPartitioning() {
        return partitioning;
    }

    public void setPartitioning(InstancePartitioningDto partitioning) {
        this.partitioning = partitioning;
    }

    public InstanceLeadershipDto getLeadership() {
        return leadership;
    }

    public void setLeadership(InstanceLeadershipDto leadership) {
        this.leadership = leadership;
    }

}
