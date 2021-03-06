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
package nl.mvdr.tinustris.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.state.GameState;

/**
 * Dummy implementation of {@link GameState}.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum DummyGameState implements GameState {
    /** Dummy game state where the game is not yet over. */
    GAME_NOT_OVER(false),
    /** Dummy game state where the game is over. */
    GAME_OVER(true);
    
    /** Whether the game is over. */
    private final boolean gameOver;
}
