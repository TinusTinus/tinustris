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
package nl.mvdr.tinustris.core.model;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.core.model.Block;
import nl.mvdr.tinustris.core.model.Orientation;
import nl.mvdr.tinustris.core.model.Point;
import nl.mvdr.tinustris.core.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link Tetromino}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class TetrominoTest {
    /**
     * Tests that each tetromino value contains a non-null points list which contains exactly four distinct points, with
     * all coordinates in the range [0, 4).
     */
    @Test
    public void testPoints() {
        for (Tetromino tetromino : Tetromino.values()) {
            log.info("Tetromino: " + tetromino);
            
            for (Orientation orientation : Orientation.values()) {
                log.info("  Orientation: " + orientation);
                
                Set<Point> points = tetromino.getPoints(orientation);
                Assert.assertEquals(4, points.size());

                for (Point point : points) {
                    log.info("    Point: " + point);
                    
                    Assert.assertTrue(0 <= point.getX());
                    Assert.assertTrue(point.getX() < 4);
                    Assert.assertTrue(0 <= point.getY());
                    Assert.assertTrue(point.getY() < 4);
                }
            }
        }
    }
    
    /** Tests what happens when {@link Tetromino#getPoints(Orientation)} is invoked with a null value. */
    @Test(expected = NullPointerException.class)
    public void testGetPointsNull() {
        Tetromino.I.getPoints(null);
    }
    
    /** Tests that the result of {@link Tetromino#getPoints(Orientation)} is unmodifiable. */
    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSet() {
        Set<Point> points = Tetromino.J.getPoints(Orientation.getDefault());
        
        points.add(new Point(3, 3));
    }
    
    /** Tests the blocks. */
    @Test
    public void testBlocks() {
        for (Tetromino tetromino: Tetromino.values()) {
            Block block = tetromino.getBlock();
            
            Assert.assertNotNull(block);
            // toString is the single-letter representation of the block for both classes
            Assert.assertEquals(tetromino.toString(), block.toString());
        }
    }
}
