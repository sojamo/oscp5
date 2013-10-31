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
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Collection;

public final class UdpClient implements Transmitter {

	private final InetSocketAddress socket;

	private DatagramChannel channel;

	@SuppressWarnings( "unused" )
	private UdpClient( ) {
		socket = null;
	}

	public UdpClient( String theHost , int thePort ) {

		socket = new InetSocketAddress( theHost , thePort );

		try {
			channel = DatagramChannel.open( );
			channel.connect( socket );
		} catch ( IOException e ) {
			e.printStackTrace( );
		}

		/* TODO initialize buffer as well? */

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

	public boolean send( byte[] theContent ) {
		try {

			ByteBuffer buffer = ByteBuffer.allocate( theContent.length );
			buffer.clear( );
			buffer.put( theContent );
			buffer.flip( );
			channel.send( buffer , socket );
			return true;

		} catch ( Exception e ) {
			System.err.println( "Could not send datagram " + e );
		}
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
			DatagramChannel channel = DatagramChannel.open( );

			for ( SocketAddress addr : theAddress ) {
				channel.send( buffer , addr );
			}

			return true;

		} catch ( Exception e ) {
			System.err.println( "Could not send datagram " + e );
		}
		return false;
	}

}
