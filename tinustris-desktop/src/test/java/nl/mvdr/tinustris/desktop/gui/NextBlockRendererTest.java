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

import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.desktop.gui.NextBlockRenderer;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Test;

/**
 * Test class for {@link NextBlockRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class NextBlockRendererTest {
    /** Tests {@link NextBlockRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRender() {
        NextBlockRenderer renderer = createNextBlockRenderer();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
    }
    
    /** Tests {@link NextBlockRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRenderTwice() {
        NextBlockRenderer renderer = createNextBlockRenderer();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        renderer.render(state);
    }
    
    /** Tests {@link NextBlockRenderer#render(OnePlayerGameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        NextBlockRenderer renderer = createNextBlockRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private NextBlockRenderer createNextBlockRenderer() {
        return new NextBlockRenderer((x, y, size, block, style, framesUnitl, framesSince) -> new Rectangle()) {
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
