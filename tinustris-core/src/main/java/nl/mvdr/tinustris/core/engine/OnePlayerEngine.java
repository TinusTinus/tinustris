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
package nl.mvdr.tinustris.core.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.game.engine.GameEngine;
import nl.mvdr.game.input.InputState;
import nl.mvdr.tinustris.core.configuration.Behavior;
import nl.mvdr.tinustris.core.engine.level.LevelSystem;
import nl.mvdr.tinustris.core.engine.speedcurve.SpeedCurve;
import nl.mvdr.tinustris.core.input.Input;
import nl.mvdr.tinustris.core.model.Action;
import nl.mvdr.tinustris.core.model.Block;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;
import nl.mvdr.tinustris.core.model.Orientation;
import nl.mvdr.tinustris.core.model.Point;
import nl.mvdr.tinustris.core.model.Tetromino;

/**
 * Implementation of {@link GameEngine}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE) // default visibility for unit tests
@ToString
public class OnePlayerEngine implements GameEngine<OnePlayerGameState, Input> {
    /**
     * The number of garbage lines certain to be aligned.
     * 
     * Say this value is 9. The first 9 lines of garbage received by the player are certain to have the gap at the same
     * location (making for easy garbage disposal). The second 9 garbage lines may have their gap in a different
     * location.
     */
    private static final int NUM_ALIGNED_GARBAGE_LINES = 9;

    /**
     * Number of frames the input is ignored while the user is holding down a button.
     * 
     * Say this value is 30 and the user is holding the left button. The active block will now move left once every
     * thirty frames.
     */
    private static final int INPUT_FRAMES = 10;
    
    /** Tetromino generator. */
    @NonNull
    private final Generator<Tetromino> generator;
    /** Speed curve. */
    @NonNull
    private final SpeedCurve curve;
    /** Leveling system. */
    @NonNull
    private final LevelSystem levelSystem;
    /** Gap generator, for use in multiplayer games. */
    @NonNull
    private final Generator<Integer> gapGenerator;
    
    /**
     * Constructor.
     * 
     * @param tetrominoGenerator
     *            tetromino generator
     * @param behavior
     *            behavior
     * @param startLevel
     *            start level
     * @param gapGenerator
     *            gap generator
     */
    public OnePlayerEngine(Generator<Tetromino> tetrominoGenerator, Behavior behavior, int startLevel,
            Generator<Integer> gapGenerator) {
        this(tetrominoGenerator, behavior.getSpeedCurve(), behavior.createLevelSystem(startLevel), gapGenerator);
    }

    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState initGameState() {
        List<Optional<Block>> grid =
            Collections.nCopies(OnePlayerGameState.DEFAULT_WIDTH * OnePlayerGameState.DEFAULT_HEIGHT, Optional.empty());
        OnePlayerGameState gameState = new OnePlayerGameState(grid, OnePlayerGameState.DEFAULT_WIDTH, generator.get(0),
                generator.get(1));
        gameState = gameState.withLevel(this.levelSystem.computeLevel(gameState, gameState));
        return gameState;
    }

    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState<Input>> inputStates) {
        if (inputStates.size() != 1) {
            throw new IllegalArgumentException("Expected 1 input state, got " + inputStates.size());
        }
        
        return computeNextState(previousState, inputStates.get(0));
    }

    /**
     * Computes the next game state based on the previous one and the state of the controls.
     * 
     * Overloaded convenience method.
     * 
     * @param previousState
     *            previous game state
     * @param inputState
     *            input state for this player
     * @return new game state
     */
    private OnePlayerGameState computeNextState(OnePlayerGameState previousState, InputState<Input> inputState) {
        OnePlayerGameState result;
        
        if (!previousState.isGameOver()) {
            
            result = updateInputStateAndCounters(previousState, inputState);

            if (previousState.getNumFramesUntilLinesDisappear() == 1) {
                result = removeLines(result);
            }

            if (!previousState.getActiveTetromino().isPresent() && previousState.getNumFramesUntilLinesDisappear() <= 1
                    && curve.computeARE(previousState) < previousState.getNumFramesSinceLastLock()) {
                result = spawnNextBlock(result);
                result = addGarbageLines(result);
            }

            if (result.getActiveTetromino().isPresent()) {
                List<Action> actions = determineActions(previousState, result, inputState);
                for (Action action : actions) {
                    result = executeAction(result, action);
                }
            }

            result = result.withLevel(this.levelSystem.computeLevel(previousState, result));
            
        } else {
            // game over, no need to update the game state anymore
            result = previousState;
        }
        
        return result;
    }

    /**
     * Determines which actions should be performed.
     * 
     * @param previousState previous game state
     * @param resultState result state
     * @param inputState input state
     * @return actions
     */
    private List<Action> determineActions(OnePlayerGameState previousState, OnePlayerGameState resultState,
            InputState<Input> inputState) {
        List<Action> actions = new ArrayList<>();
        
        // process gravity
        int internalGravity = curve.computeInternalGravity(resultState);
        if (256 / internalGravity <= resultState.getNumFramesSinceLastDownMove()) {
            int cells = Math.round(internalGravity / 256);
            cells = Math.max(cells, 1);
            
            actions.addAll(Collections.nCopies(cells, Action.GRAVITY_DROP));
        }
        
        // process player input
        actions.addAll(
                Stream.of(Input.values())
                    .filter(inputState::isPressed)
                    .filter(input -> previousState.getInputStateHistory().getNumberOfFrames(input) % INPUT_FRAMES == 0)
                    .map(Input::getAction)
                    .sorted()
                    .collect(Collectors.toList()));
        
        // process lock delay
        int lockDelay = curve.computeLockDelay(resultState);
        if (lockDelay < resultState.getNumFramesSinceLastMove()) {
            actions.add(Action.LOCK);
        }
        
        actions.sort(null);
        
        return actions;
    }
    
    /**
     * Returns a new game state based on the given previous state.
     * 
     * The input history is updated with the given input state and the frame counters are updated. All other values
     * are the same as in the given state.
     * 
     * @param previousState previous game state
     * @param inputState input state for the current frame
     * @return game state with updated input history and frame counter, otherwise unchanged
     */
    private OnePlayerGameState updateInputStateAndCounters(OnePlayerGameState previousState, InputState<Input> inputState) {
        return previousState.withInputStateHistory(previousState.getInputStateHistory().next(inputState))
                .withNumFramesSinceLastDownMove(previousState.getNumFramesSinceLastDownMove() + 1)
                .withNumFramesSinceLastLock(previousState.getNumFramesSinceLastLock() + 1)
                .withNumFramesSinceLastMove(previousState.getNumFramesSinceLastMove() + 1)
                .withNumFramesUntilLinesDisappear(Math.max(0, previousState.getNumFramesUntilLinesDisappear() - 1));
    }
    
    /**
     * Executes the given action on the given game state.
     * 
     * @param state base game state
     * @param action action to be performed
     * @return updated game state
     */
    private OnePlayerGameState executeAction(OnePlayerGameState state, Action action) {
        if (log.isTraceEnabled()) {
            log.trace("State before executing action {}: {}", action, state);
        }
        
        OnePlayerGameState result;
        if (action == Action.MOVE_DOWN) {
            result = executeMoveDown(state);
        } else if (action == Action.GRAVITY_DROP) {
            result = executeGravityDrop(state);
        } else if (action == Action.LOCK) {
            result = executeLock(state);
        } else if (action == Action.MOVE_LEFT) {
            result = executeMoveLeft(state);
        } else if (action == Action.MOVE_RIGHT){
            result = executeMoveRight(state);
        } else if (action == Action.HARD_DROP) {
            result = executeInstantDrop(state);
        } else if (action == Action.TURN_LEFT) {
            result = executeTurnLeft(state);
        } else if (action == Action.TURN_RIGHT) {
            result = executeTurnRight(state);
        } else if (action == Action.HOLD) {
            result = executeHold(state);
        } else {
            throw new IllegalArgumentException("Unexpected action: " + action);
        }
        
        if (log.isTraceEnabled()) {
            log.trace("State after executing action {}: {}", action, result);
        }
        
        return result;
    }

    /**
     * Executes the down action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeMoveDown(OnePlayerGameState state) {
        OnePlayerGameState result;
        
        if (state.canMoveDown()) {
            result = moveDown(state);
        } else {
            result = lockBlock(state);
        }
        
        return result;
    }
    
    /**
     * Executes the gravity drop action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeGravityDrop(OnePlayerGameState state) {
        OnePlayerGameState result;
        if (state.canMoveDown()) {
            result = moveDown(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }
    
    /**
     * Executes the lock block action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeLock(OnePlayerGameState state) {
        OnePlayerGameState result;
        if (state.getActiveTetromino().isPresent() && !state.canMoveDown()) {
            result = lockBlock(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }

    /**
     * Moves the current block down one position on the given state.
     * 
     * This method does not check that state.canMoveDown() is true.
     * 
     * @param state game state
     * @return updated state
     */
    private OnePlayerGameState moveDown(OnePlayerGameState state) {
        return state.withCurrentBlockLocation(state.getCurrentBlockLocation().map(p -> p.translate(0, -1)))
                .withNumFramesSinceLastDownMove(0)
                .withNumFramesSinceLastMove(0);
    }

    /**
     * Locks the current block in its current position.
     * 
     * @param state game state; must have an active block
     * @return updated game state
     */
    private OnePlayerGameState lockBlock(OnePlayerGameState state) {
        // Check if there is still an active tetromino; it may have been locked in place by a Move Down or Lock.
        OnePlayerGameState result;
        if (state.getActiveTetromino().isPresent()) {
        
        int width = state.getWidth();
        int height = state.getHeight();
        
        // Update the grid: old grid plus current location of the active block.
        List<Optional<Block>> grid = new ArrayList<>(state.getGrid());
        for (Point point : state.getCurrentActiveBlockPoints()) {
            int index = state.toGridIndex(point);
            grid.set(index, state.getActiveTetromino().map(Tetromino::getBlock));
        }
        grid = Collections.unmodifiableList(grid);
        
        // Check for newly formed lines.
        int linesScored = countLines(width, height, grid);
        log.info("Lines scored: " + linesScored);
        
        // Create the new game state.
        int numFramesUntilLinesDisappear;
        if (0 < linesScored) {
            numFramesUntilLinesDisappear = curve.computeLineClearDelay(state);
        } else {
            numFramesUntilLinesDisappear = 0;
        }
        int lines = state.getLines() + linesScored;

            result = new OnePlayerGameState(grid, width, Optional.empty(), Optional.empty(), Optional.empty(),
                    state.getNext(), 0, 0, 0, state.getInputStateHistory(), state.getBlockCounter(), lines,
                    numFramesUntilLinesDisappear, state.getLevel(), state.getGarbageLines(), state.getTotalGarbage());
        
        if (linesScored != 0 && log.isDebugEnabled()) {
            log.debug(result.toString());
        }
        } else {
            result = state;
        }
        
        return result;
    }

    /**
     * Counts the number of full lines in the grid.
     * 
     * @param width
     *            width of the grid
     * @param height
     *            height of the grid
     * @param grid
     *            list containing the grid
     * @return number of lines; between 0 and 4
     */
    private int countLines(int width, int height, List<Optional<Block>> grid) {
        long count = IntStream.range(0, height - 1)
            .filter(line -> isFullLine(line, width, grid))
            .count();
        // count should be at least 0 and less than 5, so we can safely cast to int
        return (int) count;
    }
    
    /**
     * Checks whether line y is a full line in the grid.
     *
     * @param y
     *            line index
     * @param width
     *            width of the grid
     * @param grid
     *            list containing the grid
     * @return whether this is a full line
     */
    private boolean isFullLine(int y, int width, List<Optional<Block>> grid) {
        return IntStream.range(0, width)
            .mapToObj(x -> grid.get(x + y * width))
            .allMatch(Optional<Block>::isPresent);
    }

    /**
     * Removes any full lines from the grid and drops down the lines above them.
     * 
     * @param width
     *            width of the grid
     * @param height
     *            height of the grid
     * @param grid
     *            list containing the grid
     * @return updated copy of the game state
     */
    private OnePlayerGameState removeLines(OnePlayerGameState state) {
        int width = state.getWidth();
        int height = state.getHeight();
        List<Optional<Block>> grid = new ArrayList<>(state.getGrid());

        // Update the grid by removing all full lines.
        for (int line = height - 1; 0 <= line; line--) {
            if (state.isFullLine(line)) {
                // Line found.
                // Drop all the lines above it down.
                for (int y = line; y != height - 1; y++) {
                    for (int x = 0; x != width; x++) {
                        Optional<Block> block = grid.get(x + (y + 1) * width);
                        grid.set(x + y * width, block);
                    }
                }
                
                // Make the top line empty.
                for (int x = 0; x != width; x++) {
                    grid.set(x + (height - 1) * width, Optional.empty());
                }
            }
        }
        grid = Collections.unmodifiableList(grid);
        
        return state.withGrid(grid);
    }
    
    /**
     * Spawns a new block.
     * 
     * @param state state
     * @return new state
     */
    private OnePlayerGameState spawnNextBlock(OnePlayerGameState state) {
        Tetromino activeBlock = state.getNext();
        Tetromino next = generator.get(state.getBlockCounter() + 2);
        Point location = state.getBlockSpawnLocation();
        Orientation orientation = Orientation.getDefault();
        int blockCounter = state.getBlockCounter() + 1;

        return new OnePlayerGameState(state.getGrid(), state.getWidth(), activeBlock, location, orientation, next, 0,
                state.getNumFramesSinceLastLock(), 0, state.getInputStateHistory(), blockCounter, state.getLines(),
                0, state.getLevel(), state.getGarbageLines(), state.getTotalGarbage());
    }
    
    /**
     * Process any garbage lines.
     * 
     * @param state
     * @return new state
     */
    private OnePlayerGameState addGarbageLines(OnePlayerGameState state) {
        OnePlayerGameState result = state;
        
        for (int i = 0; i != state.getGarbageLines(); i++) {
            result = addGarbageLine(result);
        }
        
        return result;
    }
    
    /**
     * Adds a single garbage line to the given state and decreases the garbage line counter.
     * 
     * @param state
     * @return new state
     */
    private OnePlayerGameState addGarbageLine(OnePlayerGameState state) {
        int gapIndex = state.getTotalGarbage() / NUM_ALIGNED_GARBAGE_LINES;
        int gap = gapGenerator.get(gapIndex).intValue();
        // fill the bottom line with garbage, except for the gap block
        List<Optional<Block>> grid = new ArrayList<>(state.getGrid().size());
        for (int i = 0; i != state.getWidth(); i++) {
            if (i == gap) {
                grid.add(Optional.empty());
            } else {
                grid.add(Optional.of(Block.GARBAGE));
            }
        }
        grid.addAll(state.getGrid().subList(0, state.getGrid().size() - state.getWidth()));
        grid = Collections.unmodifiableList(grid);
        
        return state.withGrid(grid)
                .withGarbageLines(state.getGarbageLines() - 1)
                .withTotalGarbage(state.getTotalGarbage() + 1);
    }

    /**
     * Executes the left action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeMoveLeft(OnePlayerGameState state) {
        OnePlayerGameState result;
        if (state.canMoveLeft()) {
            result = moveLeft(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }

    /**
     * Moves the current block left one position on the given state.
     * 
     * This method does not check that state.canMoveLeft() is true.
     * 
     * @param state game state
     * @return updated state
     */
    private OnePlayerGameState moveLeft(OnePlayerGameState state) {
        return state.withCurrentBlockLocation(state.getCurrentBlockLocation().map(p -> p.translate(-1, 0)))
                .withNumFramesSinceLastMove(0);
    }

    /**
     * Executes the right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeMoveRight(OnePlayerGameState state) {
        OnePlayerGameState result;
        if (state.canMoveRight()) {
            result = moveRight(state);
        } else {
            // do nothing
            result = state;
        }
        return result;
    }

    /**
     * Moves the current block right one position on the given state.
     * 
     * This method does not check that state.canMoveRight() is true.
     * 
     * @param state game state
     * @return updated state
     */
    private OnePlayerGameState moveRight(OnePlayerGameState state) {
        return state.withCurrentBlockLocation(state.getCurrentBlockLocation().map(p -> p.translate(1, 0)))
                .withNumFramesSinceLastMove(0);
    }
    
    /**
     * Executes the instant drop action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeInstantDrop(OnePlayerGameState state) {
        OnePlayerGameState result = state;
        // Check if there is still an active tetromino; it may have been locked in place by a Move Down or Lock.
        if (result.getActiveTetromino().isPresent()) {
            while (result.canMoveDown()) {
                result = moveDown(result);
            }
            result = lockBlock(result);
        }
        return result;
    }
    
    /**
     * Executes the turn left action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeTurnLeft(OnePlayerGameState state) {
        OnePlayerGameState stateAfterTurn = turnLeft(state);
        return fixStateAfterAction(state, stateAfterTurn);
    }

    /**
     * Turns the current active block counter-clockwise.
     * 
     * This method does not check whether the resulting game state is valid!
     * 
     * @param state state
     * @return updated game state (may be invalid)
     */
    private OnePlayerGameState turnLeft(OnePlayerGameState state) {
        return state.withCurrentBlockOrientation(state.getCurrentBlockOrientation().map(Orientation::getNextCounterClockwise))
                .withNumFramesSinceLastMove(0);
    }
    
    /**
     * Executes the turn right action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeTurnRight(OnePlayerGameState state) {
        OnePlayerGameState stateAfterTurn = turnRight(state);
        return fixStateAfterAction(state, stateAfterTurn);
    }
    
    /**
     * Turns the current active block clockwise.
     * 
     * This method does not check whether the resulting game state is valid!
     * 
     * @param state state
     * @return updated game state (may be invalid)
     */
    private OnePlayerGameState turnRight(OnePlayerGameState state) {
        return state.withCurrentBlockOrientation(state.getCurrentBlockOrientation().map(Orientation::getNextClockwise))
                .withNumFramesSinceLastMove(0);
    }

    /**
     * Executes the hold action.
     * 
     * @param state game state
     * @return updated game state
     */
    private OnePlayerGameState executeHold(OnePlayerGameState state) {
        OnePlayerGameState stateAfterHold = hold(state);
        return fixStateAfterAction(state, stateAfterHold);
    }

    /**
     * Performs a hold (swaps active and next tetrominoes).
     * 
     * This method does not check whether the resulting game state is valid! 
     * 
     * @param state state
     * @returnupdated game state (may be invalid)
     */
    private OnePlayerGameState hold(OnePlayerGameState state) {
        return state.withActiveTetromino(Optional.of(state.getNext()))
                .withNext(state.getActiveTetromino().get())
                .withNumFramesSinceLastMove(0);
    }
    
    /**
     * Fixes the state after an action that may have left the game in an invalid state.
     * 
     * @param originalState
     *            original game state
     * @param stateAfterAction
     *            game state after execution of the action; this game state is allowed to be invalid (that is, a game 
     *            over state or a state where the active block is partially or completely out of bounds)
     * @return new game state
     */
    private OnePlayerGameState fixStateAfterAction(OnePlayerGameState originalState,
            OnePlayerGameState stateAfterAction) {
        OnePlayerGameState result;
        if (stateAfterAction.isCurrentBlockWithinBounds() && !stateAfterAction.isTopped()) {
            // no problemo!
            result = stateAfterAction;
        } else {
            // state is not valid
            if (stateAfterAction.canMoveRight()) {
                result = moveRight(stateAfterAction);
            } else if (stateAfterAction.canMoveLeft()) {
                result = moveLeft(stateAfterAction);
            } else {
                // impossible to fix; cancel the action
                result = originalState;
            }
        }
        return result;
    }
}
