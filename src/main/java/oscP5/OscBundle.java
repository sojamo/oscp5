package oscP5;

import java.util.ArrayList;
import java.util.List;
import static oscP5.OscUtils.toList;

public class OscBundle extends ABundle {

	private final List<OscPacket> packets = new ArrayList<>();
	private final OscTimetag timetag = new OscTimetag();
	static public final byte[] BUNDLE_AS_BYTES = { 0x23, 0x62, 0x75, 0x6E, 0x64, 0x6C, 0x65, 0x00 };
	static public final int BUNDLE_HEADER_SIZE = 16;

	public OscBundle()
	{
		this(new ArrayList<>());
	}

	public OscBundle(final OscPacket... thePackets)
	{
		this(toList(thePackets));
	}

	public OscBundle(final List<OscPacket> thePackets)
	{
		addPackets(thePackets);
	}

	@Override
	public OscBundle add(final OscPacket... thePackets)
	{
		addPackets(toList(thePackets));
		return this;
	}

	public OscBundle add(final List<OscPacket> thePackets)
	{
		addPackets(thePackets);
		return this;
	}

	private void addPackets(final List<OscPacket> thePackets)
	{
		packets.addAll(thePackets);
	}

	public boolean isImmediate()
	{
		return timetag.isImmediate();
	}

	public OscTimetag getTimetag()
	{
		return timetag;
	}

	public OscBundle setTimetag(final OscTimetag theTimetag)
	{
		timetag.setTimetag(theTimetag);
		return this;
	}

	@Override
	public OscBundle setTimetag(final long theTime)
	{
		OscTimetag t = new OscTimetag();
		t.setTimeMillis(theTime);
		setTimetag(t);
		return this;
	}

	@Override
	public byte[] getBytes()
	{
		/** TODO review if this works for nested bundles? */
		byte[] bytes = OscParser.append(BUNDLE_AS_BYTES, timetag.getBytes());
		for (final OscPacket packet : packets) {
			final byte[] b1 = packet.getBytes();
			bytes = OscParser.append(bytes, OscParser.toBytes(b1.length));
			bytes = OscParser.append(bytes, packet.getBytes());
		}
		return bytes;
	}

	@Override
	public OscPacket get(final int theIndex)
	{
		/** TODO re-implement */
		return packets.get(theIndex);
	}

	@Override
	public OscMessage getMessage(final int theIndex)
	{
		OscPacket packet = packets.get(theIndex);
		if (packet instanceof OscMessage) {
			return (OscMessage) packet;
		} else {
			return new InvalidOscMessage(packet.getBytes());
		}
	}

	@Override
	public int size()
	{
		return packets.size();
	}

	@Override
	public OscBundle clear()
	{
		return this;
	}

	@Override
	public OscBundle remove(final int theIndex)
	{
		packets.remove(theIndex);
		return this;
	}

	@Override
	public OscBundle remove(final OscPacket theOscPacket)
	{
		packets.remove(theOscPacket);
		return this;
	}

	@Override
	public List<OscPacket> getAll()
	{
		/** TODO check if ref or new array of packets should be returned here. */
		return packets;
	}

	@Override
	public String toString()
	{
		final StringBuilder b = new StringBuilder().append("{ class: OscBundle").append(", timetag:").append(timetag)
				.append(", packets:[");
		for (final OscPacket packet : packets) {
			b.append(", ").append(packet.toString());
		}
		b.append("]}");
		return b.toString();
	}
}
