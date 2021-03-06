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

import java.util.Arrays;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.core.model.MultiplayerGameState;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link MultiplayerGameState}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class MultiplayerGameStateTest {
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullList() {
        new MultiplayerGameState(null);
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullValues() {
        new MultiplayerGameState(Arrays.<OnePlayerGameState>asList(null, null));
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOneValue() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Collections.singletonList(state));
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorFirstValueNull() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(null, state));
    }
    
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test(expected = NullPointerException.class)
    public void testConstructorSecondValueNull() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(state, null));
    }
    
    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test
    public void testConstructorTwoPlayers() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(state, state));
    }

    /** Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}. */
    @Test
    public void testConstructorThreePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();

        new MultiplayerGameState(Arrays.asList(state, state, state));
    }

    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List)}.
     * 
     * Passes in a null value. One value is too few arguments (there should be at least two players), and null values
     * are not allowed. Depending on the order of validation we should get either a NullPointerException or an
     * IllegalArgumentException.
     */
    @Test(expected = RuntimeException.class)
    public void testConstructorNullValue() {
        new MultiplayerGameState(Arrays.asList((OnePlayerGameState)null));
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where the target
     * list is null.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullTargets() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), null);
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where the sizes
     * of the two lists do not match.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNonMatchingSizes() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(2, 1));
    }

    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where the targets
     * list contains a null value.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorNullTarget() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(2, 1, null));
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where a player is
     * targeting themselves.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorTargetSelf() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(2, 1, 3));
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where a player is
     * targeting an index which is not a valid player value.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorTargetOutOfBounds() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(4, 4, 4));
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where a player is
     * targeting an index which is not a valid player value.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructorNegativeTarget() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(-1, -1, -1));
    }
    
    /**
     * Test case for {@link MultiplayerGameState#MultiplayerGameState(java.util.List, java.util.List)} where multiple
     * players are targeting the same player. This is allowed and should not result in a runtime exception.
     */
    @Test
    public void testConstructorMultiplePlayersTargetingSamePlayer() {
        OnePlayerGameState state = new OnePlayerGameState();
        
        new MultiplayerGameState(Arrays.asList(state, state, state), Arrays.asList(1, 0, 1));
    }
    
    /** Test case for {@link MultiplayerGameState#toString()}. */
    @Test
    public void testToString() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        String string = multiplayerState.toString();

        log.info(string);
        Assert.assertNotNull(string);
        Assert.assertNotEquals("", string);
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverTwoActivePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertFalse(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverTwoToppedPlayers() {
        OnePlayerGameState state = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertTrue(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverOneActiveOneToppedPlayer() {
        OnePlayerGameState activeState = new OnePlayerGameState();
        OnePlayerGameState toppedState = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(activeState, toppedState));

        Assert.assertTrue(multiplayerState.isGameOver());
    }

    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverOneToppedOneActivePlayer() {
        OnePlayerGameState toppedState = createToppedState();
        OnePlayerGameState activeState = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(toppedState, activeState));

        Assert.assertTrue(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverThreeActivePlayers() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state, state));
        
        Assert.assertFalse(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverThreeToppedPlayers() {
        OnePlayerGameState state = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state, state));
        
        Assert.assertTrue(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverOneActiveTwoToppedPlayers() {
        OnePlayerGameState activeState = new OnePlayerGameState();
        OnePlayerGameState toppedState = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(activeState, toppedState,
                toppedState));
        
        Assert.assertTrue(multiplayerState.isGameOver());
    }
    
    /** Test case for {@link MultiplayerGameState#isGameOver()}. */
    @Test
    public void testGameOverTwoActiveOneToppedPlayers() {
        OnePlayerGameState activeState = new OnePlayerGameState();
        OnePlayerGameState toppedState = createToppedState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(activeState, activeState,
                toppedState));
        
        // game should not be over as long as there are at least two active players
        Assert.assertFalse(multiplayerState.isGameOver());
    }
    
    /** 
     * Creates a topped single player game state.
     * 
     * @return state
     */
    private OnePlayerGameState createToppedState() {
        return new OnePlayerGameState() {
            /** 
             * {@inheritDoc}
             * 
             * Dummy implementation which always returns true.
             */
            @Override
            public boolean isTopped() {
                return true;
            }
        };
    }
    
    /** Test case for {@link MultiplayerGameState#getNumberOfPlayers()}. */
    @Test
    public void testGetNumberOfPlayers2() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertEquals(2, multiplayerState.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerGameState#getNumberOfPlayers()}. */
    @Test
    public void testGetNumberOfPlayers3() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state, state));

        Assert.assertEquals(3, multiplayerState.getNumberOfPlayers());
    }
    
    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test
    public void testGetStateForPlayer0() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertNotNull(multiplayerState.getStateForPlayer(0));
    }
    
    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test
    public void testGetStateForPlayer1() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        Assert.assertNotNull(multiplayerState.getStateForPlayer(1));
    }

    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetStateForPlayerNegative() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        multiplayerState.getStateForPlayer(-1);
    }
    
    /** Test case for {@link MultiplayerGameState#getStateForPlayer(int)}. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetStateForPlayerTooLarge() {
        OnePlayerGameState state = new OnePlayerGameState();
        MultiplayerGameState multiplayerState = new MultiplayerGameState(Arrays.asList(state, state));

        multiplayerState.getStateForPlayer(3);
    }
}
