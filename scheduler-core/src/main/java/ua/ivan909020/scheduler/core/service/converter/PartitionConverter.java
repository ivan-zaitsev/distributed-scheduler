package ua.ivan909020.scheduler.core.service.converter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartitionConverter {

    public static String toString(List<Integer> partitions) {
        return partitions.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public static List<Integer> toList(String partitions) {
        return Arrays.stream(partitions.split(",")).mapToInt(Integer::parseInt).boxed().toList();
    }

}
