package netP5;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import oscP5.OscPacket;
import oscP5.NetAddress;
import oscP5.OscMessage;

public class Multicast implements ITransfer {

	public Multicast(final String theGroup,
			final int thePort)
	{
		this(theGroup, thePort, 256, 1);
	}

	public Multicast(final String theGroup,
			final int thePort,
			final int theDatagramSize,
			final int theTTL)
	{
		/** TODO re-implement */
	}

	public String getGroup()
	{
		/** TODO re-implement */
		return null;
	}

	public List<String> getSelf()
	{
		/** TODO re-implement */
		return null;
	}

	public boolean isSelf(final Map<String, Object> m)
	{
		/** TODO re-implement */
		return false;
	}

	public Multicast setTimeToLive(final int theTTL)
	{
		/** TODO re-implement */
		return this;
	}

	public boolean send(final byte[] theContent)
	{
		/** TODO re-implement */
		return false;
	}

	public boolean send(final byte[] theContent,
			final Collection<InetSocketAddress> theAddress)
	{
		/** not implemented, use send(byte[]) */
		return false;
	}

	public boolean send(final byte[] theContent,
			final String theHost,
			final int thePort)
	{
		/** not implemented, use send(byte[]) */
		return false;
	}

	public boolean send(final byte[] theContent,
			final SocketAddress... theAddress)
	{
		/** not implemented, use send(byte[]) */
		return false;
	}

	public boolean close()
	{
		/** TODO re-implement */
		return false;
	}

	@Override
	public void send(NetAddress theIAddress,
			OscPacket thePacket)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void send(NetAddress theIAddress,
			byte[] theBytes)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void process(byte[] theData,
			NetAddress theSender)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void immediately(OscMessage theMessage)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void later(OscMessage theMessage,
			long theMillis)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<OscMessage> consume()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRunning()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
