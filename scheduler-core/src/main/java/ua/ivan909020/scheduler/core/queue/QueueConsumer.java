package ua.ivan909020.scheduler.core.queue;

import java.util.function.Consumer;

import ua.ivan909020.scheduler.core.model.domain.queue.QueueMessage;

public interface QueueConsumer {

    void start();

    void stop();

    void subscribe(Consumer<QueueMessage> handler);

}
