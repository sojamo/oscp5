package netP5;

import java.util.logging.Level;
import java.util.logging.Logger;

@Deprecated
public class NetP5 {

    final static Logger Log = Logger.getLogger(NetP5.class.getName());

    static public UdpClient createUdpClient(String theAddress,
                                            final int thePort) {
        Log.log(Level.WARNING,"createUdpClient() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

    static public UdpClient createUdpClient(final int thePort) {
        Log.log(Level.WARNING,"createUdpClient() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

    static public UdpServer createUdpServer(final int thePort,
                                            final int theDatagramSize) {
        Log.log(Level.WARNING,"createUdpServer() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

    static public UdpServer createUdpServer(final String theHost,
                                            final int thePort,
                                            final int theDatagramSize) {
        Log.log(Level.WARNING,"createUdpClient() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

    static public TcpServer createTcpServer(final int thePort) {
        Log.log(Level.WARNING,"createTcpServer() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

    static public TcpClient createTcpClient(final int thePort) {
        Log.log(Level.WARNING,"createTcpClient() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

    static public TcpClient createTcpClient(final String theClient,
                                            final int thePort) {
        Log.log(Level.WARNING,"createTcpClient() is deprecated, netP5 currently only implements the UDP protocol.");
        return null;
    }

}
