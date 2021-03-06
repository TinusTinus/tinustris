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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Wither;
import nl.mvdr.game.state.GameState;
import nl.mvdr.tinustris.core.input.InputStateHistory;

/**
 * Representation of the game state for a single Tetris player.
 * 
 * @author Martijn van de Rijdt
 */
@EqualsAndHashCode
@Getter
public class OnePlayerGameState implements GameState {
    /** Height of the vanish zone. */
    public static final int VANISH_ZONE_HEIGHT = 2;
    /** Minimum width of a Tetris grid. */
    private static final int MIN_WIDTH = 4;
    /** Minimum height of a Tetris grid. */
    private static final int MIN_HEIGHT = 4 + VANISH_ZONE_HEIGHT;
    /** Default width of a Tetris grid. */
    public static final int DEFAULT_WIDTH = 10;
    /** Default height of a Tetris grid. */
    public static final int DEFAULT_HEIGHT = 22;

    /**
     * The basin of blocks.
     * 
     * Element (i, j) of the grid is represented by grid[i * width + j]. If the element is empty, that space in the grid
     * is empty; otherwise it contains a block. The specific Block value may affect how it is represented graphically.
     * 
     * The current tetromino (that the payer is still controlling as it falls) is not contained in the grid.
     * 
     * Note that the top two (see VANISH_ZONE_HEIGHT) rows of the grid should not be visible.
     * 
     * The contents of this list may not be modified; in fact it should preferably be an unmodifiable list.
     */
    @NonNull
    @Wither
    private final List<Optional<Block>> grid;
    /** Width of the grid. */
    private final int width;
    /** Active tetromino. May be empty, if the game is in the process of showing a "cutscene" (like a disappearing line). */
    @NonNull
    @Wither
    private final Optional<Tetromino> activeTetromino;
    /** The active tetromino's location. May be empty if activeTetromino is empty as well. */
    @NonNull
    @Wither
    private final Optional<Point> currentBlockLocation;
    /** The current block's orientation. May be empty if activeTetromino is empty as well. */
    @NonNull
    @Wither
    private final Optional<Orientation> currentBlockOrientation;
    /** The next tetromino. */
    @NonNull
    @Wither
    private final Tetromino next;
    /** The number of frames since the last time the active block was moved down. */
    @Wither
    private final int numFramesSinceLastDownMove;
    /** The number of frames since the last time the active block was locked in place. */
    @Wither
    private final int numFramesSinceLastLock;
    /**
     * The number of frames since the last time the active block was moved (through gravity or player action), or
     * rotated.
     */
    @Wither
    private final int numFramesSinceLastMove;
    /** Input state history, up until and including the current frame. */
    @NonNull
    @Wither
    private final InputStateHistory inputStateHistory;
    /** Block counter. Equals the number of blocks that have been dropped. */
    private final int blockCounter;
    /** Number of lines that have been scored in this game. */
    @Wither
    private final int lines;
    /**
     * The number of frames until the current lines disappear.
     * 
     * After the player completes a line, the game pauses for a short time while the line stays on a the screen for a
     * short while before it disappears and the next active block appears. This variable indicates the number of frames
     * that still remain. It is zero if there are currently no full lines in the grid.
     */
    @Wither
    private final int numFramesUntilLinesDisappear;
    /** Current level, which may determine the results of a speed curve. */
    @Wither
    private final int level;
    /** Garbage lines to be added to this game at the first possible opportunity. */
    @Wither
    private final int garbageLines;
    /** Total number of garbage lines received in this game so far. */
    @Wither
    private final int totalGarbage;

    /**
     * Constructor for a (new) game with a completely empty grid of default size, no active block and I as the next
     * block. This constructor is not particularly useful for production code, but may be useful in unit tests.
     */
    public OnePlayerGameState() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor for a (new) game with a completely empty grid, no active block and I as the next block. This
     * constructor is not particularly useful for production code, but may be useful in unit tests.
     * 
     * @param width
     *            width of the basin, should be at least MIN_WIDTH
     * @param height
     *            height of the basin, should be at least MIN_HEIGHT
     */
    OnePlayerGameState(int width, int height) {
        super();

        checkWidth(width);
        checkHeight(height);

        this.width = width;
        this.grid = Collections.nCopies(width * height, Optional.empty());
        this.activeTetromino = Optional.empty();
        this.currentBlockLocation = Optional.empty();
        this.currentBlockOrientation = Optional.empty();
        this.next = Tetromino.I;
        this.numFramesSinceLastDownMove = 0;
        this.numFramesSinceLastLock = 0;
        this.numFramesSinceLastMove = 0;
        this.inputStateHistory = InputStateHistory.NEW;
        this.blockCounter = 0;
        this.lines = 0;
        this.numFramesUntilLinesDisappear = 0;
        this.level = 0;
        this.garbageLines = 0;
        this.totalGarbage = 0;
    }
    
    /**
     * Constructor.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param activeTetromino
     *            current block
     * @param currentBlockLocation
     *            location of the current block
     * @param currentBlockOrientation
     *            orientation of the current block
     * @param next
     *            next tetromino
     */
    public OnePlayerGameState(@NonNull List<Optional<Block>> grid, int width, @NonNull Optional<Tetromino> activeTetromino,
            @NonNull Optional<Point> currentBlockLocation, @NonNull Optional<Orientation> currentBlockOrientation,
            @NonNull Tetromino next) {
        this(grid, width, activeTetromino, currentBlockLocation, currentBlockOrientation, next, 0, 0, 0,
                InputStateHistory.NEW, 0, 0, 0, 0, 0, 0);
    }
    
    /**
     * Constructor.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param activeTetromino
     *            current block
     * @param currentBlockLocation
     *            location of the current block
     * @param currentBlockOrientation
     *            orientation of the current block
     * @param next
     *            next tetromino
     * @param numFramesSinceLastTick
     *            number of frames since the last tick
     * @param numFramesSinceLastLock
     *            number of frames since the last time the active block was locked in place
     * @param numFramesSinceLastMove
     *            number of frames since the last time the active block was moved (through gravity or player action), or
     *            rotated
     * @param inputStateHistory
     *            history of the input state; includes the current frame
     * @param blockCounter
     *            number of blocks that have been dropped
     * @param lines
     *            number of lines that have been scored in this game
     * @param numFramesUntilLinesDisappear
     *            number of remaining frames until lines disappear
     * @param level
     *            level
     * @param garbageLines
     *            number of garbage lines to be added to this game
     * @param totalGarbage
     *            total number of garbage lines received in this game so far
     */
    public OnePlayerGameState(@NonNull List<Optional<Block>> grid, int width, @NonNull Optional<Tetromino> activeTetromino,
            @NonNull Optional<Point> currentBlockLocation, @NonNull Optional<Orientation> currentBlockOrientation,
            @NonNull Tetromino next, int numFramesSinceLastTick, int numFramesSinceLastLock,
            int numFramesSinceLastMove, @NonNull InputStateHistory inputStateHistory, int blockCounter, int lines,
            int numFramesUntilLinesDisappear, int level, int garbageLines, int totalGarbage) {
        super();

        checkWidth(width);
        if (grid.size() % width != 0) {
            throw new IllegalArgumentException("Unexpected grid size, should be a multiple of width " + width
                    + ", was: " + grid.size());
        }

        this.grid = grid;
        this.width = width;

        checkHeight(getHeight());

        this.activeTetromino = activeTetromino;
        this.currentBlockLocation = currentBlockLocation;
        this.currentBlockOrientation = currentBlockOrientation;
        this.next = next;
        this.numFramesSinceLastDownMove = numFramesSinceLastTick;
        this.numFramesSinceLastLock = numFramesSinceLastLock;
        this.numFramesSinceLastMove = numFramesSinceLastMove;
        this.inputStateHistory = inputStateHistory;
        this.blockCounter = blockCounter;
        this.lines = lines;
        this.numFramesUntilLinesDisappear = numFramesUntilLinesDisappear;
        this.level = level;
        this.garbageLines = garbageLines;
        this.totalGarbage = totalGarbage;
    }
    
    /**
     * Constructor.
     * 
     * If activeTetromino is present, it is spawned at the default spawn location.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param activeTetromino
     *            current block
     * @param next
     *            next tetromino
     */
    public OnePlayerGameState(@NonNull List<Optional<Block>> grid, int width, Optional<Tetromino> activeTetromino, @NonNull Tetromino next) {
        this(grid, width, activeTetromino, activeTetromino.map(t -> getBlockSpawnLocation(width,
                computeHeight(grid, width))), Optional.of(Orientation.getDefault()), next);
    }
    
    /**
     * Convenience constructor.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param activeTetromino
     *            current block
     * @param currentBlockLocation
     *            location of the current block
     * @param currentBlockOrientation
     *            orientation of the current block
     * @param next
     *            next tetromino
     * @param numFramesSinceLastTick
     *            number of frames since the last tick
     * @param numFramesSinceLastLock
     *            number of frames since the last time the active block was locked in place
     * @param numFramesSinceLastMove
     *            number of frames since the last time the active block was moved (through gravity or player action), or
     *            rotated
     * @param inputStateHistory
     *            history of the input state; includes the current frame
     * @param blockCounter
     *            number of blocks that have been dropped
     * @param lines
     *            number of lines that have been scored in this game
     * @param numFramesUntilLinesDisappear
     *            number of remaining frames until lines disappear
     * @param level
     *            level
     * @param garbageLines
     *            number of garbage lines to be added to this game
     * @param totalGarbage
     *            total number of garbage lines received in this game so far
     */
    public OnePlayerGameState(@NonNull List<Optional<Block>> grid, int width, @NonNull Tetromino activeTetromino,
            @NonNull Point currentBlockLocation, @NonNull Orientation currentBlockOrientation, @NonNull Tetromino next,
            int numFramesSinceLastTick, int numFramesSinceLastLock, int numFramesSinceLastMove,
            @NonNull InputStateHistory inputStateHistory, int blockCounter, int lines,
            int numFramesUntilLinesDisappear, int level, int garbageLines, int totalGarbage) {
        this(grid, width, Optional.of(activeTetromino), Optional.of(currentBlockLocation), Optional
                .of(currentBlockOrientation), next, numFramesSinceLastTick, numFramesSinceLastLock,
                numFramesSinceLastMove, inputStateHistory, blockCounter, lines, numFramesUntilLinesDisappear, level,
                garbageLines, totalGarbage);
    }
    
    /**
     * Covenience constructor.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param activeTetromino
     *            current block
     * @param currentBlockLocation
     *            location of the current block
     * @param currentBlockOrientation
     *            orientation of the current block
     * @param next
     *            next tetromino
     */
    public OnePlayerGameState(@NonNull List<Optional<Block>> grid, int width, @NonNull Tetromino activeTetromino,
            @NonNull Point currentBlockLocation, @NonNull Orientation currentBlockOrientation,
            @NonNull Tetromino next) {
        this(grid, width, Optional.of(activeTetromino), Optional.of(currentBlockLocation),
                Optional.of(currentBlockOrientation), next);
    }
    
    /**
     * Convenience constructor.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param activeTetromino
     *            current block
     * @param next
     *            next tetromino
     */
    public OnePlayerGameState(@NonNull List<Optional<Block>> grid, int width, @NonNull Tetromino activeTetromino, @NonNull Tetromino next) {
        this(grid, width, Optional.of(activeTetromino), next);
    }

    /**
     * Computes the spawn location for new blocks.
     * 
     * @param width width of the grid
     * @param height height of the grid
     * @return spawn location
     */
    private static Point getBlockSpawnLocation(int width, int height) {
        return new Point(width / 2 - 2, height - 4 - VANISH_ZONE_HEIGHT);
    }
    
    /**
     * Computes the spawn location for new blocks.
     * 
     * @return spawn location
     */
    public Point getBlockSpawnLocation() {
        return getBlockSpawnLocation(width, getHeight());
    }
    
    /**
     * Checks that the given number is at least the minimum grid width.
     * 
     * @param w
     *            value to be checked
     * @throws IllegalArgumentException
     *             in case the value is incorrect
     */
    private void checkWidth(int w) {
        if (w < MIN_WIDTH) {
            throw new IllegalArgumentException("width should be at least " + MIN_WIDTH + ", was: " + w);
        }
    }

    /**
     * Checks that the given number is at least the minimum grid height.
     * 
     * @param height
     *            value to be checked
     * @throws IllegalArgumentException
     *             in case the value is incorrect
     */
    private void checkHeight(int height) {
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException("height should be at least " + MIN_HEIGHT + ", was: " + height);
        }
    }

    /** @return the grid's height */
    public int getHeight() {
        return computeHeight(this.grid, this.width);
    }
    
    /**
     * Computes the grid's height.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @return the grid's height
     */
    private static int computeHeight(List<Optional<Block>> grid, int width) {
        if (width == 0) {
            throw new IllegalArgumentException("Width must be more than zero.");
        }
        return grid.size() / width;
    }

    /**
     * Returns the block in the grid at index i, j. Does not take the player's currently controlled block into account.
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @return block at x, y
     */
    public Optional<Block> getBlock(int x, int y) {
        if (!isWithinBounds(x, y)) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Point (%s, %s) is not within bounds: x should be between %s and %s, y between %s and %s.",
                            Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(0), Integer.valueOf(width),
                            Integer.valueOf(0), Integer.valueOf(getHeight())));
        }
        
        return this.grid.get(toGridIndex(x, y));
    }
    
    /**
     * Returns the block in the grid at the given point. Does not take the player's currently controlled block into account.
     * 
     * @param point point in the grid
     * @return block at x, y
     */
    private Optional<Block> getBlock(Point point) {
        return getBlock(point.getX(), point.getY());
    }

    /**
     * Translates the given coordinates to an index in the grid.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return index in the grid
     */
    private int toGridIndex(int x, int y) {
        return x + y * this.width;
    }
    
    /**
     * Translates the given coordinates to an index in the grid.
     * 
     * @param point point
     * @return index in the grid
     */
    public int toGridIndex(Point point) {
        return toGridIndex(point.getX(), point.getY());
    }
    
    /**
     * Indicates whether the game is topped, that is, whether the game is over.
     * 
     * The game is topped out if the currently active block overlaps with blocks already in the grid, or there are
     * blocks in the vanish zone.
     * 
     * See the <a href="http://tetris.wikia.com/wiki/Top_out">Tetris Wiki</a> for more details.
     * 
     * @return whether the game is topped
     */
    public boolean isTopped() {
        return vanishZoneContainsBlock() || containsBlock(getCurrentActiveBlockPoints());
    }
    
    /** {@inheritDoc} */
    @Override
    public boolean isGameOver() {
        return isTopped();
    }
    
    /**
     * Determines whether there is a block in the vanish zone.
     * 
     * @return whether the grid's vanish zone contains at least one block
     */
    private boolean vanishZoneContainsBlock() {
        int height = getHeight();
        return IntStream.range(0, width)
            .anyMatch(x -> IntStream.range(height - VANISH_ZONE_HEIGHT, height)
                .anyMatch(y -> getBlock(x, y).isPresent()));
    }
    
    /**
     * Indicates whether any of the given points in the grid contain a block.
     * 
     * Only the grid is inspected, not the current active block.
     * 
     * @param points points to be checked
     * @return whether any of the points contain a block
     */
    private boolean containsBlock(Set<Point> points) {
        return points.stream()
            .map(this::getBlock)
            .anyMatch(Optional<Block>::isPresent);
    }
    
    /**
     * Computes the points occupied by the currently active block.
     * 
     * @return points occupied by the currently active block; empty set if there is no currently active block
     */
    public Set<Point> getCurrentActiveBlockPoints() {
        return getBlockPoints(activeTetromino, currentBlockOrientation, currentBlockLocation);
    }
    
    /**
     * Computes the points occupied by the given tetromino.
     * 
     * @param tetromino tetromino
     * @param orientation orientation of the tetromino; must be present if tetromino is present
     * @param location the tetromino's location; must be present if tetromino is present
     * @return points occupied by the given tetromino; empty set if the given tetromino is null
     */
    private Set<Point> getBlockPoints(Optional<Tetromino> tetromino, Optional<Orientation> orientation, Optional<Point> location) {
        return tetromino.map(t -> t.getPoints(orientation.get()))
            .map(set -> translate(set, location.get().getX(), location.get().getY()))
            .orElse(Collections.emptySet());
    }
    
    /**
     * Computes the points occupied by the currently active block's ghost.
     * 
     * @return points occupied by the currently active block; empty set if there is no currently active block
     */
    public Set<Point> getGhostPoints() {
        return getBlockPoints(activeTetromino, currentBlockOrientation, getGhostLocation());
    }
    
    /**
     * Indicates whether the current active block can be moved one position to the left.
     * 
     * @return whether the current active block can be moved left
     * @throws IllegalStateException
     *             if there is no active block
     */
    public boolean canMoveLeft() {
        return canMove(-1, 0);
    }
    
    /**
     * Indicates whether the current active block can be moved one position to the right.
     * 
     * @return whether the current active block can be moved right
     * @throws IllegalStateException if there is no active block
     */
    public boolean canMoveRight() {
        return canMove(1, 0);
    }
    
    /**
     * Indicates whether the current active block can be moved one position down.
     * 
     * @return whether the current active block can be moved down
     * @throws IllegalStateException if there is no active block
     */
    public boolean canMoveDown() {
        return canMove(0, -1);
    }

    /**
     * Indicates if the current active block can be moved according to the given translation.
     * 
     * @param deltaX
     *            amount the x coordinate should be moved
     * @param deltaY
     *            amount the y coordinate should be moved
     * @return whether the current active block can be moved along the given translation
     * @throws IllegalStateException
     *             if there is no active block
     */
    private boolean canMove(int deltaX, int deltaY) {
        activeTetromino.orElseThrow(() -> new IllegalStateException("no active block"));
        
        Set<Point> newPosition = translate(getCurrentActiveBlockPoints(), deltaX, deltaY);
        return isWithinBounds(newPosition) && !containsBlock(newPosition);
    }
    
    /** 
     * Translates all points with the given delta.
     * 
     * @param points points to be translated
     * @param deltaX amount to be added to x values
     * @param deltaY amount to be added to y values
     * @return 
     */
    private Set<Point> translate(Set<Point> points, int deltaX, int deltaY) {
        return points.stream()
            .map(point -> point.translate(deltaX, deltaY))
            .collect(Collectors.toSet());
    }
    
    /**
     * Determines whether the given point is within the bounds of the grid.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return whether the point is within bounds
     */
    private boolean isWithinBounds(int x, int y) {
        boolean result = 0 <= x;
        result = result && x < width;
        result = result && 0 <= y;
        result = result && y < getHeight();
        return result;
    }
    
    /**
     * Determines whether the given point is within the bounds of the grid.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return whether the point is within bounds
     */
    private boolean isWithinBounds(Point point) {
        return isWithinBounds(point.getX(), point.getY());
    }
    
    /**
     * Determines whether the given points are all within the bounds of the grid.
     * 
     * @param points points
     * @return whether all points are within bounds
     */
    private boolean isWithinBounds(Set<Point> points) {
        return points.stream().allMatch(this::isWithinBounds);
    }
    
    /**
     * Indicates whether the active block is currently within bounds. Note that if this method returns false, this is
     * actually not a valid game state!
     * 
     * @return whether the current block is within bounds.
     */
    public boolean isCurrentBlockWithinBounds() {
        return isWithinBounds(getCurrentActiveBlockPoints());
    }
    
    /**
     * Gives the location of the ghost, or null if there is no currently selected block.
     * 
     * The ghost location is the location where the block would end up if it were dropped straight down from its current
     * position.
     * 
     * @return ghost location
     */
    public Optional<Point> getGhostLocation() {
        return currentBlockLocation.map(location -> {
            int deltaY = 0;
            while (canMove(0, deltaY - 1)) {
                deltaY = deltaY - 1;
            }
            return location.translate(0, deltaY);
        });
    }
    
    /**
     * Computes if the line is filled with (non-null) tetrominoes.
     * 
     * @param y line number
     * @return whether the given line is filled
     */
    public boolean isFullLine(int y) {
        return IntStream.range(0, width)
            .mapToObj(x -> getBlock(x, y))
            .allMatch(Optional<Block>::isPresent);
    }
    
    /**
     * Creates an ascii representation of the grid.
     * 
     * @return string representation of the grid
     */
    private String gridToAscii() {
        StringBuilder result = new StringBuilder();
        
        Set<Point> currentBlockPoints = getCurrentActiveBlockPoints();
        Set<Point> ghostPoints = getGhostPoints();

        for (int y = getHeight() - 1; y != -1; y--) {
            result.append('|');
            for (int x = 0; x != width; x++) {
                if (currentBlockPoints.contains(new Point(x, y))) {
                    result.append(activeTetromino.get());
                } else if (ghostPoints.contains(new Point(x, y))) {
                    result.append('_');
                } else {
                    result.append(getBlock(x, y)
                            .map(Block::toString)
                            .orElse(" "));
                }
            }
            result.append("|\n");
        }
        
        result.append('+');
        for (int i = 0; i != width; i++) {
            result.append('-');
        }
        result.append("+");
        
        return result.toString();
    }
    
    /** 
     * {@inheritDoc} 
     * 
     * Note that this implementation contains newlines.
     */
    @Override
    public String toString() {
        return "GameState (width=" + width + ", currentBlock=" + activeTetromino + ", currentBlockLocation="
                + currentBlockLocation + ", currentBlockOrientation = " + currentBlockOrientation + ", nextBlock="
                + next + ", numFramesSinceLastDownMove = " + numFramesSinceLastDownMove + ", numFramesSinceLastLock = "
                + numFramesSinceLastLock + ", numFramesSinceLastMove = " + numFramesSinceLastMove
                + ", inputStateHistory=" + inputStateHistory + ", blockCounter = " + blockCounter + ", lines = "
                + lines + ", numFramesUntilLinesDisappear = " + numFramesUntilLinesDisappear + ", level = " + level
                + ", garbageLines = " + garbageLines + ", totalGarbage = " + totalGarbage + ", grid=\n" + gridToAscii()
                + ")";
    }
}
