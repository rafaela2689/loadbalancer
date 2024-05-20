package org.example.loadbalancer;

import org.example.loadbalancer.exceptions.DuplicatedInstanceException;
import org.example.loadbalancer.exceptions.EmptyInstancesException;
import org.example.loadbalancer.exceptions.MaxInstancesException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalancer {

    private final int MAX_INSTANCES = 10;
    private final Map<String, String> instances = new ConcurrentHashMap<>();

    private final SelectInstanceStrategy selectInstanceStrategy;

    public LoadBalancer(SelectInstanceStrategy selectStrategy) {
        this.selectInstanceStrategy = selectStrategy;
    }

    public void register(String instance) {
        if (instance == null || instance.isBlank()) {
            throw new IllegalArgumentException("Instance can not be null.");
        }

        if (instances.size() >= MAX_INSTANCES) {
            throw new MaxInstancesException();
        }

        if (instances.get(instance) != null) {
            throw new DuplicatedInstanceException();
        }

        instances.put(instance, instance);
    }

    public String get() {
        if (this.instances.isEmpty()) {
            throw new EmptyInstancesException();
        }
        return this.selectInstanceStrategy.get(this.instances);
    }
}
