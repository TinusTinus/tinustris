package nl.mvdr.tinustris.gui;

import org.junit.Test;

/**
 * Test class for {@link Tinustris}.
 * 
 * @author Martijn van de Rijdt
 */
public class TinustrisTest {
    /** Tests {@link Tinustris#Tinustris()}. */
    @Test
    public void testDefaultConstructor() {
        new Tinustris();
    }
    
    /** Test case for {@link Tinustris#logVersionInfo()}. */
    @Test
    public void testLogVersionInfo() {
        Tinustris tinustris = new Tinustris();
        
        tinustris.logVersionInfo();
    }
}