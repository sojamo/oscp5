
package netP5;

import java.net.InetAddress;

public abstract class ANetAddress {

    public ANetAddress() {
    }

    public abstract InetAddress getInetAddress();

    public abstract String getAddress();

    public abstract int getPort();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if(!(o instanceof ANetAddress)) {
            return false;
        }

        ANetAddress addr = (ANetAddress) o;
        return getAddress().equals(addr.getAddress()) && getPort() == addr.getPort();
    }

    @Deprecated
    public InetAddress inetaddress() {
        return getInetAddress();
    }

    @Deprecated
    public String address() {
        return getAddress();
    }

    @Deprecated
    public int port() {
        return getPort();
    }

    @Deprecated
    public boolean isvalid() {
        return isValid;
    }

    @Deprecated
    protected boolean isValid = true;


}
