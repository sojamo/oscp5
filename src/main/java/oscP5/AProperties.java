package oscP5;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mainly used to store networking details and to change the size of the datagram packet for UDP communication.
 */
abstract class AProperties {

    static protected final Logger Log = Logger.getLogger(AProperties.class.getName());

    public abstract AProperties setDatagramSize(final int theSize);

    public abstract int getDatagramSize();

    public abstract int getPort();

    public abstract String getAddress();

    public abstract AProperties setPort(final int thePort);

    public abstract AProperties setAddress(final String theHost);

    AProperties() {
        super();
    }

    /* Legacy constructors and methods, backwards compatibility, do not use.*/

    @Deprecated
    public AProperties(final NetAddress theNetAddress) {
        Log.log(Level.WARNING, "constructor is deprecated. Use OscP5 to add remote destinations.");
    }

    @Deprecated
    public AProperties(int theReceiveAtPort) {
        Log.log(Level.WARNING, "constructor is deprecated. Use OscP5 to add remote destinations.");
    }

    @Deprecated
    public AProperties(OscEventListener theParent) {
        Log.log(Level.WARNING, "constructor is deprecated. Use OscP5 to subscribe listeners.");
    }

    @Deprecated
    public List<OscEventListener> listeners() {
        Log.log(Level.WARNING, "listeners() is deprecated, OscP5 class handles listeners.");
        return Collections.emptyList();
    }

    @Deprecated
    public AProperties setListeningPort(final int thePort) {
        return setPort(thePort);
    }

    @Deprecated
    public boolean sendStatus() {
        Log.log(Level.WARNING, "sendStatus() is deprecated.");
        return false;
    }

    @Deprecated
    public AProperties setRemoteAddress(final String theHostAddress,
                                        final int thePort) {
        Log.log(Level.WARNING, "setRemoteAddress() is deprecated, OscP5 class handles NetAddress destinations.");
        return this;
    }

    @Deprecated
    public AProperties setRemoteAddress(NetAddress theNetAddress) {
        Log.log(Level.WARNING, "setRemoteAddress() is deprecated, OscP5 class handles NetAddress destinations.");
        return this;
    }


    @Deprecated
    public AProperties setEventMethod(final String theEventMethod) {
        Log.log(Level.WARNING, "setEventMethod() is deprecated, OscP5 class handles event method and listeners.");
        return this;
    }

    @Deprecated
    public AProperties setNetworkProtocol(final int theProtocol) {
        Log.log(Level.WARNING, "setNetworkProtocol(), OscP5 currently only implements the UDP protocol.");
        return this;
    }


    @Deprecated
    public int listeningPort() {
        return getPort();
    }

    @Deprecated
    public NetAddress remoteAddress() {
        Log.log(Level.WARNING, "remoteAddress() is deprecated, OscP5 class handles NetAddress destinations.");
        return null;
    }

    @Deprecated
    public String host() {
        Log.log(Level.WARNING, "host() is deprecated, OscP5 class handles NetAddress destinations.");
        return null;
    }

    @Deprecated
    public int datagramSize() {
        return getDatagramSize();
    }

    @Deprecated
    public String eventMethod() {
        return "oscEvent";
    }

    @Deprecated
    public int networkProtocol() {
        return -1;
    }


    @Deprecated
    public void setSRSP(final boolean theFlag) {
        /** TODO re-implement */
    }

    @Deprecated
    public boolean srsp() {
        /** TODO re-implement */
        return false;
    }

    static public final boolean ON = true;
    static public final boolean OFF = false;
    static public final int UDP = 0;
    static public final int MULTICAST = 1;
    static public final int TCP = 2;


}
