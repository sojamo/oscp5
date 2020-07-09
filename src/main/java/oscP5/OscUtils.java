package oscP5;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class OscUtils {

    static public final String delimiter = " ";

    static public final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static public int i(final Object o) {
        return i(o, Integer.MIN_VALUE);
    }

    static public int i(final Object o,
                        final int theDefault) {
        return (o instanceof Number)
                ? ((Number) o).intValue()
                : (o instanceof String)
                ? i(s(o))
                : theDefault;
    }

    static public int i(final String o) {
        return i(o, Integer.MIN_VALUE);
    }

    static public int i(final String o,
                        final int theDefault) {
        return isNumeric(o)
                ? Integer.parseInt(o)
                : theDefault;
    }

    static public float f(final Object o) {
        return f(o, Float.MIN_VALUE);
    }

    static public float f(final Object o,
                          final float theDefault) {
        return (o instanceof Number)
                ? ((Number) o).floatValue()
                : (o instanceof String)
                ? f(s(o))
                : theDefault;
    }

    static public float f(final String o) {
        return f(o, Float.MIN_VALUE);
    }

    static public float f(final String o,
                          final float theDefault) {
        return isNumeric(o)
                ? Float.parseFloat(o)
                : theDefault;
    }

    static public double d(final Object o) {
        return d(o, Double.MIN_VALUE);
    }

    static public double d(final Object o,
                           final double theDefault) {
        return (o instanceof Number)
                ? ((Number) o).doubleValue()
                : (o instanceof String)
                ? d(s(o))
                : theDefault;
    }

    static public double d(final String o) {
        return d(o, Double.MIN_VALUE);
    }

    static public double d(final String o,
                           final double theDefault) {
        return isNumeric(o)
                ? Double.parseDouble(o)
                : theDefault;
    }

    static public long l(final Object o) {
        return l(o, Long.MIN_VALUE);
    }

    static public long l(final Object o,
                         final long theDefault) {
        return (o instanceof Number)
                ? ((Number) o).longValue()
                : (o instanceof String)
                ? l(s(o))
                : theDefault;
    }

    static public long l(final String o) {
        return l(o, Integer.MIN_VALUE);
    }

    static public long l(final String o,
                         final long theDefault) {
        return isNumeric(o)
                ? Long.parseLong(o)
                : theDefault;
    }

    static public String s(final Object o) {
        return (o != null)
                ? o.toString()
                : "";
    }

    static public String s(final Number o,
                           final int theDec) {
        return (o != null)
                ? String.format("%." + theDec + "f", o.floatValue())
                : "";
    }

    static public String s(final Object o,
                           final String theDefault) {
        return (o != null)
                ? o.toString()
                : theDefault;
    }

    static public boolean b(final Object o) {
        return b(o, false);
    }

    static public boolean b(final Object o,
                            final boolean theDefault) {
        return (o instanceof Boolean)
                ? (Boolean) o
                : (o instanceof Number)
                ? ((Number) o).intValue() != 0
                : theDefault;
    }

    static public boolean b(final String o) {
        return b(o, false);
    }

    static public boolean b(final String o,
                            final boolean theDefault) {
        return o.equalsIgnoreCase("true") ||
                (!o.equalsIgnoreCase("false") && theDefault);
    }

    static public char c(Object o) {
        return (o instanceof Character)
                ? (Character) o
                : '\0';
    }

    static public byte[] bytes(final Object o) {
        return (o instanceof byte[])
                ? (byte[]) o
                : new byte[0];
    }

    static public List toList(final Object o) {
        return o != null
                ? (o instanceof List) ? (List) o
                : (o instanceof String) ? toList(o.toString()) : Collections.emptyList()
                : Collections.emptyList();
    }

    static public List toListWithDelimiter(final String o,
                                           final String theDelimiter) {
        List l = new ArrayList();
        Collections.addAll(l, o.split(theDelimiter));
        return l;
    }

    static public List toList(final String o) {
        return toListWithDelimiter(o, delimiter);
    }

    static public List toList(final Object... args) {
        List l = new ArrayList();
        Collections.addAll(l, args);
        return l;
    }

    static public boolean isNumeric(final String str) {
        return str.matches("([-+])?\\d+(\\.\\d+)?");
    }

    static public void sleep(final long theMillis) {
        try {
            Thread.sleep(theMillis);
        } catch (final Exception e) {
            OscP5.printStackTrace(e);
        }
    }

    static public byte[] serialize(Object o) {
        if (o instanceof Serializable) {
            return serialize((Serializable) o);
        }
        return new byte[0];
    }

    static public byte[] serialize(Serializable o) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes = new byte[0];
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(o);
            bytes = bos.toByteArray();
        } catch (Exception e) {
            OscP5.printStackTrace(e);
        } finally {
            try {
                assert out != null;
                out.close();
                bos.close();
            } catch (IOException e) {
                OscP5.printStackTrace(e);
            }
        }
        return bytes;
    }

    static public Object deserialize(byte[] theBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(theBytes);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            OscP5.printStackTrace(e);
        } finally {
            try {
                bis.close();
                assert in != null;
                in.close();
            } catch (IOException e) {
                OscP5.printStackTrace(e);
            }
        }
        return o;
    }

    static public String time() {
        final Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY) +
                ":" + now.get(Calendar.MINUTE) +
                ":" + now.get(Calendar.SECOND) +
                "." + now.get(Calendar.MILLISECOND);
    }

}