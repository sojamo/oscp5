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

import java.util.logging.Logger;

public class NetP5 {

	final static Logger LOGGER = Logger.getLogger(NetP5.class.getName());

	static public UdpClient createUdpClient(String theHost, final int thePort) {
		/** TODO re-implement */
		return null;
	}

	static public UdpClient createUdpClient(final int thePort) {
		/** TODO re-implement */
		return null;
	}

	static public UdpServer createUdpServer(final int thePort, final int theDatagramSize) {
		/** TODO re-implement */
		return null;
	}

	static public UdpServer createUdpServer(final String theHost, final int thePort, final int theDatagramSize) {
		/** TODO re-implement */
		return null;
	}

	static public TcpServer createTcpServer(final int thePort) {
		/** TODO re-implement */
		return null;
	}

	static public TcpClient createTcpClient(final int thePort) {
		/** TODO re-implement */
		return null;
	}

	static public TcpClient createTcpClient(final String theHost, final int thePort) {
		/** TODO re-implement */
		return null;
	}

}
