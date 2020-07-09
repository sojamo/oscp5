package oscP5;

/**
 * Represents an OSC Symbol type.
 */
public class OscSymbol {

    private final String symbol;

    public OscSymbol(final String theSymbol) {
        symbol = theSymbol;
    }

    public String getValue() {
        return symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!OscSymbol.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final OscSymbol other = (OscSymbol) obj;
        return (this.symbol == null) ? other.symbol == null : this.symbol.equals(other.symbol);
    }

    @Override
    public String toString() {
        return "{class: OscSymbol" +
                ", value: "+ symbol +
                "}";

    }

}
