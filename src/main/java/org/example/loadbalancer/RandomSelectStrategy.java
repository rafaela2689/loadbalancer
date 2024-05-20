package org.example.loadbalancer;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomSelectStrategy implements SelectInstanceStrategy {
    @Override
    public String get(Map<String, String> instances) {
        List<String> addresses = instances.keySet().stream().toList(); // O(n)
        int index = new Random().nextInt(addresses.size());
        return addresses.get(index);
    }
}
