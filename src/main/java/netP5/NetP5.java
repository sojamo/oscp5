package netP5;

import java.util.logging.Logger;

public class NetP5 {

	final static Logger Log = Logger.getLogger(NetP5.class.getName());

	static public UdpClient createUdpClient(String theAddress,
			final int thePort)
	{
		/** TODO re-implement */
		return null;
	}

	static public UdpClient createUdpClient(final int thePort)
	{
		/** TODO re-implement */
		return null;
	}

	static public UdpServer createUdpServer(final int thePort,
			final int theDatagramSize)
	{
		/** TODO re-implement */
		return null;
	}

	static public UdpServer createUdpServer(final String theHost,
			final int thePort,
			final int theDatagramSize)
	{
		/** TODO re-implement */
		return null;
	}

	static public TcpServer createTcpServer(final int thePort)
	{
		/** TODO re-implement */
		return null;
	}

	static public TcpClient createTcpClient(final int thePort)
	{
		/** TODO re-implement */
		return null;
	}

	static public TcpClient createTcpClient(final String theClient,
			final int thePort)
	{
		/** TODO re-implement */
		return null;
	}

}
