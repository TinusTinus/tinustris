package nl.mvdr.tinustris.logging;

import nl.mvdr.tinustris.logging.Logging;

import org.junit.Test;

/**
 * Test class for {@link Logging}.
 * 
 * @author Martijn van de Rijdt
 */
public class LoggingTest {
    /** Test method for {@link Logging#logVersionInfo()}. */
    @Test
    public void testLogVersionInfo() {
        Logging.logVersionInfo();
    }
}