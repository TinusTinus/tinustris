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

import javafx.scene.shape.Box;
import nl.mvdr.tinustris.core.model.Block;
import nl.mvdr.tinustris.desktop.gui.BlockStyle;
import nl.mvdr.tinustris.desktop.gui.BoxBlockCreator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link BoxBlockCreator}.
 * 
 * @author Martijn van de Rijdt
 */
public class BoxBlockCreatorTest {
    /**
     * Test method for
     * {@link BoxBlockCreator#createBlock(double, double, double, nl.mvdr.tinustris.core.model.Block, BlockStyle, int, int)}.
     */
    @Test
    public void testCreate() {
        BoxBlockCreator creator = new BoxBlockCreator();
        
        Box box = creator.createBlock(1, 2, 3, Block.L, BlockStyle.ACTIVE, 3, 2);
        
        Assert.assertNotNull(box);
    }
}
