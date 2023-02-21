package ua.ivan909020.scheduler.leader.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.AbstractLeaderEvent;
import org.springframework.integration.leader.event.LeaderEventPublisher;
import org.springframework.integration.leader.event.OnFailedToAcquireMutexEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;

public class LeaderEventPublisherZookeeper implements LeaderEventPublisher {

    private Map<Class<? extends AbstractLeaderEvent>, Runnable> subscriptions = new LinkedHashMap<>();

    public void subscribe(Runnable command, Set<Class<? extends AbstractLeaderEvent>> events) {
        for (Class<? extends AbstractLeaderEvent> event : events) {
            subscriptions.put(event, command);
        }
    }

    @Override
    public void publishOnGranted(Object source, Context context, String role) {
        Runnable command = subscriptions.get(OnGrantedEvent.class);
        if (command != null) {
            command.run();
        }
    }

    @Override
    public void publishOnRevoked(Object source, Context context, String role) {
        Runnable command = subscriptions.get(OnRevokedEvent.class);
        if (command != null) {
            command.run();
        }
    }

    @Override
    public void publishOnFailedToAcquire(Object source, Context context, String role) {
        Runnable command = subscriptions.get(OnFailedToAcquireMutexEvent.class);
        if (command != null) {
            command.run();
        }
    }

}
