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

import java.util.Arrays;

import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link MultiplayerGameRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class MultiplayerGameRendererTest {
    /** Tests the constructor. */
    @Test
    public void testConstructor() {
        new MultiplayerGameRenderer(new DummyRenderer<OnePlayerGameState>(), 2);
    }
    
    /** Tests the constructor. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullValue() {
        new MultiplayerGameRenderer(null, 2);
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test
    public void testRender0() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 0);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(1, 0));
        
        renderer.render(gameState);
        
        Assert.assertSame(state0, dummyRenderer.getLastRenderedState());
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test
    public void testRender1() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 1);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(1, 0));
        
        renderer.render(gameState);
        
        Assert.assertSame(state1, dummyRenderer.getLastRenderedState());
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test(expected = NullPointerException.class)
    public void testRenderNull() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 1);
        
        renderer.render(null);
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRenderNegativeIndex() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, -1);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(1, 0));
        
        renderer.render(gameState);
    }
    
    /** Test case for {@link MultiplayerGameRenderer#render(MultiplayerGameState)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRenderTooighIndex() {
        DummyRenderer<OnePlayerGameState> dummyRenderer = new DummyRenderer<>();
        MultiplayerGameRenderer renderer = new MultiplayerGameRenderer(dummyRenderer, 2);
        OnePlayerGameState state0 = new OnePlayerGameState();
        OnePlayerGameState state1 = new OnePlayerGameState();
        MultiplayerGameState gameState = new MultiplayerGameState(Arrays.asList(state0, state1), Arrays.asList(1, 0));
        
        renderer.render(gameState);
    }
}
