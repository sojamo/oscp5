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
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Logger;

public class Multicast extends Observable implements Transmitter {
	
	// http://download.java.net/jdk7/archive/b123/docs/api/java/net/MulticastSocket.html
	// http://stackoverflow.com/questions/5072028/multicast-in-java
	// http://stackoverflow.com/questions/15807733/udp-multicasting-from-mobile-to-pc

	// overview of Multicast addresses:
	// http://www.iana.org/assignments/multicast-addresses/multicast-addresses.xml
	//
	// Reserved [IANA]
	// 235.0.0.0-238.255.255.255
	//
	// Organization-Local Scope (use an address within this scope for your projects)
	// 239.0.0.0-239.255.255.255 e.g. 239.1.1.1
	//
	// example code with queuing https://communities.vmware.com/thread/426609
	//
	// NtpMessage code
	// http://www.windways.org/personal_page/NTP_tool/GetTime/src/GetTime.java
	// http://www.windways.org/personal_page/NTP_tool/GetTime/src/NtpMessage.java
	//

	private Thread thread;

	final static Logger LOGGER = Logger.getLogger( Multicast.class.getName( ) );

	private MulticastSocket socket;

	private InetAddress address;

	final private String group;

	final private int port;

	final private List< String > self;

	public Multicast( final String theGroup , final int thePort ) {
		this( theGroup , thePort , 256 , 1 );
	}

	public Multicast( final String theGroup , final int thePort , final int theDatagramSize , final int theTTL ) {

		group = theGroup;
		port = thePort;
		self = new ArrayList< String >( );

		try {
			socket = new MulticastSocket( thePort );
			// socket.setLoopbackMode( false ); /* TODO did not work for me.*/
			address = InetAddress.getByName( theGroup );
			socket.joinGroup( address );

			final byte[] inBuf = new byte[ theDatagramSize ];

			try {
				String ip;
				Enumeration< NetworkInterface > interfaces = NetworkInterface.getNetworkInterfaces( );
				while ( interfaces.hasMoreElements( ) ) {
					NetworkInterface iface = interfaces.nextElement( );
					// filters out 127.0.0.1 and inactive interfaces
					if ( iface.isLoopback( ) || !iface.isUp( ) )
						continue;

					Enumeration< InetAddress > addresses = iface.getInetAddresses( );
					while ( addresses.hasMoreElements( ) ) {
						InetAddress addr = addresses.nextElement( );
						ip = addr.getHostAddress( );
						System.out.println( iface.getDisplayName( ) + " " + ip );
						self.add( ip );
					}
				}
			} catch ( SocketException e ) {
				throw new RuntimeException( e );
			}

			Runnable server = new Runnable( ) {

				DatagramPacket inPacket = null;

				public void run( ) {
					setTimeToLive( theTTL );
					while ( true ) {
						inPacket = new DatagramPacket( inBuf , inBuf.length );
						try {
							socket.receive( inPacket );
							byte[] data = new byte[ inPacket.getLength( ) ];
							System.arraycopy( inBuf , 0 , data , 0 , data.length );
							final Map< String , Object > m = new HashMap< String , Object >( );
							m.put( "socket-type" , "multicast" );
							m.put( "socket-ref" , socket );
							m.put( "received-at" , System.currentTimeMillis( ) );
							m.put( "multicast-group" , group );
							m.put( "multicast-sender" , inPacket.getAddress( ).getHostAddress( ) );
							m.put( "multicast-port" , port );
							m.put( "data" , data );
							setChanged( );
							notifyObservers( m );
						} catch ( IOException e ) {
							e.printStackTrace( );
						}

					}
				}
			};

			thread = new Thread( server );
			thread.start( );
		} catch ( Exception e ) {
		}

	}

	public String getGroup( ) {
		return group;
	}

	public List< String > getSelf( ) {
		return self;
	}

	public boolean isSelf( Map< String , Object > m ) {
		Object o = m.get( "multicast-sender" );
		return o == null ? false : self.contains( o.toString( ) );
	}

	public Multicast setTimeToLive( int theTTL ) {
		try {
			socket.setTimeToLive( theTTL );
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return this;
	}

	public boolean send( byte[] theContent ) {
		try {
			socket.send( new DatagramPacket( theContent , theContent.length , address , port ) );
			return true;
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return false;
	}

	public boolean send( byte[] theContent , Collection< InetSocketAddress > theAddress ) {
		LOGGER.info( "not implemented, use send(byte[])." );
		return false;
	}

	public boolean send( byte[] theContent , String theHost , int thePort ) {
		LOGGER.info( "not implemented, use send(byte[])." );
		return false;
	}

	public boolean send( byte[] theContent , SocketAddress ... theAddress ) {
		LOGGER.info( "not implemented, use send(byte[])." );
		return false;
	}

	public boolean close( ) {
		thread.interrupt( );
		try {
			socket.leaveGroup( socket.getInetAddress( ) );
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		socket.close( );
		return false;
	}

}
