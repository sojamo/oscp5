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
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */

package oscP5;

import java.net.InetSocketAddress;
import java.util.Map;

import netP5.Bytes;

/**
 * An OSC message consists of an OSC Address Pattern, an OSC Type Tag String and the OSC arguments.
 * 
 * @related OscBundle
 * @example oscP5sendReceive
 */
public class OscMessage extends OscPacket {

	protected final OscArgument _myOscArgument = new OscArgument( );

	protected boolean isPlugged = false;

	OscMessage( Map m ) {

		parseMessage( bytes( m.get( "data" ) ) );

		InetSocketAddress socket = ( InetSocketAddress ) m.get( "socket-address" );
	}

	public OscMessage( final OscMessage theOscMessage ) {

		inetAddress = theOscMessage.inetAddress;

		port = theOscMessage.port;

		hostAddress = theOscMessage.hostAddress;

		_myTcpClient = theOscMessage.tcpConnection( );

		_myAddrPattern = theOscMessage._myAddrPattern;

		_myTypetag = theOscMessage._myTypetag;

		_myData = theOscMessage._myData;

		_myArguments = theOscMessage._myArguments;

		isValid = true;
	}

	public OscMessage( final String theAddrPattern ) {
		this( theAddrPattern , new Object[ 0 ] );
	}

	public OscMessage( final int theAddrInt ) {
		this( theAddrInt , new Object[ 0 ] );
	}

	public OscMessage( final String theAddrPattern , final Object ... theArguments ) {
		init( );
		setAddress( theAddrPattern );
		setArguments( theArguments );
	}

	public OscMessage( final int theAddrPattern , final Object ... theArguments ) {
		init( );
		setAddress( theAddrPattern );
		setArguments( theArguments );
	}

	protected void init( ) {
		_myTypetag = new byte[ 0 ];
		_myData = new byte[ 0 ];
	}

	/**
	 * clear and reset an OscMessage for reuse.
	 */
	public void clear( ) {

		init( );

		setAddress( "" );

		setArguments( new Object[ 0 ] );

	}

	public void clearArguments( ) {

		_myTypetag = new byte[ 0 ];

		_myData = new byte[ 0 ];

		_myArguments = new Object[ 0 ];
	}

	/**
	 * TODO set should enable the programmer to set values of an existing osc message.
	 */
	public void set( final int theIndex , final Object theObject ) {
		// byte[] myPreTypetag = new byte[theIndex];
		// byte[] myPostTypetag = new byte[_myTypetag.length - theIndex];
		System.out.println( "Typetag:\t" + _myTypetag.length );
		System.out.println( "Arguments:\t" );
		Bytes.printBytes( _myData );
		System.out.println( _myArguments.length );
		for ( int i = 0 ; i < _myArguments.length ; i++ ) {
			System.out.println( _myArguments[ i ] );
		}
	}

	public boolean checkTypetag( final String theTypeTag ) {
		return theTypeTag.equals( typetag( ) );
	}

	/**
	 * check if an address pattern equals a specific address pattern you are looking for. this is
	 * usually used when parsing an osc message. e.g. if( theOscMessage.checkAddress( "/test" ) ) {
	 * ... }
	 * 
	 */
	public boolean checkAddress( final String theAddrPattern ) {
		return theAddrPattern.equals( addrPattern( ) );
	}

	/**
	 * set the address pattern of an osc message. you can set a string or an int as address
	 * pattern.tnt might be useful for supercollider users. oscP5 does support ints and strings as
	 * address patterns when sending and receiving messages.
	 * 
	 */
	public void setAddress( final String theAddrPattern ) {
		_myAddrPattern = theAddrPattern.getBytes( );
	}

	public void setAddress( final int theAddrPattern ) {
		_myAddrPattern = Bytes.toBytes( theAddrPattern );
	}

	/**
	 * set the arguments of the osc message using an object array. with version 0.9.4 the existing
	 * arguments are overwritten, to add the arguments to the argument list, use
	 * addArguments(Object[])
	 * 
	 */
	public void setArguments( final Object ... theArguments ) {
		clearArguments( );
		addArguments( theArguments );
	}

	/**
	 * add a list of arguments to an exisiting set of arguments. to overwrite the existing argument
	 * list, use setArguments(Object[])
	 */
	public OscMessage addArguments( final Object ... theArguments ) {
		return add( theArguments );
	}

	public String address( ) {
		return Bytes.getAsString( _myAddrPattern );
	}

	public String addrPattern( ) {
		return Bytes.getAsString( _myAddrPattern );
	}

	/**
	 * returns the address pattern of the osc message as int.
	 */
	public int addrInt( ) {
		return _myAddrInt;
	}

	/**
	 * returns the typetag of the osc message. e.g. the message contains 3 floats then the typetag
	 * would be "fff"
	 * 
	 * @return String
	 */
	public String typetag( ) {
		return Bytes.getAsString( _myTypetag );
	}

	public long timetag( ) {
		return timetag;
	}

	public Object[] arguments( ) {
		return _myArguments;
	}

	protected Object[] argsAsArray( ) {
		switch ( _myTypetag[ 0 ] ) {
		case ( 0X66 ): // float f
			final float[] myFloatArray = new float[ _myArguments.length ];
			for ( int i = 0 ; i < myFloatArray.length ; i++ ) {
				myFloatArray[ i ] = ( ( Float ) _myArguments[ i ] ).floatValue( );
			}
			return new Object[] { myFloatArray };
		case ( 0x69 ): // int i
			final int[] myIntArray = new int[ _myArguments.length ];
			for ( int i = 0 ; i < myIntArray.length ; i++ ) {
				myIntArray[ i ] = ( ( Integer ) _myArguments[ i ] ).intValue( );
			}
			return new Object[] { myIntArray };
		case ( 0x53 ): // Symbol S
		case ( 0x73 ): // String s
			final String[] myStringArray = new String[ _myArguments.length ];
			for ( int i = 0 ; i < myStringArray.length ; i++ ) {
				myStringArray[ i ] = ( ( String ) _myArguments[ i ] );
			}
			return new Object[] { myStringArray };
		default:
			break;
		}
		return new Object[] { };
	}

	public byte[] getAddrPatternAsBytes( ) {
		return Bytes.append( _myAddrPattern , new byte[ align( _myAddrPattern.length ) ] );
	}

	public byte[] getTypetagAsBytes( ) {
		return _myTypetag;
	}

	public byte[] getBytes( ) {
		byte[] myBytes = new byte[ 0 ];
		byte[] myTypeTag = Bytes.copy( _myTypetag , 0 );
		myBytes = Bytes.append( myBytes , _myAddrPattern , new byte[ align( _myAddrPattern.length ) ] );
		if ( myTypeTag.length == 0 ) {
			myTypeTag = new byte[] { KOMMA };
		} else if ( myTypeTag[ 0 ] != KOMMA ) {
			myTypeTag = Bytes.append( new byte[] { KOMMA } , myTypeTag );
		}
		myBytes = Bytes.append( myBytes , myTypeTag , new byte[ align( myTypeTag.length ) ] );
		myBytes = Bytes.append( myBytes , _myData , new byte[ align( _myData.length ) % 4 ] );
		return myBytes;
	}

	protected Object[] increase( int theAmount ) {
		if ( _myArguments.length < 1 || _myArguments == null ) {
			return new Object[ 1 ];
		}
		Object[] myArguments = new Object[ _myArguments.length + theAmount ];
		System.arraycopy( _myArguments , 0 , myArguments , 0 , _myArguments.length );
		return myArguments;
	}

	public OscMessage add( ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x4e } );
		return this;
	}

	public OscMessage add( final int theValue ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x69 } );
		_myData = Bytes.append( _myData , Bytes.toBytes( theValue ) );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = new Integer( theValue );
		return this;
	}

	public OscMessage add( final String theValue ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x73 } );
		final byte[] myString = theValue.getBytes( );
		_myData = Bytes.append( _myData , myString , new byte[ align( myString.length ) ] );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = theValue;
		return this;
	}

	public OscMessage add( final float theValue ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x66 } );
		_myData = Bytes.append( _myData , Bytes.toBytes( Float.floatToIntBits( theValue ) ) );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = new Float( theValue );
		return this;
	}

	public OscMessage add( final double theValue ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x64 } );
		_myData = Bytes.append( _myData , Bytes.toBytes( Double.doubleToLongBits( theValue ) ) );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = new Double( theValue );
		return this;
	}

	public OscMessage add( final boolean theValue ) {
		if ( theValue ) {
			_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x54 } );
		} else {
			_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x46 } );
		}
		return this;
	}

	public OscMessage add( final Boolean theValue ) {
		add( ( theValue ).booleanValue( ) );
		return this;
	}

	public OscMessage add( final Integer theValue ) {
		add( theValue.intValue( ) );
		return this;
	}

	public OscMessage add( final Float theValue ) {
		add( theValue.floatValue( ) );
		return this;
	}

	public OscMessage add( final Double theValue ) {
		add( theValue.doubleValue( ) );
		return this;
	}

	public OscMessage add( final Character theValue ) {
		add( theValue.charValue( ) );
		return this;
	}

	public OscMessage add( final char theValue ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x63 } );
		_myData = Bytes.append( _myData , Bytes.toBytes( theValue ) );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = new Character( theValue );
		return this;
	}

	public OscMessage add( final int channel , final int status , final int value1 , final int value2 ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x6d } ); // m
		final byte[] theBytes = new byte[ 4 ];
		theBytes[ 0 ] = ( byte ) channel;
		theBytes[ 1 ] = ( byte ) status;
		theBytes[ 2 ] = ( byte ) value1;
		theBytes[ 3 ] = ( byte ) value2;
		_myData = Bytes.append( _myData , theBytes );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = theBytes;
		return this;
	}

	public OscMessage add( final int ... theArray ) {
		for ( int i : theArray ) {
			add( i );
		}
		return this;
	}

	public OscMessage add( final char ... theArray ) {
		for ( char c : theArray ) {
			add( c );
		}
		return this;
	}

	public OscMessage add( final float ... theArray ) {
		for ( float f : theArray ) {
			add( f );
		}
		return this;
	}

	public OscMessage add( final String ... theArray ) {
		for ( String s : theArray ) {
			add( s );
		}
		return this;
	}

	public OscMessage add( final byte ... theArray ) {
		_myTypetag = Bytes.append( _myTypetag , new byte[] { 0x62 } );
		_myData = Bytes.append( _myData , makeBlob( theArray ) );
		_myArguments = increase( 1 );
		_myArguments[ _myArguments.length - 1 ] = theArray;
		return this;
	}

	public OscMessage add( final Object ... os ) {
		for ( Object o : os ) {
			if ( !add( o ) ) {
				System.out.println( "type of Argument not defined in osc specs." );
			}
		}
		return this;
	}

	private boolean add( final Object theObject ) {
		if ( theObject instanceof Number ) {
			if ( theObject instanceof Integer ) {
				add( ( Integer ) theObject );
			} else if ( theObject instanceof Float ) {
				add( ( Float ) theObject );
			} else if ( theObject instanceof Double ) {
				add( ( Double ) theObject );
			} else if ( theObject instanceof Long ) {
				add( ( Long ) theObject );
			}
		} else if ( theObject instanceof String ) {
			add( ( String ) theObject );
		} else if ( theObject instanceof Boolean ) {
			add( ( Boolean ) theObject );
		} else if ( theObject instanceof Character ) {
			add( ( Character ) theObject );
		}

		else {
			if ( theObject instanceof int[] ) {
				add( ( int[] ) theObject );
				return true;
			} else if ( theObject instanceof float[] ) {
				add( ( float[] ) theObject );
				return true;
			} else if ( theObject instanceof byte[] ) {
				add( ( byte[] ) theObject );
				return true;
			}

			else if ( theObject instanceof String[] ) {
				add( ( String[] ) theObject );
				return true;
			} else if ( theObject instanceof char[] ) {
				add( ( char[] ) theObject );
				return true;
			} else if ( theObject instanceof double[] ) {
				add( ( float[] ) theObject );
				return true;
			}
			return false;
		}
		return true;
	}

	public static byte[] makeBlob( final byte[] b ) {
		final int tLength = b.length;
		byte[] b1 = Bytes.toBytes( tLength );
		b1 = Bytes.append( b1 , b );
		final int t = tLength % 4;
		if ( t != 0 ) {
			b1 = Bytes.append( b1 , new byte[ 4 - t ] );
		}
		return b1;
	}

	/**
	 * returns an OscArgument from which the value can be parsed into the right format. e.g. to
	 * parse an int from the first argument in the osc message, use theOscMessage.get(0).intValue();
	 * 
	 * @param theIndex
	 *            int
	 * @return OscArgument
	 */
	public OscArgument get( final int theIndex ) {
		if ( theIndex < arguments( ).length ) {
			_myOscArgument.value = arguments( )[ theIndex ];
			return _myOscArgument;
		}
		return null;
	}

	public final String toString( ) {
		return hostAddress + ":" + port + " | " + addrPattern( ) + " " + typetag( );
	}

	public boolean isPlugged( ) {
		return isPlugged;
	}

	public void print( ) {
		System.out.println( "-OscMessage----------" );
		System.out.println( "received from\t" + hostAddress + ":" + port );
		System.out.println( "addrpattern\t" + Bytes.getAsString( _myAddrPattern ) );
		System.out.println( "typetag\t" + Bytes.getAsString( _myTypetag ) );
		System.out.println( Bytes.getAsString( _myArguments ) );
		System.out.println( "---------------------" );
	}

	public void printData( ) {
		Bytes.printBytes( _myData );
	}

	public int intValue( int theIndex ) {
		return i( getValue( theIndex ) );
	}

	public float floatValue( int theIndex ) {
		return f( getValue( theIndex ) );
	}

	public int charValue( int theIndex ) {
		return c( getValue( theIndex ) );
	}

	public double doubleValue( int theIndex ) {
		return d( getValue( theIndex ) );
	}

	public long longValue( int theIndex ) {
		return l( getValue( theIndex ) );
	}

	public boolean booleanValue( int theIndex ) {
		return b( getValue( theIndex ) );
	}

	public String stringValue( int theIndex ) {
		return s( getValue( theIndex ) );
	}

	public byte[] bytesValue( int theIndex ) {
		return bytes( getValue( theIndex ) );
	}

	public byte[] blobValue( int theIndex ) {
		return bytesValue( theIndex );
	}

	private Object getValue( int theIndex ) {
		if ( theIndex < arguments( ).length ) {
			return arguments( )[ theIndex ];
		}
		indexOutOfBounds( theIndex );
		return null;
	}

	public void indexOutOfBounds( int n ) {
		System.err.println( "ArrayIndexOutOfBounds: index requested " + n + ", expected < " + arguments( ).length );
	}

	static public int i( Object o ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).intValue( ) : Integer.MIN_VALUE;
	}

	static public int c( Object o ) {
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

	@Deprecated
	public void setAddrPattern( final String theAddrPattern ) {
		_myAddrPattern = theAddrPattern.getBytes( );
	}

	@Deprecated
	public void setAddrPattern( final int theAddrPattern ) {
		_myAddrPattern = Bytes.toBytes( theAddrPattern );
	}

	@Deprecated
	public boolean checkAddrPattern( final String theAddrPattern ) {
		return theAddrPattern.equals( addrPattern( ) );
	}

}
