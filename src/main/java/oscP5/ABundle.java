package oscP5;

import java.util.List;

abstract class ABundle extends OscPacket {

    public abstract OscBundle add(final OscPacket... theOscPackets);

    public abstract OscBundle clear();

    public abstract OscBundle remove(final int theIndex);

    public abstract OscBundle remove(final OscPacket theOscPacket);

    public abstract OscMessage getMessage(final int theIndex);

    public abstract int size();

    public abstract OscPacket get(final int theIndex);

    public abstract List<OscPacket> getAll();

    public abstract OscBundle setTimetag(final OscTimetag theTimetag);

    public abstract OscTimetag getTimetag();

    /* TODO check backwards-comp., remove and use setTimetag(OscTimetag) instead */
    public abstract OscBundle setTimetag(final long theTime);

    static public final long now() {
        OscTimetag t = new OscTimetag();
        t.setFutureTimeMillis(1);
        return t.toTimeMillis();
    }

    @Deprecated
    public byte[] timetag() {
        /** TODO re-implement */
        return null;
    }
}
