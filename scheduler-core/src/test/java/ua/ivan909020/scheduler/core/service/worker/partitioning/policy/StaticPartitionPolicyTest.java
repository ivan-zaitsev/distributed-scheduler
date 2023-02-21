package ua.ivan909020.scheduler.core.service.worker.partitioning.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StaticPartitionPolicyTest {

    @Spy
    private List<Integer> staticPartitions = new ArrayList<>();
    {
        staticPartitions.add(0);
        staticPartitions.add(1);
        staticPartitions.add(2);
    }

    @InjectMocks
    private StaticPartitionPolicy staticPartitionPolicy;

    @Test
    void computePartitions_shouldReturnPatritions() {
        List<Integer> actualPartitions = staticPartitionPolicy.computePartitions();

        assertEquals(staticPartitions, actualPartitions);
    }

}
