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
package nl.mvdr.tinustris.model;

/**
 * An action that can be performed in a game of Tetris.
 * 
 * Note that the actions are orderes in such a way that only the last three values are able to cause a tetromino to be
 * locked in place.
 * 
 * @author Martijn van de Rijdt
 */
public enum Action {
    /** Moves the currently active block left. */
    MOVE_LEFT,
    /** Moves the currently active block right. */
    MOVE_RIGHT,
    /** Moves the currently active block down. */
    GRAVITY_DROP,
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT,
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT,
    /** Swaps the currently active block and the next block. */
    HOLD,
    /** Moves the currently active block down, or locks it in place if that is impossible. */
    MOVE_DOWN,
    /** Locks the currently active block in place. */
    LOCK,
    /** Instantly drops the currently active block. */
    HARD_DROP;
}
