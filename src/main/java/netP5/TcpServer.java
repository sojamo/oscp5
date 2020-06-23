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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */

package netP5;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

public final class TcpServer extends Observable implements Transmitter {

	public TcpServer(String theIP, int thePort) {
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

	public void write(byte[] buffer, SelectionKey key) {
		/** TODO re-implement */
	}

	public void write(byte[] buffer, List<SelectionKey> keys) {
		/** TODO re-implement */
	}

	public void write(byte[] buffer, SelectionKey... keys) {
		/** TODO re-implement */
	}

}