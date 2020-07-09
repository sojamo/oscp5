package oscP5;

import netP5.ANetAddress;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NetAddressTest {

    @Test
    public void testEquals() {
        NetAddress a1 = new NetAddress(12000);
        NetAddress a2 = new NetAddress(12000);
        NetAddress a3 = new NetAddress("127.0.0.1", 12000);
        NetAddress a4 = new NetAddress(9000);

        assertEquals(a1, a2);
        assertEquals(a1, a3);
        assertNotEquals(a1, a4);

    }
}
