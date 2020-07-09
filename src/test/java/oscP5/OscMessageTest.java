package oscP5;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class OscMessageTest {

    final String a1 = "/test";
    final String t1 = "ifdhs";

    final OscMessage m1 = new OscMessage("/m1",
            1, 2f, 3d, 4L,
            "hello");

    final OscMessage m2 = new OscMessage("/m2",
            1, 2f, 3d, 4L,
            "hello", '\u2190',
            new byte[]{0x00, 0x01, 0x02},
            Arrays.asList(1, Arrays.asList(100, 200), 3),
            true, false,
            null,
            OscImpulse.IMPULSE,
            new OscSymbol("mySymbol"));

    final String t2 = "ifdhscb[i[ii]i]TFNIS";

    @Test
    public void testGetTypetag() throws Exception {
        assertEquals(t1, m1.getTypetag());
        assertEquals(t2, m2.getTypetag());
    }

    @Test
    public void testAdd() throws Exception {
        final OscMessage m = new OscMessage(a1);
        m.add(1).add(2f).add(3d).add(4L).add("hello");
        assertEquals(a1, m.getAddress());
        assertEquals(t1, m.getTypetag());
    }

    @Test
    public void testGetAddress() throws Exception {
        final OscMessage m = new OscMessage(a1);
        assertEquals(a1, m.getAddress());
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

        assertNotNull(m.getArgumentsAsList());
        assertEquals(Arrays.asList(1, 2f, 3d, 4L, "hello", l2), m.getArgumentsAsList());
        assertEquals(6, m.getArgumentsAsList().size());

    }

    @Test
    public void testGetTimetag() throws Exception {
        /* TODO */
    }

    @Test
    public void testParseArguments() {

        final byte[] bytes = OscParser.messageToBytes(m2); /* message to bytes */
        final OscMessage m = OscParser.bytesToMessage(bytes); /* bytes to message */
        assertNotEquals(null, m);
        assertNotEquals(null, m.getArgumentsAsList());
        assertNotEquals(null, m.getObjectAt(0));
        assertFalse(m.getArgumentsAsList().isEmpty());
        assertEquals(null, m.getObjectAt(10)); /* is null */

        for(int i=0;i<m2.getArgumentsAsList().size();i++) {
            assertEquals(OscArgument.class, m2.get(i).getClass());
        }
    }

    @Test
    public void testParseArgumentInt() {
        assertEquals(Integer.class, m2.getObjectAt(0).getClass());
        int v0 = (int) m2.getObjectAt(0);
        int v1 = m2.getIntAt(0);
        assertEquals(v0, v1);
    }

    @Test
    public void testParseArgumentFloat() {
        assertEquals(Float.class, m2.getObjectAt(1).getClass());
        float v0 = (float) m2.getObjectAt(1);
        float v1 = m2.getFloatAt(1);
        assertEquals(v0, v1, 0.001);
    }

    @Test
    public void testParseArgumentDouble() {
        assertEquals(Double.class, m2.getObjectAt(2).getClass());
        double v0 = (double) m2.getObjectAt(2);
        double v1 = m2.getDoubleAt(2);
        assertEquals(v0, v1, 0.001);
    }

    @Test
    public void testParseArgumentLong() {
        assertEquals(Long.class, m2.getObjectAt(3).getClass());
        long v0 = (long) m2.getObjectAt(3);
        long v1 = m2.getLongAt(3);
        assertEquals(v0, v1);
    }
    @Test
    public void testParseArgumentString() {
        assertEquals(String.class, m2.getObjectAt(4).getClass());

//        final String v4 = m.getStringAt(4);
//        assertEquals("hello", v4);
//
//        assertEquals(Character.class, m.getValueAt(5).getClass());
//        final char v5 = m.getCharAt(5);
//        assertEquals('←', v5); /* \u2190 */
//
//        assertEquals(byte[].class, m.get(6).getClass());
//        final byte[] v6 = m.getBlobAt(6);
//        assertArrayEquals(v6, new byte[]{0x00, 0x01, 0x02});
//
//        assertEquals(ArrayList.class, m.get(7).getClass());
//        final List v7 = m.getListAt(7);
//        assertTrue(v7 instanceof ArrayList);
//
//        assertEquals(Boolean.class, m.get(8).getClass());
//        final boolean v8 = m.getBooleanAt(8);
//        assertTrue(v8);
//
//        assertEquals(Boolean.class, m.get(9).getClass());
//        final boolean v9 = m.getBooleanAt(9);
//        assertTrue(!v9);
//
//        assertEquals(null, m.get(10));
//        final Object v10 = m.getNilAt(10);
//        assertEquals(null, v10);
//
//        assertEquals(OscImpulse.class, m.get(11).getClass());
//        final OscImpulse v11 = m.getImpulseAt(11);
//        assertEquals(OscImpulse.IMPULSE, v11);
//
//        assertEquals(OscSymbol.class, m.get(12).getClass());
//        final OscSymbol v12 = m.getSymbolAt(12);
//        assertEquals(new OscSymbol("mySymbol"), v12);

    }

    @Test
    public void testOldArguments() {
        /* test against getArguments() which returns Object[] */
        Object[] o = m2.getArguments();
        assertEquals(1, o[0]);
        assertEquals(2f, o[1]);
        assertEquals(0x00, ((byte[]) o[6])[0]);
        assertEquals(OscImpulse.IMPULSE, o[11]);
    }
}
