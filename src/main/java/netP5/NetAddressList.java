package netP5;

import oscP5.NetAddress;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class NetAddressList {

    /* TODO extend Collection, Set or List */

    protected List<ANetAddress> _myList = new ArrayList<ANetAddress>();

    public void add(final ANetAddress theNetAddress) {
        _myList.add(theNetAddress);
    }

    public void add(final String theAddress,
                    final int thePort) {
        _myList.add(new NetAddress(theAddress, thePort));
    }

    public void remove(final String theAddress,
                       final int thePort) {
        /** TODO re-implement */
    }

    public void remove(final ANetAddress theNetAddress) {
        _myList.remove(theNetAddress);
    }

    public ANetAddress get(final String theIPaddress,
                           final int thePort) {
        for (int i = 0; i < _myList.size(); i++) {
            final ANetAddress address = _myList.get(i);
            if (address.getAddress().equals(theIPaddress) && address.getPort() == thePort) {
                return address;
            }
        }
        return null;

    }

    public boolean contains(final ANetAddress theNetAddress) {
        /** TODO re-implement */
        return false;
    }

    public boolean contains(final String theIPaddress,
                            final int thePort) {
        /** TODO re-implement */
        return false;
    }

    public int size() {
        /** TODO re-implement */
        return -1;
    }

    public void set(final ANetAddress... theList) {
        /** TODO re-implement */
    }

    public List list() {
        /** TODO re-implement */
        return null;
    }

    public ANetAddress get(final int theIndex) {
        /** TODO re-implement */
        return null;
    }

}
