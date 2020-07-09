package netP5;

import oscP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscPacket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Deprecated
public final class UdpServer implements ITransfer {

    final static Logger LOGGER = Logger.getLogger(UdpServer.class.getName());

    public UdpServer(final int thePort,
                     final int theDatagramSize) {
    }

    public UdpServer(final String theHost,
                     final int thePort,
                     final int theDatagramSize) {
    }

    public boolean close() {
        return false;
    }

    public boolean send(byte[] theContent) {
        /** TODO re-implement */
        return false;
    }

    public boolean send(byte[] theContent,
                        String theHost,
                        int thePort) {
        /** TODO re-implement */
        return false;
    }

    @Deprecated
    public boolean send(byte[] theContent,
                        Collection<InetSocketAddress> theAddress) {
        /** TODO re-implement */
        return false;
    }

    @Deprecated
    public boolean send(byte[] theContent,
                        SocketAddress... theAddress) {
        /** TODO re-implement */
        return false;
    }

    @Override
    public void send(NetAddress theIAddress,
                     OscPacket thePacket) {
        // TODO Auto-generated method stub

    }

    @Override
    public void send(NetAddress theIAddress,
                     byte[] theBytes) {
        // TODO Auto-generated method stub

    }

    @Override
    public void process(byte[] theData,
                        NetAddress theSender) {
        // TODO Auto-generated method stub

    }

    @Override
    public void immediately(OscMessage theMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void later(OscMessage theMessage,
                      long theMillis) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<OscMessage> publish() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRunning() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * TODO consider to use java.util.concurrent.Executor here
     * instead of Thread.
     */

}
