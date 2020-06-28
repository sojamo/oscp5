package oscP5;

import java.util.Date;

public class OscTimetag {

    static public final long TIMETAG_OFFSET = 2208988800000L;
    static public final long TIMETAG_NOW = 1L;
    private long timetag = TIMETAG_NOW;

    public boolean isImmediate()
    {
        return (timetag == TIMETAG_NOW);
    }

    public OscTimetag setTimetag(final long theTimetag)
    {
        timetag = theTimetag;
        return this;
    }

    public OscTimetag setTimetag(final OscTimetag theTimetag)
    {
        timetag = theTimetag.getTimetag();
        return this;
    }

    public OscTimetag setTimeMillis(final long theMillis)
    {
        timetag = fromTimeMillisToTimetag(theMillis);
        return this;
    }

    static public long fromTimeMillisToTimetag(long theMillis)
    {
        if (theMillis == TIMETAG_NOW) {
            return TIMETAG_NOW;
        } else {
            final long secsSince1900 = (theMillis + TIMETAG_OFFSET) / 1000L;
            final long secsFractional = Math.round((((theMillis + TIMETAG_OFFSET) % 1000) * 0x100000000L) / 1000D);
            return ((secsSince1900 << 32) | secsFractional);
        }
    }

    static public OscTimetag inFutureTimeMillis(final long t)
    {
        OscTimetag tag = new OscTimetag();
        return tag.setFutureTimeMillis(t);
    }

    public OscTimetag setFutureTimeMillis(final long t)
    {
        setTimeMillis(System.currentTimeMillis() + (t < 0 ? 0 : t));
        return this;
    }

    public long getTimetag()
    {
        return timetag;
    }

    public Date getDate()
    {
        return new Date(toTimeMillis());
    }

    public long toTimeMillis()
    {
        return toTimeMillis(timetag);
    }

    static public long toTimeMillis(long theTime)
    {
        final long FILTER_LOWER_32 = 0xFFFFFFFFL;
        final long fraction = theTime & FILTER_LOWER_32;
        final long seconds = theTime >>> 32;
        final long fractionInMs = Math.round(1000D * fraction / 0x100000000L);
        final long secondsInMs = seconds * 1000L;
        final long t = secondsInMs + fractionInMs - TIMETAG_OFFSET;
        return t <= TIMETAG_NOW ? TIMETAG_NOW : t;
    }

    public byte[] getBytes()
    {
        return OscParser.toBytes(timetag);
    }

    @Override
    public String toString()
    {
        return "{ class: OscTimetag, long:" + timetag + ", date:" + getDate() + ", isNow:" + isImmediate() + " }";
    }

}
