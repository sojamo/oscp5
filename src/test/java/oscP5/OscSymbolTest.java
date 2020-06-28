package oscP5;

import org.junit.Test;

import static org.junit.Assert.*;

public class OscSymbolTest {

    @Test
    public void testSymbol() throws Exception {
        OscMessage m = new OscMessage("/test");
        m.add(new OscSymbol("mySymbol"));
        assertEquals(m.getTypetag(), "S");

        Object o1 = m.get(0);
        assertEquals(o1.getClass(), OscSymbol.class);

        OscSymbol o2 = m.getSymbolAt(0);
        assertEquals(o2.get(), "mySymbol");
    }
}