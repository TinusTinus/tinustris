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
package nl.mvdr.tinustris.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Orientation}.
 * 
 * @author Martijn van de Rijdt
 */
public class OrientationTest {
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatDown() {
        Assert.assertEquals(Orientation.FLAT_LEFT, Orientation.FLAT_DOWN.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatLeft() {
        Assert.assertEquals(Orientation.FLAT_UP, Orientation.FLAT_LEFT.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatUp() {
        Assert.assertEquals(Orientation.FLAT_RIGHT, Orientation.FLAT_UP.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextClockwiseFlatRight() {
        Assert.assertEquals(Orientation.FLAT_DOWN, Orientation.FLAT_RIGHT.getNextClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatDown() {
        Assert.assertEquals(Orientation.FLAT_RIGHT, Orientation.FLAT_DOWN.getNextCounterClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatRight() {
        Assert.assertEquals(Orientation.FLAT_UP, Orientation.FLAT_RIGHT.getNextCounterClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatUp() {
        Assert.assertEquals(Orientation.FLAT_LEFT, Orientation.FLAT_UP.getNextCounterClockwise());
    }
    
    /** Tests the retrieval of the next orientation when rotating a tetromino. */
    @Test
    public void testGetNextCounterClockwiseFlatLeft() {
        Assert.assertEquals(Orientation.FLAT_DOWN, Orientation.FLAT_LEFT.getNextCounterClockwise());
    }
}
