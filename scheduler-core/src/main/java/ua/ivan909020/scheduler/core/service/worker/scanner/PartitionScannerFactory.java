package ua.ivan909020.scheduler.core.service.worker.scanner;

import java.util.List;

public interface PartitionScannerFactory {

    PartitionScanner create(List<Integer> partitions);

}
