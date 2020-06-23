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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import netP5.Bytes;

/**
 * Osc Bundles are collections of Osc Messages. use bundles to send multiple osc
 * messages to one destination. the OscBundle timetag is supported for sending
 * but not for receiving yet.
 */
public class OscBundle extends OscPacket {

	protected static final int BUNDLE_HEADER_SIZE = 16;
	protected static final byte[] BUNDLE_AS_BYTES = { 0x23, 0x62, 0x75, 0x6E, 0x64, 0x6C, 0x65, 0x00 };

	public OscBundle() {
	}

	public OscBundle add(final OscMessage... theOscMessages) {
		return this;
	}

	public OscBundle clear() {
		return this;
	}

	public OscBundle remove(final int theIndex) {
		return this;
	}

	public OscBundle remove(final OscMessage theOscMessage) {
		return this;
	}

	public OscMessage getMessage(final int theIndex) {
		/** TODO re-implement */
		return null;
	}

	public int size() {
		return -1;
	}

	public OscMessage get(final int theIndex) {
		/** TODO re-implement */
		return null;
	}

	public List<OscMessage> get() {
		/** TODO re-implement */
		return null;
	}

	/**
	 * TODO set the timetag of an osc bundle. timetags are used to synchronize
	 * events and execute events at a given time in the future or immediately.
	 * timetags can only be set for osc bundles, not for osc messages. oscP5
	 * supports receiving timetags, but does not queue messages for execution at a
	 * set time.
	 * 
	 */
	public OscBundle setTimetag(final long theTime) {
		/** TODO re-implement */
		return this;
	}

	public static long now() {
		return System.currentTimeMillis();
	}

	public byte[] timetag() {
		/** TODO re-implement */
		return null;
	}

	public byte[] getBytes() {
		byte[] myBytes = new byte[0];
		myBytes = Bytes.append(myBytes, BUNDLE_AS_BYTES);
		myBytes = Bytes.append(myBytes, timetag());
		for (int i = 0; i < size(); i++) {
			final byte[] tBytes = getMessage(i).getBytes();
			myBytes = Bytes.append(myBytes, Bytes.toBytes(tBytes.length));
			myBytes = Bytes.append(myBytes, tBytes);
		}
		return myBytes;
	}

	public final String toString() {
		/** TODO re-implement */
		return null;
	}
}
