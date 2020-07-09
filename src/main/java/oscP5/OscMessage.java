package oscP5;

import java.util.ArrayList;
import java.util.List;

import static oscP5.OscUtils.*;

/**
 * Implements OSC required types following the 1.0 and 1.1 specifications (i,f,s,b,T,F,N,I,t).
 */
public class OscMessage extends AMessage {

    private final List<Object> arguments;
    private NetAddress receivedFrom;
    protected String address;


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
        super();
        address = theAddress == null ? "undefined" : theAddress;
        /* TODO copy theArguments instead of passing reference ? */
        arguments = theArguments == null ? new ArrayList<>() : theArguments;
    }

    public OscMessage(final AMessage theOscMessage) {
        this(theOscMessage.getAddress(), theOscMessage.getArgumentsAsList());
        /* TODO copy arguments, currently they are passed by reference here */
    }

    private boolean checkIfAddressIsValid(String theAddress) {
        /* TODO */
        return true;
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
        getArgumentsAsList().add(o);
        return this;
    }

    @Override
    public OscMessage addRgba(final int r) {
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
    public OscMessage setAddress(final String theAddress) {
        address = theAddress;
        return this;
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
    public final void setFrom(NetAddress theAddress) {
        receivedFrom = theAddress;
    }

    @Override
    public final NetAddress getFrom() {
        return receivedFrom;
    }

    @Override
    public List<Object> getArgumentsAsList() {
        return arguments;
    }

    @Override
    public OscMessage clearArguments() {
        arguments.clear();
        return this;
    }

    @Override
    public OscMessage set(final int theIndex,
                          final Object theObject) {
        if (theIndex < arguments.size()) {
            arguments.set(theIndex, theObject);
        }
        return this;
    }

    @Override
    public final Object getObjectAt(int theIndex) {
        if (theIndex < getArgumentsAsList().size()) {
            return getArgumentsAsList().get(theIndex);
        }
        return null;
    }

    @Override
    public byte[] getBytes() {
        return OscParser.messageToBytes(this);
    }

    public boolean matches(String theAddress, String theTypetag) {
        return isAddress(theAddress) && isTypetag(theTypetag);
    }

    /* Getter to cast values from object to requested type */

    public int getIntAt(final int theIndex) {
        return i(getObjectAt(theIndex));
    }

    public float getFloatAt(final int theIndex) {
        return f(getObjectAt(theIndex));
    }

    public char getCharAt(final int theIndex) {
        return (char) (getObjectAt(theIndex));
    }

    public double getDoubleAt(final int theIndex) {
        return d(getObjectAt(theIndex));
    }

    public byte[] getBlobAt(final int theIndex) {
        return OscUtils.bytes(getObjectAt(theIndex));
    }

    public long getLongAt(final int theIndex) {
        return l(getObjectAt(theIndex));
    }

    public String getStringAt(final int theIndex) {
        return s(getObjectAt(theIndex));
    }

    public boolean getBooleanAt(final int theIndex) {
        return b(getObjectAt(theIndex));
    }

    public Object getNilAt(final int theIndex) {
        return getObjectAt(theIndex);
    }

    public OscImpulse getImpulseAt(final int theIndex) {
        /* TODO type check required */
        return (OscImpulse) getObjectAt(theIndex);
    }

    public OscMidi getMidiAt(final int theIndex) {
        /* TODO type check required */
        return (OscMidi) (getObjectAt(theIndex));
    }

    public OscRgba getRgbaAt(final int theIndex) {
        /* TODO type check required */
        return (OscRgba) (getObjectAt(theIndex));
    }

    public OscSymbol getSymbolAt(final int theIndex) {
        /* TODO type check required */
        return (OscSymbol) (getObjectAt(theIndex));
    }

    public List getArrayAt(final int theIndex) {
        /* TODO type check required */
        return toList(getObjectAt(theIndex));
    }

    public OscTimetag getTimetagAt(final int theIndex) {
        return getObjectAt(theIndex) instanceof OscTimetag ? (OscTimetag) getObjectAt(theIndex) : new OscTimetag();
    }

    @Override
    public String toString() {
        return "{ class: OscMessage" +
                ", address: " + getAddress() +
                ", typetag: " + getTypetag() +
                ", arguments: " + OscParser.asString(getArgumentsAsList()) +
                ", from: " + getFrom() +
                "}";
    }

}
