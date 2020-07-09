package oscP5;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class OscTimetagTest {


    @Test
    public void testOscTimetagTimeConversion() {

        final long delta = 1000;
        final OscTimetag timetag1 = OscTimetag.inFutureTimeMillis(delta); /* set a timetag in the future (1000ms from now) */
        final long t1 = timetag1.getValue(); /* Timetags are represented by a 64 bit fixed point number */
        final long t2 = timetag1.toTimeMillis(); /* convert timetag value to system time millis */
        final long t3 = System.currentTimeMillis(); /* get system time millis */
        final long t4 = OscTimetag.toTimeMillis(timetag1.getValue()); /* convert timetag value to system time millis */
        final java.util.Date t5 = timetag1.getDate(); /* get the timetag's date */
        final java.util.Date t6 = new Date(System.currentTimeMillis() + delta);

        assertTrue(Math.abs(t2 - t3) <= delta);
        assertTrue(Math.abs(t4 - t3) <= delta);
        assertEquals(t5, t6); /* TODO this sometimes results in an error due to time conflicts */
    }

    @Test
    public void testIsImmediate() {
        final OscTimetag timetag1 = new OscTimetag();
        assertTrue(timetag1.isImmediate());

        final OscTimetag timetag2 = OscTimetag.inFutureTimeMillis(1000);
        assertFalse(timetag2.isImmediate());
    }

    @Test
    public void testSetTimetag() {
        /* TODO */
    }

    @Test
    public void testSetTimeMillis() {
        final OscTimetag timetag1 = new OscTimetag();
        final long t1 = System.currentTimeMillis();
        timetag1.setTimeMillis(t1);
        assertEquals(t1, timetag1.toTimeMillis());
    }

    @Test
    public void testFromTimeMillisToTimetag() {
        final OscTimetag timetag1 = new OscTimetag();
        final long time1 = System.currentTimeMillis();
        timetag1.setTimeMillis(time1);
        assertEquals(timetag1.getValue(), OscTimetag.fromTimeMillisToTimetag(time1));
    }

    @Test
    public void testSetFutureTimeMillis() {
        final OscTimetag timetag1 = new OscTimetag();
        final long t1 = System.currentTimeMillis();
        final long delta1 = 1000;
        timetag1.setFutureTimeMillis(delta1); /* set a time delta (in millis) in the future from now */
        assertEquals(t1 + delta1, timetag1.toTimeMillis());

        final OscTimetag timetag2 = new OscTimetag();
        final long t2 = System.currentTimeMillis();
        final long delta2 = -1000;
        timetag2.setFutureTimeMillis(delta2); /* set a time delta (in millis) in the future from now */
        assertEquals(t2, timetag2.toTimeMillis());
    }

    @Test
    public void testGetTimetag() {
        final OscTimetag t1 = new OscTimetag();
        final long time1 = System.currentTimeMillis();
        t1.setTimeMillis(time1);
        assertEquals(OscTimetag.fromTimeMillisToTimetag(time1), t1.getValue());
    }

}