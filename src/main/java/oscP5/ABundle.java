package oscP5;

import java.util.List;

public abstract class ABundle extends OscPacket {

    protected static final int BUNDLE_HEADER_SIZE = 16;
    protected static final byte[] BUNDLE_AS_BYTES = {0x23, 0x62, 0x75, 0x6E, 0x64, 0x6C, 0x65, 0x00};

    public abstract OscBundle add(final OscPacket... theOscPackets);

    public abstract OscBundle clear();

    public abstract OscBundle remove(final int theIndex);

    public abstract OscBundle remove(final OscPacket theOscPacket);

    public abstract OscMessage getMessage(final int theIndex);

    public abstract int size();

    public abstract OscPacket get(final int theIndex);

    public abstract List<OscPacket> getAll();

    public abstract OscBundle setTimetag(final long theTime);

    static public long now() {
        return System.currentTimeMillis();
    }

    @Deprecated
    public byte[] timetag() {
        /** TODO re-implement */
        return null;
    }
}
