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

import java.util.List;
import java.util.Vector;

import netP5.NetAddress;

/**
 * osc properties are used to start oscP5 with more specific settings. osc properties have to be
 * passed to oscP5 in the constructor when starting a new instance of oscP5.
 * 
 * @related OscP5
 * @example oscP5properties
 */
public class OscProperties {

	static public final boolean ON = true;

	static public final boolean OFF = false;

	static public final int UDP = 0;

	static public final int MULTICAST = 1;

	static public final int TCP = 2;

	private boolean isLocked = false;

	private final List< OscEventListener > listeners = new Vector< OscEventListener >( );

	static public final NetAddress defaultnetaddress = new NetAddress( "" , 0 );

	private NetAddress _myRemoteAddress = defaultnetaddress;

	private int _myListeningPort = 0; /* default listening port */

	private int _myDatagramSize = 1536; /* default datagram buffer size */

	private String _myDefaultEventMethodName = "oscEvent";

	private int _myNetworkProtocol = UDP;

	private boolean _mySendStatus = false;

	private boolean _mySRSP = OFF; // (S)end (R)eceive (S)ame (P)ort

	public OscProperties( ) {
	}

	public OscProperties( final NetAddress theNetAddress ) {
		_myRemoteAddress = theNetAddress;
	}

	public OscProperties( int theReceiveAtPort ) {
		_myListeningPort = theReceiveAtPort;
	}

	public OscProperties( OscEventListener theParent ) {
		listeners.add( theParent );
	}

	public List< OscEventListener > listeners( ) {
		return listeners;
	}

	public boolean sendStatus( ) {
		return _mySendStatus;
	}
	
	public void setRemoteAddress( final String theHostAddress , final int thePort ) {
		setRemoteAddress( new NetAddress( theHostAddress , thePort ) );
	}

	public void setRemoteAddress( NetAddress theNetAddress ) {
		_myRemoteAddress = theNetAddress;
		_mySendStatus = _myRemoteAddress.isvalid( );
	}

	public void setListeningPort( final int thePort ) {
		_myListeningPort = thePort;
	}

	public void setDatagramSize( final int theSize ) {
		if ( !isLocked ) {
			_myDatagramSize = theSize;
		} else {
			OscP5.LOGGER.warning( "datagram size can only be set before initializing oscP5\ncurrent datagram size is " + _myDatagramSize + ", use OscProperties.setDatagramSize( int )." );
		}
	}

	/**
	 * set the name of the default event method. the event method is the method to which incoming
	 * osc messages are forwarded. the default name for the event method is "oscEvent"
	 */
	public void setEventMethod( final String theEventMethod ) {
		_myDefaultEventMethodName = theEventMethod;
	}

	/**
	 * set the network protocol over which osc messages are transmitted. options are
	 * OscProperties.UDP and OscProperties.MULTICAST the network protocol can only be set before
	 * initializing oscP5. TODO
	 */
	public void setNetworkProtocol( final int theProtocol ) {
		if ( !isLocked ) {
			if ( theProtocol > 2 ) {
				OscP5.LOGGER.warning( "OscProperties.setNetworkProtocol, not in the range of supported Network protocols. the network protocol defaults to UDP" );
			} else {
				_myNetworkProtocol = theProtocol;
			}
		} else {
			OscP5.LOGGER.warning( "OscProperties.setNetworkProtocol, network protocol can only be set before initializing oscP5." );
		}
	}

	public int listeningPort( ) {
		return _myListeningPort;
	}

	public NetAddress remoteAddress( ) {
		return _myRemoteAddress;
	}

	public int datagramSize( ) {
		return _myDatagramSize;
	}

	public String eventMethod( ) {
		return _myDefaultEventMethodName;
	}

	/**
	 * returns the network protocol being used to transmit osc packets. returns an int. 0 (UDP), 1
	 * (MULTICAST), 2 (TCP)
	 */
	public int networkProtocol( ) {
		return _myNetworkProtocol;
	}

	/**
	 * prints out the current osc properties settings.
	 * 
	 * @return String
	 * @related OscProperties
	 */
	public String toString( ) {
		String s = "\nnetwork protocol: " + ( ( new String[] { "udp" , "tcp" , "multicast" } )[ _myNetworkProtocol ] ) + "\n";
		s += "host: " + ( ( _myRemoteAddress.address( ) != null ) ? _myRemoteAddress.address( ) : "host address not set." ) + "\n";
		s += "sendToPort: " + _myRemoteAddress.port( ) + "\n";
		s += "receiveAtPort: " + listeningPort( ) + "\n";
		s += "datagramSize: " + _myDatagramSize + "\n";
		s += "event Method: " + _myDefaultEventMethodName + "\n";
		s += "(S)end(R)eceive(S)ame(P)ort: " + this._mySRSP + "\n\n";
		return s;
	}

	/* Deprecated */

	/**
	 * SRSP stand for Send and Receive on Same Port. by default osc packets are not received and
	 * sent by the same port. if you need to send and receive on the same port call
	 * setSRSP(OscProperties.ON)
	 */
	@Deprecated
	public void setSRSP( final boolean theFlag ) {
		_mySRSP = theFlag;
	}

	/**
	 * you can send and receive at the same port while on a udp con
	 */
	@Deprecated
	public boolean srsp( ) {
		return _mySRSP;
	}

}
