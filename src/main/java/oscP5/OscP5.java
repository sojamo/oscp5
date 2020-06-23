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

package oscP5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import netP5.NetAddress;
import netP5.NetAddressList;
import netP5.NetInfo;
import netP5.NetP5;
import netP5.TcpClient;
import netP5.TcpServer;
import netP5.Transmitter;
import netP5.UdpClient;
import netP5.UdpServer;

/**
 * oscP5 is an osc implementation for the programming environment processing.
 * osc is the acronym for open sound control, a network protocol developed at
 * cnmat, uc berkeley. open sound control is a protocol for communication among
 * computers, sound synthesizers, and other multimedia devices that is optimized
 * for modern networking technology and has been used in many application areas.
 * for further specifications and application implementations please visit the
 * official osc site.
 * 
 */

public class OscP5 implements Observer {

	static public boolean DEBUG = false;
	final static Logger LOGGER = Logger.getLogger(OscP5.class.getName());
	public final static boolean ON = OscProperties.ON;
	public final static boolean OFF = OscProperties.OFF;

	/*
	 * a static variable used when creating an oscP5 instance with a specified
	 * network protocol.
	 */
	public final static int UDP = OscProperties.UDP;

	/*
	 * a static variable used when creating an oscP5 instance with a specified
	 * network protocol.
	 */
	public final static int MULTICAST = OscProperties.MULTICAST;

	/*
	 * a static variable used when creating an oscP5 instance with a specified
	 * network protocol.
	 */
	public final static int TCP = OscProperties.TCP;

	public static final String VERSION = "0.9.10";

	public OscP5(final Object theParent, final int theListeningPort) {
		/** TODO re-implement */
	}

	public OscP5(final Object theParent, final int theListeningPort, final int theProtocol) {
		/** TODO re-implement */
	}

	public OscP5(final Object theParent, final String theRemoteAddress, final int theRemotePort,
			final int theProtocol) {
		/** TODO re-implement */
	}

	public OscP5(final Object theParent, final OscProperties theProperties) {
		/** TODO re-implement */
	}

	public void update(final Observable ob, final Object map) {
		/** TODO re-implement */
	}

	public String version() {
		return VERSION;
	}

	public void dispose() {
		/** TODO re-implement */
	}

	public void stop() {
		/** TODO re-implement */
	}

	public void addListener(final OscEventListener theListener) {
		/** TODO re-implement */
	}

	public void removeListener(final OscEventListener theListener) {
		/** TODO re-implement */
	}

	public List<OscEventListener> listeners() {
		/** TODO re-implement */
		return null;
	}

	public static void flush(final NetAddress theNetAddress, final byte[] theBytes) {
		/** TODO re-implement */
	}

	public void plug(final Object theObject, final String theMethodName, final String theAddrPattern,
			final String theTypeTag) {
		/** TODO re-implement */
	}

	public void plug(final Object theObject, final String theMethodName, final String theAddrPattern) {
		/** TODO re-implement */
	}

	public void process(final Object o) {
		/** TODO re-implement */
	}

	public OscProperties properties() {
		/** TODO re-implement */
		return null;
	}

	public boolean isBroadcast() {
		/** TODO re-implement */
		return false;
	}

	public String ip() {
		/** TODO check or re-implement */
		return NetInfo.getHostAddress();
	}

	public void send(final OscPacket thePacket) {
		/** TODO re-implement */
	}

	public void send(final String theAddrPattern, final Object... theArguments) {
		/** TODO re-implement */
	}

	public void send(final NetAddress theNetAddress, final String theAddrPattern, final Object... theArguments) {
		/** TODO re-implement */
	}

	public void send(final NetAddress theNetAddress, final OscPacket thePacket) {
		/** TODO re-implement */
	}

	public void send(final List<NetAddress> theList, final String theAddrPattern, final Object... theArguments) {
		/** TODO re-implement */
	}

	public void send(final List<NetAddress> theList, final OscPacket thePacket) {
		/** TODO re-implement */
	}

	public boolean send(final OscPacket thePacket, final Object theRemoteSocket) {
		/** TODO re-implement */
		return false;
	}

	static final public byte[] serialize(final Object o) {
		/** TODO re-implement */
		/** TODO see OscUtils */
		return null;
	}

	static final public byte[] serialize(final Serializable o) {
		/** TODO re-implement */
		/** TODO see OscUtils */
		return null;
	}

	static final public Object deserialize(final byte[] theBytes) {
		/** TODO re-implement */
		/** TODO see OscUtils */
		return null;
	}

	public void send(final int thePort, final String theAddrPattern, final String theAddress,
			final Object... theArguments) {
		/** TODO re-implement */
	}

	public void send(final TcpClient theClient, final OscPacket thePacket) {
		/** TODO re-implement */
	}

	public void send(final TcpClient theClient, final String theAddrPattern, final Object... theArguments) {
		/** TODO re-implement */
	}

	public void send(final String theHost, final int thePort, final OscPacket thePacket) {
		/** TODO re-implement */
	}

	public void send(final OscPacket thePacket, final String theHost, final int thePort) {
		/** TODO re-implement */
	}

	static public void flush(final NetAddress theNetAddress, final OscMessage theOscMessage) {
		/** TODO re-implement */
	}

	static public void flush(final NetAddress theNetAddress, final OscPacket theOscPacket) {
		/** TODO re-implement */
	}

	static public void flush(final NetAddress theNetAddress, final String theAddrPattern,
			final Object... theArguments) {
		/** TODO re-implement */
	}

	static public void print(final Object... strs) {
		for (final Object str : strs) {
			System.out.print(str + " ");
		}
	}

	static public void println(final Object... strs) {
		print(strs);
		System.out.println();
	}

	static public void debug(final Object... strs) {
		/** TODO re-implement */
	}

	static public void sleep(final long theMillis) {
		/** TODO check or re-implement */
		try {
			Thread.sleep(theMillis);
		} catch (final Exception e) {
		}
	}

	/* DEPRECATED methods and constructors. */

	@Deprecated
	public void process(final DatagramPacket thePacket, final int thePort) {
		/** TODO re-implement */
	}

	@Deprecated
	public static void flush(final OscMessage theOscMessage, final NetAddress theNetAddress) {
		flush(theOscMessage.getBytes(), theNetAddress);
	}

	@Deprecated
	public static void flush(final OscPacket theOscPacket, final NetAddress theNetAddress) {
		flush(theOscPacket.getBytes(), theNetAddress);
	}

	@Deprecated
	public static void flush(final String theAddrPattern, final Object[] theArguments, final NetAddress theNetAddress) {
		flush((new OscMessage(theAddrPattern, theArguments)).getBytes(), theNetAddress);
	}

	@Deprecated
	public static void flush(final byte[] theBytes, final NetAddress theNetAddress) {
		/** TODO re-implement */
	}

	@Deprecated
	public static void flush(final byte[] theBytes, final String theAddress, final int thePort) {
		flush(theBytes, new NetAddress(theAddress, thePort));
	}

	@Deprecated
	public static void flush(final OscMessage theOscMessage, final String theAddress, final int thePort) {
		flush(theOscMessage.getBytes(), new NetAddress(theAddress, thePort));
	}

	@Deprecated
	public OscP5(final Object theParent, final String theHost, final int theSendToPort, final int theReceiveAtPort,
			final String theMethodName) {
		/** TODO re-implement */
	}

	@Deprecated
	public OscMessage newMsg(final String theAddrPattern) {
		return new OscMessage(theAddrPattern);
	}

	@Deprecated
	public OscBundle newBundle() {
		return new OscBundle();
	}

	@Deprecated
	public void disconnectFromTEMP() {
		/** TODO re-implement */
		/** TODO used by the monome library by jklabs */
	}

	@Deprecated
	public OscP5(final Object theParent, final String theAddress, final int thePort) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final String theAddrPattern, final Object[] theArguments, final NetAddress theNetAddress) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final String theAddrPattern, final Object[] theArguments, final NetAddressList theNetAddressList) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final String theAddrPattern, final Object[] theArguments, final String theAddress,
			final int thePort) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final String theAddrPattern, final Object[] theArguments, final TcpClient theClient) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final OscPacket thePacket, final NetAddress theNetAddress) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final OscPacket thePacket, final NetAddressList theNetAddressList) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final String theAddress, final int thePort, final String theAddrPattern,
			final Object... theArguments) {
		/** TODO re-implement */
	}

	@Deprecated
	public void send(final OscPacket thePacket, final TcpClient theClient) {
	}

	public void send(final NetAddressList theNetAddressList, final OscPacket thePacket) {
		/** TODO re-implement */
	}

	public void send(final NetAddressList theNetAddressList, final String theAddrPattern,
			final Object... theArguments) {
		/** TODO re-implement */
	}

	@Deprecated
	public static void setLogStatus(final int theIndex, final int theValue) {
	}

	@Deprecated
	public static void setLogStatus(final int theValue) {
	}

	@Deprecated
	public NetInfo netInfo() {
		return new NetInfo();
	}
}
