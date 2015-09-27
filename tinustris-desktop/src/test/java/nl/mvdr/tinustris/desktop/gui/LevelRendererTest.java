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

import java.util.Optional;

import javafx.embed.swing.JFXPanel;
import nl.mvdr.tinustris.desktop.gui.LevelRenderer;
import nl.mvdr.tinustris.desktop.gui.LinesRenderer;
import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link LinesRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class LevelRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Tests {@link LinesRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRender() {
        LevelRenderer renderer = createLevelRenderer();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        
        Assert.assertEquals("0", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRenderPositiveLevel() {
        LevelRenderer renderer = createLevelRenderer();
        OnePlayerGameState state = new OnePlayerGameState(new OnePlayerGameState().getGrid(), 4, Optional.empty(),
                Optional.empty(), Optional.empty(), Tetromino.L, 0, 0, 0, InputStateHistory.NEW, 0, 365, 0, 36, 0, 0);
        
        renderer.render(state);
        
        Assert.assertEquals("36", renderer.getText());
    }
    
    /** Tests {@link LinesRenderer#render(OnePlayerGameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        LevelRenderer renderer = createLevelRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private LevelRenderer createLevelRenderer() {
        return new LevelRenderer() {
            /** 
             * Mock implementation which just executes the runnable on the current thread.
             * 
             * @param runnable runnable to be executed
             */
            @Override
            protected void runOnJavaFXThread(Runnable runnable) {
                runnable.run();
            }
        };
    }
}
