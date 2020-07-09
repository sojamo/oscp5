package oscP5;

import netP5.ANetAddress;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Holds the network address and port number a network packet should be sent to.
 */
public class NetAddress extends ANetAddress {

    static public String DEFAULT_ADDR = "0.0.0.0";
    static public final String LOCALHOST_ADDR = "127.0.0.1";
    private final InetSocketAddress socketAddress;

    public NetAddress(final int thePort) {
        this(LOCALHOST_ADDR, thePort);
    }

    public NetAddress(final String theAddress,
                      final int thePort) {
        super();
        socketAddress = new InetSocketAddress(theAddress, thePort);
    }

    public NetAddress(final NetAddress theNetAddress) {
        this(theNetAddress.getAddress(), theNetAddress.getPort());
    }

    public NetAddress(final InetAddress theInetAddress,
                      final int thePort) {
        super();
        socketAddress = new InetSocketAddress(theInetAddress, thePort);
    }

    @Override
    public InetAddress getInetAddress() {
        return socketAddress.getAddress();
    }

    @Override
    public String getAddress() {
        return socketAddress.getAddress().getHostAddress();
    }

    @Override
    public int getPort() {
        return socketAddress.getPort();
    }

    public String toString() {
        return "{class: NetAddress"+
                ", address: "+getAddress() +
                ", port: "+ getPort() +
                "}";
    }
}