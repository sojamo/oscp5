package oscP5;

/**
 * Represents an OSC Impulse type when received, it does not contain any value(s).
 */
public class OscImpulse {

    static public final OscImpulse IMPULSE = new OscImpulse();

    private OscImpulse() {
    }

    @Override
    public String toString() {
        return "{class:OscImpulse}";
    }

}
