package ua.ivan909020.scheduler.core.service.discovery;

import java.util.Set;

import org.springframework.integration.leader.event.AbstractLeaderEvent;

public interface LeaderRegistry {

    String getLeaderInstanceId();

    boolean isLeader();

    void subscribe(Runnable command, Set<Class<? extends AbstractLeaderEvent>> events);

}
