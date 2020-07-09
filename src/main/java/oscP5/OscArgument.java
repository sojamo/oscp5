package oscP5;

/**
 * Contains a single value of an OscMessage from its list of arguments.
 */
public final class OscArgument {

    private Object value;

    OscArgument() {
    }

    public int intValue() {
        return (Integer) this.value;
    }

    public char charValue() {
        return (Character) value;
    }

    public float floatValue() {
        return (Float) value;
    }

    public double doubleValue() {
        return (Double) value;
    }

    public long longValue() {
        return (Long) value;
    }

    public boolean booleanValue() {
        return (Boolean) value;
    }

    public String stringValue() {
        return ((String) value);
    }

    public byte[] bytesValue() {
        return ((byte[]) value);
    }

    public byte[] blobValue() {
        return ((byte[]) value);
    }

    public int[] midiValue() {
        final int[] myInt = new int[4];
        final byte[] myByte = (byte[]) value;
        for (int i = 0; i < 4; i++) {
            myInt[i] = myByte[i];
        }
        return (myInt);
    }

    void setValue(final Object theValue) {
        value = theValue;
    }

    public String toString() {
        return "{class: OscArgument" +
                ", value: "+ value +
                "}";
    }
}
