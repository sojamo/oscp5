
package oscP5;

public class InvalidOscMessage extends OscMessage {

    public InvalidOscMessage(byte[] theData) {
        super("/invalid");
        add(theData);
    }

}
