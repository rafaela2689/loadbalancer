package org.example.loadbalancer;

import java.util.Map;

public interface SelectInstanceStrategy {

    String get(Map<String, String> instances);
}
