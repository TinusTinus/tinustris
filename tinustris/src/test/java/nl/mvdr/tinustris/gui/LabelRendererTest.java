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

import javafx.embed.swing.JFXPanel;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.DummyGameState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link LabelRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class LabelRendererTest {
    /** Setup method. */
    @Before
    public void setUp() {
        // Force JavaFX graphics initialisation; this is required by the Image constructor.
        // When running the actual application this is performed by Application.launch().
        new JFXPanel();
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.game.state.GameState)}. */
    @Test
    public void testRender() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        String text = renderer.getText();
        log.info(text);
        Assert.assertNotNull(text);
        Assert.assertNotEquals("", text);
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.game.state.GameState)} in case the text stays the same. */
    @Test
    public void testRenderSameValue() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        DummyGameState state = DummyGameState.GAME_NOT_OVER;
        renderer.setText(state.toString());
        
        renderer.render(state);
        
        String text = renderer.getText();
        log.info(text);
        Assert.assertNotNull(text);
        Assert.assertNotEquals("", text);
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.game.state.GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        LabelRenderer<DummyGameState> renderer = createLabelRenderer();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new label renderer.
     * 
     * @return renderer
     */
    private LabelRenderer<DummyGameState> createLabelRenderer() {
        return new LabelRenderer<DummyGameState>() {
            /** 
             * Mock implementation which just executes the runnable on the current thread.
             * 
             * @param runnable runnable to be executed
             */
            @Override
            protected void runOnJavaFXThread(Runnable runnable) {
                log.info("Executing runnable: " + runnable);
                runnable.run();
            }

            /**
             * Mock implementation which returns the state's toString.
             * 
             * @return string representation of the state
             */
            @Override
            protected String toText(DummyGameState state) {
                return state.toString();
            }
        };
    }
}
