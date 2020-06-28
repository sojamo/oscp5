package oscP5;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class OscBundleTest {

    final OscMessage m1 = new OscMessage("/m2", 1, 2.0f, 3.0d, 4L);
    final OscMessage m2 = new OscMessage("/m1", 1, 2, 3, 4);
    final OscMessage m3 = new OscMessage("/m3", "hello");

    @Test
    public void testBundle() throws Exception {
        OscBundle b1 = new OscBundle();
        b1.add(m1);
        b1.add(m2, m3);
        assertEquals(3, b1.getAll().size());

        OscBundle b2 = new OscBundle();
        b2.add(b1, m1, m2);
        assertEquals(3, b2.getAll().size());

    }

    @Test
    public void testIsImmediate() throws Exception {
        OscBundle b1 = new OscBundle();
        assertTrue(b1.isImmediate());

        OscBundle b2 = new OscBundle();
        b2.getTimetag().setFutureTimeMillis(1000);
        assertFalse(b2.isImmediate());

    }

    @Test
    public void testSetTimetag() throws Exception {
        OscBundle b1 = new OscBundle();
        b1.getTimetag().setFutureTimeMillis(1000);
        assertFalse(b1.getTimetag().isImmediate());
    }

    @Test
    public void testSetTimetag1() throws Exception {
         OscBundle b1 = new OscBundle();
         b1.setTimetag(new OscTimetag().setTimeMillis(System.currentTimeMillis()));
         assertFalse(b1.getTimetag().isImmediate());
    }

    @Test
    public void testAdd() throws Exception {
         /* b1: bundle with 2 elements */
         OscBundle b1 = new OscBundle();
         b1.add(m1, m2);
         assertEquals(2, b1.getAll().size());

         /* b2: bundle with 3 elements, nested. */
         OscBundle b2 = new OscBundle();
         b2.add(m1, m2);

         OscBundle b3 = new OscBundle();
         b3.add(m1, m2, m3);
         b2.add(b3);

         /* bundle b2 has 3 elements at top level, nested elements are not counted. */
         assertEquals(3, b2.getAll().size());
    }

    @Test
    public void testGetPackets() throws Exception {
         /*
         * 1. create Bundle 2. convert Bundle into bytes and then back into a Bundle
         3.
         * request all messages from a Bundle with OscParser.bytesToPackets()
         */

         /* b1: Bundle with 3 messages */
         OscBundle b1 = new OscBundle();
         b1.add(m1, m2, m3);
         byte[] bytes1 = b1.getBytes();
         Map r1 = OscParser.bytesToPackets(bytes1);
         assertEquals(3, r1.size());

         /* b2: Nested Bundle with a total of 5 messages */
         OscBundle b2 = new OscBundle();
         OscBundle b3 = new OscBundle();
         b3.add(m1, m2);
         b2.add(m1, m2, m3, b3);
         assertEquals(4, b2.getAll().size()); /* 3 messages + 1 bundle */
         byte[] bytes2 = b2.getBytes();
         Map r2 = OscParser.bytesToPackets(bytes2);
         assertEquals(5, r2.size()); /* 5 messages parsed from nested bundle */

    }
}