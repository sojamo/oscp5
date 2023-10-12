/**
 * A network library for processing which supports UDP, TCP and Multicast.
 *
 * (c) 2016
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
 * @author		Vojtech Leischner https://trackmeifyoucan.com
 * @modified	11/19/2015
 * @version		2.0.4
 */

package netP5;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.logging.Logger;

public final class TcpServer extends Observable implements Transmitter {

	private final static Logger LOGGER = Logger.getLogger( TcpServer.class.getName( ) );
	private Selector selector;
	private final List< SelectionKey > clients = new ArrayList< SelectionKey >( );
	private final byte[] emptybuffer = new byte[ 0 ];
	private final Server server;

	public TcpServer( String theIP , int thePort ) {
		server = new Server( theIP , thePort );
		( new Thread( server ) ).start( );
	}

	public TcpServer( int thePort ) {
		this( null , thePort );
	}

	/**
	 * just for legacy purposes to get the size of clients registered, returns an array of size clients.size() with null
	 * objects.
	 */
	public Object[] getClients( ) {
		return new Object[ clients.size( ) ];
	}

	private void doAccept( SelectionKey sk ) {

		ServerSocketChannel server = ( ServerSocketChannel ) sk.channel( );
		SocketChannel clientChannel;

		try {
			clientChannel = server.accept( );
			clientChannel.configureBlocking( false );

			/* Register this channel for reading. */
			SelectionKey clientKey = clientChannel.register( selector , SelectionKey.OP_READ );

			/* Allocate an Client instance and attach it to this selection key. */
			Client client = new Client( clientKey );
			clientKey.attach( client );
			clients.add( clientKey );
			// notification( emptybuffer , SelectionKey.OP_CONNECT , clientKey );
		} catch ( Exception e ) {
			LOGGER.warning( "Failed to accept new client." );
			e.printStackTrace( );
		}

		for ( SelectionKey client : clients ) {
			System.out.println( client + " " + ( ( SocketChannel ) client.channel( ) ) );
		}
		System.out.println( String.format( "size %d" , clients.size( ) ) );

	}

	/**
	 * Read from a client. Enqueue the data on the clients output queue and set the selector to notify on OP_WRITE.
	 */
	private void doRead( SelectionKey sk ) {

		SocketChannel channel = ( SocketChannel ) sk.channel( );
		ByteBuffer bb = ByteBuffer.allocate( 8192 );
		int len;
		try {
			len = channel.read( bb );
			if ( len < 0 ) {
				disconnect( sk );
				System.out.println( "Disconnecting " + channel + "; client size = "+clients.size( ) );
				return;
			}
			notification( bb.array( ) , SelectionKey.OP_READ , sk );
		} catch ( Exception e ) {
			LOGGER.warning( "Failed to read from client." );
			e.printStackTrace( );
			return;
		}

	}

	public boolean close( ) {
		try {
			for ( SelectionKey client : clients ) {
				client.cancel( );
			}
			clients.clear( );
			server.channel.close( );
			return true;
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return false;
	}

	public boolean send( byte[] buffer ) {
		write( buffer , clients );
		return true;
	}

	public boolean send( byte[] theContent , Collection< InetSocketAddress > theAddress ) {
		return send( theContent );
	}

	public boolean send( byte[] theContent , String theHost , int thePort ) {
		return send( theContent );
	}

	public boolean send( byte[] theContent , SocketAddress ... theAddress ) {
		return send( theContent );
	}

	public void write( byte[] buffer , SelectionKey key ) {
		Client client = ( Client ) key.attachment( );
		client.enqueue( ByteBuffer.wrap( buffer ) );
		key.interestOps( SelectionKey.OP_READ | SelectionKey.OP_WRITE );
	}

	public void write( byte[] buffer , List< SelectionKey > keys ) {
		for ( SelectionKey key : keys ) {
			write( buffer , key );
		}
	}

	public void write( byte[] buffer , SelectionKey ... keys ) {
		for ( SelectionKey key : keys ) {
			write( buffer , key );
		}
	}

	/**
	 * Called when a SelectionKey is ready for writing.
	 */
	private void doWrite( SelectionKey sk ) {
		SocketChannel channel = ( SocketChannel ) sk.channel( );
		Client client = ( Client ) sk.attachment( );
		LinkedList< ByteBuffer > outq = client.getOutputQueue( );
		ByteBuffer bb = outq.getLast( );
		try {
			int len = channel.write( bb );
			if ( len == -1 ) {
				disconnect( sk );
				return;
			}

			if ( bb.remaining( ) == 0 ) {
				/* The buffer was completely written, remove it. */
				outq.removeLast( );
				notification( emptybuffer , SelectionKey.OP_WRITE , sk );
			}
		} catch ( Exception e ) {
			System.out.println( "Failed to write to client." );
			e.printStackTrace( );
		}

		/* If there is no more data to be written, remove interest in OP_WRITE. */
		if ( outq.size( ) == 0 ) {
			sk.interestOps( SelectionKey.OP_READ );
		}
	}

	private void disconnect( SelectionKey sk ) {
		SocketChannel channel = ( SocketChannel ) sk.channel( );

		System.out.println( "disconnecting; size=" + clients.size( ) );
		notification( emptybuffer , 0 , sk );
		clients.remove( sk ); /* TODO does not remove properly */
		System.out.println( "disconnected; size=" + clients.size( ) );
		try {
			channel.close( );
		} catch ( Exception e ) {
			LOGGER.warning( "Failed to close client socket channel." );
			e.printStackTrace( );
		}
	}

	private void notification( byte[] theData , int theOperation , SelectionKey theKey ) {
		Map< String , Object > m = new HashMap< String , Object >( );
		SocketChannel channel = ( ( SocketChannel ) theKey.channel( ) );
		m.put( "data" , theData );
		m.put( "length" , theData.length );
		m.put( "received-at" , System.currentTimeMillis( ) );
		m.put( "socket-type" , "tcp" );
		m.put( "operation" , theOperation );
		m.put( "socket-ref" , channel );
		m.put( "socket-address" , channel.socket( ).getInetAddress( ).getHostAddress( ) );
		m.put( "socket-port" , channel.socket( ).getPort( ) );
		m.put( "local-port" , channel.socket( ).getLocalPort( ) );
		setChanged( );
		notifyObservers( m );
	}

	private class Server implements Runnable {

		private final int port;
		private ServerSocketChannel channel;
		private final String ip;

		Server( String theIP , int thePort ) {
			ip = theIP;
			port = thePort;

		}

		Server( int thePort ) {
			ip = null;
			port = thePort;
		}

		public void run( ) {
			try {
				selector = SelectorProvider.provider( ).openSelector( );

				/* more information on selectors and how to use them http://tutorials.jenkov.com/java-nio/selectors.html */

				/* Create non-blocking server socket. */
				channel = ServerSocketChannel.open( );

				channel.configureBlocking( false );

				/* Bind the server socket to Localhost */
				InetSocketAddress isa;
				if ( ip != null ) {
					isa = new InetSocketAddress( ip , port );
				} else {
					isa = new InetSocketAddress( InetAddress.getLocalHost( ) , port );
				}
				channel.socket( ).bind( isa );

				/* Register the socket for select events. */
				SelectionKey acceptKey = channel.register( selector , SelectionKey.OP_ACCEPT );

				LOGGER.info( "Starting server at " + isa );

				/* Loop forever. */
				for ( ; ; ) {

					selector.select( );
					Set< SelectionKey > readyKeys = selector.selectedKeys( );
					Iterator< SelectionKey > i = readyKeys.iterator( );

					while ( i.hasNext( ) ) {
						SelectionKey sk = ( SelectionKey ) i.next( );
						i.remove( );
						if ( sk.isAcceptable( ) ) {
							doAccept( sk );
						}
						if ( sk.isValid( ) && sk.isReadable( ) ) {
							doRead( sk );
						}
						if ( sk.isValid( ) && sk.isWritable( ) ) {
							doWrite( sk );
						}
					}
				}
			} catch ( Exception e ) {
				System.out.println( e );
			}

		}

	}

	private class Client {

		private LinkedList< ByteBuffer > outq;
		final SelectionKey clientKey;

		Client( SelectionKey theKey ) {
			clientKey = theKey;
			outq = new LinkedList< ByteBuffer >( );
		}

		/* Return the output queue. */
		public LinkedList< ByteBuffer > getOutputQueue( ) {
			return outq;
		}

		/* Enqueue a ByteBuffer on the output queue. */
		public void enqueue( ByteBuffer bb ) {
			outq.addFirst( bb );
		}
	}

	public static void main( String[] args ) {
		TcpServer server = new TcpServer( "127.0.0.1" , 10000 );
		server.addObserver( new Observer( ) {

			public void update( Observable o , Object arg ) {
				System.out.println( "received a packet " + arg );
			}
		} );
	}

	/* Notes */

	/* adapted from http://ishbits.googlecode.com/svn-history/r29/trunk/java.nio.EchoServer/EchoServer.java */

}