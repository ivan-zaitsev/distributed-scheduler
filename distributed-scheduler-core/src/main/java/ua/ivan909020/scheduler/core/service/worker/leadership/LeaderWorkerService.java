package ua.ivan909020.scheduler.core.service.worker.leadership;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;

import ua.ivan909020.scheduler.core.model.domain.SchedulerMode;
import ua.ivan909020.scheduler.core.service.leader.LeaderRegistry;
import ua.ivan909020.scheduler.core.service.worker.WorkerService;

public class LeaderWorkerService implements WorkerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final LeaderRegistry leaderRegistry;

    public LeaderWorkerService(LeaderRegistry leaderRegistry) {
        this.leaderRegistry = leaderRegistry;
    }

    @Override
    public SchedulerMode getMode() {
        return SchedulerMode.LEADERSHIP;
    }

    @Override
    public void start() {
        logger.info("Starting LeaderWorkerService");

        leaderRegistry.subscribe(this::reelectLeader, Set.of(OnGrantedEvent.class, OnRevokedEvent.class));
    }

    @Override
    public void stop() {
        logger.info("Stopping LeaderWorkerService");
    }

    private void reelectLeader() {
        logger.info("Starting leader reelection");

        boolean isLeader = leaderRegistry.isLeader();

        System.out.println("isLeader=" + isLeader);
    }

}
