
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DummyTest {

    @Test
    public void testOnePlusOne() {
        assertEquals(2, 1+1);
    }

    @Test
    public void testSum() {
        assertDoesNotThrow(() -> 1+1);
    }
}
