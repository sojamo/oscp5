package netP5;

import oscP5.NetAddress;
import oscP5.OscP5;
import oscP5.OscPacket;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPTransfer extends ATransfer {

    static public int DEFAULT_PACKET_SIZE = 1500;
    static private final Logger Log = Logger.getLogger(UDPTransfer.class.getName());
    final private int packetSize;
    private DatagramSocket socket;

    /**
     * DEFAULT_PACKET_SIZE
     * <p>
     * the Maximum Transition Unit (MTU, the packet size) is the
     * largest number of bytes and individual datagram can have
     * on a particular data communications link. For most
     * Ethernet networks this is set to 1500 bytes and this size
     * is used almost universally on access networks.
     */

    public UDPTransfer(final String theHostAddress,
                       final int thePort) {
        this(theHostAddress, thePort, DEFAULT_PACKET_SIZE);
    }

    public UDPTransfer(final String theHostAddress,
                       final int thePort,
                       final int thePacketSize) {
        super(2048);
        packetSize = thePacketSize;
        InetAddress networkAddress = null;
        try {
            networkAddress = InetAddress.getByName(theHostAddress);
        } catch (final Exception e) {
            Log.log(Level.WARNING, "Network address " + theHostAddress + " not available" + e.getMessage());
            OscP5.printStackTrace(e);
        }
        try {

            /* open a UDP connection */
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);

            if (networkAddress == null) {
                networkAddress = socket.getLocalAddress();
            }

            socket.bind(new InetSocketAddress(networkAddress, thePort));
            Log.log(Level.CONFIG, "Binding to address " + networkAddress + ":" + thePort);

            /*
             * alternatively consider to use java.nio.channels here
             * instead
             */

            try {
                Log.log(Level.CONFIG, "UDP socket running on port " + thePort);
                ExecutorService exec = Executors.newFixedThreadPool(1);
                exec.execute(new Runnable() {
                    public void run() {
                        final byte[] receiveData = new byte[packetSize];
                        final DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
                        try {
                            while (!socket.isClosed()) {
                                socket.receive(packet);
                                final byte[] data = new byte[packet.getLength()];
                                System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());
                                final NetAddress addr = new NetAddress(packet.getAddress(), packet.getPort());
                                process(data, addr);
                            }
                        } catch (final IOException e) {
                            Log.log(Level.INFO, "UDP socket running on port " + thePort + ", " + e.getMessage());
                            OscP5.printStackTrace(e);
                        }
                    }
                });

            } catch (final Exception e) {
                Log.log(Level.WARNING, "Can't create socket, " + e.getMessage());
                OscP5.printStackTrace(e);
            }
        } catch (final SocketException e1) {
            Log.log(Level.WARNING, "Can't create socket, " + e1.getMessage());
            OscP5.printStackTrace(e1);
        }
    }

    @Override
    public void send(final NetAddress theIAddress,
                     final OscPacket thePacket) {
        try {
            send(theIAddress, thePacket.getBytes());
        } catch (final NullPointerException npe) {
            Log.log(Level.WARNING, "Can't send OscPacket to " + theIAddress.getAddress() + ", " + npe.getMessage());
            OscP5.printStackTrace(npe);
        }
    }

    @Override
    public void send(final NetAddress theAddress,
                     final byte[] theBytes) {
        final DatagramPacket myPacket = new DatagramPacket(theBytes,
                theBytes.length,
                theAddress.getInetAddress(),
                theAddress.getPort());

        try {
            socket.send(myPacket);
        } catch (final Exception e) {
            Log.log(Level.WARNING, "Can't send bytes to " + myPacket.getAddress().getCanonicalHostName() + ", " + e.getMessage());
            OscP5.printStackTrace(e);
        }
    }

    @Override
    public boolean isRunning() {
        return !socket.isClosed();
    }

    @Override
    public boolean close() {
        socket.close();
        return true;
    }
}
