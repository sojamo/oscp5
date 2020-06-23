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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 * 
 * @author ##author##
 * @modified ##date##
 * @version ##version##
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

	private final static Logger LOGGER = Logger.getLogger(NetInfo.class.getName());

	public NetInfo() {
	}

	public static void print() {
		/** TODO re-implement */
	}

	public static String getHostAddress() {
		/** TODO re-implement */
		return null;
	}

	public static String lan() {
		/** TODO re-implement */
		return null;
	}

	public static String wan() {
		/** TODO re-implement */
		String myIp = null;
		URL u = null;
		final String URLstring = "http://checkip.dyndns.org";
		boolean isConnectedToInternet = false;
		LOGGER.info("Checking internet  connection ...");
		try {
			u = new URL(URLstring);
		} catch (final MalformedURLException e) {
			LOGGER.warning("Bad URL " + URLstring + " " + e);
		}

		InputStream in = null;
		try {
			in = u.openStream();
			isConnectedToInternet = true;
		} catch (final IOException e) {
			LOGGER.warning("Unable to open  " + URLstring + "\n" + "Either the  " + URLstring
					+ " is unavailable or this machine  is not" + "connected to the internet !");
		}

		if (isConnectedToInternet) {
			try {
				final BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line;
				String theToken = "";
				while ((line = br.readLine()) != null) {
					theToken += line;
				}
				br.close();

				final StringTokenizer st = new StringTokenizer(theToken, " <>", false);

				while (st.hasMoreTokens()) {
					String myToken = st.nextToken();
					if (myToken.compareTo("Address:") == 0) {
						myToken = st.nextToken();
						myIp = myToken;
						LOGGER.info("WAN address : " + myIp);
					}
				}
			} catch (final IOException e) {
				LOGGER.warning("I/O error reading  " + URLstring + " Exception = " + e);
			}
		}
		return myIp;
	}

	/**
	 * 
	 * returns a map of available network interfaces. this map's keys use the
	 * network interface's name as identifier. Each value is a map with the
	 * following keys: name (eg en0, eth0, lo0), display-name (en0, Wireless Network
	 * Connection, Loopback), mac (the device's MAC address as byte-array),
	 * inet-address (java.net.InetAddress see javadoc)and network-interface (the raw
	 * java.net.NetworkInterface object, see javadoc).
	 */
	static public Map<String, Map> getNetworkInterfaces() {
		final Map<String, Map> m = new HashMap<String, Map>();
		Enumeration<NetworkInterface> nets;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
			for (final NetworkInterface netint : Collections.list(nets)) {
				final Map<String, Object> m1 = new HashMap<String, Object>();
				m.put(netint.getDisplayName(), m1);
				m1.put("name", netint.getName());
				m1.put("display-name", netint.getDisplayName());
				m1.put("mac", netint.getHardwareAddress());
				m1.put("network-interface", netint);
				final Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
				for (final InetAddress inetAddress : Collections.list(inetAddresses)) {
					m1.put("inet-address", inetAddress);
				}

			}
		} catch (final SocketException e) {
			e.printStackTrace();
		}

		return m;
	}

	static public String getIpAddress() {
		/** TODO re-implement */
		String ip = "";
		try {
			final Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				final NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
				final Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					final InetAddress inetAddress = enumInetAddress.nextElement();

					String ipAddress = "";
					if (inetAddress.isLoopbackAddress()) {
						ipAddress = "LoopbackAddress: ";
					} else if (inetAddress.isSiteLocalAddress()) {
						ipAddress = "SiteLocalAddress: ";
					} else if (inetAddress.isLinkLocalAddress()) {
						ipAddress = "LinkLocalAddress: ";
					} else if (inetAddress.isMulticastAddress()) {
						ipAddress = "MulticastAddress: ";
					}
					ip += ipAddress + inetAddress.getHostAddress() + "\n";
				}

			}

		} catch (final SocketException e) {
			/** TODO Auto-generated catch block */
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}

		return ip;
	}

	public static void main(final String[] args) {
		NetInfo.wan();
	}

}
