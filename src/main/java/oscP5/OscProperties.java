package oscP5;

import netP5.UDPTransfer;

import java.util.logging.Logger;

/**
 * Mainly used to store networking details and to change the size of the datagram packet for UDP communication.
 */
public class OscProperties extends AProperties {

    static protected final Logger Log = Logger.getLogger(OscProperties.class.getName());
    private int port = 12000;
    private String address = NetAddress.DEFAULT_ADDR;
    private int datagramPacketSize = UDPTransfer.DEFAULT_PACKET_SIZE;

    public OscProperties() {
        super();
    }

    public OscProperties setDatagramSize(final int theSize) {
        datagramPacketSize = theSize;
        return this;
    }

    public int getDatagramSize() {
        return datagramPacketSize;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public OscProperties setPort(final int thePort) {
        port = thePort;
        return this;
    }

    public OscProperties setAddress(final String theHost) {
        address = theHost;
        return this;
    }

    public String toString() {
        return "{class: OscProperties" +
                ", host: "+ getAddress() +
                ", port: "+getPort() +
                ", datagramSize: "+ getDatagramSize() +
                "}";
    }

}
