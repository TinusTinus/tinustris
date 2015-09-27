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
package nl.mvdr.tinustris.engine.level;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Determines the leveling system for a game.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface LevelSystem {
    /**
     * Computes the new level value.
     * 
     * @param previousState previous game state
     * @param newState next game state; all fields should be filled except for level
     * @return level value
     */
    int computeLevel(OnePlayerGameState previousState, OnePlayerGameState newState);
}
