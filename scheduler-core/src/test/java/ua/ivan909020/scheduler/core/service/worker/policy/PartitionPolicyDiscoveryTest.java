package ua.ivan909020.scheduler.core.service.worker.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;

import ua.ivan909020.scheduler.core.configuration.properties.SchedulerProperties;
import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

@ExtendWith(MockitoExtension.class)
class PartitionPolicyDiscoveryTest {

    @Mock
    private SchedulerProperties schedulerProperties;

    @Mock
    private InstanceRegistry instanceRegistry;

    @InjectMocks
    private PartitionPolicyDiscovery partitionPolicyDiscovery;

    @ParameterizedTest
    @MethodSource("computePartitions_shouldReturnPatritions_arguments")
    void computePartitions_shouldReturnPatritions(
            Instance currentInstance,
            List<Instance> allInstances,
            int maxPartitions,
            List<Integer> expectedPartitions) {

        doReturn(currentInstance).when(instanceRegistry).getCurrentInstance();
        doReturn(allInstances).when(instanceRegistry).getAllInstances();

        if (!allInstances.isEmpty()) {
            doReturn(maxPartitions).when(schedulerProperties).getMaxPartitions();
        }

        List<Integer> actualPartitions = partitionPolicyDiscovery.computePartitions();

        assertEquals(expectedPartitions, actualPartitions);
    }

    private static Stream<Arguments> computePartitions_shouldReturnPatritions_arguments() {
        Stream<Stream<Arguments>> streams = Stream.of(
                argumets_1(),
                argumets_2(),
                argumets_3(),
                argumets_4(),
                argumets_5(),
                argumets_6());

        return streams.flatMap(Function.identity());
    }

    private static Stream<Arguments> argumets_1() {
        List<ServiceInstance> allInstances = new ArrayList<>();

        int maxPartitions = 2;

        return Stream.of(
                Arguments.of(buildInstance("1"), allInstances, maxPartitions, List.of()));
    }

    private static Stream<Arguments> argumets_2() {
        List<Instance> allInstances = new ArrayList<>();
        allInstances.add(buildInstance("1"));

        int maxPartitions = 4;

        return Stream.of(
                Arguments.of(allInstances.get(0), allInstances, maxPartitions, List.of(1, 2, 3, 4)));
    }

    private static Stream<Arguments> argumets_3() {
        List<Instance> allInstances = new ArrayList<>();
        allInstances.add(buildInstance("1"));
        allInstances.add(buildInstance("2"));
        allInstances.add(buildInstance("3"));

        int maxPartitions = 2;

        return Stream.of(
                Arguments.of(allInstances.get(0), allInstances, maxPartitions, List.of(1)),
                Arguments.of(allInstances.get(1), allInstances, maxPartitions, List.of(2)),
                Arguments.of(allInstances.get(2), allInstances, maxPartitions, List.of()));
    }

    private static Stream<Arguments> argumets_4() {
        List<Instance> allInstances = new ArrayList<>();
        allInstances.add(buildInstance("1"));
        allInstances.add(buildInstance("2"));
        allInstances.add(buildInstance("3"));
        allInstances.add(buildInstance("4"));
        allInstances.add(buildInstance("5"));

        int maxPartitions = 5;

        return Stream.of(
                Arguments.of(allInstances.get(0), allInstances, maxPartitions, List.of(1)),
                Arguments.of(allInstances.get(1), allInstances, maxPartitions, List.of(2)),
                Arguments.of(allInstances.get(2), allInstances, maxPartitions, List.of(3)),
                Arguments.of(allInstances.get(3), allInstances, maxPartitions, List.of(4)),
                Arguments.of(allInstances.get(4), allInstances, maxPartitions, List.of(5)));
    }

    private static Stream<Arguments> argumets_5() {
        List<Instance> allInstances = new ArrayList<>();
        allInstances.add(buildInstance("1"));
        allInstances.add(buildInstance("2"));
        allInstances.add(buildInstance("3"));
        allInstances.add(buildInstance("4"));

        int maxPartitions = 10;

        return Stream.of(
                Arguments.of(allInstances.get(0), allInstances, maxPartitions, List.of(1, 2, 3)),
                Arguments.of(allInstances.get(1), allInstances, maxPartitions, List.of(4, 5, 6)),
                Arguments.of(allInstances.get(2), allInstances, maxPartitions, List.of(7, 8)),
                Arguments.of(allInstances.get(3), allInstances, maxPartitions, List.of(9, 10)));
    }

    private static Stream<Arguments> argumets_6() {
        List<Instance> allInstances = new ArrayList<>();
        allInstances.add(buildInstance("1"));
        allInstances.add(buildInstance("2"));
        allInstances.add(buildInstance("3"));
        allInstances.add(buildInstance("4"));
        allInstances.add(buildInstance("5"));

        int maxPartitions = 10;

        return Stream.of(
                Arguments.of(allInstances.get(0), allInstances, maxPartitions, List.of(1, 2)),
                Arguments.of(allInstances.get(1), allInstances, maxPartitions, List.of(3, 4)),
                Arguments.of(allInstances.get(2), allInstances, maxPartitions, List.of(5, 6)),
                Arguments.of(allInstances.get(3), allInstances, maxPartitions, List.of(7, 8)),
                Arguments.of(allInstances.get(4), allInstances, maxPartitions, List.of(9, 10)));
    }

    private static Instance buildInstance(String instanceId) {
        Instance instance = new Instance();
        instance.setServiceInstance(new DefaultServiceInstance(instanceId, null, null, 0, false));
        instance.setRegisteredAt(Instant.now());
        return instance;
    }

}
