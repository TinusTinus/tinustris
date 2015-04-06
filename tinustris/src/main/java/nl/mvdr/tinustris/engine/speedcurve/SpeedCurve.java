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
package nl.mvdr.tinustris.engine.speedcurve;

import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Determines numeric speed values for a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
public interface SpeedCurve {
    /**
     * Determines the internal gravity based on the given game state.
     * 
     * The internal gravity is expressed in 1 / 256 G, where G is the number of cells a tetromino drops per frame of
     * animation.
     * 
     * @param state
     *            game state
     * @return internal gravity in 1 / 256 G, must be positive
     */
    int computeInternalGravity(OnePlayerGameState state);
    
    /**
     * Determines the lock delay based on the given game state. The lock delay is expressed in frames.
     * 
     * @param state
     *            game state
     * @return lock delay in frames, must be 0 or more
     */
    int computeLockDelay(OnePlayerGameState state);
    
    /**
     * Determines the ARE (also known as entry delay, appearance delay or spawn delay) in frames.
     * 
     * @param state state
     * @return ARE in frames, must be 0 or more
     */
    int computeARE(OnePlayerGameState state);
    
    /**
     * Determines the line clear delay in frames.
     * 
     * @param state state
     * @return line clear delay in frames, must be positive
     */
    int computeLineClearDelay(OnePlayerGameState state);
    
    // TODO add delayed auto shift (DAS)?
}
