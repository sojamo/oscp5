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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author              ##author##
 * @modified    ##date##
 * @version             ##version##
 */

package oscP5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netP5.Bytes;

public abstract class OscPatcher {

	public void parseMessage(final byte[] theBytes) {
		/** TODO re-implement */
	}

	static protected int align(int theInt) {
		return (4 - (theInt % 4));
	}

	static public int i(Object o) {
		return (o instanceof Number) ? ((Number) o).intValue() : Integer.MIN_VALUE;
	}

	static public int i(Object o, int theDefault) {
		return (o instanceof Number) ? ((Number) o).intValue() : theDefault;
	}

	static public char c(Object o) {
		return (o instanceof Character) ? ((Character) o).charValue() : '\0';
	}

	static public double d(Object o) {
		return (o instanceof Number) ? ((Number) o).doubleValue() : Double.MIN_VALUE;
	}

	static public long l(Object o) {
		return (o instanceof Number) ? ((Number) o).longValue() : Long.MIN_VALUE;
	}

	static public float f(Object o) {
		return (o instanceof Number) ? ((Number) o).floatValue() : Float.MIN_VALUE;
	}

	static public boolean b(Object o) {
		return (o instanceof Boolean) ? ((Boolean) o).booleanValue()
				: (o instanceof Number) ? ((Number) o).intValue() == 0 ? false : true : false;
	}

	static public byte[] bytes(Object o) {
		return (o instanceof byte[]) ? (byte[]) o : new byte[0];
	}

	static public String s(Object o) {
		return o.toString();
	}

	static public String s(Object o, String theDefault) {
		if (o != null) {
			return o.toString();
		}
		return theDefault;
	}

}