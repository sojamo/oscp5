package netP5;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ANetAddress extends InetSocketAddress {

    static public final String LOCALHOST_ADDR = "localhost";
    static public String DEFAULT_ADDR = "0.0.0.0";

    /**
     * TODO re-implement
     */
    protected boolean isValid = true;

    public ANetAddress(final int thePort) {
        this(LOCALHOST_ADDR, thePort);
    }

    public ANetAddress(final String theAddress,
                       final int thePort) {
        super(theAddress, thePort);
    }

    public ANetAddress(final ANetAddress theNetAddress) {
        this(theNetAddress.getAddress(), theNetAddress.getPort());
    }

    public ANetAddress(final InetAddress theInetAddress,
                       final int thePort) {
        super(theInetAddress, thePort);
    }

    public String getHost() {
        return super.getHostName();
    }

    public InetAddress inetaddress() {
        return super.getAddress();
    }

    public String address() {
        return getHost();
    }

    public int port() {
        return getPort();
    }

    public boolean isvalid() {
        return isValid;
    }

    public String toString() {
        return "{ class: NetAddress, address: " + getHost() + ", port: " + getPort() + "}";
    }
}
