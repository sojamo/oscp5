package netP5;

import oscP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscPacket;

import java.util.List;

public interface ITransfer {

    boolean close();

    void send(NetAddress theIAddress, OscPacket thePacket);

    void send(NetAddress theIAddress, byte[] theBytes);

    void process(byte[] theData, NetAddress theSender);

    void immediately(OscMessage theMessage);

    void later(OscMessage theMessage, long theMillis);

    List<OscMessage> publish();

    boolean isRunning();

}
