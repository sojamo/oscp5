package oscP5;

/**
 * Represents an OSC RGBA Color type.
 */
public class OscRgba {

    private int value;

    public OscRgba() {
        setColor(0,0,0,255);
    }

    public OscRgba(int theValue) {
        value = theValue;
    }

    public int getValue(){
        return value;
    }

    public int red(){
        return value >> 16 & 0xff;
    }

    public int green(){
        return value >> 8 & 0xff;
    }

    public int blue(){
        return value & 0xff;
    }

    public int alpha(){
        return value >> 24 & 0xff;
    }
    public OscRgba setColor(int theRed, int theGreen, int theBlue) {
        return setColor(theRed,theGreen,theBlue,255);
    }

    public OscRgba setColor(int theRed, int theGreen, int theBlue, int theAlpha) {
        int c = (theAlpha << 24);
        c = c | (theRed << 16);
        c = c | (theGreen << 8);
        c = c | (theBlue);
        value = c;
        return this;
    }

    public byte[] getBytes() {
        return OscParser.toBytes(value);
    }

    @Override
    public String toString() {
        return "{ class: OscRgba" +
                ", value:" + "[" +red() + ", " + green() + ", " + blue() + ", " + alpha() +"]" +
                " }";
    }

}
