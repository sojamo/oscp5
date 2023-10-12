/**
 * An OSC (Open Sound Control) library for processing.
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 * 
 * @author Vojtech Leischner https://trackmeifyoucan.com
 * @modified 11/19/2015
 * @version 2.0.4
 */

package oscP5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import netP5.Bytes;

/**
 * Osc Bundles are collections of Osc Messages. use bundles to send multiple osc messages to one
 * destination. the OscBundle timetag is supported for sending but not for receiving yet.
 */
public class OscBundle extends OscPacket {

	protected static final int BUNDLE_HEADER_SIZE = 16;
	protected static final byte[] BUNDLE_AS_BYTES = { 0x23 , 0x62 , 0x75 , 0x6E , 0x64 , 0x6C , 0x65 , 0x00 };
	private int _myMessageSize = 0;

	public OscBundle( ) {
		messages = new ArrayList< OscMessage >( );
	}

	OscBundle( Map m ) {
		_myMessageSize = parseBundle( m );
		_myRef = m.get( "socket-ref" );
		hostAddress = s( m.get( "socket-address" ) , null );
		port = i( m.get( "socket-port" ) , 0 );
		localPort = i( m.get( "local-port" ) , 0 );
	}

	public OscBundle add( OscMessage ... theOscMessages ) {
		for ( OscMessage m : theOscMessages ) {
			messages.add( new OscMessage( m ) );
			/* duplicate the OSC message ( sure this is a deep copy? ) */
		}
		_myMessageSize = messages.size( );
		return this;
	}

	public OscBundle clear( ) {
		messages = new ArrayList< OscMessage >( );
		return this;
	}

	public OscBundle remove( int theIndex ) {
		messages.remove( theIndex );
		return this;
	}

	public OscBundle remove( OscMessage theOscMessage ) {
		messages.remove( theOscMessage );
		return this;
	}

	public OscMessage getMessage( int theIndex ) {
		return messages.get( theIndex );
	}

	public int size( ) {
		return _myMessageSize;
	}

	public OscMessage get( int theIndex ) {
		if ( theIndex < 0 || theIndex >= size( ) ) {
			return null;
		}
		return messages.get( theIndex );
	}

	public List< OscMessage > get( ) {
		return messages;
	}

	/**
	 * TODO set the timetag of an osc bundle. timetags are used to synchronize events and execute
	 * events at a given time in the future or immediately. timetags can only be set for osc
	 * bundles, not for osc messages. oscP5 supports receiving timetags, but does not queue messages
	 * for execution at a set time.
	 * 
	 */
	public OscBundle setTimetag( long theTime ) {
		final long secsSince1900 = theTime / 1000 + TIMETAG_OFFSET;
		final long secsFractional = ( ( theTime % 1000 ) << 32 ) / 1000;
		timetag = ( secsSince1900 << 32 ) | secsFractional;
		return this;
	}

	public static long now( ) {
		return System.currentTimeMillis( );
	}

	public byte[] timetag( ) {
		return Bytes.toBytes( timetag );
	}

	public byte[] getBytes( ) {
		byte[] myBytes = new byte[ 0 ];
		myBytes = Bytes.append( myBytes , BUNDLE_AS_BYTES );
		myBytes = Bytes.append( myBytes , timetag( ) );
		for ( int i = 0 ; i < size( ) ; i++ ) {
			byte[] tBytes = getMessage( i ).getBytes( );
			myBytes = Bytes.append( myBytes , Bytes.toBytes( tBytes.length ) );
			myBytes = Bytes.append( myBytes , tBytes );
		}
		return myBytes;
	}

	public final String toString( ) {
		return String.format( "OscBundle{hostAddress=%s, port=%s, messages=%s}" , hostAddress , port , size( ) );
	}
}
