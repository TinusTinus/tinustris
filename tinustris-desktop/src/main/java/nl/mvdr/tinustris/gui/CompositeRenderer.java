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

import java.util.List;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.game.state.GameState;

/**
 * Game renderer which merely defers to a number of other renderers.
 * 
 * <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CompositeRenderer<S extends GameState> implements GameRenderer<S> {
    /** Renderers. */
    private final List<GameRenderer<S>> renderers;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull S gameState) {
        renderers.forEach(renderer -> renderer.render(gameState));
    }
}
