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

import java.io.IOException;
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

/**
 * TODO add better error message handling for oscEvents, see this post
 * http://forum.processing.org/topic/oscp5-major-problems-with-error-handling# 25080000000811163
 */
public class OscP5 implements Observer {

	final static Logger LOGGER = Logger.getLogger( OscP5.class.getName( ) );

	protected Map< String , List< OscPlug >> _myOscPlugMap = new HashMap< String , List< OscPlug >>( );

	public final static boolean ON = OscProperties.ON;

	public final static boolean OFF = OscProperties.OFF;

	/* a static variable used when creating an oscP5 instance with a specified network protocol. */
	public final static int UDP = OscProperties.UDP;

	/* a static variable used when creating an oscP5 instance with a specified network protocol. */
	public final static int MULTICAST = OscProperties.MULTICAST;

	/* a static variable used when creating an oscP5 instance with a specified network protocol. */
	public final static int TCP = OscProperties.TCP;

	private OscProperties _myOscProperties;

	private Method _myEventMethod;

	private Class< ? > _myEventClass = OscMessage.class;

	private boolean isEventMethod;

	private boolean isBroadcast = false;

	public static final String VERSION = "2.0.1";

	static private int welcome = 0;

	private Transmitter transmit;

	private Object parent;

	/**
	 * default constructor, starts an UDP server with maximum packet size of 1536 bytes.
	 */
	public OscP5( final Object theParent , final int theListeningPort ) {
		OscProperties properties = new OscProperties( theListeningPort );
		init( theParent , properties );
	}

	/**
	 * Constructor for a server using theProtocol.
	 */
	public OscP5( final Object theParent , final int theListeningPort , final int theProtocol ) {
		OscProperties properties = new OscProperties( theListeningPort );
		properties.setNetworkProtocol( theProtocol );
		init( theParent , properties );
	}

	/**
	 * Constructor for a client using theProtocol.
	 */
	public OscP5( final Object theParent , String theRemoteAddress , final int theRemotePort , final int theProtocol ) {
		OscProperties properties = new OscProperties( new NetAddress( theRemoteAddress , theRemotePort ) );
		properties.setNetworkProtocol( theProtocol );
		init( theParent , properties );
	}

	public OscP5( final Object theParent , final OscProperties theProperties ) {
		init( theParent , theProperties );
	}

	private void init( Object theParent , OscProperties theProperties ) {

		welcome( );
		parent = ( theParent == null ) ? new Object( ) : theParent;
		registerDispose( parent );
		_myOscProperties = theProperties;
		isEventMethod = checkEventMethod( );

		switch ( _myOscProperties.networkProtocol( ) ) {
		case ( OscProperties.UDP ):
			if ( !_myOscProperties.remoteAddress( ).equals( OscProperties.defaultnetaddress ) ) {
				UdpClient udpclient = NetP5.createUdpClient( _myOscProperties.remoteAddress( ).address( ) , _myOscProperties.remoteAddress( ).port( ) );
				transmit = udpclient;
			} else {
				UdpServer udpserver = NetP5.createUdpServer( _myOscProperties.listeningPort( ) , _myOscProperties.datagramSize( ) );
				udpserver.addObserver( this );
				transmit = udpserver;
			}
			break;
		case ( OscProperties.TCP ):
			if ( !_myOscProperties.remoteAddress( ).equals( OscProperties.defaultnetaddress ) ) {
				TcpClient tcpclient = NetP5.createTcpClient( _myOscProperties.remoteAddress( ).address( ) , _myOscProperties.remoteAddress( ).port( ) );
				tcpclient.addObserver( this );
				transmit = tcpclient;
			} else {
				TcpServer tcpserver = NetP5.createTcpServer( _myOscProperties.listeningPort( ) );
				tcpserver.addObserver( this );
				transmit = tcpserver;
			}
			break;
		case ( OscProperties.MULTICAST ):
			LOGGER.info( "Multicast is not yet implemented with this version. " );
			break;
		default:
			LOGGER.info( "Unknown protocol." );
			break;
		}

	}

	public void update( Observable ob , Object map ) {
		/* gets called when an OSC packet was received, a Map is expected as second argument. */
		process( map );
	}

	private void welcome( ) {
		if ( welcome++ < 1 ) {
			System.out.println( "OscP5 " + VERSION + " " + "infos, comments, questions at http://www.sojamo.de/libraries/oscP5\n\n" );
		}
	}

	public String version( ) {
		return VERSION;
	}

	public void dispose( ) {
		transmit.close( );
		stop( );
	}

	public void stop( ) {
		/* TODO notify clients and servers. */
		LOGGER.finest( "stopping oscP5." );
	}

	public void addListener( OscEventListener theListener ) {
		_myOscProperties.listeners( ).add( theListener );
	}

	public void removeListener( OscEventListener theListener ) {
		_myOscProperties.listeners( ).remove( theListener );
	}

	public List< OscEventListener > listeners( ) {
		return _myOscProperties.listeners( );
	}

	/**
	 * In case the parent is of type PApplet, register "dispose". Do so quietly, no error messages
	 * will be displayed.
	 */
	private void registerDispose( Object theObject ) {
		try {

			Object parent = null;
			String child = "processing.core.PApplet";

			try {

				Class< ? > childClass = Class.forName( child );
				Class< ? > parentClass = Object.class;
				if ( parentClass.isAssignableFrom( childClass ) ) {
					parent = childClass.newInstance( );
					parent = theObject;
				}
			} catch ( Exception e ) {
				e.printStackTrace( );
			}

			try {
				Method method = parent.getClass( ).getMethod( "registerMethod" , String.class , Object.class );
				try {
					method.invoke( parent , new Object[] { "dispose" , this } );
				} catch ( Exception e ) {
					e.printStackTrace( );
				}

			} catch ( NoSuchMethodException e ) {
				// e.printStackTrace( );
			}

		} catch ( NullPointerException e ) {
			e.printStackTrace( );
		}
	}

	private boolean checkEventMethod( ) {

		Class< ? > _myParentClass = parent.getClass( );

		try {

			Method[] myMethods = _myParentClass.getDeclaredMethods( );

			for ( int i = 0 ; i < myMethods.length ; i++ ) {
				if ( myMethods[ i ].getName( ).indexOf( _myOscProperties.eventMethod( ) ) != -1 ) {
					Class< ? >[] myClasses = myMethods[ i ].getParameterTypes( );
					if ( myClasses.length == 1 ) {
						_myEventClass = myClasses[ 0 ];
						break;
					}
				}
			}

		} catch ( Throwable e ) {

		}

		String tMethod = _myOscProperties.eventMethod( );

		if ( tMethod != null ) {

			try {

				Class< ? >[] tClass = { _myEventClass };
				_myEventMethod = _myParentClass.getDeclaredMethod( tMethod , tClass );
				_myEventMethod.setAccessible( true );
				return true;

			} catch ( SecurityException e1 ) {
				LOGGER.warning( "### security issues in OscP5.checkEventMethod(). (this occures when running in applet mode)" );
			} catch ( NoSuchMethodException e1 ) {
			}
		}

		if ( _myEventMethod != null ) {
			return true;
		}
		return false;
	}

	public static void flush( final NetAddress theNetAddress , final byte[] theBytes ) {
		DatagramSocket mySocket;
		try {
			mySocket = new DatagramSocket( );
			DatagramPacket myPacket = new DatagramPacket( theBytes , theBytes.length , theNetAddress.inetaddress( ) , theNetAddress.port( ) );
			mySocket.send( myPacket );
		} catch ( SocketException e ) {
			LOGGER.warning( "OscP5.openSocket, can't create socket " + e.getMessage( ) );
		} catch ( IOException e ) {
			LOGGER.warning( "OscP5.openSocket, can't create multicastSocket " + e.getMessage( ) );
		}
	}

	/**
	 * osc messages can be automatically forwarded to a specific method of an object. the plug
	 * method can be used to by-pass parsing raw osc messages - this job is done for you with the
	 * plug mechanism. you can also use the following array-types int[], float[], String[]. (but
	 * only as on single parameter e.g. somemethod(int[] theArray) {} ).
	 * 
	 */
	public void plug( final Object theObject , final String theMethodName , final String theAddrPattern , final String theTypeTag ) {

		final OscPlug myOscPlug = new OscPlug( );
		myOscPlug.plug( theObject , theMethodName , theAddrPattern , theTypeTag );

		if ( _myOscPlugMap.containsKey( theAddrPattern ) ) {
			_myOscPlugMap.get( theAddrPattern ).add( myOscPlug );
		} else {
			List< OscPlug > myOscPlugList = new ArrayList< OscPlug >( );
			myOscPlugList.add( myOscPlug );
			_myOscPlugMap.put( theAddrPattern , myOscPlugList );
		}
	}

	public void plug( final Object theObject , final String theMethodName , final String theAddrPattern ) {

		final Class< ? > myClass = theObject.getClass( );
		final Method[] myMethods = myClass.getDeclaredMethods( );
		Class< ? >[] myParams = null;

		for ( int i = 0 ; i < myMethods.length ; i++ ) {
			String myTypetag = "";
			try {
				myMethods[ i ].setAccessible( true );
			} catch ( Exception e ) {
			}
			if ( ( myMethods[ i ].getName( ) ).equals( theMethodName ) ) {
				myParams = myMethods[ i ].getParameterTypes( );
				OscPlug myOscPlug = new OscPlug( );
				for ( Class< ? > c : myParams ) {
					myTypetag += myOscPlug.checkType( c.getName( ) );
				}

				myOscPlug.plug( theObject , theMethodName , theAddrPattern , myTypetag );

				// _myOscPlugList.add(myOscPlug);

				if ( _myOscPlugMap.containsKey( theAddrPattern ) ) {
					_myOscPlugMap.get( theAddrPattern ).add( myOscPlug );
				} else {
					ArrayList< OscPlug > myOscPlugList = new ArrayList< OscPlug >( );
					myOscPlugList.add( myOscPlug );
					_myOscPlugMap.put( theAddrPattern , myOscPlugList );
				}
			}
		}
	}

	private void callMethod( final OscMessage theOscMessage ) {

		/* forward the received message to all OscEventListeners */

		for ( int i = listeners( ).size( ) - 1 ; i >= 0 ; i-- ) {
			( ( OscEventListener ) listeners( ).get( i ) ).oscEvent( theOscMessage );
		}

		/* check if the arguments can be forwarded as array */

		if ( theOscMessage.isArray ) {
			if ( _myOscPlugMap.containsKey( theOscMessage.getAddress( ) ) ) {
				List< OscPlug > myOscPlugList = _myOscPlugMap.get( theOscMessage.getAddress( ) );
				for ( OscPlug plug : myOscPlugList ) {
					if ( plug.isArray && plug.checkMethod( theOscMessage , true ) ) {
						invoke( plug.getObject( ) , plug.getMethod( ) , theOscMessage.argsAsArray( ) );
					}
				}
			}

		}

		if ( _myOscPlugMap.containsKey( theOscMessage.getAddress( ) ) ) {
			List< OscPlug > myOscPlugList = _myOscPlugMap.get( theOscMessage.getAddress( ) );
			for ( OscPlug plug : myOscPlugList ) {
				if ( !plug.isArray && plug.checkMethod( theOscMessage , false ) ) {
					theOscMessage.isPlugged = true;
					invoke( plug.getObject( ) , plug.getMethod( ) , theOscMessage.getArguments( ) );
				}
			}
		}

		/* if no plug method was detected, then use the default oscEvent method */

		if ( isEventMethod ) {
			try {
				invoke( parent , _myEventMethod , new Object[] { theOscMessage } );
			} catch ( ClassCastException e ) {
				LOGGER.warning( "OscHandler.callMethod, ClassCastException." + e );
			}
		}

	}

	private void invoke( final Object theObject , final Method theMethod , final Object[] theArgs ) {

		try {
			theMethod.invoke( theObject , theArgs );
		} catch ( IllegalArgumentException e ) {
			e.printStackTrace( );
		} catch ( IllegalAccessException e ) {
			e.printStackTrace( );
		} catch ( InvocationTargetException e ) {
			e.printStackTrace( );
			LOGGER.finest( "An error occured while forwarding an OscMessage\n " + "to a method in your program. please check your code for any \n" + "possible errors that might occur in the method where incoming\n "
					+ "OscMessages are parsed e.g. check for casting errors, possible\n " + "nullpointers, array overflows ... .\n" + "method in charge : " + theMethod.getName( ) + "  " + e );
		}

	}

	public void process( Object o ) {
		if ( o instanceof Map ) {
			process( OscPacket.parse( ( Map ) o ) );
		}
	}

	private void process( OscPacket thePacket ) {
		if ( thePacket instanceof OscMessage ) {
			callMethod( ( OscMessage ) thePacket );
		} else if ( thePacket instanceof OscBundle ) {
			OscBundle bundle = ( OscBundle ) thePacket;
			for ( OscPacket p : bundle.messages ) {
				process( p );
			}
		}
	}

	public OscProperties properties( ) {
		return _myOscProperties;
	}

	public boolean isBroadcast( ) {
		return isBroadcast;
	}

	public String ip( ) {
		return NetInfo.getHostAddress( );
	}

	/* TODO */

	// public void setTimeToLive( int theTTL ) {
	// _myOscNetManager.setTimeToLive( theTTL );
	// }
	//
	// public TcpServer tcpServer( ) {
	// return _myOscNetManager.tcpServer( );
	// }
	//
	// public TcpClient tcpClient( ) {
	// return _myOscNetManager.tcpClient( );
	// }
	//
	/**
	 * you can send osc packets in many different ways. see below and use the send method that fits
	 * your needs.
	 */

	public void send( final OscPacket thePacket ) {
		transmit.send( thePacket.getBytes( ) );
	}

	public void send( final String theAddrPattern , final Object ... theArguments ) {
		transmit.send( new OscMessage( theAddrPattern , theArguments ).getBytes( ) );
	}

	public void send( final NetAddress theNetAddress , String theAddrPattern , Object ... theArguments ) {
		transmit.send( new OscMessage( theAddrPattern , theArguments ).getBytes( ) , theNetAddress.address( ) , theNetAddress.port( ) );
	}

	public void send( final NetAddress theNetAddress , final OscPacket thePacket ) {
		transmit.send( thePacket.getBytes( ) , theNetAddress.address( ) , theNetAddress.port( ) );
	}

	public boolean send( final OscPacket thePacket , final Object theRemoteSocket ) {
		if ( theRemoteSocket != null ) {

			byte[] b = thePacket.getBytes( );
			ByteBuffer buffer = ByteBuffer.allocate( b.length );
			buffer.clear( );
			buffer.put( b );
			buffer.flip( );

			if ( theRemoteSocket instanceof SocketChannel ) {
				try {
					( ( SocketChannel ) theRemoteSocket ).write( buffer );
					return true;
				} catch ( IOException e ) {
					e.printStackTrace( );
				}
			} else if ( theRemoteSocket instanceof DatagramChannel ) {
				try {
					DatagramChannel d = ( ( DatagramChannel ) theRemoteSocket );
					
					System.out.println( String.format( "channel :  %s %s" ,   d.isConnected( ) , d.socket( ).getInetAddress( )));	
					( ( DatagramChannel ) theRemoteSocket ).write( buffer );
					return true;
				} catch ( IOException e ) {
					e.printStackTrace( );
				} catch ( NotYetConnectedException e ) {
					/* received a datagram packet, sender ip and port have been identified but we
					 * are not able to connect to remote address due to no open socket availability. */
					// e.printStackTrace( );
				}
			}
		}
		return false;
	}

	public void send( final int thePort , final String theAddrPattern , final String theAddress , final Object ... theArguments ) {
		/* TODO */
		// _myOscNetManager.send( theAddress , thePort , theAddrPattern , theArguments );
	}

	public void send( final TcpClient theClient , final OscPacket thePacket ) {
		/* TODO */
	}

	public void send( final TcpClient theClient , final String theAddrPattern , final Object ... theArguments ) {
		send( theClient , new OscMessage( theAddrPattern , theArguments ) );
	}

	public void send( final String theHost , final int thePort , final OscPacket thePacket ) {
		transmit.send( thePacket.getBytes( ) , theHost , thePort );
	}

	public void send( final OscPacket thePacket , final String theHost , final int thePort ) {
		transmit.send( thePacket.getBytes( ) , theHost , thePort );
	}

	/**
	 * a static method to send an OscMessage straight out of the box without having to instantiate
	 * oscP5.
	 */
	public static void flush( final NetAddress theNetAddress , final OscMessage theOscMessage ) {
		flush( theNetAddress , theOscMessage.getBytes( ) );
	}

	public static void flush( final NetAddress theNetAddress , final OscPacket theOscPacket ) {
		flush( theNetAddress , theOscPacket.getBytes( ) );
	}

	public static void flush( final NetAddress theNetAddress , final String theAddrPattern , final Object ... theArguments ) {
		flush( theNetAddress , ( new OscMessage( theAddrPattern , theArguments ) ).getBytes( ) );
	}

	/* DEPRECATED methods and constructors. */

	@Deprecated
	public void process( final DatagramPacket thePacket , final int thePort ) {
		/* TODO , process( Map ) should be used. */
	}

	@Deprecated
	public static void flush( final OscMessage theOscMessage , final NetAddress theNetAddress ) {
		flush( theOscMessage.getBytes( ) , theNetAddress );
	}

	@Deprecated
	public static void flush( final OscPacket theOscPacket , final NetAddress theNetAddress ) {
		flush( theOscPacket.getBytes( ) , theNetAddress );
	}

	@Deprecated
	public static void flush( final String theAddrPattern , final Object[] theArguments , final NetAddress theNetAddress ) {
		flush( ( new OscMessage( theAddrPattern , theArguments ) ).getBytes( ) , theNetAddress );
	}

	@Deprecated
	public static void flush( final byte[] theBytes , final NetAddress theNetAddress ) {
		DatagramSocket mySocket;
		try {
			mySocket = new DatagramSocket( );

			DatagramPacket myPacket = new DatagramPacket( theBytes , theBytes.length , theNetAddress.inetaddress( ) , theNetAddress.port( ) );
			mySocket.send( myPacket );
		} catch ( SocketException e ) {
			LOGGER.warning( "OscP5.openSocket, can't create socket " + e.getMessage( ) );
		} catch ( IOException e ) {
			LOGGER.warning( "OscP5.openSocket, can't create multicastSocket " + e.getMessage( ) );
		}
	}

	@Deprecated
	public static void flush( final byte[] theBytes , final String theAddress , final int thePort ) {
		flush( theBytes , new NetAddress( theAddress , thePort ) );
	}

	@Deprecated
	public static void flush( final OscMessage theOscMessage , final String theAddress , final int thePort ) {
		flush( theOscMessage.getBytes( ) , new NetAddress( theAddress , thePort ) );
	}

	@Deprecated
	public OscP5( final Object theParent , final String theHost , final int theSendToPort , final int theReceiveAtPort , final String theMethodName ) {

		welcome( );

		parent = theParent;

		registerDispose( parent );

		/* TODO */
	}

	@Deprecated
	public OscMessage newMsg( String theAddrPattern ) {
		return new OscMessage( theAddrPattern );
	}

	@Deprecated
	public OscBundle newBundle( ) {
		return new OscBundle( );
	}

	/**
	 * used by the monome library by jklabs
	 */
	@Deprecated
	public void disconnectFromTEMP( ) {
	}

	@Deprecated
	public OscP5( final Object theParent , final String theAddress , final int thePort ) {
		// this( theParent , theAddress , thePort , OscProperties.MULTICAST );
		parent = theParent;
	}

	@Deprecated
	public void send( final String theAddrPattern , final Object[] theArguments , final NetAddress theNetAddress ) {
		/* TODO */
		// _myOscNetManager.send( theAddrPattern , theArguments , theNetAddress );
	}

	@Deprecated
	public void send( final String theAddrPattern , final Object[] theArguments , final NetAddressList theNetAddressList ) {
		/* TODO */
		// _myOscNetManager.send( theAddrPattern , theArguments , theNetAddressList );
	}

	@Deprecated
	public void send( final String theAddrPattern , final Object[] theArguments , final String theAddress , int thePort ) {
		transmit.send( new OscMessage( theAddrPattern , theArguments ).getBytes( ) , theAddress , thePort );
	}

	@Deprecated
	public void send( final String theAddrPattern , final Object[] theArguments , final TcpClient theClient ) {
		send( new OscMessage( theAddrPattern , theArguments ) , theClient );
	}

	@Deprecated
	public void send( final OscPacket thePacket , final NetAddress theNetAddress ) {
		send( theNetAddress , thePacket );
	}

	@Deprecated
	public void send( final OscPacket thePacket , final NetAddressList theNetAddressList ) {
		/* TODO */
		// _myOscNetManager.send( thePacket , theNetAddressList );
	}

	@Deprecated
	public void send( final String theAddress , final int thePort , final String theAddrPattern , final Object ... theArguments ) {
		transmit.send( new OscMessage( theAddrPattern , theArguments ).getBytes( ) , theAddress , thePort );
	}

	@Deprecated
	public void send( final OscPacket thePacket , final TcpClient theClient ) {
	}

	public void send( final NetAddressList theNetAddressList , final OscPacket thePacket ) {
		/* TODO */
		// _myOscNetManager.send( thePacket , theNetAddressList );
	}

	public void send( final NetAddressList theNetAddressList , final String theAddrPattern , final Object ... theArguments ) {
		/* TODO */
		// _myOscNetManager.send( theNetAddressList , theAddrPattern , theArguments );
	}

	@Deprecated
	public static void setLogStatus( final int theIndex , final int theValue ) {
	}

	@Deprecated
	public static void setLogStatus( final int theValue ) {
	}

	@Deprecated
	public NetInfo netInfo( ) {
		return new NetInfo( );
	}

	/* Notes */

	/* TODO implement polling option to avoid threading and synchronization issues. check email from
	 * tom lieber. look into mutex objects.
	 * http://www.google.com/search?hl=en&q=mutex+java&btnG=Search */

	/* how to disable the logger
	 * 
	 * Logger l0 = Logger.getLogger(""); // get the global logger
	 * 
	 * l0.removeHandler(l0.getHandlers()[0]); // remove handler */

}
