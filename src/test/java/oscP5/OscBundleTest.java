package oscP5;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import static org.junit.Assert.*;

public class OscBundleTest {


    final OscMessage m1 = new OscMessage("/m1", 1, 2.0f, 3.0d, 4L);
    final OscMessage m2 = new OscMessage("/m2", 1, 2, 3, 4);
    final OscMessage m3 = new OscMessage("/m3", "hello");
    final OscBundle b0 = new OscBundle();

    @Test
    public void testBundle1() {
        final OscBundle b1 = new OscBundle();
        b1.add(m1);
        b1.add(m2, m3);

        assertEquals(3, b1.getAll().size());
    }

    @Test
    public void testBundle2() {
        final OscBundle b2 = new OscBundle();
        b2.add(b0, m1, m2);

        assertEquals(3, b2.getAll().size());
        assertTrue(b2.getTimetag().isImmediate());
    }

    @Test
    public void testIsImmediate1() {
        OscBundle b1 = new OscBundle();
        assertTrue(b1.isImmediate());
    }

    @Test
    public void testIsImmediate2() {
        OscBundle b2 = new OscBundle();
        b2.getTimetag().setFutureTimeMillis(1000);
        assertFalse(b2.isImmediate());

    }

    @Test
    public void testSetTimetag() {
        OscBundle b1 = new OscBundle();
        b1.getTimetag().setFutureTimeMillis(1000);
        assertFalse(b1.getTimetag().isImmediate());
    }

    @Test
    public void testSetTimetag1() {
        OscBundle b1 = new OscBundle();
        b1.setTimetag(new OscTimetag().setTimeMillis(System.currentTimeMillis()));
        assertFalse(b1.getTimetag().isImmediate());
    }

    @Test
    public void testAdd() {
        /* b1: bundle with 2 elements */
        OscBundle b1 = new OscBundle();
        b1.add(m1, m2);

        /* b2: bundle with 3 elements, nested. */
        OscBundle b2 = new OscBundle();
        b2.add(m1, m2);

        OscBundle b3 = new OscBundle();
        b3.add(m1, m2, m3);

        b2.add(b3);

        assertEquals(2, b1.getAll().size());
        assertEquals(3, b2.getAll().size()); /* bundle b2 has 3 elements at top level, nested elements are not counted. */
        assertEquals(3, b3.getAll().size());
    }

    @Test
    public void testGetAll1() {
        /*
         * 1. create Bundle
         * 2. convert Bundle into bytes and then back into a Bundle
         * 3.request all messages from a Bundle with OscParser.bytesToPackets()
         */

        /* b1: Bundle with 3 messages */
        OscBundle b1 = new OscBundle();
        b1.add(m1, m2, m3);
        byte[] bytes1 = b1.getBytes();
        Map<OscMessage, Long> r1 = OscParser.bytesToPackets(bytes1);

        assertEquals(3, r1.size());
    }

    @Test
    public void testGetAll2() {
        /* b2: Nested Bundle with a total of 5 messages */
        OscBundle b2 = new OscBundle();
        OscBundle b3 = new OscBundle();
        b3.add(m1, m2);
        b2.add(m1, m2, m3, b3);

        byte[] bytes2 = b2.getBytes();
        Map<OscMessage, Long> r2 = OscParser.bytesToPackets(bytes2);

        Object[] o0 = Arrays.asList(1L,1L,1L,1L,1L).toArray(); /* expected value for each timetag-value is 1 */
        Object[] o1 = r2.values().toArray();

        assertArrayEquals(o0, o1);
        assertEquals(4, b2.size()); /* 3 messages + 1 bundle */
        assertEquals(5, r2.size()); /* 5 messages parsed from nested bundle */

    }

}