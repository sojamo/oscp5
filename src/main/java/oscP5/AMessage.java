package oscP5;

import netP5.Bytes;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static oscP5.OscUtils.*;

abstract class AMessage extends OscPacket {

    static final Logger Log = Logger.getLogger(AMessage.class.getName());

    public AMessage() {
    }

    public abstract OscMessage add(final Object o);

    public abstract OscMessage add(final Object... o);

    public abstract OscMessage add(final List o);

    public abstract OscMessage addRgba(final int r);

    public abstract OscMessage addMidi(final byte thePortId, final byte theStatus, final byte theData1, final byte theData2);

    public abstract boolean isAddress(final String theAddress);

    public abstract boolean isTypetag(final String theTypetag);

    public abstract String getAddress();

    public abstract String getTypetag();

    public abstract void setFrom(NetAddress theAddress);

    public abstract NetAddress getFrom();

    public abstract List<Object> getArgumentsAsList();

    public abstract OscMessage clearArguments();

    public abstract OscMessage setAddress(final String theAddrPattern);

    public abstract Object getObjectAt(int theIndex);

    public abstract OscMessage set(final int theIndex, final Object theObject);


    /* Legacy constructors and methods. Backwards compatibility, don't use. */

    public OscArgument get(final int theIndex) {
        /* TODO check exceptions (null, outofbounds) */
        OscArgument o = new OscArgument();
        o.setValue(getArgumentsAsList().get(theIndex));
        return o;
    }

    public Object[] getArguments() {
        return getArgumentsAsList().toArray();
    }

    public Object getArgument(int theIndex) {
        return getArgumentsAsList().get(theIndex);
    }

    public AMessage setArguments(final Object... theArguments) {
        clearArguments();
        for(Object o:theArguments) {
            add(o);
        }
        return this;
    }




    /* Legacy methods, backwards compatibility, deprecated, don't use. */

    static public byte[] makeBlob(final byte[] b) {
        /* TODO check implemetation or re-implement or delete */
        final int tLength = b.length;
        byte[] b1 = Bytes.toBytes(tLength);
        b1 = Bytes.append(b1, b);
        final int t = tLength % 4;
        if (t != 0) {
            b1 = Bytes.append(b1, new byte[4 - t]);
        }
        return b1;
    }

    private String arrayToString(Object[] os) {
        /* TODO check or re-implement */
        String s = "[";
        for (Object o : os) {
            s += o.toString() + ",";
        }
        s = s.replaceAll(",$", "");
        s += "]";
        return s;
    }


    public void print() {
        /* TODO re-implement */
    }

    public int intValue(int theIndex) {
        /* TODO check or re-implement */
        return i(getObjectAt(theIndex));
    }

    public float floatValue(int theIndex) {
        /* TODO check or re-implement */
        return f(getObjectAt(theIndex));
    }

    public char charValue(int theIndex) {
        /* TODO check or re-implement */
        return c(getObjectAt(theIndex));
    }

    public double doubleValue(int theIndex) {
        /* TODO check or re-implement */
        return d(getObjectAt(theIndex));
    }

    public long longValue(int theIndex) {
        /* TODO check or re-implement */
        return l(getObjectAt(theIndex));
    }

    public boolean booleanValue(int theIndex) {
        /* TODO check or re-implement */
        return b(getObjectAt(theIndex));
    }

    public String stringValue(int theIndex) {
        /* TODO check or re-implement */
        return s(getObjectAt(theIndex));
    }

    public String[] stringValues(int theStart,
                                 int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        String[] arr = new String[n];
        for (int i = 0; i < n; i++) {
            arr[i] = stringValue(theStart + i);
        }
        return arr;
    }

    public int[] intValues(int theStart,
                           int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = intValue(theStart + i);
        }
        return arr;
    }

    public float[] floatValues(int theStart,
                               int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        float[] arr = new float[n];
        for (int i = 0; i < n; i++) {
            arr[i] = intValue(theStart + i);
        }
        return arr;
    }

    public char[] charValues(int theStart,
                             int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        char[] arr = new char[n];
        for (int i = 0; i < n; i++) {
            arr[i] = charValue(theStart + i);
        }
        return arr;
    }

    public double[] doubleValues(int theStart,
                                 int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = doubleValue(theStart + i);
        }
        return arr;
    }

    public long[] longValues(int theStart,
                             int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = longValue(theStart + i);
        }
        return arr;
    }

    public boolean[] booleanValues(int theStart,
                                   int theEnd) {
        /* TODO check or re-implement */
        int n = theEnd - theStart;
        boolean[] arr = new boolean[n];
        for (int i = 0; i < n; i++) {
            arr[i] = booleanValue(theStart + i);
        }
        return arr;
    }

    public byte[] bytesValue(int theIndex) {
        /* TODO check or re-implement */
        return OscUtils.bytes(getObjectAt(theIndex));
    }

    public byte[] blobValue(int theIndex) {
        /* TODO check or re-implement */
        return bytesValue(theIndex);
    }


    /* Deprecated methods */



    @Deprecated
    public AMessage(final int theAddrInt) {
        this();
        Log.log(Level.WARNING, "This constructor is no longer available, please choose a different one, see class OscMessage.");
    }

    @Deprecated
    public AMessage(final int theAddrInt,
                    final Object... theArguments) {
        this();
        Log.log(Level.WARNING, "This constructor is no longer available, please choose a different one, see class OscMessage.");
    }

    @Deprecated
    private void addArgumentAsObject(Object o) {
        getArgumentsAsList().add(o);
    }

    @Deprecated
    public AMessage clear() {
        clearArguments();
        return this;
    }


    @Deprecated
    public boolean checkTypetag(final String theTypetag) {
        return getTypetag().equals(theTypetag);
    }

    @Deprecated
    public boolean checkAddress(final String theAddress) {
        return getAddress().equals(theAddress);
    }

    @Deprecated
    public AMessage setAddress(final int theAddress) {
        Log.log(Level.WARNING, "setAddress(int) is deprecated.");
        return this;
    }


    @Deprecated
    public int addrInt() {
        Log.log(Level.WARNING, "addrInt() is deprecated and not implemented anymore.");
        return -1;
    }

    @Deprecated
    public byte[] getAddrPatternAsBytes() {
        /* TODO re-implement */
        return null;
    }

    @Deprecated
    public byte[] getTypetagAsBytes() {
        /* TODO re-implement */
        return null;
    }

    @Deprecated
    public byte[] bytes() {
        /* TODO re-implement */
        return null;
    }

    @Deprecated
    public AMessage add() {
        /* TODO re-implement NIL */
        return this;
    }


    @Deprecated
    public boolean isPlugged() {
        Log.log(Level.WARNING, "isPlugged() is deprecated and not implemented anymore.");
        return false;
    }

    @Deprecated
    public void printData() {
        Log.log(Level.WARNING, "printData() is deprecated and not implemented anymore.");
    }

    @Deprecated
    public void setAddrPattern(final String theAddress) {
        setAddress(theAddress);
    }

    @Deprecated
    public void setAddrPattern(final int theAddress) {
        setAddress(theAddress);
    }

    @Deprecated
    public boolean checkAddrPattern(final String theAddress) {
        return checkAddress(theAddress);
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


    @Deprecated
    public long getTimetag() {
        /* TODO re-implement */
        return 1L;
    }


}
