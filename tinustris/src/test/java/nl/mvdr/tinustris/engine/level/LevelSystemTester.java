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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nl.mvdr.tinustris.input.InputStateHistory;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Tetromino;

import org.junit.Test;

/**
 * Abstract superclass for test cases for implementations of {@link LevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public abstract class LevelSystemTester {
    /** Test method that simply invokes {@link LevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testSameState() {
        OnePlayerGameState state = createGameState(0, 0, 0);
        LevelSystem levelSystem = createLevelSystem();
        
        levelSystem.computeLevel(state, state);
    }
    
    /** Test method that simply invokes {@link LevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void testDifferentStates() {
        OnePlayerGameState previousState = createGameState(0, 0, 0);
        OnePlayerGameState newState = createGameState(0, 0, 0);
        LevelSystem levelSystem = createLevelSystem();
        
        levelSystem.computeLevel(previousState, newState);
    }
    
    /**
     * Creates a game state with the given parameters.
     * 
     * @param blockCounter block counter
     * @param lines lines
     * @param level level
     * @return game state
     */
    OnePlayerGameState createGameState(int blockCounter, int lines, int level) {
        List<Optional<Block>> grid = Collections.nCopies(220, Optional.empty());
        OnePlayerGameState state = new OnePlayerGameState(grid, 10, Optional.empty(), Optional.empty(),
                Optional.empty(), Tetromino.Z, 0, 0, 0, InputStateHistory.NEW, blockCounter, lines, 0, level, 0, 0);
        return state;
    }
    
    /**
     * Creates the level system to be tested.
     * 
     * @return level system
     */
    abstract LevelSystem createLevelSystem();
}
