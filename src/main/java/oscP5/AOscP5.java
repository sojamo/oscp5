package oscP5;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import netP5.NetAddressList;
import netP5.NetInfo;
import netP5.TcpClient;

public abstract class AOscP5 {

    static public boolean DEBUG = false;
    public final static boolean ON = OscProperties.ON;
    public final static boolean OFF = OscProperties.OFF;
    static protected final Logger Log = Logger.getLogger(OscP5.class.getName());
    /*
     * a static variable used when creating an oscP5 instance
     * with a specified network protocol.
     */
    public final static int UDP = OscProperties.UDP;

    /*
     * a static variable used when creating an oscP5 instance
     * with a specified network protocol.
     */
    public final static int MULTICAST = OscProperties.MULTICAST;

    /*
     * a static variable used when creating an oscP5 instance
     * with a specified network protocol.
     */
    public final static int TCP = OscProperties.TCP;

    public AOscP5() {
        /**
         * TODO currently only serves the implementing class to call
         * the super constructor
         */
    }

    @Deprecated
    public AOscP5(final Object theParent,
            final int theListeningPort,
            final int theProtocol) {
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }

    @Deprecated
    public AOscP5(final Object theParent,
            final String theRemoteAddress,
            final int theRemotePort,
            final int theProtocol) {
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }

    @Deprecated
    public AOscP5(final Object theParent,
            final OscProperties theProperties) {
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }

    public void dispose() {
        /** TODO re-implement */
    }

    public void stop() {
        /** TODO re-implement */
    }

    public void addListener(final OscEventListener theListener) {
        /** TODO re-implement */
    }

    public void removeListener(final OscEventListener theListener) {
        /** TODO re-implement */
    }

    public List<OscEventListener> listeners() {
        /** TODO re-implement */
        return null;
    }

    public static void flush(final NetAddress theNetAddress,
            final byte[] theBytes) {
        /** TODO re-implement */
    }

    public void plug(final Object theObject,
            final String theMethodName,
            final String theAddrPattern,
            final String theTypeTag) {
        /** TODO re-implement */
    }

    public void plug(final Object theObject,
            final String theMethodName,
            final String theAddrPattern) {
        /** TODO re-implement */
    }

    public void process(final Object o) {
        /** TODO re-implement */
    }

    public OscProperties properties() {
        /** TODO re-implement */
        return null;
    }

    public boolean isBroadcast() {
        /** TODO re-implement */
        return false;
    }

    public String ip() {
        /** TODO check or re-implement */
        return NetInfo.getHostAddress();
    }

    public void send(final OscPacket thePacket) {
        /** TODO re-implement */
    }

    public void send(final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    public void send(final NetAddress theNetAddress,
            final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    public void send(final NetAddress theNetAddress,
            final OscPacket thePacket) {
        /** TODO re-implement */
    }

    public void send(final List<NetAddress> theList,
            final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    public void send(final List<NetAddress> theList,
            final OscPacket thePacket) {
        /** TODO re-implement */
    }

    public void send(final NetAddressList theNetAddressList,
            final OscPacket thePacket) {
        /** TODO re-implement */
    }

    public void send(final NetAddressList theNetAddressList,
            final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    static public byte[] serialize(final Object o) {
        /** TODO re-implement */
        /** TODO see OscUtils */
        return null;
    }

    static public byte[] serialize(final Serializable o) {
        /** TODO re-implement */
        /** TODO see OscUtils */
        return null;
    }

    static public Object deserialize(final byte[] theBytes) {
        /** TODO re-implement */
        /** TODO see OscUtils */
        return null;
    }

    static public void print(final Object... strs) {
        for (final Object str : strs) {
            System.out.print(str + " ");
        }
    }

    static public void println(final Object... strs) {
        print(strs);
        System.out.println();
    }

    static public void debug(final Object... strs) {
        /** TODO re-implement */
    }

    static public void sleep(final long theMillis) {
        OscUtils.sleep(theMillis);
    }

    public String version() {
        return "?";
    }

    /* DEPRECATED methods and constructors. */

    @Deprecated
    public void process(final DatagramPacket thePacket,
            final int thePort) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final int thePort,
            final String theAddrPattern,
            final String theAddress,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final TcpClient theClient,
            final OscPacket thePacket) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final TcpClient theClient,
            final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final String theHost,
            final int thePort,
            final OscPacket thePacket) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final OscPacket thePacket,
            final String theHost,
            final int thePort) {
        /** TODO re-implement */
    }

    @Deprecated
    static public void flush(final NetAddress theNetAddress,
            final OscMessage theOscMessage) {
        /** TODO re-implement */
    }

    @Deprecated
    static public void flush(final NetAddress theNetAddress,
            final OscPacket theOscPacket) {
        /** TODO re-implement */
    }

    @Deprecated
    static public void flush(final NetAddress theNetAddress,
            final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    @Deprecated
    public static void flush(final OscMessage theOscMessage,
            final NetAddress theNetAddress) {
        flush(theOscMessage.getBytes(), theNetAddress);
    }

    @Deprecated
    public static void flush(final OscPacket theOscPacket,
            final NetAddress theNetAddress) {
        flush(theOscPacket.getBytes(), theNetAddress);
    }

    @Deprecated
    public static void flush(final String theAddrPattern,
            final Object[] theArguments,
            final NetAddress theNetAddress) {
        flush((new OscMessage(theAddrPattern, theArguments)).getBytes(), theNetAddress);
    }

    @Deprecated
    public static void flush(final byte[] theBytes,
            final NetAddress theNetAddress) {
        /** TODO re-implement */
    }

    @Deprecated
    public static void flush(final byte[] theBytes,
            final String theAddress,
            final int thePort) {
        flush(theBytes, new NetAddress(theAddress, thePort));
    }

    @Deprecated
    public static void flush(final OscMessage theOscMessage,
            final String theAddress,
            final int thePort) {
        flush(theOscMessage.getBytes(), new NetAddress(theAddress, thePort));
    }

    @Deprecated
    public AOscP5(final Object theParent,
            final String theHost,
            final int theSendToPort,
            final int theReceiveAtPort,
            final String theMethodName) {
        /** TODO re-implement */
    }

    @Deprecated
    public OscMessage newMsg(final String theAddrPattern) {
        return new OscMessage(theAddrPattern);
    }

    @Deprecated
    public OscBundle newBundle() {
        return new OscBundle();
    }

    @Deprecated
    public void disconnectFromTEMP() {
        /** TODO re-implement */
        /** TODO used by the monome library by jklabs */
    }

    @Deprecated
    public AOscP5(final Object theParent,
            final String theAddress,
            final int thePort) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final String theAddrPattern,
            final Object[] theArguments,
            final NetAddress theNetAddress) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final String theAddrPattern,
            final Object[] theArguments,
            final NetAddressList theNetAddressList) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final String theAddrPattern,
            final Object[] theArguments,
            final String theAddress,
            final int thePort) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final String theAddrPattern,
            final Object[] theArguments,
            final TcpClient theClient) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final OscPacket thePacket,
            final NetAddress theNetAddress) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final OscPacket thePacket,
            final NetAddressList theNetAddressList) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final String theAddress,
            final int thePort,
            final String theAddrPattern,
            final Object... theArguments) {
        /** TODO re-implement */
    }

    @Deprecated
    public void send(final OscPacket thePacket,
            final TcpClient theClient) {
    }

    @Deprecated
    public boolean send(final OscPacket thePacket,
            final Object theRemoteSocket) {
        /** TODO re-implement */
        return false;
    }

    @Deprecated
    public static void setLogStatus(final int theIndex,
            final int theValue) {
    }

    @Deprecated
    public static void setLogStatus(final int theValue) {
    }

    @Deprecated
    public NetInfo netInfo() {
        return new NetInfo();
    }
}
