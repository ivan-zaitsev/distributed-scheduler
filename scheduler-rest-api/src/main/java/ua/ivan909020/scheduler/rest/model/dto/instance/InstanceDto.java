package ua.ivan909020.scheduler.rest.model.dto.instance;

import java.net.URI;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ua.ivan909020.scheduler.core.model.domain.instance.InstanceStatus;

@JsonInclude(Include.NON_NULL)
public class InstanceDto {

    private String id;

    private URI uri;

    private Instant registeredAt;

    private InstanceStatus status;

    private Boolean leader;

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

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

}
