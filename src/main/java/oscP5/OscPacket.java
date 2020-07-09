package oscP5;

/**
 * The base class for OscMessage and OscBundle.
 */
public abstract class OscPacket extends APacket {

    public abstract byte[] getBytes();

    @Override
    public NetAddress netAddress() {
        return null;
    }

    @Override
	public String getAddress() {
		return null;
	}

	@Override
    public int getPort() {
        return super.port();
    }


}
