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
package nl.mvdr.tinustris.core.engine;

import nl.mvdr.tinustris.core.engine.GapGenerator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GapGenerator}.
 * 
 * @author Martijn van de Rijdt
 */
public class GapGeneratorTest {
    /** Test case for {@link GapGenerator#get(int)}. */
    @Test
    public void testGet() {
        GapGenerator generator = new GapGenerator(10);
        
        Integer value = generator.get(0);
        
        Assert.assertTrue(0 <= value.intValue());
        Assert.assertTrue(value.intValue() < 10);
    }
}
