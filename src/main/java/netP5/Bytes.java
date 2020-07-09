
package netP5;

public class Bytes {

    private Bytes() {
    }

    static public byte[] toBytes(final int i) {
        return toBytes(i, new byte[4]);
    }

    static public byte[] toBytes(int i,
                                 final byte[] abyte0) {
        abyte0[3] = (byte) i;
        i >>>= 8;
        abyte0[2] = (byte) i;
        i >>>= 8;
        abyte0[1] = (byte) i;
        i >>>= 8;
        abyte0[0] = (byte) i;
        return abyte0;
    }

    static public byte[] append(final byte[] abyte0,
                                final byte[] abyte1) {
        final byte[] abyte2 = new byte[abyte0.length + abyte1.length];
        System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
        System.arraycopy(abyte1, 0, abyte2, abyte0.length, abyte1.length);
        return abyte2;
    }

}
