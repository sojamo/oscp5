package oscP5;

import netP5.ITransfer;
import netP5.UDPTransfer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static oscP5.OscUtils.toList;

/**
 * The main class to run an OSC implementation for the java based Processing sketches over UDP protocol.
 */
public class OscP5 extends AOscP5 {

    static public final String VERSION = "2.1.0";
    static public final String METHOD_TO_INVOKE = "oscEvent";
    static public final OscPatternMatcher DEFAULT_PATTERN_MATCHER = new OscPatternMatcher("//");
    static public boolean PRINT_STACKTRACE = false;
    static protected final Logger Log = Logger.getLogger(OscP5.class.getName());
    static protected final Class[] oscMessageClass = new Class[]{OscMessage.class};
    static private boolean welcome;
    final private List<NetAddress> destinations = new ArrayList<>();
    final private Map<OscPatternMatcher, List<OscEventListener>> addressSpace = new LinkedHashMap<>();
    private OscProperties properties;
    private ITransfer transfer;


    {
        /**
         * setLogLevel is a static function that can be called to adjust the Logging Level.
         * By default the Logging Level is set to Warning but if necessary can be changed.
         * Invoke OscP5.setLogLevel(java.util.logging.Level) before instantiating OscP5()
         * https://docs.oracle.com/javase/8/docs/api/java/util/logging/class-use/Level.html
         */
        setLogLevel(Level.WARNING);
    }

    static private final OscEventListener placeholder = new OscEventListener() {
        public void oscEvent(final OscMessage m) {
        }
    };

    /**
     * private constructor to say hello and invoke the super-class constructor.
     */
    private OscP5() {
        super();
        if (!welcome) {
            System.out.println("OscP5 library " + VERSION + " https://sojamo.de/libraries/oscp5");
            welcome = true;
        }
        addressSpace.put(DEFAULT_PATTERN_MATCHER, new ArrayList<>());
    }

    /**
     * The default constructor to call.
     * If theApp is of type PApplet, then the publishing of OscMessages happens automatically.
     * OscP5 does queue incoming messages and releases these by invoking osc.publish().
     * It is then when OscMessages are published to their listeners.
     *
     * @param theApp  the reference to the instantiating class
     * @param thePort the port to use when listening for incoming messages
     */
    public OscP5(final Object theApp,
                 final int thePort) {
        this(NetAddress.DEFAULT_ADDR, thePort);
        register(theApp);
    }


    /**
     * Start OscP5 without reference to the instantiating class.
     * We wont check for the presence of the oscEvent nor will the publishing of received OscMessages be automated.
     * In order to publish and consume OscMessages you must call oscP5.publish();
     * This is necessary so that queued OscMessages can be released to their listeners.
     *
     * @param theHost the host address to use when listening for incoming messages (default is 0.0.0.0)
     * @param thePort the port to use when listening for incoming messages
     */
    public OscP5(final String theHost,
                 final int thePort) {
        this();
        properties = new OscProperties();
        properties.setAddress(theHost);
        properties.setPort(thePort);
        startWith(properties);
    }

    /**
     * Starts OscP5 with reference to the instantiating class.
     * If this class is of type PApplet, the publishing of OscMessages will happen automatically.
     * If this is not the case, you need to call osc.publish(); in order to release queued OscMessages to its listeners.
     *
     * @param theApp  the reference to the instantiating class
     * @param theHost the host address to use when listening for incoming messages (default is 0.0.0.0)
     * @param thePort the port to use when listening for incoming messages
     */
    public OscP5(final Object theApp,
                 final String theHost,
                 final int thePort) {
        this(theHost, thePort);
        register(theApp);
    }

    /**
     * Use this constructor when passing custom OscProperties.
     * This can be useful when increasing the UDP datagram packet size which by default is set to 1500 bytes.
     *
     * @param theApp
     * @param theProperties
     */
    public OscP5(final Object theApp,
                 final OscProperties theProperties) {
        /* let the superclass know about us */
        this();
        startWith(theProperties);
        register(theApp);
    }

    private void startWith(OscProperties theProperties) {
        String host = theProperties.getAddress();
        int port = theProperties.getPort();
        int datagramSize = theProperties.getDatagramSize();
        transfer = new UDPTransfer(host, port, datagramSize);
    }

    private void register(Object theApp) {
        /* Check if theApp is of type PApplet */
        registerPApplet(theApp);
        /* Check if theApp implements the oscEvent method */
        subscribe(DEFAULT_PATTERN_MATCHER, checkEventMethod(theApp, METHOD_TO_INVOKE, oscMessageClass));
        /* OK we are done with initializing OscP5 */
    }

    @Override
    public void dispose() {
        Log.log(Level.INFO, "Disposing OscP5 instance, closing socket.");
        transfer.close();
    }


    @Override
    public OscEventListener addListener(OscEventListener theListener) {
        addressSpace.get(DEFAULT_PATTERN_MATCHER).add(theListener);
        return theListener;
    }

    @Override
    public void removeListener(OscEventListener theListener) {
        for (List<OscEventListener> o0 : addressSpace.values()) {
            o0.remove(theListener);
        }
    }

    @Override
    public List<OscEventListener> getListeners() {
        List<OscEventListener> list = new ArrayList<>();
        for (List<OscEventListener> o0 : addressSpace.values()) {
            list.addAll(o0);
        }
        return list;
    }

    @Override
    public void addDestination(NetAddress theDestination) {
        destinations.add(theDestination);
    }

    @Override
    public void removeDestination(NetAddress theDestination) {
        destinations.remove(theDestination);
    }

    @Override
    public void removeDestination(String theAddress, int thePort) {
        /* TODO identigy NetAddress by Address and port number */
    }

    @Override
    public List<NetAddress> getDestinations() {
        return destinations;
    }

    @Override
    public OscProperties getProperties() {
        return properties;
    }

    @Override
    public String version() {
        return VERSION;
    }

    @Override
    public void send(final NetAddress theIAddress,
                     final OscPacket thePacket) {
        transfer.send(theIAddress, thePacket);
    }

    @Override
    public void send(final List<NetAddress> theList,
                     final OscPacket thePacket) {
        for (NetAddress addr : theList) {
            send(addr, thePacket);
        }
    }

    @Override
    public void send(final NetAddress theIAddress,
                     final String theAddress,
                     final Object... theArguments) {
        send(theIAddress, new OscMessage(theAddress, toList(theArguments)));
    }

    @Override
    public void send(final List<NetAddress> theList,
                     final String theAddress,
                     final Object... theArguments) {
        OscMessage m = new OscMessage(theAddress);
        m.add(theArguments);
        send(theList, m);
    }

    @Override
    public void broadcast(OscPacket thePacket) {
        send(destinations, thePacket);
    }

    public OscEventListener subscribe(final String theAddressPattern,
                                      final Object theObject,
                                      final String theMethod
    ) {
        return subscribe(new OscPatternMatcher(theAddressPattern), checkEventMethod(theObject, theMethod, oscMessageClass));
    }

    public OscEventListener subscribe(final String theAddressPattern, final OscEventListener theListener) {
        return subscribe(new OscPatternMatcher(theAddressPattern), theListener);
    }

    public OscEventListener subscribe(final OscEventListener theListener) {
        subscribe(DEFAULT_PATTERN_MATCHER, theListener);
        return theListener;
    }

    public OscEventListener subscribe(final OscPatternMatcher thePattern, final OscEventListener theListener) {
        if (theListener != null && !theListener.equals(placeholder)) {
            if (!addressSpace.containsKey(thePattern)) {
                addressSpace.put(thePattern, new ArrayList<>());
            }
            addressSpace.get(thePattern).add(theListener);
        }
        System.out.println(addressSpace);
        return theListener;
    }

    public OscP5 cancel(final OscEventListener theListener) {
        for (Map.Entry<OscPatternMatcher, List<OscEventListener>> entry : addressSpace.entrySet()) {
            List<OscEventListener> listeners = entry.getValue();
            listeners.remove(theListener);
        }
        return this;
    }

    public final int publish() {
        /* request queued messages from the transfer implementation */
        final List<OscMessage> messages = transfer.publish();
        final Map<OscEventListener, OscMessage> result = new LinkedHashMap<>();
        /* go through all messages and match them against the address space */
        for (final OscMessage message : messages) {
            final String address = message.getAddress();
            /* Go through the address space and, if the matching is successful, forward the message */
            for (Map.Entry<OscPatternMatcher, List<OscEventListener>> entry : addressSpace.entrySet()) {
                final OscPatternMatcher pattern = entry.getKey();
                final List<OscEventListener> listeners = entry.getValue();
                if (true) { /* TODO do the matching of address against OscPattern here */
                    for (final OscEventListener listener : listeners) {
                        listener.oscEvent(message);
                        /* TODO can result in ConcurrentModificationException when adding new pattern to address space in oscEvent() */
                    }
                }
            }
        }
        return messages.size();
    }

    private OscP5 invoke(final Object theObject,
                         final String theMethodName,
                         final Class<?>[] theClasses,
                         final Object[] theArgs) {
        try {
            final Method method = theObject.getClass().getMethod(theMethodName, theClasses);
            try {
                method.invoke(theObject, theArgs);
            } catch (final Exception e) {
                Log.log(Level.WARNING, "Invoking " + theArgs, " failed " + e.getMessage());
                OscP5.printStackTrace(e);
            }

        } catch (final NoSuchMethodException e) {
            Log.log(Level.WARNING, "Invoking method " + theMethodName + " failed " + e.getMessage());
            OscP5.printStackTrace(e);
        }
        return this;
    }

    private boolean match(final String s1,
                          final String s2) {
        /* TODO check the received address pattern (s1) against the expected (s2). Possibly wildcard, exact match, etc. */
        return true;
    }

    private OscEventListener checkEventMethod(final Object theObject,
                                              final String theMethod,
                                              final Class<?>[] theClass) {
        try {
            final Method method = theObject.getClass().getDeclaredMethod(theMethod, theClass);
            method.setAccessible(true);

            return new OscEventListener() {
                @Override
                public void oscEvent(final OscMessage m) {
                    try {
                        if (theClass.equals(oscMessageClass)) {
                            method.invoke(theObject, m);
                        } else {
                            Object[] args = new Object[m.getArgumentsAsList().size()];
                            for (Class c : theClass) {
                                if (c.equals(int.class)) {
                                    args[0] = m.getObjectAt(0);
                                }
                            }
                            System.out.println(args);
                            method.invoke(theObject, args);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        OscP5.printStackTrace(e);
                    }
                }
            };
        } catch (SecurityException | NoSuchMethodException e1) {
            Log.log(Level.WARNING, "checkEventMethod was not successful, " +
                    e1.getMessage() + " may not implement oscEvent(). " + e1.getLocalizedMessage());
            OscP5.printStackTrace(e1);
        }
        return placeholder;
    }


    public void plug(final Object theObject,
                     final String theMethodName,
                     final String theAddressPattern,
                     final String theTypeTag) {

    }

    public void plug(final Object theObject,
                     final String theMethodName,
                     final String theAddressPattern) {
        subscribe(theAddressPattern, checkEventMethod(theObject, theMethodName, new Class[]{int.class}));
    }


    /**
     * Check if we are dealing with a PApplet parent. If this is
     * the case, register "dispose". Do so quietly, no error
     * messages will be displayed.
     */
    private void registerPApplet(final Object theObject) {

        final String child = "processing.core.PApplet";
        Object parent = null;

        /* Check if we are dealing with a PApplet instance */
        try {

            final Class<?> childClass = Class.forName(child);
            final Class<?> parentClass = Object.class;

            if (parentClass.isAssignableFrom(childClass)) {
                parent = childClass.getDeclaredConstructor().newInstance();
                parent = theObject;
            }

            assert parent != null;
            invoke(parent, "registerMethod", new Class[]{String.class, Object.class}, new Object[]{"pre", this});
            invoke(parent, "registerMethod", new Class[]{String.class, Object.class},
                    new Object[]{"dispose", this});

        } catch (Exception e) {
            Log.log(Level.WARNING, "Failed to register PApplet " + e.getMessage());
            OscP5.printStackTrace(e);
        }

    }

    /**
     * PApplet specific method which is called automatically before the sketch calls its draw routine.
     * Here all queued OscMessages are consumed and then passed on to oscEvent and OscEventListeners.
     */
    public void pre() {
        publish();
    }

    public static void setLogLevel(Level targetLevel) {
        Logger root = Logger.getLogger("");
        root.setLevel(targetLevel);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(targetLevel);
        }
    }

    public static void printStackTrace(Exception e) {
        if (PRINT_STACKTRACE) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "{class: OscP5" +
                ", properties: " + properties +
                "}";
    }
}
