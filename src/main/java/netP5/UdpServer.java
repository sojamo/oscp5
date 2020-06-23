/**
 * A network library for processing which supports UDP, TCP and Multicast.
 * 
 * ##copyright##
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 * 
 * @author ##author##
 * @modified ##date##
 * @version ##version##
 */

package netP5;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Logger;

public final class UdpServer extends Observable implements Transmitter {

	final static Logger LOGGER = Logger.getLogger(UdpServer.class.getName());

	public UdpServer(final int thePort, final int theDatagramSize) {
	}

	public UdpServer(final String theHost, final int thePort, final int theDatagramSize) {
	}

	public boolean close() {
		return false;
	}

	public boolean send(byte[] theContent) {
		/** TODO re-implement */
		return false;
	}

	public boolean send(byte[] theContent, Collection<InetSocketAddress> theAddress) {
		/** TODO re-implement */
		return false;
	}

	public boolean send(byte[] theContent, String theHost, int thePort) {
		/** TODO re-implement */
		return false;
	}

	public boolean send(byte[] theContent, SocketAddress... theAddress) {
		/** TODO re-implement */
		return false;
	}

	/* TODO consider to use java.util.concurrent.Executor here instead of Thread. */

}
