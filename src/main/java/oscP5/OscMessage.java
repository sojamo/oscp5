package oscP5;

import java.util.ArrayList;
import java.util.List;

import static oscP5.OscUtils.*;

public class OscMessage extends AMessage {

    public OscMessage(final String theAddress) {
        this(theAddress, new ArrayList<>());
    }

    public OscMessage(final OscMessage theMessage) {
        this(theMessage.getAddress(), theMessage.getArgumentsAsList());
    }

    public OscMessage(final String theAddress,
                      final Object... theArguments) {
        this(theAddress, toList(theArguments));
    }

    public OscMessage(final String theAddress,
                      final List theArguments) {
        super(theAddress, theArguments);
    }

    @Override
    public OscMessage add(final Object o) {
        getArgumentsAsList().add(o);
        return this;
    }

    @Override
    public OscMessage add(final Object... o) {
        getArgumentsAsList().addAll(toList(o));
        return this;
    }

    @Override
    public OscMessage add(List o) {
        getArgumentsAsList().addAll(o);
        return this;
    }

    @Override
    public OscMessage addRGBA(final int r) {
        /* TODO implement */
        return this;
    }

    @Override
    public OscMessage addMidi(final byte thePortId, final byte theStatus, final byte theData1, final byte theData2) {
        /* TODO implement */
        return this;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getTypetag() {
        return OscParser.getTypetag(new StringBuilder(), getArgumentsAsList());
    }

    @Override
    public boolean isAddress(final String theAddress) {
        return getAddress().equals(theAddress);
    }

    @Override
    public boolean isTypetag(final String theTypetag) {
        return getTypetag().equals(theTypetag);
    }

    @Override
    public byte[] getBytes() {
        return OscParser.messageToBytes(this);
    }

    @Override
    public Object getArgumentAt(final int theIndex) {
        return getArgumentsAsList().get(theIndex);
    }

    /**
     * Getter to cast values from object to type
     */

    public int getIntAt(final int theIndex) {
        return i(getArgumentAt(theIndex));
    }

    public float getFloatAt(final int theIndex) {
        return f(getArgumentAt(theIndex));
    }

    public char getCharAt(final int theIndex) {
        return (char) (getArgumentAt(theIndex));
    }

    public double getDoubleAt(final int theIndex) {
        return d(getArgumentAt(theIndex));
    }

    public byte[] getBlobAt(final int theIndex) {
        return OscUtils.bytes(getArgumentAt(theIndex));
    }

    public long getLongAt(final int theIndex) {
        return l(getArgumentAt(theIndex));
    }

    public String getStringAt(final int theIndex) {
        return s(getArgumentAt(theIndex));
    }

    public boolean getBooleanAt(final int theIndex) {
        return b(getArgumentAt(theIndex));
    }

    public Object getNilAt(final int theIndex) {
        return getArgumentAt(theIndex);
    }

    public OscImpulse getImpulseAt(final int theIndex) {
        return (OscImpulse) getArgumentAt(theIndex);
    }

    public int getMidiAt(final int theIndex) {
        return i(getArgumentAt(theIndex));
    }

    public int getRGBAAt(final int theIndex) {
        /** TODO check, is this correct, returns an int ? */
        return i(getArgumentAt(theIndex));
    }

    public OscTimetag getTimetagAt(final int theIndex) {
        return getArgumentAt(theIndex) instanceof OscTimetag ? (OscTimetag) getArgumentAt(theIndex) : new OscTimetag();
    }

    public List getListAt(final int theIndex) {
        return toList(getArgumentAt(theIndex));
    }

    public OscSymbol getSymbolAt(final int theIndex) {
        return (OscSymbol) (getArgumentAt(theIndex));
    }

    @Override
    public String toString() {
        String s = "{ class: OscMessage" + ", address: " + getAddress() + ", typetag: " + getTypetag() + ", arguments: "
                + OscParser.asString(getArgumentsAsList()) + ", receivedFrom: " + receivedFrom() + "}";
        return s;
    }

}
