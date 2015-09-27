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
package nl.mvdr.tinustris.gui;

import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.Block;

import org.junit.Test;

/**
 * Test class for {@link BlockStyle}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class BlockStyleTest {
    /** Tests {@link BlockStyle#apply(Rectangle, Block, int, int)} for all possible styles and tetrominoes. */
    @Test
    public void testApplyRectangle() {
        Rectangle rectangle = new Rectangle(10, 10);
        
        for (BlockStyle style: BlockStyle.values()) {
            for (Block block: Block.values()) {
                log.info("Applying style {} for block {}.", style, block);
                style.apply(rectangle, block, 25, 0);
            }
        }
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block, int, int)} for a null tetromino value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullTetrominoRectangle() {
        Rectangle rectangle = new Rectangle(10, 10);
        BlockStyle.ACTIVE.apply(rectangle, null, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(Rectangle, Block, int, int)} for a null rectangle value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullRectangleRectangle() {
        BlockStyle.ACTIVE.apply((Rectangle)null, Block.I, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(javafx.scene.shape.Shape3D, Block, int, int)} for all possible styles and tetrominoes. */
    @Test
    public void testApplyBox() {
        Box box = new Box();
        
        for (BlockStyle style: BlockStyle.values()) {
            for (Block block: Block.values()) {
                log.info("Applying style {} for block {}.", style, block);
                style.apply(box, block, 25, 0);
            }
        }
    }
    
    /** Tests {@link BlockStyle#apply(javafx.scene.shape.Shape3D, Block, int, int)} for a null tetromino value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullTetrominoBox() {
        Box box = new Box();
        BlockStyle.ACTIVE.apply(box, null, 25, 0);
    }
    
    /** Tests {@link BlockStyle#apply(javafx.scene.shape.Shape3D, Block, int, int)} for a null box value. */
    @Test(expected = NullPointerException.class)
    public void testApplyNullRectangleBox() {
        BlockStyle.ACTIVE.apply((Box)null, Block.I, 25, 0);
    }
}
