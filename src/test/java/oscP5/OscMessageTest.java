package oscP5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OscMessageTest {

    final String a1 = "/test";
    final String t1 = "ifdhs";
    final OscMessage m1 = new OscMessage("/m1", 1, 2f, 3d, 4L, "hello");
    final OscMessage m2 = new OscMessage("/m2", 1, 2f, 3d, 4L,
            "hello", '\u2190', new byte[]{0x00, 0x01, 0x02},
            Arrays.asList(1, Arrays.asList(100, 200), 3), true, false, null, OscImpulse.IMPULSE,
            new OscSymbol("mySymbol"));
    final String t2 = "ifdhscb[i[ii]i]TFNIS";

    @Test
    public void testGetTypetag() throws Exception {
        assertEquals(m1.getTypetag(), t1);
        assertEquals(m2.getTypetag(), t2);
    }

    @Test
    public void testAdd() throws Exception {
        final OscMessage m = new OscMessage(a1);
        m.add(1).add(2f).add(3d).add(4L).add("hello");
        assertEquals(m.getAddress(), a1);
        assertEquals(m.getTypetag(), t1);
    }

    @Test
    public void testGetAddress() throws Exception {
        final OscMessage m = new OscMessage(a1);
        assertEquals(m.getAddress(), a1);
    }

    @Test
    public void testGetArguments() throws Exception {
        final List l1 = Arrays.asList(1, 2, 3);
        final List l2 = new ArrayList();
        l2.add(1);
        l2.add(2);
        l2.add(3);

        final OscMessage m = new OscMessage(a1);
        m.add(1).add(2f).add(3d).add(4L).add("hello").add(l1);

        assertEquals(m.getArgumentsAsList(), Arrays.asList(1, 2f, 3d, 4L, "hello", l2));
        assertTrue(m.getArgumentsAsList() instanceof ArrayList);
        assertTrue(m.getArgumentsAsList().size() == 6);

    }

    @Test
    public void testGetTimetag() throws Exception {
        /* TODO */
    }

    @Test
    public void testParseArguments() throws Exception {

        final byte[] bytes = OscParser.messageToBytes(m2); /* message to bytes */
        final OscMessage m = OscParser.bytesToMessage(bytes); /* bytes to message */

        assertEquals(Integer.class, m.get(0).getClass());
        final int v0 = m.getIntAt(0);
        assertTrue(1 == v0);

        assertEquals(Float.class, m.get(1).getClass());
        final float v1 = m.getFloatAt(1);
        assertTrue(2f == v1);

        assertEquals(Double.class, m.get(2).getClass());
        final double v2 = m.getDoubleAt(2);
        assertTrue(3d == v2);

        assertEquals(Long.class, m.get(3).getClass());
        final long v3 = m.getLongAt(3);
        assertTrue(4L == v3);

        assertEquals(String.class, m.get(4).getClass());
        final String v4 = m.getStringAt(4);
        assertEquals("hello", v4);

        assertEquals(Character.class, m.get(5).getClass());
        final char v5 = m.getCharAt(5);
        assertEquals('←', v5); /* \u2190 */

        assertEquals(byte[].class, m.get(6).getClass());
        final byte[] v6 = m.getBlobAt(6);
        assertArrayEquals(v6, new byte[]{0x00, 0x01, 0x02});

        assertEquals(ArrayList.class, m.get(7).getClass());
        final List v7 = m.getListAt(7);
        assertTrue(v7 instanceof ArrayList);

        assertEquals(Boolean.class, m.get(8).getClass());
        final boolean v8 = m.getBooleanAt(8);
        assertTrue(v8);

        assertEquals(Boolean.class, m.get(9).getClass());
        final boolean v9 = m.getBooleanAt(9);
        assertTrue(!v9);

        assertEquals(null, m.get(10));
        final Object v10 = m.getNilAt(10);
        assertEquals(null, v10);

        assertEquals(OscImpulse.class, m.get(11).getClass());
        final OscImpulse v11 = m.getImpulseAt(11);
        assertEquals(OscImpulse.IMPULSE, v11);

        assertEquals(OscSymbol.class, m.get(12).getClass());
        final OscSymbol v12 = m.getSymbolAt(12);
        assertEquals(new OscSymbol("mySymbol"), v12);

    }
}