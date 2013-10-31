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

public final class TcpClient extends Observable implements Runnable , Transmitter {

	/* adapted from http://bobah.net/d4d/source-code/networking/tcp-client-java-nio */

	private static final long INITIAL_RECONNECT_INTERVAL = 500; // 500 ms.

	private static final long MAXIMUM_RECONNECT_INTERVAL = 30000; // 30 sec.

	private static final int READ_BUFFER_SIZE = 0x100000;

	private static final int WRITE_BUFFER_SIZE = 0x100000;

	private long reconnectInterval = INITIAL_RECONNECT_INTERVAL;

	private ByteBuffer readBuffer = ByteBuffer.allocateDirect( READ_BUFFER_SIZE ); // 1Mb

	private ByteBuffer writeBuffer = ByteBuffer.allocateDirect( WRITE_BUFFER_SIZE ); // 1Mb

	private final Thread thread = new Thread( this );

	private SocketAddress address;

	private Selector selector;

	private SocketChannel channel;

	private final AtomicBoolean connected = new AtomicBoolean( false );

	private AtomicLong bytesOut = new AtomicLong( 0L );

	private AtomicLong bytesIn = new AtomicLong( 0L );

	private final byte[] emptybuffer = new byte[ 0 ];

	public TcpClient( String theHost , int thePort ) {

		address = new InetSocketAddress( theHost , thePort );

		try {
			thread.start( );
		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	public TcpClient( int thePort ) {

		try {
			address = new InetSocketAddress( InetAddress.getLocalHost( ) , thePort );
		} catch ( UnknownHostException e1 ) {
			e1.printStackTrace( );
		}

		try {
			thread.start( );
		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	public void join( ) throws InterruptedException {
		if ( Thread.currentThread( ).getId( ) != thread.getId( ) ) {
			thread.join( );
		}
	}

	public void stop( ) throws IOException , InterruptedException {

		System.out.println( "stopping event loop" );
		thread.interrupt( );
		selector.wakeup( );

	}

	public boolean close( ) {
		try {
			channel.close( );
			return true;
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return false;
	}

	private void notification( byte[] theData , int theOperation , SelectionKey theKey ) {

		Map< String , Object > m = new HashMap< String , Object >( );
		SocketChannel channel = ( ( SocketChannel ) theKey.channel( ) );
		m.put( "data" , theData );
		m.put( "received-at" , System.currentTimeMillis( ) );
		m.put( "operation" , theOperation );
		m.put( "socket-type" , "tcp" );
		m.put( "socket-ref" , channel );
		m.put( "socket-address" , channel.socket( ).getInetAddress( ).getHostAddress( ) );
		m.put( "socket-port" , channel.socket( ).getPort( ) );
		m.put( "local-port" , channel.socket( ).getLocalPort( ) );
		setChanged( );
		notifyObservers( m );

	}

	public boolean isConnected( ) {
		return connected.get( );
	}

	public boolean send( byte[] theBytes ) {

		ByteBuffer buffer = ByteBuffer.allocate( theBytes.length );
		buffer.put( theBytes );
		buffer.flip( );

		try {
			send( buffer );
			return true;
		} catch ( InterruptedException e ) {
			e.printStackTrace( );
		} catch ( IOException e ) {
			e.printStackTrace( );
		}
		return false;
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

	/**
	 * the buffer-data to be sent, the buffer should be flipped (ready to be read)
	 */
	public void send( ByteBuffer buffer ) throws InterruptedException , IOException {

		if ( !connected.get( ) ) {
			throw new IOException( "not connected" );
		}

		synchronized ( writeBuffer ) {

			/* try direct write of what's in the buffer to free up space */

			if ( writeBuffer.remaining( ) < buffer.remaining( ) ) {
				writeBuffer.flip( );
				int bytesOp = 0 , bytesTotal = 0;
				while ( writeBuffer.hasRemaining( ) && ( bytesOp = channel.write( writeBuffer ) ) > 0 ) {
					bytesTotal += bytesOp;
				}
				writeBuffer.compact( );
			}

			if ( Thread.currentThread( ).getId( ) != thread.getId( ) ) {
				while ( writeBuffer.remaining( ) < buffer.remaining( ) ) {
					writeBuffer.wait( );
				}

			} else {
				if ( writeBuffer.remaining( ) < buffer.remaining( ) ) {
					/* TODO add reallocation or buffers chain */
					throw new IOException( "send buffer full" );
				}
			}

			writeBuffer.put( buffer );

			/* try direct write to decrease the latency */
			writeBuffer.flip( );
			int bytesOp = 0 , bytesTotal = 0;
			while ( writeBuffer.hasRemaining( ) && ( bytesOp = channel.write( writeBuffer ) ) > 0 ) {
				bytesTotal += bytesOp;
			}
			writeBuffer.compact( );
			if ( writeBuffer.hasRemaining( ) ) {
				SelectionKey key = channel.keyFor( selector );
				key.interestOps( key.interestOps( ) | SelectionKey.OP_WRITE );
				selector.wakeup( );
			}
		}
	}

	protected void onRead( ByteBuffer buffer , SelectionKey theKey ) {

		buffer.rewind( );
		byte[] bytes = new byte[ buffer.limit( ) ];
		int n = 0;
		while ( buffer.hasRemaining( ) ) {
			bytes[ n++ ] = buffer.get( );
		}

		buffer.position( buffer.limit( ) );
		notification( bytes , SelectionKey.OP_READ , theKey );

	}

	protected void onConnected( SelectionKey theKey ) {
		notification( emptybuffer , SelectionKey.OP_CONNECT , theKey );
	}

	protected void onDisconnected( SelectionKey theKey ) {
		notification( emptybuffer , 0 , theKey );
	}

	private void configureChannel( SocketChannel channel ) throws IOException {
		channel.configureBlocking( false );
		channel.socket( ).setSendBufferSize( 0x100000 ); // 1Mb
		channel.socket( ).setReceiveBufferSize( 0x100000 ); // 1Mb
		channel.socket( ).setKeepAlive( true );
		channel.socket( ).setReuseAddress( true );
		channel.socket( ).setSoLinger( false , 0 );
		channel.socket( ).setSoTimeout( 0 );
		channel.socket( ).setTcpNoDelay( true );
	}

	public void run( ) {
		try {
			while ( !Thread.interrupted( ) ) { // reconnection loop

				try {
					selector = Selector.open( );
					channel = SocketChannel.open( );
					configureChannel( channel );
					channel.connect( address );
					channel.register( selector , SelectionKey.OP_CONNECT );
					while ( !thread.isInterrupted( ) && channel.isOpen( ) ) {
						if ( selector.select( ) > 0 ) { /* events multiplexing loop */
							processSelectedKeys( selector.selectedKeys( ) );
						}
					}

				} catch ( Exception e ) {
					System.out.println( "exception " + e );
				} finally {
					connected.set( false );
					onDisconnected( channel.keyFor( selector ) );
					writeBuffer.clear( );
					readBuffer.clear( );

					if ( channel != null ) {
						channel.close( );
					}

					if ( selector != null ) {
						selector.close( );
					}

					System.out.println( "connection closed" );

				}

				try {

					Thread.sleep( reconnectInterval );
					if ( reconnectInterval < MAXIMUM_RECONNECT_INTERVAL ) {
						reconnectInterval *= 2;
					}

					System.out.println( "reconnecting to " + address );

				} catch ( InterruptedException e ) {
					break;
				}
			}
		} catch ( Exception e ) {
			System.out.println( "unrecoverable error " + e );
		}

		System.out.println( "event loop terminated" );

	}

	private void processSelectedKeys( Set< SelectionKey > keys ) throws Exception {

		Iterator< SelectionKey > itr = keys.iterator( );
		while ( itr.hasNext( ) ) {
			SelectionKey key = itr.next( );
			if ( key.isReadable( ) ) {
				processRead( key );
			}
			if ( key.isWritable( ) ) {
				processWrite( key );
			}
			if ( key.isConnectable( ) ) {
				processConnect( key );
			}
			if ( key.isAcceptable( ) )
				;
			itr.remove( );
		}
	}

	private void processConnect( SelectionKey key ) throws Exception {

		SocketChannel ch = ( SocketChannel ) key.channel( );

		if ( ch.finishConnect( ) ) {
			System.out.println( "connected to " + address );
			key.interestOps( key.interestOps( ) ^ SelectionKey.OP_CONNECT );
			key.interestOps( key.interestOps( ) | SelectionKey.OP_READ );
			reconnectInterval = INITIAL_RECONNECT_INTERVAL;
			connected.set( true );
			onConnected( key );
		}
	}

	private void processRead( SelectionKey key ) throws Exception {

		ReadableByteChannel ch = ( ReadableByteChannel ) key.channel( );
		int bytesOp = 0 , bytesTotal = 0;
		while ( readBuffer.hasRemaining( ) && ( bytesOp = ch.read( readBuffer ) ) > 0 ) {
			bytesTotal += bytesOp;
		}

		if ( bytesTotal > 0 ) {
			readBuffer.flip( );
			onRead( readBuffer , key );
			readBuffer.compact( );
		} else if ( bytesOp == -1 ) {
			System.out.println( "peer closed read channel" );
			ch.close( );
		}

		bytesIn.addAndGet( bytesTotal );
	}

	private void processWrite( SelectionKey key ) throws IOException {

		WritableByteChannel ch = ( WritableByteChannel ) key.channel( );

		synchronized ( writeBuffer ) {
			writeBuffer.flip( );
			int bytesOp = 0 , bytesTotal = 0;
			while ( writeBuffer.hasRemaining( ) && ( bytesOp = ch.write( writeBuffer ) ) > 0 ) {
				bytesTotal += bytesOp;
			}

			bytesOut.addAndGet( bytesTotal );

			if ( writeBuffer.remaining( ) == 0 ) {
				key.interestOps( key.interestOps( ) ^ SelectionKey.OP_WRITE );
			}

			if ( bytesTotal > 0 ) {
				writeBuffer.notify( );
			} else if ( bytesOp == -1 ) {
				System.out.println( "peer closed write channel" );
				ch.close( );
			}

			notification( emptybuffer , SelectionKey.OP_WRITE , key );
			writeBuffer.compact( );
		}
	}

	public SocketAddress getAddress( ) {
		return address;
	}

	public String toString( ) {
		return "{ host-address:" + ( ( InetSocketAddress ) address ).getAddress( ).getHostAddress( ) + " , host-name:" + ( ( InetSocketAddress ) address ).getAddress( ).getHostName( ) + " , port:" + ( ( InetSocketAddress ) address ).getPort( )
				+ " }";
	}

	public static void main( String ... args ) {

		new TcpClient( "127.0.0.1" , 10000 );

	}

}