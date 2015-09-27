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

import nl.mvdr.game.input.InputState;
import nl.mvdr.tinustris.core.input.Input;
import nl.mvdr.tinustris.core.input.InputStateHistory;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link InputStateHistory}.
 * 
 * @author Martijn van de Rijdt
 */
public class InputStateHistoryTest {
    /** Test method for {@link InputStateHistory#NEW}. */
    @Test
    public void testNew() {
        InputStateHistory history = InputStateHistory.NEW;
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case nothing is pressed. */
    @Test
    public void testGetNumberOfFramesNothingPressed() {
        InputState<Input> inputState = input -> false;
        
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case nothing is pressed. */
    @Test
    public void testGetNumberOfFramesNothingPressedTwice() {
        InputStateHistory history = InputStateHistory.NEW;
        InputState<Input> inputState = input -> false;
        
        history = history.next(inputState);
        history = history.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed. */
    @Test
    public void testGetNumberOfFramesEverythingPressed() {
        InputState<Input> inputState = input -> true;
        
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 1, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed. */
    @Test
    public void testGetNumberOfFramesEverythingPressedTwice() {
        InputStateHistory history = InputStateHistory.NEW;
        InputState<Input> inputState = input -> true;
        
        history = history.next(inputState);
        history = history.next(inputState);

        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 2, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case every button is pressed, then let go. */
    @Test
    public void testGetNumberOfFramesEverythingPressedThenLetGo() {
        InputStateHistory history = InputStateHistory.NEW;
        
        history = history.next(input -> true);
        history = history.next(input -> false);
        
        for (Input input: Input.values()) {
            Assert.assertEquals(input.toString(), 0, history.getNumberOfFrames(input));
        }
    }
    
    /** Test method for {@link InputStateHistory#next(InputState)} in case a single button is pressed. */
    @Test
    public void testGetNumberOfFramesOneButtonPressed() {
        InputState<Input> inputState = input -> input == Input.TURN_RIGHT;
        
        InputStateHistory history = InputStateHistory.NEW.next(inputState);
        
        Assert.assertEquals(0, history.getNumberOfFrames(Input.SOFT_DROP));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.HOLD));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.HARD_DROP));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.LEFT));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.RIGHT));
        Assert.assertEquals(0, history.getNumberOfFrames(Input.TURN_LEFT));
        Assert.assertEquals(1, history.getNumberOfFrames(Input.TURN_RIGHT));
    }
}
