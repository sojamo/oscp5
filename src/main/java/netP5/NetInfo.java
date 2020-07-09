package netP5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class NetInfo {

    private final static Logger Log = Logger.getLogger(NetInfo.class.getName());

    public NetInfo() {
    }

    @Deprecated
    public static void print() {
        /** TODO re-implement */
    }

    @Deprecated
    public static String getHostAddress() {
        /** TODO re-implement */
        return null;
    }

    @Deprecated
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
        Log.info("Checking internet  connection ...");
        try {
            u = new URL(URLstring);
        } catch (final MalformedURLException e) {
            Log.warning("Bad URL " + URLstring + " " + e);
        }

        InputStream in = null;
        try {
            in = u.openStream();
            isConnectedToInternet = true;
        } catch (final IOException e) {
            Log.warning("Unable to open  " + URLstring + "\n" + "Either the  " + URLstring
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
                        Log.info("WAN address : " + myIp);
                    }
                }
            } catch (final IOException e) {
                Log.warning("I/O error reading  " + URLstring + " Exception = " + e);
            }
        }
        return myIp;
    }

    /**
     * returns a map of available network interfaces. this map's
     * keys use the network interface's name as identifier. Each
     * value is a map with the following keys: name (eg en0,
     * eth0, lo0), display-name (en0, Wireless Network
     * Connection, Loopback), mac (the device's MAC address as
     * byte-array), inet-address (java.net.InetAddress see
     * javadoc)and network-interface (the raw
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
