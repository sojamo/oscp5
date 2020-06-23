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

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class TcpClient extends Observable implements Runnable, Transmitter {

	/*
	 * adapted from http://bobah.net/d4d/source-code/networking/tcp-client-java-nio
	 */

	public TcpClient(String theHost, int thePort) {
		/** TODO re-implement */
	}

	public TcpClient(int thePort) {
		/** TODO re-implement */
	}

	public void join() throws InterruptedException {
		/** TODO re-implement */
	}

	public void stop() throws IOException, InterruptedException {
		/** TODO re-implement */
	}

	public boolean close() {
		/** TODO re-implement */
		return false;
	}

	public boolean isConnected() {
		/** TODO re-implement */
		return false;
	}

	public boolean send(byte[] theBytes) {
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

	public void send(ByteBuffer buffer) throws InterruptedException, IOException {
	}

	protected void onRead(ByteBuffer buffer, SelectionKey theKey) {
		/** TODO re-implement */
	}

	protected void onConnected(SelectionKey theKey) {
		/** TODO re-implement */
	}

	protected void onDisconnected(SelectionKey theKey) {
		/** TODO re-implement */
	}

	public void run() {
	}

	public SocketAddress getAddress() {
		/** TODO re-implement */
		return null;
	}

	public String toString() {
		/** TODO re-implement */
		return null;
	}

}