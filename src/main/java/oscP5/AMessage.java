package oscP5;

import netP5.Bytes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static oscP5.OscUtils.*;

public abstract class AMessage extends OscPacket {

    private final List<Object> arguments;
    private NetAddress receivedFrom;
    protected String address;
    static final Logger Log = Logger.getLogger(AMessage.class.getName());

    public AMessage(final String theAddress,
                    final List<Object> theArguments) {
        address = theAddress == null ? "/null" : theAddress;

        /* TODO copy theArguments instead of passing ref ? */
        arguments = theArguments == null ? new ArrayList<>() : theArguments;
    }

    public abstract OscMessage add(final Object o);

    public abstract OscMessage add(final Object... o);

    public abstract OscMessage add(final List o);

    public abstract OscMessage addRGBA(final int r);

    public abstract OscMessage addMidi(final byte thePortId, final byte theStatus, final byte theData1, final byte theData2);

    public abstract boolean isAddress(final String theAddress);

    public abstract boolean isTypetag(final String theTypetag);

    public abstract Object getArgumentAt(final int theIndex);

    public abstract String getAddress();

    public abstract String getTypetag();

    public List<Object> getArgumentsAsList() {
        return arguments;
    }

    public void setReceivedFrom(NetAddress theAddress) {
        receivedFrom = theAddress;
    }

    public NetAddress receivedFrom() {
        return receivedFrom;
    }

    /**
     * Legacy constructors and methods.
     * Backwards compatibility, deprecated, don't use.
     */

    public AMessage(final AMessage theOscMessage) {
        this(theOscMessage.getAddress(), theOscMessage.getArgumentsAsList());
    }

    public AMessage(final String theAddrPattern) {
        this(theAddrPattern, new ArrayList<>());
    }

    public AMessage(final int theAddrInt) {
        this(theAddrInt + "");
    }

    public AMessage(final int theAddrInt,
                    final Object... theArguments) {
        this(theAddrInt + "", toList(theArguments));
    }

    private void addArgumentAsObject(Object o) {
        getArgumentsAsList().add(o);
    }

    public AMessage clear() {
        clearArguments();
        return this;
    }

    public AMessage clearArguments() {
        arguments.clear();
        return this;
    }

    public AMessage set(final int theIndex,
                        final Object theObject) {
        /* NOTE to modify existing OscMessage */
        /* TODO re-implement */
        return this;
    }

    public boolean checkTypetag(final String theTypetag) {
        return getTypetag().equals(theTypetag);
    }

    public boolean checkAddress(final String theAddress) {
        return getAddress().equals(theAddress);
    }

    public AMessage setAddress(final String theAddrPattern) {
        address = theAddrPattern;
        return this;
    }

    public AMessage setAddress(final int theAddrPattern) {
        address = theAddrPattern + "";
        return this;
    }

    public AMessage setArguments(final Object... theArguments) {
        clearArguments();
        /* TODO re-implement */
        return this;
    }

    public long getTimetag() {
        /* TODO re-implement */
        return -1;
    }

    public String toString() {
        /** TODO re-implement */
        return null;
    }

    /**
     * Legacy methods, backwards compatibility, deprecated,
     * don't use.
     */

    @Deprecated
    public Object[] getArguments() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
    public Object getArgument(int theIndex) {
        return getArgumentsAsList().get(theIndex);
    }

    @Deprecated
    public int addrInt() {
        Log.log(Level.WARNING, "addrInt() is deprecated and not implemented anymore.");
        return -1;
    }

    @Deprecated
    public byte[] getAddrPatternAsBytes() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
    public byte[] getTypetagAsBytes() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
    public byte[] bytes() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
    public AMessage add() {
        /** TODO re-implement NIL */
        return this;
    }

    static public byte[] makeBlob(final byte[] b) {
        /** TODO check implemetation or re-implement or delete */
        final int tLength = b.length;
        byte[] b1 = Bytes.toBytes(tLength);
        b1 = Bytes.append(b1, b);
        final int t = tLength % 4;
        if (t != 0) {
            b1 = Bytes.append(b1, new byte[4 - t]);
        }
        return b1;
    }

    public OscArgument get(final int theIndex) {
        /** TODO re-implement */
        return null;
    }

    private String arrayToString(Object[] os) {
        /** TODO check or re-implement */
        String s = "[";
        for (Object o : os) {
            s += o.toString() + ",";
        }
        s = s.replaceAll(",$", "");
        s += "]";
        return s;
    }

    @Deprecated
    public boolean isPlugged() {
        Log.log(Level.WARNING, "isPlugged() is deprecated and not implemented anymore.");
        return false;
    }

    public void print() {
        /** TODO re-implement */
    }

    @Deprecated
    public void printData() {
        Log.log(Level.WARNING, "printData() is deprecated and not implemented anymore.");
    }

    public int intValue(int theIndex) {
        /** TODO check or re-implement */
        return i(getValue(theIndex));
    }

    public float floatValue(int theIndex) {
        /** TODO check or re-implement */
        return f(getValue(theIndex));
    }

    public char charValue(int theIndex) {
        /** TODO check or re-implement */
        return c(getValue(theIndex));
    }

    public double doubleValue(int theIndex) {
        /** TODO check or re-implement */
        return d(getValue(theIndex));
    }

    public long longValue(int theIndex) {
        /** TODO check or re-implement */
        return l(getValue(theIndex));
    }

    public boolean booleanValue(int theIndex) {
        /** TODO check or re-implement */
        return b(getValue(theIndex));
    }

    public String stringValue(int theIndex) {
        /** TODO check or re-implement */
        return s(getValue(theIndex));
    }

    public String[] stringValues(int theStart,
                                 int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = stringValue(theStart + i);
        }
        return arr;
    }

    public int[] intValues(int theStart,
                           int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = intValue(theStart + i);
        }
        return arr;
    }

    public float[] floatValues(int theStart,
                               int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        float[] arr = new float[n];
        for (int i = 0; i < n; i++) {
            arr[i] = intValue(theStart + i);
        }
        return arr;
    }

    public char[] charValues(int theStart,
                             int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        char[] arr = new char[n];
        for (int i = 0; i < n; i++) {
            arr[i] = charValue(theStart + i);
        }
        return arr;
    }

    public double[] doubleValues(int theStart,
                                 int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = doubleValue(theStart + i);
        }
        return arr;
    }

    public long[] longValues(int theStart,
                             int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = longValue(theStart + i);
        }
        return arr;
    }

    public boolean[] booleanValues(int theStart,
                                   int theEnd) {
        /** TODO check or re-implement */
        int n = theEnd - theStart;
        boolean[] arr = new boolean[n];
        for (int i = 0; i < n; i++) {
            arr[i] = booleanValue(theStart + i);
        }
        return arr;
    }

    public byte[] bytesValue(int theIndex) {
        /** TODO check or re-implement */
        return OscUtils.bytes(getValue(theIndex));
    }

    public byte[] blobValue(int theIndex) {
        /** TODO check or re-implement */
        return bytesValue(theIndex);
    }

    public Object getValue(int theIndex) {
        /** TODO check or re-implement */
        if (theIndex < getArguments().length) {
            return getArguments()[theIndex];
        }
        indexOutOfBounds(theIndex);
        return null;
    }

    public void indexOutOfBounds(int n) {
        /** TODO check or re-implement */
        Log.log(Level.WARNING,"ArrayIndexOutOfBounds: index requested " + n + ", expected < " + getArguments().length);
    }

    /* Deprecated methods */

    @Deprecated
    public void setAddrPattern(final String theAddrPattern) {
        setAddress(theAddrPattern);
    }

    @Deprecated
    public void setAddrPattern(final int theAddrPattern) {
        setAddress(theAddrPattern);
    }

    @Deprecated
    public boolean checkAddrPattern(final String theAddrPattern) {
        return checkAddress(theAddrPattern);
    }

    @Deprecated
    public String typetag() {
        return getTypetag();
    }

    @Deprecated
    public long timetag() {
        return getTimetag();
    }

    @Deprecated
    public Object[] arguments() {
        return getArguments();
    }

    @Deprecated
    public AMessage addArguments(final Object... theArguments) {
        add(theArguments);
        return this;
    }

    @Deprecated
    public String addrPattern() {
        return getAddress();
    }

}
