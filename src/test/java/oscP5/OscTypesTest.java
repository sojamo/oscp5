package oscP5;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OscTypesTest {

    @Test
    public void testRgba() {
        final OscMessage m1 = new OscMessage("/test/rgba");
        final OscRgba rgba = new OscRgba();
        rgba.setColor(255, 0, 0, 255);
        m1.add(rgba);
        final OscMessage m11 = OscParser.bytesToMessage(OscParser.messageToBytes(m1));

        assertArrayEquals(m1.getBytes(), m11.getBytes());
        assertEquals("r", m11.getTypetag());

        final Object o1 = m11.getObjectAt(0);

        assertEquals(OscRgba.class, o1.getClass());
    }

    @Test
    public void testImpulse() {
        final OscMessage m1 = new OscMessage("/test/impulse");
        m1.add(OscImpulse.IMPULSE);
        final OscMessage m11 = OscParser.bytesToMessage(OscParser.messageToBytes(m1));

        assertArrayEquals(m1.getBytes(), m11.getBytes());
        assertEquals("I", m11.getTypetag());

        final Object o1 = m11.getObjectAt(0);

        assertEquals(OscImpulse.class, o1.getClass());
    }

    @Test
    public void testMidi() {
        final OscMessage m1 = new OscMessage("/test/midi");
        final byte portId = (byte) 16;
        final byte status = (byte) 144;
        final byte data1 = (byte) 48;
        final byte data2 = (byte) 127;
        m1.add(new OscMidi(portId, status, data1, data2));
        final OscMessage m11 = OscParser.bytesToMessage(OscParser.messageToBytes(m1));

        assertArrayEquals(m1.getBytes(), m11.getBytes());
        assertEquals(m11.getTypetag(), "m");

        final Object o1 = m11.getObjectAt(0);

        assertEquals(OscMidi.class, o1.getClass());

        final OscMidi o2 = m11.getMidiAt(0);

        assertEquals(16, o2.getPortId());
        assertEquals(144, o2.getStatus());
        assertEquals(48, o2.getData1());
        assertEquals(127, o2.getData2());

        final OscMessage m2 = new OscMessage("/test/midi");
        final OscMidi midi = new OscMidi();
        midi.setPortId(16).setStatus(144).setData1(48).setData2(127);
        m2.add(midi);
        final OscMessage m21 = OscParser.bytesToMessage(OscParser.messageToBytes(m2));

        assertArrayEquals(m2.getBytes(), m21.getBytes());
        assertEquals("m", m21.getTypetag());

        final Object o3 = m21.getObjectAt(0);

        assertEquals(OscMidi.class, o3.getClass());

        final OscMidi o4 = m21.getMidiAt(0);

        assertEquals(16, o4.getPortId());
        assertEquals(144, o4.getStatus());
        assertEquals(48, o4.getData1());
        assertEquals(127, o4.getData2());

    }

    @Test
    public void testSymbol() {
        OscMessage m1 = new OscMessage("/test/symbol");
        m1.add(new OscSymbol("mySymbol"));
        OscMessage m11 = OscParser.bytesToMessage(OscParser.messageToBytes(m1));
        assertArrayEquals(m1.getBytes(), m11.getBytes());

        assertEquals("S", m11.getTypetag());

        Object o1 = m11.getObjectAt(0);
        assertEquals(OscSymbol.class, o1.getClass());

        OscSymbol o2 = m11.getSymbolAt(0);
        assertEquals("mySymbol", o2.getValue());
    }


}
