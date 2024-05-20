package org.example.loadbalancer;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinSelectStrategy implements SelectInstanceStrategy {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public String get(Map<String, String> instances) {
        List<String> addresses = instances.keySet().stream().sorted().toList(); // O(n log n)
        int index = counter.getAndIncrement() % addresses.size();

        return addresses.get(index);
    }
}
