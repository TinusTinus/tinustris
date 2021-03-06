/*
 * Copyright 2013-2015 Martijn van de Rijdt 
 * 
 * This file is part of Tinustris.
 * 
 * Tinustris is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Tinustris is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Tinustris. If not, see <http://www.gnu.org/licenses/>.
 */
package nl.mvdr.tinustris.core.configuration;

import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.core.configuration.Behavior;
import nl.mvdr.tinustris.core.configuration.ConfigurationImpl;
import nl.mvdr.tinustris.core.configuration.GraphicsConfiguration;

import org.junit.Test;

/**
 * Test class for {@link ConfigurationImpl}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class ConfigurationImplTest {
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsConfiguration, Behavior, int, long, long)}. */
    @Test
    public void testConstructor() {
        ConfigurationImpl<DummyGraphicsConfiguration> configuration = new ConfigurationImpl<>(
                Collections.singletonList(() -> ""), new DummyGraphicsConfiguration(), Behavior.defaultBehavior(), 0,
                0L, 0L);

        log.info(configuration.toString());
    }

    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsConfiguration, Behavior, int, long, long)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullPlayerConfiguration() {
        new ConfigurationImpl<>(null, new DummyGraphicsConfiguration(), Behavior.defaultBehavior(), 0, 0L, 0L);
    }
    
    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsConfiguration, Behavior, int, long, long)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullStyle() {
        new ConfigurationImpl<DummyGraphicsConfiguration>(Collections.singletonList(() -> ""), null,
                Behavior.defaultBehavior(), 0, 0L, 0L);
    }

    /** Test method for {@link ConfigurationImpl#ConfigurationImpl(java.util.List, GraphicsConfiguration, Behavior, int, long, long)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullBehavior() {
        new ConfigurationImpl<>(Collections.singletonList(() -> ""), new DummyGraphicsConfiguration(), null, 0, 0L, 0L);
    }
}
