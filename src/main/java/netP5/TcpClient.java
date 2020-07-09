package netP5;

import oscP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscPacket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.List;

@Deprecated
public final class TcpClient implements Runnable, ITransfer {

    public TcpClient(final String theHost,
                     final int thePort) {
        /* TODO re-implement */
    }

    public TcpClient(final int thePort) {
        /* TODO re-implement */
    }

    public void join() throws InterruptedException {
        /* TODO re-implement */
    }

    public void stop() throws IOException, InterruptedException {
        /* TODO re-implement */
    }

    public boolean close() {
        /* TODO re-implement */
        return false;
    }

    public boolean isConnected() {
        /* TODO re-implement */
        return false;
    }

    public boolean send(final byte[] theBytes) {
        /* TODO re-implement */
        return false;
    }

    public boolean send(final byte[] theContent,
                        final String theHost,
                        final int thePort) {
        /* TODO re-implement */
        return false;
    }

    public void send(final ByteBuffer buffer) throws InterruptedException, IOException {
    }

    protected void onRead(final ByteBuffer buffer,
                          final SelectionKey theKey) {
        /* TODO re-implement */
    }

    protected void onConnected(final SelectionKey theKey) {
        /* TODO re-implement */
    }

    protected void onDisconnected(final SelectionKey theKey) {
        /* TODO re-implement */
    }

    public void run() {
    }

    public SocketAddress getAddress() {
        /* TODO re-implement */
        return null;
    }

    public String toString() {
        /* TODO re-implement */
        return null;
    }

    @Override
    public void send(final NetAddress theIAddress,
                     final OscPacket thePacket) {
        // TODO Auto-generated method stub

    }

    @Override
    public void send(final NetAddress theIAddress,
                     final byte[] theBytes) {
        // TODO Auto-generated method stub

    }

    @Override
    public void process(final byte[] theData,
                        final NetAddress theSender) {
        // TODO Auto-generated method stub

    }

    @Override
    public void immediately(final OscMessage theMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void later(final OscMessage theMessage,
                      final long theMillis) {
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
    public boolean send(final byte[] theContent,
                        final Collection<InetSocketAddress> theAddress) {
        /* TODO re-implement */
        return false;
    }

    @Deprecated
    public boolean send(final byte[] theContent,
                        final SocketAddress... theAddress) {
        // TODO Auto-generated method stub
        return false;
    }


}