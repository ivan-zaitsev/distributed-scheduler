package ua.ivan909020.scheduler.core.service.worker.scanner;

import java.util.List;

public interface PartitionScanner {

    List<Integer> getPartitions();

    void start();

    void stop();

}
