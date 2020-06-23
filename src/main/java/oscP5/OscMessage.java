/**
 * An OSC (Open Sound Control) library for processing.
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

package oscP5;

import java.util.Map;

import netP5.Bytes;

/**
 * An OSC message consists of an OSC Address Pattern, an OSC Type Tag String and
 * the OSC arguments.
 * 
 * @related OscBundle
 * @example oscP5sendReceive
 */
public class OscMessage extends OscPacket {

	public OscMessage(final OscMessage theOscMessage) {
		/** TODO re-implement */
	}

	public OscMessage(final String theAddrPattern) {
		/** TODO re-implement */
	}

	public OscMessage(final int theAddrInt) {
		/** TODO re-implement */
	}

	public OscMessage(final String theAddrPattern, final Object... theArguments) {
		/** TODO re-implement */
	}

	public OscMessage(final int theAddrPattern, final Object... theArguments) {
		/** TODO re-implement */
	}

	public OscMessage clear() {
		/** TODO re-implement */
		return this;
	}

	public OscMessage clearArguments() {
		return this;
	}

	public OscMessage set(final int theIndex, final Object theObject) {
		/** NOTE to modify existing OscMessage */
		/** TODO re-implement */
		return this;
	}

	public boolean checkTypetag(final String theTypeTag) {
		/** TODO re-implement */
		return false;
	}

	public boolean checkAddress(final String theAddrPattern) {
		/** TODO re-implement */
		return false;
	}

	public OscMessage setAddress(final String theAddrPattern) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage setAddress(final int theAddrPattern) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage setArguments(final Object... theArguments) {
		/** TODO re-implement */
		return this;
	}

	public String getAddress() {
		/** TODO re-implement */
		return null;
	}

	public String getTypetag() {
		/** TODO re-implement */
		return null;
	}

	public long getTimetag() {
		/** TODO re-implement */
		return -1;
	}

	public Object[] getArguments() {
		/** TODO re-implement */
		return null;
	}

	public Object getArgument(int theIndex) {
		/** TODO re-implement */
		return null;
	}

	public int addrInt() {
		/** TODO re-implement */
		return -1;
	}

	public byte[] getAddrPatternAsBytes() {
		/** TODO re-implement */
		return null;
	}

	public byte[] getTypetagAsBytes() {
		/** TODO re-implement */
		return null;
	}

	public byte[] getBytes() {
		/** TODO re-implement */
		return null;
	}

	public OscMessage add() {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final int theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final String theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final float theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final double theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final boolean theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Boolean theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Integer theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Float theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Double theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Long theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Character theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final char theValue) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final int theChannel, final int theStatus, final int theValue1, final int theValue2) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final int... theArray) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final char... theArray) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final float... theArray) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final String... theArray) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final byte... theArray) {
		/** TODO re-implement */
		return this;
	}

	public OscMessage add(final Object... os) {
		/** TODO re-implement */
		return this;
	}

	private boolean add(final Object theObject) {
		/** TODO check or re-implement */
		if (theObject instanceof Number) {
			if (theObject instanceof Integer) {
			} else if (theObject instanceof Float) {
			} else if (theObject instanceof Double) {
			} else if (theObject instanceof Long) {
			}
		} else if (theObject instanceof String) {
		} else if (theObject instanceof Boolean) {
		} else if (theObject instanceof Character) {
		} else {
			if (theObject instanceof int[]) {
			} else if (theObject instanceof float[]) {
			} else if (theObject instanceof byte[]) {
			} else if (theObject instanceof String[]) {
			} else if (theObject instanceof char[]) {
			} else if (theObject instanceof double[]) {
			} else {
				return false;
			}
		}
		return true;
	}

	public static byte[] makeBlob(final byte[] b) {
		/** TODO check or re-implement */
		final int tLength = b.length;
		byte[] b1 = Bytes.toBytes(tLength);
		b1 = Bytes.append(b1, b);
		final int t = tLength % 4;
		if (t != 0) {
			b1 = Bytes.append(b1, new byte[4 - t]);
		}
		return b1;
	}

	public OscArgument get(final int theIndex) {
		/** TODO re-implement */
		return null;
	}

	public final String toString() {
		/** TODO re-implement */
		return null;
	}

	private final String arrayToString(Object[] os) {
		/** TODO check or re-implement */
		String s = "[";
		for (Object o : os) {
			s += o.toString() + ",";
		}
		s = s.replaceAll(",$", "");
		s += "]";
		return s;
	}

	public boolean isPlugged() {
		/** TODO re-implement */
		return false;
	}

	public void print() {
		/** TODO re-implement */
	}

	public void printData() {
		/** TODO re-implement */
	}

	public int intValue(int theIndex) {
		/** TODO check or re-implement */
		return i(getValue(theIndex));
	}

	public float floatValue(int theIndex) {
		/** TODO check or re-implement */
		return f(getValue(theIndex));
	}

	public char charValue(int theIndex) {
		/** TODO check or re-implement */
		return c(getValue(theIndex));
	}

	public double doubleValue(int theIndex) {
		/** TODO check or re-implement */
		return d(getValue(theIndex));
	}

	public long longValue(int theIndex) {
		/** TODO check or re-implement */
		return l(getValue(theIndex));
	}

	public boolean booleanValue(int theIndex) {
		/** TODO check or re-implement */
		return b(getValue(theIndex));
	}

	public String stringValue(int theIndex) {
		/** TODO check or re-implement */
		return s(getValue(theIndex));
	}

	public String[] stringValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		String[] arr = new String[n];
		for (int i = 0; i < n; i++) {
			arr[i] = stringValue(theStart + i);
		}
		return arr;
	}

	public int[] intValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = intValue(theStart + i);
		}
		return arr;
	}

	public float[] floatValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		float[] arr = new float[n];
		for (int i = 0; i < n; i++) {
			arr[i] = intValue(theStart + i);
		}
		return arr;
	}

	public char[] charValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		char[] arr = new char[n];
		for (int i = 0; i < n; i++) {
			arr[i] = charValue(theStart + i);
		}
		return arr;
	}

	public double[] doubleValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		double[] arr = new double[n];
		for (int i = 0; i < n; i++) {
			arr[i] = doubleValue(theStart + i);
		}
		return arr;
	}

	public long[] longValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		long[] arr = new long[n];
		for (int i = 0; i < n; i++) {
			arr[i] = longValue(theStart + i);
		}
		return arr;
	}

	public boolean[] booleanValues(int theStart, int theEnd) {
		/** TODO check or re-implement */
		int n = theEnd - theStart;
		boolean[] arr = new boolean[n];
		for (int i = 0; i < n; i++) {
			arr[i] = booleanValue(theStart + i);
		}
		return arr;
	}

	public byte[] bytesValue(int theIndex) {
		/** TODO check or re-implement */
		return bytes(getValue(theIndex));
	}

	public byte[] blobValue(int theIndex) {
		/** TODO check or re-implement */
		return bytesValue(theIndex);
	}

	public Object getValue(int theIndex) {
		/** TODO check or re-implement */
		if (theIndex < getArguments().length) {
			return getArguments()[theIndex];
		}
		indexOutOfBounds(theIndex);
		return null;
	}

	public void indexOutOfBounds(int n) {
		/** TODO check or re-implement */
		System.err.println("ArrayIndexOutOfBounds: index requested " + n + ", expected < " + getArguments().length);
	}

	/* Deprecated methods */

	@Deprecated
	public void setAddrPattern(final String theAddrPattern) {
		/** TODO check or re-implement */
	}

	@Deprecated
	public void setAddrPattern(final int theAddrPattern) {
		/** TODO check or re-implement */
	}

	@Deprecated
	public boolean checkAddrPattern(final String theAddrPattern) {
		/** TODO check or re-implement */
		return false;
	}

	@Deprecated
	public String typetag() {
		/** TODO check or re-implement */
		return null;
	}

	@Deprecated
	public long timetag() {
		/** TODO check or re-implement */
		return -1;
	}

	@Deprecated
	public Object[] arguments() {
		/** TODO check or re-implement */
		return null;
	}

	@Deprecated
	public OscMessage addArguments(final Object... theArguments) {
		/** TODO check or re-implement */
		return this;
	}

	@Deprecated
	public String addrPattern() {
		/** TODO check or re-implement */
		return null;
	}

}
