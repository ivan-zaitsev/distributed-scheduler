package ua.ivan909020.scheduler.leader.service;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.zookeeper.leader.LeaderInitiator;

import ua.ivan909020.scheduler.core.service.leader.LeaderRegistry;

public class LeaderRegistryZookeeper implements LeaderRegistry {

    private final LeaderInitiator leaderInitiator;

    private final LeaderEventPublisherZookeeper leaderEventPublisherZookeeper;

    public LeaderRegistryZookeeper(
            LeaderInitiator leaderInitiator,
            LeaderEventPublisherZookeeper leaderEventPublisherZookeeper) {

        this.leaderInitiator = leaderInitiator;
        this.leaderEventPublisherZookeeper = leaderEventPublisherZookeeper;
    }

    //TODO: Change to leaderInitiator.getContext().getLeader().getId();
    @Override
    public String getLeaderInstanceId() {
        try {
            Field field = LeaderInitiator.class.getDeclaredField("leaderSelector");
            field.trySetAccessible();
            return ((LeaderSelector) field.get(leaderInitiator)).getLeader().getId();
        } catch (Exception e) {
            return null;
        }
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
