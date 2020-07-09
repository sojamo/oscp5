

package netP5;

import oscP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscParser;
import oscP5.OscTimetag;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public abstract class ATransfer implements ITransfer {

    static final Logger Log = Logger.getLogger(ATransfer.class.getName());

    /* start a scheduled executor service to invoke time-tagged messages in the future */
    final private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

    /* initialize a queue to store all incoming OSC messages before they are published */
    final private ArrayBlockingQueue<OscMessage> queue;

    public ATransfer(final int theQueueSize) {
        queue = new ArrayBlockingQueue<>(theQueueSize);
    }

    @Override
    public void immediately(final OscMessage theMessage) {
        queue.offer(theMessage);
    }

    @Override
    public void later(final OscMessage theMessage,
                      final long theMillis) {
        if (theMillis < 0) {
            return; /* in case the message has expired, don't schedule it. */
        }
        schedule.schedule(new Runnable() {
            @Override
            public void run() {
                queue.offer(theMessage);
            }
        }, theMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void process(final byte[] theBytes,
                        final NetAddress theReceivedFrom) {
        if (OscParser.isBundle(theBytes)) {

            /*
             * use a map to collect all incoming message, then pass the
             * incoming data to the parser
             */
            final Map<OscMessage, Long> collect = OscParser.bytesToPackets(theBytes);

            /* schedule the messages that were received */
            for (final Map.Entry<OscMessage, Long> entry : collect.entrySet()) {
                final long t = entry.getValue();
                final OscMessage m = entry.getKey();
                m.setFrom(theReceivedFrom);
                /* if time has passed or timetag is default 1, then publish immediately */
                if (t == OscTimetag.TIMETAG_NOW || t <= 0) {
                    immediately(m);
                } else {
                    /* if timetag is set to be a future time, then schedule to be published later */
                    later(m, t);
                }
            }
        } else {
            final OscMessage m = OscParser.bytesToMessage(theBytes);
            m.setFrom(theReceivedFrom);
            immediately(m);
        }

        /*
         * to grab a copy of the raw data received, add an observer
         * to the Transfer implementation
         */

        /* TODO notify listeners here, we are not using Observer anymore since it is deprecated with Java 9 */

        // setChanged();
        // notifyObservers(theBytes);

        /* messages can now be consumed by calling @see #consume() */
    }

    @Override
    public List<OscMessage> publish() {
        final List<OscMessage> messages = new ArrayList<>();
        queue.drainTo(messages);
        return messages;
    }

    public static HashMap<String, String> getNetworkInterfaces() {
        final HashMap<String, String> interfaces = new HashMap<>();
        final String domain = "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}";
        final String localhost = "localhost";
        final String ip = "(([0-9]{1,3}\\.){3})[0-9]{1,3})";
        final Pattern p = Pattern.compile("^" + domain + "|" + localhost + "|" + ip);
        try {
            for (final Enumeration<NetworkInterface> item = NetworkInterface.getNetworkInterfaces();
                /* iterate interfaces */
                 item.hasMoreElements(); ) {
                final NetworkInterface intf = item.nextElement();
                for (final Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                    /* iterate addresses */
                     enumIpAddr.hasMoreElements(); ) {
                    final String addr = enumIpAddr.nextElement().toString().replaceAll("/", "");
                    /* extract ip address and ignore other interfaces */
                    if (p.matcher(addr).matches()) {
                        interfaces.put(intf.getName(), addr);
                    }
                }
            }
        } catch (final SocketException e) {
            Log.log(Level.WARNING, "ATransfer.printNetworkInterfaces: (error retrieving network interface list)");
        }
        return interfaces;
    }
}
