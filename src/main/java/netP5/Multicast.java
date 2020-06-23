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

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Logger;

public class Multicast extends Observable implements Transmitter {

	public Multicast(final String theGroup, final int thePort) {
		this(theGroup, thePort, 256, 1);
	}

	public Multicast(final String theGroup, final int thePort, final int theDatagramSize, final int theTTL) {
		/** TODO re-implement */
	}

	public String getGroup() {
		/** TODO re-implement */
		return null;
	}

	public List<String> getSelf() {
		/** TODO re-implement */
		return null;
	}

	public boolean isSelf(final Map<String, Object> m) {
		/** TODO re-implement */
		return false;
	}

	public Multicast setTimeToLive(final int theTTL) {
		/** TODO re-implement */
		return this;
	}

	public boolean send(final byte[] theContent) {
		/** TODO re-implement */
		return false;
	}

	public boolean send(final byte[] theContent, final Collection<InetSocketAddress> theAddress) {
		/** not implemented, use send(byte[]) */
		return false;
	}

	public boolean send(final byte[] theContent, final String theHost, final int thePort) {
		/** not implemented, use send(byte[]) */
		return false;
	}

	public boolean send(final byte[] theContent, final SocketAddress... theAddress) {
		/** not implemented, use send(byte[]) */
		return false;
	}

	public boolean close() {
		/** TODO re-implement */
		return false;
	}

}
