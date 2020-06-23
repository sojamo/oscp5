/**
 * A network library for processing which supports UDP, TCP and Multicast.
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
 * @author		##author##
 * @modified	##date##
 * @version		##version##
 */

package netP5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class NetAddressList {
	/** TODO consider to extend Collection, Set or List */

	protected List<NetAddress> _myList = new ArrayList<NetAddress>();

	public void add(final NetAddress theNetAddress) {
		/** TODO re-implement */
	}

	public void add(final String theAddress, final int thePort) {
		/** TODO re-implement */
	}

	public void remove(final String theAddress, final int thePort) {
		/** TODO re-implement */
	}

	public void remove(final NetAddress theNetAddress) {
		_myList.remove(theNetAddress);
	}

	public NetAddress get(final String theIPaddress, final int thePort) {
		for (int i = 0; i < _myList.size(); i++) {
			final NetAddress myHost = ((NetAddress) _myList.get(i));
			if (myHost.hostAddress.equals(theIPaddress) && myHost.port == thePort) {
				return myHost;
			}
		}
		return null;

	}

	public boolean contains(final NetAddress theNetAddress) {
		/** TODO re-implement */
		return false;
	}

	public boolean contains(final String theIPaddress, final int thePort) {
		/** TODO re-implement */
		return false;
	}

	public int size() {
		/** TODO re-implement */
		return -1;
	}

	public void set(final NetAddress... theList) {
		/** TODO re-implement */
	}

	public List list() {
		/** TODO re-implement */
		return null;
	}

	public NetAddress get(final int theIndex) {
		/** TODO re-implement */
		return null;
	}

}
