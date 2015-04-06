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
package nl.mvdr.tinustris.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.model.Action;

/**
 * The different kinds of input the payer can give.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Input {
    /** Moves the currently active block left. */
    LEFT(Action.MOVE_LEFT, "Move Left", "Shifts the tetromino one column to the left."),
    /** Moves the currently active block right. */
    RIGHT(Action.MOVE_RIGHT, "Move Right", "Shifts the tetromino one column to the right."),
    /** Accelerates the currently active block's descent. */
    SOFT_DROP(Action.MOVE_DOWN, "Soft Drop", "Accelerates the tetromino's descent."),
    /** Instantly drops the currently active block. */
    HARD_DROP(Action.HARD_DROP, "Hard Drop", "Instantly drops the tetromino down."),
    /** Rotates the currently active block 90 degrees counter-clockwise. */
    TURN_LEFT(Action.TURN_LEFT, "Turn Left", "Rotates the tetromino 90 degrees counter-clockwise."),
    /** Rotates the currently active block 90 degrees clockwise. */
    TURN_RIGHT(Action.TURN_RIGHT, "Turn Right", "Rotates the tetromino 90 degrees clockwise."),
    /** Swaps the currently active block and the next block. */
    HOLD(Action.HOLD, "Hold", "Swaps the current tetromino with the next one.");
    
    /** Action to be taken when this input is pressed. */
    private final Action action;
    /** Nice name. */
    private final String name;
    /** Description of the effect of this input. */
    private final String description;
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return name;
    }
}
