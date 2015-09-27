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
package nl.mvdr.tinustris.desktop.gui;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.desktop.gui.BlockCreator;
import nl.mvdr.tinustris.desktop.gui.GraphicsStyle;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GraphicsStyle}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GraphicsStyleTest {
    /** Test method for {@link GraphicsStyle#makeBlockCreator()}. */
    @Test
    public void test2DBlockCreator() {
        BlockCreator creator = GraphicsStyle.TWO_DIMENSIONAL.makeBlockCreator();
        
        Assert.assertNotNull(creator);
    }
    
    /** Test method for {@link GraphicsStyle#makeBlockCreator()}. */
    @Test
    public void test3DBlockCreator() {
        BlockCreator creator = GraphicsStyle.THREE_DIMENSIONAL.makeBlockCreator();
        
        Assert.assertNotNull(creator);
    }
    
    /** Test method for {@link GraphicsStyle#isAvailable()}. */
    @Test
    public void test2DIsAvailable() {
        boolean available = GraphicsStyle.TWO_DIMENSIONAL.isAvailable();
        
        Assert.assertTrue(available);
    }
    
    /** Test method for {@link GraphicsStyle#isAvailable()}. */
    @Test
    public void test3DIsAvailable() {
        boolean available = GraphicsStyle.TWO_DIMENSIONAL.isAvailable();
        
        log.info("3D available: " + available);
    }
    
    /** Tests the toString method. */
    @Test
    public void testToString() {
        for (GraphicsStyle style: GraphicsStyle.values()) {
            log.info(style.toString());
        }
    }
}
