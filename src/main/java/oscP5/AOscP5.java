package oscP5;

import netP5.NetAddressList;
import netP5.NetInfo;
import netP5.TcpClient;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static oscP5.OscUtils.hexDigits;

abstract class AOscP5 {

    static protected final Logger Log = Logger.getLogger(AOscP5.class.getName());

    public AOscP5() {
    }

    /* abstract methods to be implemented by extending class, this should be the remaining API for OscP5 */

    public abstract void dispose();

    public abstract OscEventListener addListener(final OscEventListener theListener);

    public abstract void removeListener(final OscEventListener theListener);

    public abstract List<OscEventListener> getListeners();

    public abstract void addDestination(NetAddress theDestination);

    public abstract void removeDestination(NetAddress theDestination);

    public abstract void removeDestination(String theAddress, int thePort);

    public abstract List<NetAddress> getDestinations();

    public abstract void broadcast(OscPacket thePacket);

    public abstract String version();

    public abstract OscProperties getProperties();

    public abstract void send(final NetAddress theNetAddress,
                              final String theAddress,
                              final Object... theArguments);

    public abstract void send(final NetAddress theNetAddress,
                              final OscPacket thePacket);


    public abstract void send(final List<NetAddress> theList,
                              final String theAddress,
                              final Object... theArguments);

    public abstract void send(final List<NetAddress> theList,
                              final OscPacket thePacket);

    public abstract void plug(final Object theObject,
                              final String theMethodName,
                              final String theAddress,
                              final String theTypeTag);

    public abstract void plug(final Object theObject,
                              final String theMethodName,
                              final String theAddress);


    @Deprecated
    public void send(final NetAddressList theNetAddressList,
                     final OscPacket thePacket) {
        /* TODO re-implement */
    }

    @Deprecated
    public void send(final NetAddressList theNetAddressList,
                     final String theAddress,
                     final Object... theArguments) {
        /* TODO re-implement */
    }


    static public byte[] serialize(final Object o) {
        /* TODO re-implement */
        /* TODO see OscUtils */
        Log.log(Level.WARNING, "OscP5.serialize() is currently not active.");
        return null;
    }

    static public byte[] serialize(final Serializable o) {
        /* TODO re-implement */
        /* TODO see OscUtils */
        Log.log(Level.WARNING, "OscP5.serialize() is currently not active.");
        return null;
    }

    static public Object deserialize(final byte[] theBytes) {
        /* TODO re-implement */
        /* TODO see OscUtils */
        Log.log(Level.WARNING, "OscP5.deserialize() is currently not active.");
        return null;
    }


    static public void printBytes(final byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            System.out.println((char) byteArray[i] + "(" + hexDigits[byteArray[i] >>> 4 & 0xf] + " " + hexDigits[byteArray[i] & 0xf] + ") ");
            if ((i + 1) % 4 == 0) {
                System.out.print("\n");
            }
        }
    }

    /* Legacy constructors and methods, backwards compatibility, do not use.*/

    @Deprecated
    public AOscP5(final Object theParent,
                  final int theListeningPort,
                  final int theProtocol) {
        this();
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }

    @Deprecated
    public AOscP5(final Object theParent,
                  final String theRemoteAddress,
                  final int theRemotePort,
                  final int theProtocol) {
        this();
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }

    @Deprecated
    public AOscP5(final Object theParent,
                  final String theHost,
                  final int theSendToPort,
                  final int theReceiveAtPort,
                  final String theMethodName) {
        this();
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }


    @Deprecated
    public AOscP5(final Object theApp,
                  final String theAddress,
                  final int thePort) {
        this();
        Log.log(Level.WARNING, "constructor deprecated, please instantiate OscP5 with a different constructor.");
    }


    @Deprecated
    public void process(final Object o) {
        Log.log(Level.WARNING, "OscP5.process(Object) is deprecated and not active anymore.");
    }

    @Deprecated
    public OscProperties properties() {
        return getProperties();
    }

    @Deprecated
    public boolean isBroadcast() {
        Log.log(Level.WARNING, "OscP5.isBroadcast() is deprecated and not active anymore.");
        return false;
    }

    @Deprecated
    public String ip() {
        /* TODO check or re-implement */
        return NetInfo.getHostAddress();
    }

    @Deprecated
    static public void print(final Object... strs) {
        for (final Object str : strs) {
            System.out.print(str + " ");
        }
    }

    @Deprecated
    static public void println(final Object... strs) {
        print(strs);
        System.out.println();
    }

    @Deprecated
    static public void debug(final Object... strs) {
        Log.log(Level.WARNING, "OscP5.debug() is deprecated and not active anymore.");
    }

    @Deprecated
    static public void sleep(final long theMillis) {
        OscUtils.sleep(theMillis);
    }

    @Deprecated
    public List<OscEventListener> listeners() {
        Log.log(Level.WARNING, "OscP5.listeners() is deprecated and not active anymore, use OscP5.getListeners() instead.");
        return new ArrayList<OscEventListener>(getListeners());
    }

    @Deprecated
    public void stop() {
        dispose();
    }

    @Deprecated
    public void process(final DatagramPacket thePacket,
                        final int thePort) {
        Log.log(Level.WARNING, "OscP5.process() is deprecated and not active anymore.");
    }

    @Deprecated
    public void send(final int thePort,
                     final String theAddress,
                     final String theNetAddress,
                     final Object... theArguments) {
        NetAddress addr = new NetAddress(theNetAddress, thePort);
        OscMessage m = new OscMessage(theAddress);
        m.add(theArguments);
        send(addr, m);
    }

    @Deprecated
    public void send(final TcpClient theClient,
                     final OscPacket thePacket) {
        Log.log(Level.WARNING, "OscP5.send(TcpClient, OscPacket) is deprecated and not active anymore.");
    }

    @Deprecated
    public void send(final TcpClient theClient,
                     final String theAddress,
                     final Object... theArguments) {
        Log.log(Level.WARNING, "OscP5.send(TcpClient, String, Object...) is deprecated and not active anymore.");
    }

    @Deprecated
    public void send(final String theHost,
                     final int thePort,
                     final OscPacket thePacket) {
        send(new NetAddress(theHost, thePort), thePacket);
    }

    @Deprecated
    public void send(final OscPacket thePacket,
                     final String theHost,
                     final int thePort) {
        NetAddress addr = new NetAddress(theHost, thePort);
        send(addr, thePacket);
    }


    @Deprecated
    public void send(final String theAddress,
                     final Object[] theArguments,
                     final NetAddress theNetAddress) {
        OscMessage m = new OscMessage(theAddress);
        m.add(theArguments);
        send(theNetAddress, m);
    }

    @Deprecated
    public void send(final String theAddress,
                     final Object[] theArguments,
                     final NetAddressList theNetAddressList) {
        Log.log(Level.WARNING, "OscP5.send(String, Objec[], NetAddressList) is deprecated and not active anymore.");
    }

    @Deprecated
    public void send(final String theAddress,
                     final Object[] theArguments,
                     final String theNetAddress,
                     final int thePort) {
        NetAddress addr = new NetAddress(theNetAddress, thePort);
        OscMessage m = new OscMessage(theAddress);
        m.add(theArguments);
        send(addr, m);
    }

    @Deprecated
    public void send(final String theAddress,
                     final Object[] theArguments,
                     final TcpClient theClient) {
        Log.log(Level.WARNING, "OscP5.send(String, Object[], TcpClient) is deprecated and not active anymore.");
    }

    @Deprecated
    public void send(final OscPacket thePacket,
                     final NetAddress theNetAddress) {
        send(theNetAddress, thePacket);
    }

    @Deprecated
    public void send(final OscPacket thePacket,
                     final NetAddressList theNetAddressList) {
        Log.log(Level.WARNING, "OscP5.send(OscPacket, NetAddressList) is deprecated and not active anymore.");
    }

    @Deprecated
    public void send(final String theNetAddress,
                     final int thePort,
                     final String theAddress,
                     final Object... theArguments) {
        NetAddress addr = new NetAddress(theNetAddress, thePort);
        OscMessage m = new OscMessage(theAddress);
        m.add(theArguments);
        send(addr, m);
    }

    @Deprecated
    public void send(final OscPacket thePacket,
                     final TcpClient theClient) {
        Log.log(Level.WARNING, "OscP5.send(OscPacket, TcpClient) is deprecated and not active anymore.");
    }

    @Deprecated
    public boolean send(final OscPacket thePacket,
                        final Object theRemoteSocket) {
        Log.log(Level.WARNING, "OscP5.send(OscPacket, Object) is deprecated and not active anymore.");
        return false;
    }


    @Deprecated
    public void send(final OscPacket thePacket) {
        Log.log(Level.WARNING, "No destination NetAddress specified, please use oscP5.send(theNetAddress, thePacket) instead.");
    }

    @Deprecated
    public void send(final String theAddress,
                     final Object... theArguments) {
        OscMessage m = new OscMessage(theAddress);
        m.add(theArguments);
        send(m);
    }

    @Deprecated
    public static void flush(final NetAddress theNetAddress,
                             final byte[] theBytes) {
        Log.log(Level.WARNING, "OscP5.flush() is deprecated and not active anymore.");
    }

    @Deprecated
    static public void flush(final NetAddress theNetAddress,
                             final OscMessage theOscMessage) {
        Log.log(Level.WARNING, "OscP5.flush() is deprecated and not active anymore.");
    }

    @Deprecated
    static public void flush(final NetAddress theNetAddress,
                             final OscPacket theOscPacket) {
        Log.log(Level.WARNING, "OscP5.flush() is deprecated and not active anymore.");
    }

    @Deprecated
    static public void flush(final NetAddress theNetAddress,
                             final String theAddress,
                             final Object... theArguments) {
        Log.log(Level.WARNING, "OscP5.flush() is deprecated and not active anymore.");
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
    public static void flush(final String theAddress,
                             final Object[] theArguments,
                             final NetAddress theNetAddress) {
        flush((new OscMessage(theAddress, theArguments)).getBytes(), theNetAddress);
    }

    @Deprecated
    public static void flush(final byte[] theBytes,
                             final NetAddress theNetAddress) {
        Log.log(Level.WARNING, "OscP5.flush() is deprecated and not active anymore.");
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
    public OscMessage newMsg(final String theAddress) {
        return new OscMessage(theAddress);
    }

    @Deprecated
    public OscBundle newBundle() {
        return new OscBundle();
    }

    @Deprecated
    public void disconnectFromTEMP() {
        Log.log(Level.WARNING, "method disconnectFromTEMP() is deprecated.");
    }


    @Deprecated
    public static void setLogStatus(final int theIndex,
                                    final int theValue) {
        Log.log(Level.WARNING, "OscP5.setLogStatus() is deprecated, please use OscP5.setLevel(java.util.logging.Level) instead.");
    }

    @Deprecated
    public static void setLogStatus(final int theValue) {
        Log.log(Level.WARNING, "OscP5.setLogStatus() is deprecated, please use OscP5.setLevel(java.util.logging.Level) instead.");
    }

    @Deprecated
    public NetInfo netInfo() {
        return new NetInfo();
    }

    @Deprecated
    static public boolean DEBUG = false;

    /* Legacy and backwards compatibility, don't use. */
    public final static boolean ON = OscProperties.ON;
    public final static boolean OFF = OscProperties.OFF;
    public final static int UDP = OscProperties.UDP;
    public final static int MULTICAST = OscProperties.MULTICAST;
    public final static int TCP = OscProperties.TCP;
}
