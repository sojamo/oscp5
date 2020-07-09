package oscP5;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parses incoming packets and constructs outgoing packets.
 * <p>
 * OSC specifications from the NIME 2009 paper https://www.nime.org/proceedings/2009/nime2009_116.pdf
 */
public class OscParser {

    static final private byte KOMMA = 0x2C;
    static final Logger Log = Logger.getLogger(OscParser.class.getName());

    static public String getTypetag(final OscMessage theMessage) {
        return getTypetag(new StringBuilder(), theMessage.getArgumentsAsList());
    }

    static protected String getTypetag(final StringBuilder theTypetag,
                                       final List<?> theData) {
        for (final Object o : theData) {
            if (o == null) {
                theTypetag.append('N'); /* Nil */
            } else if (o instanceof Integer)
                /* Integer */ {
                theTypetag.append('i');
            } else if (o instanceof Float)
                /* Float */ {
                theTypetag.append('f');
            } else if (o instanceof Double)
                /* Double */ {
                theTypetag.append('d');
            } else if (o instanceof Long)
                /* Long */ {
                theTypetag.append('h');
            } else if (o instanceof OscTimetag)
                /* Timetag */ {
                theTypetag.append('t');
            } else if (o instanceof OscImpulse)
                /* Impulse */ {
                theTypetag.append('I');
            } else if (o instanceof OscMidi)
                /* String */ {
                theTypetag.append('m');
            } else if (o instanceof OscRgba)
                /* String */ {
                theTypetag.append('r');
            } else if (o instanceof OscSymbol)
                /* Symbol */ {
                theTypetag.append('S');
            } else if (o instanceof Character)
                /* Character */ {
                theTypetag.append('c');
            } else if (o instanceof byte[])
                /* blob */ {
                theTypetag.append('b');
            } else if (o instanceof String)
                /* String */ {
                theTypetag.append('s');
            } else if (o instanceof Boolean)
                /* Boolean */ {
                theTypetag.append((boolean) o ? 'T' : 'F');
            } else if (o instanceof List)
                /* Array */ {
                theTypetag.append('[');
                getTypetag(theTypetag, (List<?>) o);
                theTypetag.append(']');
            }
        }
        return theTypetag.toString();
    }

    static public boolean isBundle(final byte[] theBytes) {
        return startsWith(theBytes, OscBundle.BUNDLE_AS_BYTES);
    }

    static public Map<OscMessage, Long> bytesToPackets(final byte[] theBytes) {
        final Map<OscMessage, Long> collect = new LinkedHashMap<>();
        bytesToPackets(collect, theBytes, OscTimetag.TIMETAG_NOW);
        return collect;
    }

    static public void bytesToPackets(final Map<OscMessage, Long> theCollection,
                                      final byte[] theBytes,
                                      final long theMillis) {

        /* check if we are dealing with a valid OSC packet size */
        if (theBytes.length % 4 != 0) {
            return;
        }

        /* check if we are dealing with a Bundle */
        if (isBundle(theBytes)) {

            /* extract timetag */
            final long time = l(Arrays.copyOfRange(theBytes, 8, 16));
            final long javaTime = OscTimetag.toTimeMillis(time);
            final OscTimetag timetag = new OscTimetag();
            timetag.setTimeMillis(javaTime);

            /* determine future time */
            final long millis = timetag.isImmediate() ? OscTimetag.TIMETAG_NOW : javaTime - System.currentTimeMillis();

            /*
             * make a copy of the byte array excluding the Bundle header
             */
            final byte[] bundle = Arrays.copyOfRange(theBytes, OscBundle.BUNDLE_HEADER_SIZE, theBytes.length);

            /* start parsing the bundle */
            final int size = bundle.length;
            int index = 0;

            /*
             * while parsing the bundle recursively check for Bundles or
             * Messages
             */
            while (index < size) {
                /* determine the size of the byte block */
                final int len = i(Arrays.copyOfRange(bundle, index, index + 4));
                /* convert byte array to OSC packet recursively */
                bytesToPackets(theCollection, Arrays.copyOfRange(bundle, index + 4, index + len + 4), millis);
                index += (len + 4);
            }

        } else {
            theCollection.put(bytesToMessage(theBytes), theMillis);
        }
    }

    private static InvalidOscMessage invalid(final byte[] theData) {
        return new InvalidOscMessage(theData);
    }

    static public OscMessage bytesToMessage(final byte[] theData) {
        int n = 0;
        final int len = theData.length;

        if (len == 0 || theData.length % 4 != 0 || theData[0] != '/') {
            return invalid(theData);
        }

        while (theData[n] != KOMMA) {
            if (++n >= len) {
                return invalid(theData);
            }
        }

        /* getting the OSC address */
        final String address = (new String(Arrays.copyOfRange(theData, 0, n))).trim();

        /* if the komma has been found, extract the typetag */
        final StringBuilder typetag = new StringBuilder();

        while (theData[n] != 0x00) {
            typetag.append((char) theData[n]);
            if (++n >= len) {
                return invalid(theData);
            }
        }

        /* now start converting bytes to osc arguments starting from index n */
        n = (n + (4 - n % 4));

        final List<Object> arguments = new ArrayList<>();

        /* TODO if there is a better way to avoid checking for ArrayIndexOutOfBoundsException, do implement */
        try {
            bytesToArguments(theData, n, typetag.toString(), arguments);
        } catch (final ArrayIndexOutOfBoundsException e) {
            Log.log(Level.WARNING, "OscParser.bytesToMessage " +
                    e.getMessage() +
                    ". OscMessage probably incomplete, returning InvalidOscMessage."
            );
            OscP5.printStackTrace(e);
            return invalid(theData);
        }

        /* finally return a new OscMessage */
        return new OscMessage(address, arguments);
    }

    static private int bytesToArguments(final byte[] theByteArray,
                                        final int theByteArrayPosition,
                                        final String theTypetag,
                                        final List<Object> theArguments) throws ArrayIndexOutOfBoundsException {
        int byteArrayPosition = theByteArrayPosition;
        int index = 0;
        final int length = theTypetag.length();

        for (; index < length; index++) {
            final char c = theTypetag.charAt(index);
            switch (c) {
                case ('b'): /* blob */
                    final int len = i(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 4));
                    theArguments.add(bytes(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += len)));
                    final int mod = len % 4;
                    byteArrayPosition += mod == 0 ? 0 : 4 - mod;
                    break;
                case ('c'): /* character */
                    final char chr = c(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 4));
                    theArguments.add(chr);
                    break;
                case ('d'): /* double */
                    theArguments.add(d(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 8)));
                    break;
                case ('f'): /* float */
                    theArguments.add(f(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 4)));
                    break;
                case ('h'): /* long */
                    theArguments.add(l(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 8)));
                    break;
                case ('i'): /* integer */
                    theArguments.add(i(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 4)));
                    break;
                case ('m'): /* midi */
                    theArguments.add(new OscMidi(bytes(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 4))));
                    break;
                case ('r'): /* rgba */
                    theArguments.add(new OscRgba(i(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 4))));
                    break;
                case ('S'): /* Symbol */
                case ('s'): /* String */
                    int n1 = byteArrayPosition;
                    final StringBuilder buffer = new StringBuilder();
                    stringLoop:
                    do {
                        if (theByteArray[n1] == 0x00) {
                            break stringLoop;
                        } else {
                            buffer.append((char) theByteArray[n1]);
                        }
                        n1++;
                    } while (n1 < theByteArray.length);
                    theArguments.add(c == 's' ? buffer.toString() : new OscSymbol(buffer.toString()));
                    byteArrayPosition = n1 + (4 - buffer.length() % 4);
                    break;
                case ('t'): /* Timetag */
                    final OscTimetag timetag = new OscTimetag();
                    timetag.setTimetag(l(Arrays.copyOfRange(theByteArray, byteArrayPosition, byteArrayPosition += 8)));
                    theArguments.add(timetag);
                    break;
                case ('F'): /* false */
                    theArguments.add(false);
                    break;
                case ('I'): /* impulse */
                    theArguments.add(OscImpulse.IMPULSE);
                    break;
                case ('N'): /* nil */
                    theArguments.add(null);
                    break;
                case ('T'): /* true */
                    theArguments.add(true);
                    break;
                case ('['): /* array */
                    final List<Object> sub = new ArrayList<>();
                    final int p0 = theTypetag.indexOf('[') + 1;
                    final int p1 = theTypetag.lastIndexOf(']');
                    byteArrayPosition = bytesToArguments(theByteArray, byteArrayPosition, theTypetag.substring(p0, p1),
                            sub);
                    index += p1 - p0;
                    theArguments.add(sub);
                    break;
                case (']'):
                    break;
            }
        }
        return byteArrayPosition;
    }

    static public byte[] messageToBytes(final OscMessage theMessage) {
        final byte[] address = theMessage.getAddress().getBytes();
        final byte[] arguments = argumentsToBytes(theMessage.getArgumentsAsList());
        return append(append(address, filln(address.length)), arguments);
    }

    static public byte[] argumentsToBytes(final List<?> theData) {
        final StringBuilder typetag = new StringBuilder();
        typetag.append(',');
        final byte[] arguments = argumentsToBytes(typetag, theData);
        return append(append(String.valueOf(typetag).getBytes(), filln(typetag.length())), arguments);
    }

    static private byte[] argumentsToBytes(final StringBuilder theTypetag,
                                           final Collection<?> theData) {
        byte[] arguments = new byte[0];

        for (final Object o : theData) {
            if (o == null) {
                theTypetag.append('N');
            } else if (o instanceof byte[]) {
                /* blob */
                theTypetag.append('b');
                final byte[] bytes = (byte[]) o;
                final int len = bytes.length;
                arguments = append(arguments, toBytes(len));
                arguments = append(arguments, bytes);
                arguments = append(arguments, zeros(len));
            } else if (o instanceof Character) {
                /* Character */
                theTypetag.append('c');
                final int chr = (int) ((Character) o);
                arguments = append(arguments, toBytes(chr));
            } else if (o instanceof Double) {
                /* Double */
                theTypetag.append('d');
                arguments = append(arguments, toBytes(Double.doubleToLongBits(((Double) o))));
            } else if (o instanceof Float) {
                /* Float */
                theTypetag.append('f');
                arguments = append(arguments, toBytes(Float.floatToIntBits(((Float) o))));
            } else if (o instanceof Long) {
                /* Long */
                theTypetag.append('h');
                arguments = append(arguments, toBytes((Long) o));
            } else if (o instanceof Integer) {
                /* Integer */
                theTypetag.append('i');
                arguments = append(arguments, toBytes(((Integer) o)));
            } else if (o instanceof OscMidi) {
                /* Midi */
                theTypetag.append('m');
                arguments = append(arguments, ((OscMidi) o).getBytes());
            } else if (o instanceof OscRgba) {
                /* RGBA */
                theTypetag.append('r');
                arguments = append(arguments, ((OscRgba) o).getBytes());
            } else if (o instanceof String) {
                /* String */
                theTypetag.append('s');
                arguments = append(arguments, o.toString().getBytes());
                arguments = append(arguments, zeros(o.toString().getBytes().length));
            } else if (o instanceof OscTimetag) {
                /* Timetag */
                theTypetag.append('t');
                arguments = append(arguments, ((OscTimetag) o).getBytes());
            } else if (o instanceof OscImpulse) {
                /* Impulse */
                theTypetag.append('I');
            } else if (o instanceof OscSymbol) {
                /* Symbol */
                theTypetag.append('S');
                arguments = append(arguments, ((OscSymbol) o).getValue().getBytes());
                arguments = append(arguments, zeros(((OscSymbol) o).getValue().getBytes().length));
            } else if (o instanceof Boolean) {
                /* Boolean */
                theTypetag.append((boolean) o ? 'T' : 'F');
            } else if (o instanceof List) {
                /* Collection */
                theTypetag.append('[');
                arguments = append(arguments, argumentsToBytes(theTypetag, (List<?>) o));
                theTypetag.append(']');
            } else {
                /* TODO should arrays be accepted, if yes, how? Treat as collection or iterate over array? */
                Log.log(Level.WARNING,
                        "OscParser.argumentsToBytes:",
                        "type not supported " + o.getClass().getSimpleName()
                );
            }
        }
        return arguments;
    }

    static public byte[] bytes(final Object o) {
        return (o instanceof byte[]) ? (byte[]) o : new byte[0];
    }

    static public int i(final byte[] abyte0) {
        return (abyte0[3] & 0xff) + ((abyte0[2] & 0xff) << 8) + ((abyte0[1] & 0xff) << 16) + ((abyte0[0] & 0xff) << 24);
    }

    static public long l(final byte[] abyte0) {
        return ((long) abyte0[7] & 255L) + (((long) abyte0[6] & 255L) << 8) + (((long) abyte0[5] & 255L) << 16)
                + (((long) abyte0[4] & 255L) << 24) + (((long) abyte0[3] & 255L) << 32)
                + (((long) abyte0[2] & 255L) << 40) + (((long) abyte0[1] & 255L) << 48)
                + (((long) abyte0[0] & 255L) << 56);
    }

    static public float f(final byte[] abyte0) {
        final int i = i(abyte0);
        return Float.intBitsToFloat(i);
    }

    static public char c(final byte[] abyte0) {
        return (char) i(abyte0);
    }

    static public double d(final byte[] abyte0) {
        final long l = l(abyte0);
        return Double.longBitsToDouble(l);
    }

    static public boolean startsWith(final byte[] theData,
                                     final byte[] thePattern) {
        final byte[] section = Arrays.copyOfRange(theData, 0, thePattern.length);
        return Arrays.equals(thePattern, section);
    }

    static public byte[] append(final byte[] abyte0,
                                final byte[] abyte1) {
        final byte[] abyte2 = new byte[abyte0.length + abyte1.length];
        System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
        System.arraycopy(abyte1, 0, abyte2, abyte0.length, abyte1.length);
        return abyte2;
    }

    static public byte[] toBytes(int i) {
        final byte[] buffer = new byte[4];
        for (int n = buffer.length - 1; n >= 1; n--) {
            buffer[n] = (byte) i;
            i >>>= 8;
        }
        buffer[0] = (byte) i;
        return buffer;
    }

    static public byte[] toBytes(long i) {
        final byte[] buffer = new byte[8];
        for (int n = buffer.length - 1; n >= 1; n--) {
            buffer[n] = (byte) i;
            i >>>= 8;
        }
        buffer[0] = (byte) i;
        return buffer;
    }

    static public byte[] zeros(final int len) {
        int n = 4 - len % 4;
        n = len == 4 ? 0 : n;
        return new byte[n];
    }

    static public byte[] filln(final int len) {
        /*
         * adds an additional 4 bytes if the len % 4 = 0, required
         * for address
         */
        return new byte[4 - len % 4];
    }

    static public String asString(final List<?> theArguments) {
        final StringBuilder s1 = new StringBuilder();
        s1.append("[");
        String d1 = "";
        for (final Object o : theArguments) {
            if (o instanceof List) {
                s1.append(d1).append(asString((List<?>) o));
            } else if (o instanceof byte[]) {
                final byte[] bytes = (byte[]) o;
                final StringBuilder s2 = new StringBuilder();
                s2.append("[");
                String d2 = "";
                for (final byte b : bytes) {
                    s2.append(d2).append(String.format("0x%02x", b & 0xff));
                    d2 = ",";
                }
                s2.append("]");
                s1.append(d1).append(s2);
            } else {
                s1.append(d1).append(o == null ? "nil" : o.toString());
            }
            d1 = ",";
        }
        s1.append("]");
        return s1.toString();
    }

}
