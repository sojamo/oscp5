package oscP5;

/**
 * An interface that can be added to OscP5 to push forward OscMessages when received.
 */
public abstract class OscEventListener {

    public abstract void oscEvent(OscMessage theMessage);

    public String toString() {
        return "{class: OscEventListener}";
    }
}
