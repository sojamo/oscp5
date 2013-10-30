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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author              ##author##
 * @modified    ##date##
 * @version             ##version##
 */

package oscP5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netP5.Bytes;

public abstract class OscPatcher {

	protected static final byte ZEROBYTE = 0x00;

	protected static final byte KOMMA = 0x2c;

	protected static final long TIMETAG_OFFSET = 2208988800L;

	protected static final long TIEMTAG_NOW = 1;

	protected List< OscMessage > messages;

	protected byte[] _myAddrPattern;

	protected int _myAddrInt = -1;

	protected byte[] _myTypetag = new byte[ 0 ];

	protected byte[] _myData = new byte[ 0 ];

	protected Object[] _myArguments;

	protected boolean isValid = false;

	protected long timetag = 1;

	protected boolean isArray = false;

	protected byte _myArrayType = 0X00;

	protected int parseBundle( Map m ) {
		byte[] bytes = bytes( m.get( "data" ) );
		if ( bytes.length > OscBundle.BUNDLE_HEADER_SIZE ) {
			timetag = ( new Long( Bytes.toLong( Bytes.copy( bytes , 8 , 8 ) ) ) ).longValue( );
			int myPosition = OscBundle.BUNDLE_HEADER_SIZE;
			messages = new ArrayList< OscMessage >( );
			int myMessageLength = Bytes.toInt( Bytes.copy( bytes , myPosition , 4 ) );
			while ( myMessageLength != 0 && ( myMessageLength % 4 == 0 ) && myPosition < bytes.length ) {
				myPosition += 4;
				Map< String , Object > m0 = new HashMap< String , Object >( );
				m0.put( "data" , Bytes.copy( bytes , myPosition , myMessageLength ) );
				m0.put( "socket-ref" , m.get( "socket-ref" ) );
				m0.put( "socket-address" , m.get( "socket-address" ) );
				m0.put( "socket-port" , m.get( "socket-port" ) );
				m0.put( "local-port" , m.get( "local-port" ) );

				messages.add( new OscMessage( m ) );
				myPosition += myMessageLength;
				if ( myPosition >= bytes.length ) {
					break;
				}
				myMessageLength = Bytes.toInt( Bytes.copy( bytes , myPosition , 4 ) );
			}
		}

		List< OscMessage > f = new ArrayList< OscMessage >( );

		for ( OscMessage msg : messages ) {
			if ( !msg.isValid ) {
				f.add( msg );
			}
		}

		messages.removeAll( f );
		isValid = ( messages.size( ) > 0 ) ? true : false;
		return messages.size( );

	}

	protected void parseMessage( final byte[] theBytes ) {

		int myLength = theBytes.length;
		int myIndex = 0;
		myIndex = parseAddrPattern( theBytes , myLength , myIndex );
		if ( myIndex != -1 ) {
			myIndex = parseTypetag( theBytes , myLength , myIndex );
		}

		if ( myIndex != -1 ) {
			_myData = Bytes.copy( theBytes , myIndex );
			_myArguments = parseArguments( _myData );
			isValid = true;
		}

	}

	protected int parseAddrPattern( final byte[] theBytes , final int theLength , final int theIndex ) {
		if ( theLength > 4 && theBytes[ 4 ] == KOMMA ) {
			_myAddrInt = Bytes.toInt( Bytes.copy( theBytes , 0 , 4 ) );
		}
		for ( int i = theIndex ; i < theLength ; i++ ) {
			if ( theBytes[ i ] == ZEROBYTE ) {
				_myAddrPattern = Bytes.copy( theBytes , theIndex , i );
				return i + align( i );
			}
		}
		return -1;
	}

	protected int parseTypetag( final byte[] theBytes , final int theLength , int theIndex ) {
		if ( theBytes[ theIndex ] == KOMMA ) {
			theIndex++;
			for ( int i = theIndex ; i < theLength ; i++ ) {
				if ( theBytes[ i ] == ZEROBYTE ) {
					_myTypetag = Bytes.copy( theBytes , theIndex , i - theIndex );
					return i + align( i );
				}
			}
		}
		return -1;
	}

	/**
	 * cast the arguments passed with the incoming osc message and store them in an object array.
	 * 
	 * @param theBytes
	 * @return
	 */
	protected Object[] parseArguments( final byte[] theBytes ) {

		Object[] myArguments = new Object[ 0 ];
		int myTagIndex = 0;
		int myIndex = 0;
		myArguments = new Object[ _myTypetag.length ];
		isArray = ( _myTypetag.length > 0 ) ? true : false;

		while ( myTagIndex < _myTypetag.length ) {

			/* check if we still save the arguments as an array */

			if ( myTagIndex == 0 ) {
				_myArrayType = _myTypetag[ myTagIndex ];
			} else {
				if ( _myTypetag[ myTagIndex ] != _myArrayType ) {
					isArray = false;
				}
			}
			switch ( _myTypetag[ myTagIndex ] ) {
			case ( 0x63 ): // char c
				myArguments[ myTagIndex ] = ( new Character( ( char ) ( Bytes.toInt( Bytes.copy( theBytes , myIndex , 4 ) ) ) ) );
				myIndex += 4;
				break;
			case ( 0x69 ): // int i
				myArguments[ myTagIndex ] = ( new Integer( Bytes.toInt( Bytes.copy( theBytes , myIndex , 4 ) ) ) );
				myIndex += 4;
				break;
			case ( 0x66 ): // float f
				myArguments[ myTagIndex ] = ( new Float( Bytes.toFloat( Bytes.copy( theBytes , myIndex , 4 ) ) ) );
				myIndex += 4;

				break;
			case ( 0x6c ): // long l
			case ( 0x68 ): // long h
				myArguments[ myTagIndex ] = ( new Long( Bytes.toLong( Bytes.copy( theBytes , myIndex , 8 ) ) ) );
				myIndex += 8;
				break;
			case ( 0x64 ): // double d
				myArguments[ myTagIndex ] = ( new Double( Bytes.toDouble( Bytes.copy( theBytes , myIndex , 8 ) ) ) );
				myIndex += 8;
				break;
			case ( 0x53 ): // Symbol S
			case ( 0x73 ): // String s
				int newIndex = myIndex;
				StringBuffer stringBuffer = new StringBuffer( );

				stringLoop: do {
					if ( theBytes[ newIndex ] == 0x00 ) {
						break stringLoop;
					} else {
						stringBuffer.append( ( char ) theBytes[ newIndex ] );
					}
					newIndex++;
				} while ( newIndex < theBytes.length );

				myArguments[ myTagIndex ] = ( stringBuffer.toString( ) );
				myIndex = newIndex + align( newIndex );
				break;
			case 0x62: // byte[] b - blob
				int myLen = Bytes.toInt( Bytes.copy( theBytes , myIndex , 4 ) );
				myIndex += 4;
				myArguments[ myTagIndex ] = Bytes.copy( theBytes , myIndex , myLen );
				myIndex += myLen + ( align( myLen ) % 4 );
				break;
			case 0x6d: // midi m
				myArguments[ myTagIndex ] = Bytes.copy( theBytes , myIndex , 4 );
				myIndex += 4;
				break;
			case 'T':
				myArguments[ myTagIndex ] = true;
				break;
			case 'F':
				myArguments[ myTagIndex ] = false;
				break;
			/* no arguments for typetags T,F,N T = true F = false N = false */
			}
			myTagIndex++;
		}

		_myData = Bytes.copy( _myData , 0 , myIndex );

		return myArguments;
	}

	protected static int align( int theInt ) {
		return ( 4 - ( theInt % 4 ) );
	}

	static public int i( Object o ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).intValue( ) : Integer.MIN_VALUE;
	}

	static public int i( Object o , int theDefault ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).intValue( ) : theDefault;
	}

	static public char c( Object o ) {
		return ( o instanceof Character ) ? ( ( Character ) o ).charValue( ) : '\0';
	}

	static public double d( Object o ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).doubleValue( ) : Double.MIN_VALUE;
	}

	static public long l( Object o ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).longValue( ) : Long.MIN_VALUE;
	}

	static public float f( Object o ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).floatValue( ) : Float.MIN_VALUE;
	}

	static public boolean b( Object o ) {
		return ( o instanceof Boolean ) ? ( ( Boolean ) o ).booleanValue( ) : ( o instanceof Number ) ? ( ( Number ) o ).intValue( ) == 0 ? false : true : false;
	}

	static public byte[] bytes( Object o ) {
		return ( o instanceof byte[] ) ? ( byte[] ) o : new byte[ 0 ];
	}

	static public String s( Object o ) {
		return o.toString( );
	}

	static public String s( Object o , String theDefault ) {
		if ( o != null ) {
			return o.toString( );
		}
		return theDefault;
	}

}