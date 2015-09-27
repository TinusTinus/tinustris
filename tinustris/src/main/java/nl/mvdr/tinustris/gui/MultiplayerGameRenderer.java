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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.tinustris.model.MultiplayerGameState;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Renderer for a game state belonging to a specific player in a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public class MultiplayerGameRenderer implements GameRenderer<MultiplayerGameState> {
    /** Renderer for the one player game. All {@link #render(MultiplayerGameState)} calls are deferred to this instance. */
    private final @NonNull GameRenderer<OnePlayerGameState> renderer;
    /**
     * Index of the player whose state is to be rendered by this renderer. Must be a valid index in all multiplayer game
     * states passed into {@link #render(MultiplayerGameState)}.
     */
    private final int playerIndex;

    /** 
     * {@inheritDoc} 
     * 
     * The value of {@link #playerIndex} must be a valid index in the value of gameState.
     */
    @Override
    public void render(@NonNull MultiplayerGameState gameState) {
        OnePlayerGameState state = gameState.getStateForPlayer(playerIndex);
        this.renderer.render(state);
    }
}
