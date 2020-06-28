package oscP5;

public final class OscArgument {

	private Object value;

	OscArgument()
	{
	}

	public int intValue()
	{
		return ((Integer) value).intValue();
	}

	public char charValue()
	{
		return ((Character) value).charValue();
	}

	public float floatValue()
	{
		return ((Float) value).floatValue();
	}

	public double doubleValue()
	{
		return ((Double) value).doubleValue();
	}

	public long longValue()
	{
		return ((Long) value).longValue();
	}

	public boolean booleanValue()
	{
		return ((Boolean) value).booleanValue();
	}

	public String stringValue()
	{
		return ((String) value);
	}

	public String toString()
	{
		return ((String) value);
	}

	public byte[] bytesValue()
	{
		return ((byte[]) value);
	}

	public byte[] blobValue()
	{
		return ((byte[]) value);
	}

	public int[] midiValue()
	{
		final int[] myInt = new int[4];
		final byte[] myByte = (byte[]) value;
		for (int i = 0; i < 4; i++) {
			myInt[i] = myByte[i];
		}
		return (myInt);
	}

	void setValue(final Object theValue)
	{
		value = theValue;
	}
}
