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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import nl.mvdr.game.state.GameState;

/**
 * Contains the game state for a multiplayer game.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class MultiplayerGameState implements GameState {
    /** Game states of the individualy players. Contains at least two elements and no null values. */
    @NonNull
    private final List<OnePlayerGameState> states;
    
    /**
     * Per player: the player index that will be targeted for the next garbage line. Size must be equal to the size of
     * states. May not contain null values, and each player's target must be a different player.
     */
    @NonNull
    @Getter
    private final List<Integer> nextGarbageTargets;
    
    /**
     * Returns the default list of targets for the given number of players.
     * 
     * A list of targets is a list containing, for each player index, the index of the player targeted for garbage
     * attacks. By default each player targets the next.
     * 
     * @param numberOfPlayers number of players
     * @return targets
     */
    public static List<Integer> defaultTargets(int numberOfPlayers) {
        return IntStream.range(0, numberOfPlayers)
                .map(i -> (i + 1) % numberOfPlayers)
                .collect(ArrayList<Integer>::new, ArrayList<Integer>::add, ArrayList<Integer>::addAll);
    }

    /**
     * Convenience constructor, only provided for use in unit tests. Targets should be explicitly specified by users 
     * of this class; use {@link #MultiplayerGameState(List, List)} instead in production code.
     * 
     * @param states
     *            game states; one for each individual player; must contain at least two states and no null values
     */
    MultiplayerGameState(List<OnePlayerGameState> states) {
        this(states, defaultTargets(states.size()));
    }

    /**
     * Constructor.
     * 
     * @param states
     *            game states; one for each individual player; must contain at least two states and no null values
     * @param nextGarbageTargets
     *            per player: the player index that will be targeted for the next garbage line; size must be equal to
     *            the size of states; may not contain null values, and each player's target must be a different player
     * 
     */
    public MultiplayerGameState(@NonNull List<OnePlayerGameState> states, @NonNull List<Integer> nextGarbageTargets) {
        super();

        // check the contents
        if (states.size() < 2) {
            throw new IllegalArgumentException("A multiplayer game must have at least two players; was: "
                    + states.size());
        }
        if (states.contains(null)) {
            throw new NullPointerException("No null values allowed for player states; found one at index: "
                    + states.indexOf(null));
        }
        
        if (nextGarbageTargets.size() != states.size()) {
            throw new IllegalArgumentException("Number of states (" + states.size()
                    + ") does not match the number of targets (" + nextGarbageTargets.size() + ").");
        }
        if (nextGarbageTargets.contains(null)) {
            throw new NullPointerException("No null values allowed for targets; found one at index: "
                    + nextGarbageTargets.indexOf(null));
        }
        if (IntStream.range(0, nextGarbageTargets.size()).anyMatch(i -> Integer.valueOf(i).equals(nextGarbageTargets.get(i)))) {
            throw new IllegalArgumentException("Players may not taget themselvers.");
        }
        if (nextGarbageTargets.stream().anyMatch(i -> i < 0 || states.size() <= i)) {
            throw new IndexOutOfBoundsException("Target out of bounds.");
        }
        
        this.states = states;
        this.nextGarbageTargets = nextGarbageTargets;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isGameOver() {
        // The game is not over as long as there are at least two players still playing.
        int numberOfActivePlayers = 0;
        Iterator<OnePlayerGameState> iterator = states.iterator();
        
        while (numberOfActivePlayers < 2 && iterator.hasNext()) {
            if (!iterator.next().isGameOver()) {
                numberOfActivePlayers++;
            }
        }
        return numberOfActivePlayers < 2;
    }
    
    /** @return the number of players in the game represented by this game state*/
    public int getNumberOfPlayers() {
        return this.states.size();
    }

    /**
     * Returns the state for a given player.
     * 
     * @param i player index, must be at least 0 and less than the number of players
     * @return state
     * @throws IndexOutOfBoundsException if i is out of bounds (less than 0 or at least the number of players)
     */
    public OnePlayerGameState getStateForPlayer(int i) {
        return this.states.get(i);
    }
}
