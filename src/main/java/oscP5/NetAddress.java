package oscP5;

import java.net.InetAddress;

import netP5.ANetAddress;

public class NetAddress extends ANetAddress {

    public NetAddress(final int thePort)
    {
        super(LOCALHOST_ADDR, thePort);
    }

    public NetAddress(final String theAddress,
            final int thePort)
    {
        super(theAddress, thePort);
    }

    public NetAddress(final NetAddress theNetAddress)
    {
        super(theNetAddress.getAddress(), theNetAddress.getPort());
    }

    public NetAddress(final InetAddress theInetAddress,
            final int thePort)
    {
        super(theInetAddress, thePort);
    }
}