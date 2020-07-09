
package oscP5;

/**
 * An internally generated message when a received message incomplete or not properly formatted.
 */
public class InvalidOscMessage extends OscMessage {

    public InvalidOscMessage(byte[] theData) {
        super("/invalid");
        add(theData);
    }

}
