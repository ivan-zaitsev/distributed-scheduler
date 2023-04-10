package ua.ivan909020.scheduler.core.queue;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;

public interface QueueProducer {

    void send(QueueMessage message);

}
