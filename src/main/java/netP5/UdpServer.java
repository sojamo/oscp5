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

	final static Logger LOGGER = Logger.getLogger( UdpServer.class.getName( ) );

	private final InternalServer server;

	public UdpServer( final int thePort , final int theDatagramSize ) {

		/* This is a very basic UDP server listening for incoming message and forwarding the message
		 * to all registered observers. This server can be used for simple networking operations
		 * with a small amount of clients. For larger scale network operations make use of more
		 * sophisticated services such as for example netty.io, apache mina - for NAT traversal
		 * consider JSTUN - or use a messaging middleware such as rabbitMQ or the messaging library
		 * zeroMQ */

		server = new InternalServer( thePort , theDatagramSize );

	}

	public boolean close( ) {
		try {
			server.thread.interrupt( );
			server.channel.close( );
			return true;
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return false;
	}

	public boolean send( byte[] theContent ) {
		/* TODO send to all clients */
		return false;
	}

	public boolean send( byte[] theContent , Collection< InetSocketAddress > theAddress ) {
		InetSocketAddress[] o = new InetSocketAddress[ theAddress.size( ) ];
		return send( theContent , theAddress.toArray( o ) );
	}

	public boolean send( byte[] theContent , String theHost , int thePort ) {
		return send( theContent , new InetSocketAddress( theHost , thePort ) );
	}

	public boolean send( byte[] theContent , SocketAddress ... theAddress ) {
		try {

			ByteBuffer buffer = ByteBuffer.allocate( theContent.length );
			buffer.clear( );
			buffer.put( theContent );
			buffer.flip( );
			for ( SocketAddress addr : theAddress ) {
				server.channel.send( buffer , addr );
			}
			return true;
		} catch ( Exception e ) {
			System.err.println( "Could not send datagram " + e );
		}
		return false;
	}

	class InternalServer implements Runnable {

		private DatagramChannel channel;

		private final int port;

		private final int size;

		private final Thread thread;

		InternalServer( int thePort , int theDatagramSize ) {

			port = thePort;
			size = theDatagramSize;
			thread = ( new Thread( this ) );
			thread.start( );
		}

		public void run( ) {
			LOGGER.info( "starting server, listening on port " + port );
			/* Create a selector to multiplex client connections. */

			try {
				Selector selector = SelectorProvider.provider( ).openSelector( );
				channel = DatagramChannel.open( );
				channel.configureBlocking( false );
				channel.socket( ).bind( new InetSocketAddress( port ) );
				channel.register( selector , SelectionKey.OP_READ , ByteBuffer.allocate( size ) );

				/* Let's listen for incoming messages */
				while ( !Thread.currentThread( ).isInterrupted( ) ) {
					/* Wait for task or until timeout expires */
					int timeout = 1000;
					if ( selector.select( timeout ) == 0 ) {
						/* just checking if we are still alive. */
						continue;
					}

					/* Get iterator on set of keys with I/O to process */

					Iterator< SelectionKey > keyIter = selector.selectedKeys( ).iterator( );
					while ( keyIter.hasNext( ) ) {
						SelectionKey key = keyIter.next( ); /* Key is bit mask */
						/* Client socket channel has pending data? */
						if ( key.isReadable( ) ) {

							DatagramChannel channel0 = ( DatagramChannel ) key.channel( );
							ByteBuffer buffer = ( ( ByteBuffer ) key.attachment( ) );
							buffer.clear( ); /* Prepare buffer for receiving */
							SocketAddress client = channel0.receive( buffer );
							InetSocketAddress addr = ( InetSocketAddress ) client;

							if ( client != null ) { /* handle received message */
								buffer.flip( );
								final Map< String , Object > m = new HashMap< String , Object >( );
								final byte[] data = new byte[ buffer.remaining( ) ];
								buffer.get( data );
								DatagramSocket socket = channel0.socket( );
								m.put( "socket-type" , "udp" );
								m.put( "socket-ref" , channel0 );
								m.put( "received-at" , System.currentTimeMillis( ) );
								m.put( "socket-address" , addr.getAddress( ).getHostAddress( ) );
								m.put( "socket-port" , addr.getPort( ) );
								m.put( "local-port" , socket.getLocalPort( ) );
								m.put( "data" , data );
								setChanged( );
								notifyObservers( m );
							}

						}

						keyIter.remove( );

					}
				}
			} catch ( IOException e ) {
				e.printStackTrace( );
			}
			LOGGER.info( "thread interrupted and closed." );
		}

	}

	/* TODO consider to use java.util.concurrent.Executor here instead of Thread. */

}
