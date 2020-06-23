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

import java.util.List;
import netP5.NetAddress;

public class OscProperties {

	static public final boolean ON = true;
	static public final boolean OFF = false;
	static public final int UDP = 0;
	static public final int MULTICAST = 1;
	static public final int TCP = 2;

	public OscProperties() {
		/** TODO re-implement */
	}

	public OscProperties(final NetAddress theNetAddress) {
		/** TODO re-implement */
	}

	public OscProperties(int theReceiveAtPort) {
		/** TODO re-implement */
	}

	public OscProperties(OscEventListener theParent) {
		/** TODO re-implement */
	}

	public List<OscEventListener> listeners() {
		/** TODO re-implement */
		return null;
	}

	public boolean sendStatus() {
		/** TODO re-implement */
		return false;
	}

	public OscProperties setRemoteAddress(final String theHostAddress, final int thePort) {
		/** TODO re-implement */
		return this;
	}

	public OscProperties setRemoteAddress(NetAddress theNetAddress) {
		/** TODO re-implement */
		return this;
	}

	public OscProperties setListeningPort(final int thePort) {
		/** TODO re-implement */
		return this;
	}

	public OscProperties setPort(final int thePort) {
		return setListeningPort(thePort);
	}

	public OscProperties setDatagramSize(final int theSize) {
		/** TODO re-implement */
		return this;
	}

	public OscProperties setEventMethod(final String theEventMethod) {
		/** TODO re-implement */
		return this;
	}

	public OscProperties setNetworkProtocol(final int theProtocol) {
		/** TODO re-implement */
		return this;
	}

	public OscProperties setHost(String theHost) {
		/** TODO re-implement */
		return this;
	}

	public int listeningPort() {
		/** TODO re-implement */
		return -1;
	}

	public NetAddress remoteAddress() {
		return null;
	}

	public String host() {
		return null;
	}

	public int datagramSize() {
		return -1;
	}

	public String eventMethod() {
		return null;
	}

	public int networkProtocol() {
		return -1;
	}

	public String toString() {
		return null;
	}

	@Deprecated
	public void setSRSP(final boolean theFlag) {
		/** TODO re-implement */
	}

	@Deprecated
	public boolean srsp() {
		/** TODO re-implement */
		return false;
	}

}
