import org.example.loadbalancer.LoadBalancer;
import org.example.loadbalancer.RandomSelectStrategy;
import org.example.loadbalancer.SelectInstanceStrategy;
import org.example.loadbalancer.exceptions.DuplicatedInstanceException;
import org.example.loadbalancer.exceptions.MaxInstancesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegisterInstanceTest {

    @Test
    public void shouldNotAcceptNullInstance() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        String instance = null;
        assertThrows(IllegalArgumentException.class, () -> loadBalancer.register(instance));
    }

    @Test
    public void shouldNotAcceptEmptyIntance() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        String instance = "";
        assertThrows(IllegalArgumentException.class, () -> loadBalancer.register(instance));
    }

    @Test
    public void shouldNotAcceptMoreThan10Instances() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        assertThrows(MaxInstancesException.class, () -> {
            for (int i=0; i<11; i++) {
                String instance = "192.168.0." + i;
                loadBalancer.register(instance);
            }
        });
    }

    @Test
    public void shouldNotAcceptDuplicatedInstances() {
        SelectInstanceStrategy strategy = new RandomSelectStrategy();
        LoadBalancer loadBalancer = new LoadBalancer(strategy);
        String instance = "192.168.0.1";
        loadBalancer.register(instance);
        assertThrows(DuplicatedInstanceException.class, () -> loadBalancer.register(instance));
    }
}
