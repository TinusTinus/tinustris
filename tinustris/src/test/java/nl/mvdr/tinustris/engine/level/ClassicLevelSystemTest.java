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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link ClassicLevelSystem}.
 * 
 * @author Martijn van de Rijdt
 */
public class ClassicLevelSystemTest extends LevelSystemTester {
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test0Lines() {
        test(0, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test1Lines() {
        test(1, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test2Lines() {
        test(2, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test3Lines() {
        test(3, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test4Lines() {
        test(4, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test5Lines() {
        test(5, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test6Lines() {
        test(6, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test7Lines() {
        test(7, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test8Lines() {
        test(8, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test9Lines() {
        test(9, 0);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test10Lines() {
        test(10, 1);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test11Lines() {
        test(11, 1);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test21Lines() {
        test(21, 2);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test65Lines() {
        test(65, 6);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test999Lines() {
        test(999, 99);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test0LinesStartingLevel5() {
        test(5, 0, 5);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test34LinesStartingLevel5() {
        test(5, 34, 5);
    }
    
    /** Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}. */
    @Test
    public void test89LinesStartingLevel5() {
        test(5, 89, 8);
    }

    /**
     * Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}.
     * 
     * @param startingLevel starting level
     * @param lines number of lines
     * @param expectedLevel expected resulting level
     */
    private void test(int lines, int expectedLevel) {
        test(0, lines, expectedLevel);
    }
    
    /**
     * Tests {@link ClassicLevelSystem#computeLevel(OnePlayerGameState, OnePlayerGameState)}.
     * 
     * @param startingLevel starting level
     * @param lines number of lines
     * @param expectedLevel expected resulting level
     */
    private void test(int startingLevel, int lines, int expectedLevel) {
        ClassicLevelSystem levelSystem = new ClassicLevelSystem(startingLevel);
        OnePlayerGameState state = createGameState(0, lines, 0);
        
        int level = levelSystem.computeLevel(state, state);
        
        Assert.assertEquals(expectedLevel, level);
    }
    
    /** {@inheritDoc} */
    @Override
    LevelSystem createLevelSystem() {
        return new ClassicLevelSystem();
    }
}
