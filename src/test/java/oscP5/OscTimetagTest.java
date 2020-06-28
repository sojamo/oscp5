package oscP5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OscTimetagTest {

    @Test
    public void testIsImmediate() throws Exception {
        OscTimetag t1 = new OscTimetag();
        assertTrue(t1.isImmediate());
    }

    @Test
    public void testSetTimetag() throws Exception {
        /* TODO */
    }

    @Test
    public void testSetTimeMillis() throws Exception {
        final OscTimetag t1 = new OscTimetag();
        final long time1 = System.currentTimeMillis();
        t1.setTimeMillis(time1);
        assertEquals(time1, t1.toTimeMillis());
    }

    @Test
    public void testFromTimeMillisToTimetag() throws Exception {
        final OscTimetag t1 = new OscTimetag();
        final long time1 = System.currentTimeMillis();
        t1.setTimeMillis(time1);
        assertEquals(t1.getTimetag(),OscTimetag.fromTimeMillisToTimetag(time1));
    }

    @Test
    public void testSetFutureTimeMillis() throws Exception {
        final OscTimetag t1 = new OscTimetag();
        final long time1 = System.currentTimeMillis();
        final long delta1 = 1000;
        t1.setFutureTimeMillis(delta1);
        assertEquals(time1 + delta1, t1.toTimeMillis());

        final OscTimetag t2 = new OscTimetag();
        final long time2 = System.currentTimeMillis();
        final long delta2 = -1000;
        t2.setFutureTimeMillis(delta2);
        assertEquals(time2, t2.toTimeMillis());
    }

    @Test
    public void testGetTimetag() throws Exception {
        final OscTimetag t1 = new OscTimetag();
        final long time1 = System.currentTimeMillis();
        t1.setTimeMillis(time1);
        assertEquals(OscTimetag.fromTimeMillisToTimetag(time1), t1.getTimetag());
    }

}