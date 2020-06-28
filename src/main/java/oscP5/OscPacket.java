package oscP5;

public abstract class OscPacket extends APacket {

	public abstract byte[] getBytes();

	public int getPort()
	{
		return super.port();
	}

	@Override
	public NetAddress netAddress()
	{
		return null;
	}

	@Override
	public String getAddress()
	{
		return null;
	}

}
