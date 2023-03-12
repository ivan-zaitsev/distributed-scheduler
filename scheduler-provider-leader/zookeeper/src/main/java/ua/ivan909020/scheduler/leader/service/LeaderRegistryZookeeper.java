package ua.ivan909020.scheduler.leader.service;

import java.util.Set;

import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;

import ua.ivan909020.scheduler.core.service.discovery.LeaderRegistry;

public class LeaderRegistryZookeeper implements LeaderRegistry {

    private final LeaderInitiator leaderInitiator;

    private final LeaderEventPublisherZookeeper leaderEventPublisherZookeeper;

    public LeaderRegistryZookeeper(
            LeaderInitiator leaderInitiator,
            LeaderEventPublisherZookeeper leaderEventPublisherZookeeper) {

        this.leaderInitiator = leaderInitiator;
        this.leaderEventPublisherZookeeper = leaderEventPublisherZookeeper;
    }

    @Override
    public String getLeaderInstanceId() {
        return leaderInitiator.getContext().getLeader().getId();
    }

    @Override
    public boolean isLeader() {
        return leaderInitiator.getContext().isLeader();
    }

    @Override
    public void subscribe(Runnable command, Set<Class<? extends AbstractLeaderEvent>> events) {
        leaderEventPublisherZookeeper.subscribe(command, events);
    }

}
