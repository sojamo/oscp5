package oscP5;


/**
 * Represents an OSC Midi type and contains 4 Midi specific values: portId, status, data1 and data2.
 */
public class OscMidi {

    private byte portId;
    private byte status;
    private byte data1;
    private byte data2;

    public OscMidi()  {
        this(new byte[4]);
    }

    public OscMidi(byte[] theBytes) {
        this(getByteAt(theBytes, 0), getByteAt(theBytes, 1), getByteAt(theBytes, 2), getByteAt(theBytes, 3));
    }

    public OscMidi(byte thePortId, byte theStatus, byte theData1, byte theData2) {
        portId = thePortId;
        status = theStatus;
        data1 = theData1;
        data2 = theData2;
    }

    private static byte getByteAt(byte[] theBytes, int theIndex) {
        return theIndex < theBytes.length ? theBytes[theIndex] : 0x00;
    }

    public final byte[] getBytes() {
        return new byte[]{portId, status, data1, data2};
    }

    public OscMidi setPortId(int thePortId) {
        portId = (byte) thePortId;
        return this;
    }

    public OscMidi setStatus(int theStatus) {
        status = (byte) theStatus;
        return this;
    }

    public OscMidi setData1(int theData1) {
        data1 = (byte) theData1;
        return this;
    }

    public OscMidi setData2(int theData2) {
        data2 = (byte) theData2;
        return this;
    }


    public int getPortId() {
        return portId & 0xff;
    }

    public int getStatus() {
        return status & 0xff;
    }

    public int getData1() {
        return data1 & 0xff;
    }

    public int getData2() {
        return data2 & 0xff;
    }

    @Override
    public String toString() {
        return "{ class: OscMidi" +
                ", portId:" + getPortId() +
                ", status:" + getStatus() +
                ", data1:" + getData1() +
                ", data2:" + getData2() +
                " }";
    }


}
