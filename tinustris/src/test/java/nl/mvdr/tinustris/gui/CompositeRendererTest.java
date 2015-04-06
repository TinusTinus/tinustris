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
import java.util.Collections;
import java.util.List;

import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.tinustris.model.DummyGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link CompositeRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
public class CompositeRendererTest {
    /** Tests {@link CompositeRenderer#render(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testRenderEmptyList() {
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(Collections.emptyList());
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
    }
    
    /** Tests {@link CompositeRenderer#render(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testRenderOneRenderer() {
        DummyRenderer<DummyGameState> dummyRenderer = new DummyRenderer<>();
        List<GameRenderer<DummyGameState>> renderers = Collections.singletonList(dummyRenderer);
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(renderers);
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        Assert.assertSame(DummyGameState.GAME_NOT_OVER, dummyRenderer.getLastRenderedState());
    }
    
    /** Tests {@link CompositeRenderer#render(nl.mvdr.tinustris.model.GameState)}. */
    @Test
    public void testRenderTwoRenderers() {
        DummyRenderer<DummyGameState> dummyRenderer0 = new DummyRenderer<>();
        DummyRenderer<DummyGameState> dummyRenderer1 = new DummyRenderer<>();
        List<GameRenderer<DummyGameState>> renderers = Arrays.asList(dummyRenderer0, dummyRenderer1);
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(renderers);
        
        renderer.render(DummyGameState.GAME_NOT_OVER);
        
        Assert.assertSame(DummyGameState.GAME_NOT_OVER, dummyRenderer0.getLastRenderedState());
        Assert.assertSame(DummyGameState.GAME_NOT_OVER, dummyRenderer1.getLastRenderedState());
    }
    
    /** Tests {@link LabelRenderer#render(nl.mvdr.tinustris.model.GameState)} when a null value of GameState is passed in. */
    @Test(expected = NullPointerException.class)
    public void testNullState() {
        CompositeRenderer<DummyGameState> renderer = new CompositeRenderer<>(Collections.emptyList());
        
        renderer.render(null);
    }
}
