package oscP5;

import java.nio.channels.SocketChannel;

public abstract class APacket {

    public abstract byte[] getBytes();

    public SocketChannel tcpConnection()
    {
        /** TODO re-implement */
        return null;
    }

    public Object remoteChannel()
    {
        /** TODO re-implement */
        return null;
    }

    public int port()
    {
        /** TODO re-implement */
        return -1;
    }

    public NetAddress netAddress()
    {
        return null;
    }

    public String getAddress()
    {
        return null;
    }

    @Deprecated
    public NetAddress netaddress()
    {
        return netAddress();
    }

}
