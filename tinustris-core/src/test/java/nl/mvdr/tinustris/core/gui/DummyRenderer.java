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
package nl.mvdr.tinustris.core.gui;

import lombok.Getter;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.game.state.GameState;

/**
 * Dummy renderer, which simply stores the last game state it has received.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@Getter
public class DummyRenderer<S extends GameState> implements GameRenderer<S> {
    /** Game state from the last time render was called. Initially null. */
    private S lastRenderedState;
    
    /** {@inheritDoc} */
    @Override
    public void render(S gameState) {
        this.lastRenderedState = gameState;
    }
}
