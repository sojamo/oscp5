package netP5;

import java.io.IOException;
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

public class UdpServer extends Observable implements Sender {

	/* This is a very simple UDP server listening for incoming message and forwarding the message
	 * details to all Observers registered. This server can be used for simple networking operations
	 * with a small amount of clients. For larger scale network operations make use of more
	 * sophisticated services such as for example netty.io, apache mina - for NAT traversal consider
	 * JSTUN.
	 * 
	 * how to each each an incoming message, see UdpServer_a, handleWrite and the switching of
	 * interestOps. */

	private final static Logger LOGGER = Logger.getLogger( UdpServer.class.getName( ) );

	private final InternalServer server;

	public UdpServer( int thePort , int theDatagramSize ) {

		server = new InternalServer( thePort , theDatagramSize );

		/* TODO consider to use java.util.concurrent.Executor here instead of Thread. */

		( new Thread( server ) ).start( );

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

	private class InternalServer implements Runnable {

		DatagramChannel channel;

		private final int port;

		private final int size;

		InternalServer( int thePort , int theDatagramSize ) {

			port = thePort;

			size = theDatagramSize;
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

				while ( true ) {

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

							if ( client != null ) { /* handle received message */

								buffer.flip( );

								final Map< String , Object > m = new HashMap< String , Object >( );

								final byte[] data = new byte[ buffer.remaining( ) ];

								buffer.get( data );

								m.put( "socket-address" , client );
								/* receiver needs to cast SocketAddress */

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
		}

	}

}
