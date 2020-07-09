package oscP5;

import java.nio.channels.SocketChannel;

abstract class APacket {

    public abstract byte[] getBytes();

    public abstract NetAddress netAddress();

    public abstract String getAddress();

    public abstract int getPort();

    @Deprecated
    public SocketChannel tcpConnection() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
    public Object remoteChannel() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
    public int port() {
        return getPort();
    }

    @Deprecated
    public NetAddress netaddress() {
        return netAddress();
    }

}
