import org.example.loadbalancer.LoadBalancer;
import org.example.loadbalancer.RandomSelectStrategy;
import org.example.loadbalancer.RoundRobinSelectStrategy;
import org.example.loadbalancer.SelectInstanceStrategy;
import org.example.loadbalancer.exceptions.EmptyInstancesException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetInstanceTest {

    @Test
    public void shouldReturnRandomInstance() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        loadBalancer.register("192.168.0.1");
        assertNotNull(loadBalancer.get());
    }

    @Test
    public void shouldNotGetAnInstanceFromEmptyInstanceList() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        assertThrows(EmptyInstancesException.class, () -> loadBalancer.get());
    }

    @Test
    public void shouldReturnRandomInstanceFromMultipleInstances() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        List<String> instances = new ArrayList<>();
        for (int i=0; i<5; i++) {
            loadBalancer.register("192.168.0." + i);
            instances.add("192.168.0." + i);
        }

        for (int i=0; i<10; i++) {
            assertTrue(instances.contains(loadBalancer.get()));
        }
    }

    @Test
    public void shouldGetAnInstanceWithRoundRobinStrategy() {
        SelectInstanceStrategy strategy = new RoundRobinSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        for (int i=0; i<4; i++) {
            loadBalancer.register("192.168.0." + i);
        }
        assertEquals("192.168.0.0", loadBalancer.get());
        assertEquals("192.168.0.1", loadBalancer.get());
        assertEquals("192.168.0.2", loadBalancer.get());
        assertEquals("192.168.0.3", loadBalancer.get());
    }
}
