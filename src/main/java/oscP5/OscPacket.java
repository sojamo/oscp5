/**
 * An OSC (Open Sound Control) library for processing.
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

package oscP5;

import java.net.InetAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;

import netP5.Bytes;
import netP5.NetAddress;

public abstract class OscPacket extends OscPatcher {

	protected static final int SYSTEM = 1;
	protected static final int MESSAGE = 2;
	protected static final int BUNDLE = 3;

	private static int evaluatePacket(byte[] theBytes) {
		/** TODO check or re-implement */
		return ((theBytes.length > 0)
				? (Bytes.areEqual(OscBundle.BUNDLE_AS_BYTES, Bytes.copy(theBytes, 0, OscBundle.BUNDLE_AS_BYTES.length))
						? BUNDLE
						: MESSAGE)
				: SYSTEM);
	}

	public SocketChannel tcpConnection() {
		/** TODO re-implement */
		return null;
	}

	public Object remoteChannel() {
		/** TODO re-implement */
		return null;
	}

	public int port() {
		/** TODO re-implement */
		return -1;
	}

	public NetAddress netAddress() {
		return null;
	}

	public String getAddress() {
		return null;
	}

	public abstract byte[] getBytes();

	@Deprecated
	public NetAddress netaddress() {
		return netAddress();
	}

}
