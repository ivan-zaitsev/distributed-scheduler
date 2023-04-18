package ua.ivan909020.scheduler.core.service.core;

import java.util.List;

public interface PartitionService {

    List<Integer> getAll();

    int generate();

}
