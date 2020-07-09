package oscP5;


import org.junit.Test;
import static org.junit.Assert.*;

public class OscMatcherTest {

    private boolean matches(final OscPatternMatcher matcher, final String address) {
        return matcher.matches(new OscMessage(address));
    }

    @Test
    public void testDefaultMatches() {
        /* default pattern matcher matches against Path Traversal Wildcard // so anything goes */
        assertTrue(matches(OscP5.DEFAULT_PATTERN_MATCHER, "/hello/world/how/are/you/2/day"));

    }
    @Test
    public void testBasicMatches() {
        OscPatternMatcher matcher = new OscPatternMatcher("/hello");
        assertTrue(matches(matcher, "/hello"));
    }

    @Test
    public void testWildcardMatches() {
        OscPatternMatcher matcher = new OscPatternMatcher("/hello/*");

        assertTrue(matches(matcher, "/hello/world"));
        assertTrue(matches(matcher, "/hello/1"));
        assertTrue(matches(matcher, "/hello/100"));

        assertFalse(matches(matcher, "/hello/100/worlds"));
    }

    @Test
    public void testPathTraversalWildcardMatches() {
        OscPatternMatcher matcher = new OscPatternMatcher("/hello//");

        assertTrue(matches(matcher, "/hello/world/it/is"));

    }
}
