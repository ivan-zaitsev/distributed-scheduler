package ua.ivan909020.scheduler.core.service.leader;

import java.util.Set;

import org.springframework.integration.leader.event.AbstractLeaderEvent;

public interface LeaderRegistry {

    boolean isLeader();

    void subscribe(Runnable command, Set<Class<? extends AbstractLeaderEvent>> events);

}
