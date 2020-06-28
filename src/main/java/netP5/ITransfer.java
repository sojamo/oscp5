package netP5;

import oscP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscPacket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.List;

public interface ITransfer {

    boolean send(byte[] theContent);

    boolean send(byte[] theContent,
                 Collection<InetSocketAddress> theAddress);

    boolean send(byte[] theContent,
                 String theHost,
                 int thePort);

    boolean send(byte[] theContent,
                 SocketAddress... theAddress);

    boolean close();

    void send(NetAddress theIAddress,
              OscPacket thePacket);

    void send(NetAddress theIAddress,
              byte[] theBytes);

    void process(byte[] theData,
                 final NetAddress theSender);

    void immediately(OscMessage theMessage);

    void later(OscMessage theMessage,
               long theMillis);

    List<OscMessage> consume();

    boolean isRunning();

}
