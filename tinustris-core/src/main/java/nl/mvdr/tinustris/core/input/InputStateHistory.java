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
package nl.mvdr.tinustris.core.input;

import java.util.EnumMap;
import java.util.Map;

import nl.mvdr.game.input.InputState;

/**
 * Keeps administration of the number of frames each input has been pressed.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputStateHistory {
    /** Input state history where no inputs have been pressed. */
    public static final InputStateHistory NEW = input -> 0;

    /**
     * Retrieves the number of frames the given input has been pressed.
     * 
     * @param input
     *            input
     * @return number of frames
     */
    int getNumberOfFrames(Input input);

    /**
     * Creates the input state history for the next frame.
     * 
     * @param inputState
     *            input state for the next frame
     * @return new input state history
     */
    default InputStateHistory next(InputState<Input> inputState) {
        Map<Input, Integer> frames = new EnumMap<>(Input.class);
        for (Input input : Input.values()) {
            int value;
            if (inputState.isPressed(input)) {
                value = getNumberOfFrames(input) + 1;
            } else {
                value = 0;
            }
            frames.put(input, Integer.valueOf(value));
        }

        return frames::get;
    }
}