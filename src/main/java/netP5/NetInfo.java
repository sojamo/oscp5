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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class NetInfo {

	private final static Logger LOGGER = Logger.getLogger( NetInfo.class.getName( ) );

	public NetInfo( ) {
	}

	public static void print( ) {
		try {
			java.net.InetAddress i = java.net.InetAddress.getLocalHost( );
			System.out.println( "### hostname/ip " + i ); // name and IP address
			System.out.println( "### hostname " + i.getHostName( ) ); // name
			System.out.println( "### ip " + i.getHostAddress( ) ); // IP address
			// only
		} catch ( Exception e ) {
			e.printStackTrace( );
		}
	}

	public static String getHostAddress( ) {
		try {
			java.net.InetAddress i = java.net.InetAddress.getLocalHost( );
			return i.getHostAddress( );
		} catch ( Exception e ) {
		}
		return "ERROR";
	}

	public static String lan( ) {
		LOGGER.info( "host address : " + getHostAddress( ) );
		return getHostAddress( );
	}

	public static String wan( ) {
		// create URL object.
		String myIp = null;
		URL u = null;
		String URLstring = "http://checkip.dyndns.org";
		boolean isConnectedToInternet = false;
		LOGGER.info( "Checking internet  connection ..." );
		try {
			u = new URL( URLstring );
		} catch ( MalformedURLException e ) {
			LOGGER.warning( "Bad URL " + URLstring + " " + e );
		}

		InputStream in = null;
		try {
			in = u.openStream( );
			isConnectedToInternet = true;
		} catch ( IOException e ) {
			LOGGER.warning( "Unable to open  " + URLstring + "\n" + "Either the  " + URLstring + " is unavailable or this machine  is not" + "connected to the internet !" );
		}

		if ( isConnectedToInternet ) {
			try {
				BufferedReader br = new BufferedReader( new InputStreamReader( in ) );
				String line;
				String theToken = "";
				while ( ( line = br.readLine( ) ) != null ) {
					theToken += line;
				}
				br.close( );

				StringTokenizer st = new StringTokenizer( theToken , " <>" , false );

				while ( st.hasMoreTokens( ) ) {
					String myToken = st.nextToken( );
					if ( myToken.compareTo( "Address:" ) == 0 ) {
						myToken = st.nextToken( );
						myIp = myToken;
						LOGGER.info( "WAN address : " + myIp );
					}
				}
			} catch ( IOException e ) {
				LOGGER.warning( "I/O error reading  " + URLstring + " Exception = " + e );
			}
		}
		return myIp;
	}

	/**
	 * 
	 * returns a map of available network interfaces. this map's keys use the network interface's
	 * name as identifier. Each value is a map with the following keys: name (eg en0, eth0, lo0),
	 * display-name (en0, Wireless Network Connection, Loopback), mac (the device's MAC address as
	 * byte-array), inet-address (java.net.InetAddress see javadoc)and network-interface (the raw
	 * java.net.NetworkInterface object, see javadoc).
	 */
	static public Map< String , Map > getNetworkInterfaces( ) {
		Map< String , Map > m = new HashMap< String , Map >( );
		Enumeration< NetworkInterface > nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces( );
			for ( NetworkInterface netint : Collections.list( nets ) ) {
				Map< String , Object > m1 = new HashMap< String , Object >( );
				m.put( netint.getDisplayName( ) , m1 );
				m1.put( "name" , netint.getName( ) );
				m1.put( "display-name" , netint.getDisplayName( ) );
				m1.put( "mac" , netint.getHardwareAddress( ) );
				m1.put( "network-interface" , netint );
				Enumeration< InetAddress > inetAddresses = netint.getInetAddresses( );
				for ( InetAddress inetAddress : Collections.list( inetAddresses ) ) {
					m1.put( "inet-address" , inetAddress );
				}

			}
		} catch ( SocketException e ) {
			e.printStackTrace( );
		}

		return m;
	}

	public static void main( String[] args ) {
		NetInfo.wan( );
	}
}
