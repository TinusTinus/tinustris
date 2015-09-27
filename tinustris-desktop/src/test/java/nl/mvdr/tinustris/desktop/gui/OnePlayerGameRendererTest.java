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

import javafx.embed.swing.JFXPanel;
import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.desktop.gui.BlockCreator;
import nl.mvdr.tinustris.desktop.gui.OnePlayerGameRenderer;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link OnePlayerGameRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class OnePlayerGameRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /**
     * Test case for {@link OnePlayerGameRenderer#OnePlayerGameRenderer(String, int, int, BlockCreator)} in case the
     * name parameter is null.
     */
    @Test(expected = NullPointerException.class)
    public void testNullName() {
        new OnePlayerGameRenderer(null, 1, 1, (x, y, size, block, style, framesLines, framesLock) -> new Rectangle());
    }
    
    /**
     * Test case for {@link OnePlayerGameRenderer#OnePlayerGameRenderer(String, int, int, BlockCreator)} in case the
     * block creator parameter is null.
     */
    @Test(expected = NullPointerException.class)
    public void testNullBlockCreator() {
        new OnePlayerGameRenderer("name", 1, 1, null);
    }
    
    /** Test case for {@link OnePlayerGameRenderer#OnePlayerGameRenderer(String, int, int, BlockCreator)}. */
    @Test
    public void testRender() {
        OnePlayerGameRenderer renderer = new OnePlayerGameRenderer("name", 1, 1,
                (x, y, size, block, style, framesLines, framesLock) -> new Rectangle());
        
        renderer.render(new OnePlayerGameState());
    }
}
