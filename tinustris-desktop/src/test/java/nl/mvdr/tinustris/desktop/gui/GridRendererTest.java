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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.scene.shape.Rectangle;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.core.model.Block;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;
import nl.mvdr.tinustris.core.model.Orientation;
import nl.mvdr.tinustris.core.model.Point;
import nl.mvdr.tinustris.core.model.Tetromino;
import nl.mvdr.tinustris.desktop.gui.GridRenderer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GridRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GridRendererTest {
    /** Tests {@link GridRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRenderSimpleState() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(OnePlayerGameState)} with the same game state twice. */
    @Test
    public void testRenderSimpleStateTwice() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState state = new OnePlayerGameState();
        
        renderer.render(state);
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(OnePlayerGameState)}. */
    @Test
    public void testRender() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState gameState = createNontrivialGameState();
        log.info(gameState.toString());
        
        renderer.render(gameState);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }

    /** Tests {@link GridRenderer#render(OnePlayerGameState)} with the same game state twice. */
    @Test
    public void testRenderTwice() {
        GridRenderer renderer = createGridGroup();
        OnePlayerGameState state = createNontrivialGameState();
        log.info(state.toString());
        
        renderer.render(state);
        renderer.render(state);
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(OnePlayerGameState)} with two different game states. */
    @Test
    public void testRenderTwiceWithDifferentStates() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(new OnePlayerGameState());
        renderer.render(createNontrivialGameState());
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(OnePlayerGameState)} with a full line. */
    @Test
    public void testRenderWithFullLine() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(createGameStateWithFullLine());
        
        Assert.assertFalse(renderer.getChildren().isEmpty());
    }
    
    /** Tests {@link GridRenderer#render(OnePlayerGameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        GridRenderer renderer = createGridGroup();
        
        renderer.render(null);
    }
    
    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private GridRenderer createGridGroup() {
        return new GridRenderer((x, y, size, block, style, framesUnitl, framesSince) -> new Rectangle()) {
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
        };
    }
    
    /**
     * Creates a nontrivial game state, containing a block in the grid, an active block and a ghost.
     * 
     * @return game state
     */
    private OnePlayerGameState createNontrivialGameState() {
        List<Optional<Block>> grid = new ArrayList<>(Collections.nCopies(220, Optional.empty()));
        // add a single block at (2, 0)
        grid.set(2, Optional.of(Block.S));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(5, 10), Orientation.getDefault(),
                Tetromino.I);
        return gameState;
    }
    
    /**
     * Creates a game state, containing a full line in the grid.
     * 
     * @return game state
     */
    private OnePlayerGameState createGameStateWithFullLine() {
        List<Optional<Block>> grid = new ArrayList<>(220);
        grid.addAll(Collections.nCopies(10, Optional.of(Block.S)));
        grid.addAll(Collections.nCopies(210, Optional.empty()));
        OnePlayerGameState gameState = new OnePlayerGameState(grid, 10, Tetromino.O, new Point(5, 10), Orientation.getDefault(),
                Tetromino.I);
        return gameState;
    }
}
