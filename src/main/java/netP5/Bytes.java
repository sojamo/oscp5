package netP5;

import java.io.*;

public class Bytes {

    private Bytes() {
    }

    /**
     * converts an object array into a String that is formated
     * like a list
     */
    static public String getAsString(
            final Object[] theObject) {
        final StringBuffer s = new StringBuffer();
        for (int i = 0; i < theObject.length; i++) {
            s.append("[" + i + "]" + " " + theObject[i] + "\n");
        }
        return s.toString();
    }

    static public String getAsString(
            final byte[] theBytes) {
        final StringBuffer s = new StringBuffer();
        for (int i = 0; i < theBytes.length; i++) {
            s.append((char) theBytes[i]);
        }
        return s.toString();
    }

    static public int toInt(
            final byte[] abyte0) {
        return (abyte0[3] & 0xff) + ((abyte0[2] & 0xff) << 8) + ((abyte0[1] & 0xff) << 16) + ((abyte0[0] & 0xff) << 24);
    }

    static public long toLong(
            final byte[] abyte0) {
        return ((long) abyte0[7] & 255L) + (((long) abyte0[6] & 255L) << 8) + (((long) abyte0[5] & 255L) << 16)
                + (((long) abyte0[4] & 255L) << 24) + (((long) abyte0[3] & 255L) << 32)
                + (((long) abyte0[2] & 255L) << 40) + (((long) abyte0[1] & 255L) << 48)
                + (((long) abyte0[0] & 255L) << 56);
    }

    static public float toFloat(
            final byte[] abyte0) {
        final int i = toInt(abyte0);
        return Float.intBitsToFloat(i);
    }

    static public double toDouble(
            final byte[] abyte0) {
        final long l = toLong(abyte0);
        return Double.longBitsToDouble(l);
    }

    static public byte[] toBytes(
            final int i) {
        return toBytes(i, new byte[4]);
    }

    static public byte[] toBytes(
            int i,
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

    static public byte[] toBytes(
            final long l) {
        return toBytes(l, new byte[8]);
    }

    static public byte[] toBytes(
            long l,
            final byte[] abyte0) {
        abyte0[7] = (byte) (int) l;
        l >>>= 8;
        abyte0[6] = (byte) (int) l;
        l >>>= 8;
        abyte0[5] = (byte) (int) l;
        l >>>= 8;
        abyte0[4] = (byte) (int) l;
        l >>>= 8;
        abyte0[3] = (byte) (int) l;
        l >>>= 8;
        abyte0[2] = (byte) (int) l;
        l >>>= 8;
        abyte0[1] = (byte) (int) l;
        l >>>= 8;
        abyte0[0] = (byte) (int) l;
        return abyte0;
    }

    static public boolean areEqual(
            final byte[] abyte0,
            final byte[] abyte1) {
        final int i = abyte0.length;
        if (i != abyte1.length) {
            return false;
        }
        for (int j = 0; j < i; j++) {
            if (abyte0[j] != abyte1[j]) {
                return false;
            }
        }

        return true;
    }

    static public byte[] append(
            final byte[] abyte0,
            final byte[] abyte1) {
        final byte[] abyte2 = new byte[abyte0.length + abyte1.length];
        System.arraycopy(abyte0, 0, abyte2, 0, abyte0.length);
        System.arraycopy(abyte1, 0, abyte2, abyte0.length, abyte1.length);
        return abyte2;
    }

    static public byte[] append(
            final byte[] abyte0,
            final byte[] abyte1,
            final byte[] abyte2) {
        final byte[] abyte3 = new byte[abyte0.length + abyte1.length + abyte2.length];
        System.arraycopy(abyte0, 0, abyte3, 0, abyte0.length);
        System.arraycopy(abyte1, 0, abyte3, abyte0.length, abyte1.length);
        System.arraycopy(abyte2, 0, abyte3, abyte0.length + abyte1.length, abyte2.length);
        return abyte3;
    }

    static public byte[] copy(
            final byte[] abyte0,
            final int i) {
        return copy(abyte0, i, abyte0.length - i);
    }

    static public byte[] copy(
            final byte[] abyte0,
            final int i,
            final int j) {
        final byte[] abyte1 = new byte[j];
        System.arraycopy(abyte0, i, abyte1, 0, j);
        return abyte1;
    }

    static public void merge(
            final byte[] abyte0,
            final byte[] abyte1,
            final int i,
            final int j,
            final int k) {
        System.arraycopy(abyte0, i, abyte1, j, k);
    }

    static public void merge(
            final byte[] abyte0,
            final byte[] abyte1,
            final int i) {
        System.arraycopy(abyte0, 0, abyte1, i, abyte0.length);
    }

    static public void merge(
            final byte[] abyte0,
            final byte[] abyte1) {
        System.arraycopy(abyte0, 0, abyte1, 0, abyte0.length);
    }

    static public void merge(
            final byte[] abyte0,
            final byte[] abyte1,
            final int i,
            final int j) {
        System.arraycopy(abyte0, 0, abyte1, i, j);
    }

    static public String toString(
            final byte[] abyte0,
            final int i,
            final int j) {
        final char[] ac = new char[j * 2];
        int k = i;
        int l = 0;
        for (; k < i + j; k++) {
            final byte byte0 = abyte0[k];
            ac[l++] = hexDigits[byte0 >>> 4 & 0xf];
            ac[l++] = hexDigits[byte0 & 0xf];
        }

        return new String(ac);
    }

    static public String toString(
            final byte[] abyte0) {
        return toString(abyte0, 0, abyte0.length);
    }

    static public void printBytes(
            final byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            System.out.print((char) byteArray[i] + " (" + hexDigits[byteArray[i] >>> 4 & 0xf] + ""
                    + hexDigits[byteArray[i] & 0xf] + ")  ");
            if ((i + 1) % 4 == 0) {
                System.out.print("\n");
            }
        }
    }

    static public final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F'};

    /**
     * ByteStream
     */

    static byte[] toByteArray(
            final int in_int) {
        final byte[] a = new byte[4];
        for (int i = 0; i < 4; i++) {

            final int b_int = (in_int >> (i * 8)) & 255;
            final byte b = (byte) (b_int);

            a[i] = b;
        }
        return a;
    }

    static byte[] toByteArrayBigEndian(
            final int theInt) {
        final byte[] a = new byte[4];
        for (int i = 0; i < 4; i++) {
            final int b_int = (theInt >> (i * 8)) & 255;
            final byte b = (byte) (b_int);
            a[3 - i] = b;
        }
        return a;
    }

    static int asInt(
            final byte[] byte_array_4) {
        int ret = 0;
        for (int i = 0; i < 4; i++) {
            int b = byte_array_4[i];
            if (i < 3 && b < 0) {
                b = 256 + b;
            }
            ret += b << (i * 8);
        }
        return ret;
    }

    static public int toIntLittleEndian(
            final InputStream theInputStream) throws java.io.IOException {
        final byte[] byte_array_4 = new byte[4];

        byte_array_4[0] = (byte) theInputStream.read();
        byte_array_4[1] = (byte) theInputStream.read();
        byte_array_4[2] = (byte) theInputStream.read();
        byte_array_4[3] = (byte) theInputStream.read();

        return asInt(byte_array_4);
    }

    static public int toIntBigEndian(
            final InputStream theInputStream) throws java.io.IOException {
        final byte[] byte_array_4 = new byte[4];
        /*
         * used to reverse the int32 Big Endian of the tcp header to
         * convert it to an int
         */
        byte_array_4[3] = (byte) theInputStream.read();
        byte_array_4[2] = (byte) theInputStream.read();
        byte_array_4[1] = (byte) theInputStream.read();
        byte_array_4[0] = (byte) theInputStream.read();
        return asInt(byte_array_4);
    }

    static public String toString(
            final InputStream ins) throws java.io.IOException {
        final int len = toIntLittleEndian(ins);
        return toString(ins, len);
    }

    private static String toString(
            final InputStream ins,
            final int len) throws java.io.IOException {
        String ret = "";
        for (int i = 0; i < len; i++) {
            ret += (char) ins.read();
        }
        return ret;
    }

    static public void toStream(
            final OutputStream os,
            final int i) throws Exception {
        final byte[] byte_array_4 = toByteArrayBigEndian(i);
        os.write(byte_array_4);
    }

    static public void toStream(
            final OutputStream os,
            final String s) throws Exception {
        final int len_s = s.length();
        toStream(os, len_s);
        for (int i = 0; i < len_s; i++) {
            os.write((byte) s.charAt(i));
        }
        os.flush();
    }

    static public void toStream(
            final OutputStream os,
            final byte[] theBytes) throws Exception {
        final int myLength = theBytes.length;
        toStream(os, myLength);
        os.write(theBytes);
        os.flush();
    }

    static public byte[] toByteArray(
            final InputStream ins) throws java.io.IOException {
        final int len = toIntLittleEndian(ins);
        try {
            return toByteArray(ins, len);
        } catch (final Exception e) {
            return new byte[0];
        }
    }

    protected static byte[] toByteArray(
            final InputStream ins,
            final int an_int) throws Exception {

        final byte[] ret = new byte[an_int];

        int offset = 0;
        int numRead = 0;
        int outstanding = an_int;

        while ((offset < an_int) && ((numRead = ins.read(ret, offset, outstanding)) > 0)) {
            offset += numRead;
            outstanding = an_int - offset;
        }
        if (offset < ret.length) {
            throw new Exception(
                    "Could not completely read from stream, numRead=" + numRead + ", ret.length=" + ret.length); // ???
        }
        return ret;
    }

    private static void toFile(
            final InputStream ins,
            final FileOutputStream fos,
            final int len,
            final int buf_size) throws java.io.IOException {

        final byte[] buffer = new byte[buf_size];

        int len_read = 0;
        int total_len_read = 0;

        while (total_len_read + buf_size <= len) {
            len_read = ins.read(buffer);
            total_len_read += len_read;
            fos.write(buffer, 0, len_read);
        }

        if (total_len_read < len) {
            toFile(ins, fos, len - total_len_read, buf_size / 2);
        }
    }

    static void toFile(
            final InputStream ins,
            final File file,
            final int len) throws java.io.IOException {

        final FileOutputStream fos = new FileOutputStream(file);

        toFile(ins, fos, len, 1024);
    }

    static public void toFile(
            final InputStream ins,
            final File file) throws java.io.IOException {

        final int len = toIntLittleEndian(ins);
        toFile(ins, file, len);
    }

    static public void toStream(
            final OutputStream os,
            final File file) throws Exception {

        toStream(os, (int) file.length());

        final byte[] b = new byte[1024];
        final InputStream is = new FileInputStream(file);
        int numRead = 0;

        while ((numRead = is.read(b)) > 0) {
            os.write(b, 0, numRead);
        }
        os.flush();
        is.close();
    }

}
