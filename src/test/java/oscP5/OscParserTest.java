package oscP5;

import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OscParserTest {

    final OscMessage m1 = new OscMessage(
            "/m1",
            1, 2.0f, 3.0d, 4L,
            "hello", 'c',
            new byte[] { 0x00, 0x01, 0x02 },
            Arrays.asList(1, Arrays.asList(100, 200), 3),
            true, false,
            null,
            OscImpulse.IMPULSE,
            new OscSymbol("mySymbol"),
            new OscMidi().setPortId(10).setStatus(144).setData1(100).setData2(127),
            new OscRgba().setColor(128,255,64,255)
            );
    final String t1 = "ifdhscb[i[ii]i]TFNISmr";

    @Test
    public void testGetTypetag()  {
        assertEquals(t1, m1.getTypetag());
    }

    @Test
    public void testMessageToByteArray()  {
        byte[] bytes = OscParser.messageToBytes(m1); /* message to bytes */
        OscMessage m2 = OscParser.bytesToMessage(bytes); /* bytes to message */
        assertEquals(m1.getAddress(), m2.getAddress());
        assertEquals(m1.getTypetag(),m2.getTypetag());
    }

    @Test
    public void testInvalidOscMessage() {

        /* address pattern is missing the forward-slash */
        byte[] b1 = "abc".getBytes();
        OscMessage m1 = OscParser.bytesToMessage(b1);
        assertTrue(m1 instanceof InvalidOscMessage);

        /* incomplete message, typetag and data is missing */
        byte[] b2 = "/ab".getBytes();
        OscMessage m2 = OscParser.bytesToMessage(b2);
        assertTrue(m2 instanceof InvalidOscMessage);

        /* incomplete message, address pattern passes, typetag and data is missing */
        byte[] b3 = "/abc\0\0\0\0".getBytes();
        OscMessage m3 = OscParser.bytesToMessage(b3);
        assertTrue(m3 instanceof InvalidOscMessage);

        /* incomplete message, address pattern passes, typetag and data is missing */
        byte[] b4 = "/ab\0,".getBytes();
        OscMessage m4 = OscParser.bytesToMessage(b4);
        assertTrue(m4 instanceof InvalidOscMessage);

        /* incomplete message, address pattern passes, typetag malformed, and data partially missing */
        byte[] b5 = "/abc\0\0\0iifabcd".getBytes();
        OscMessage m5 = OscParser.bytesToMessage(b5);
        assertTrue(m5 instanceof InvalidOscMessage);

        /* address pattern and typetag pass, data is incomplete but passes */
        byte[] b6 = "/abc\0\0\0\0,ii\0abcd".getBytes();
        OscMessage m6 = OscParser.bytesToMessage(b6);
        OscMessage r6 = new OscMessage("/abc", 1633837924, 0);
        assertEquals(m6.getAddress(), r6.getAddress());
        assertEquals(m6.getTypetag(), r6.getTypetag());
        assertEquals(m6.getIntAt(0), r6.getIntAt(0));
        assertEquals(m6.getIntAt(1), r6.getIntAt(1));

        /* address pattern and typetag pass, data is incomplete and fails */
        byte[] b7 = "/abc\0\0\0\0,ifdhscb[i[ii]i]TFNI\0\0\0\0abcd".getBytes();
        OscMessage m7 = OscParser.bytesToMessage(b7);
        assertTrue(m7 instanceof InvalidOscMessage);

    }
}