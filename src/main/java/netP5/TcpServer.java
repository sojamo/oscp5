package netP5;

import oscP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscPacket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.List;

@Deprecated
public final class TcpServer implements ITransfer {

    public TcpServer(String theIP,
                     int thePort) {
        /** TODO re-implement */
    }

    public TcpServer(int thePort) {
        /** TODO re-implement */
    }

    public Object[] getClients() {
        /** TODO re-implement */
        return null;
    }

    public boolean close() {
        /** TODO re-implement */
        return false;
    }

    public boolean send(byte[] buffer) {
        /** TODO re-implement */
        return false;
    }

    public boolean send(byte[] theContent,
                        String theHost,
                        int thePort) {
        /** TODO re-implement */
        return false;
    }

    public void write(byte[] buffer,
                      SelectionKey key) {
        /** TODO re-implement */
    }

    public void write(byte[] buffer,
                      List<SelectionKey> keys) {
        /** TODO re-implement */
    }

    public void write(byte[] buffer,
                      SelectionKey... keys) {
        /** TODO re-implement */
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
}