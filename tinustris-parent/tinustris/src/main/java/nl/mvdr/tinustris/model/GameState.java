package nl.mvdr.tinustris.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * Representation of the game state for a single Tetris player.
 * 
 * @author Martijn van de Rijdt
 */
@EqualsAndHashCode
@Getter
public class GameState {
    /** Minimum width of a Tetris grid. */
    private static final int MIN_WIDTH = 4;
    /** Minimum height of a Tetris grid. */
    private static final int MIN_HEIGHT = 6;
    /** Default width of a Tetris grid. */
    private static final int DEFAULT_WIDTH = 10;
    /** Default height of a Tetris grid. */
    private static final int DEFAULT_HEIGHT = 22;

    /**
     * The basin of blocks.
     * 
     * Element (i, j) of the grid is represented by grid[i * width + j]. If the element is null, that space in the grid
     * is empty; otherwise it contains a block. The specific Tetromino value indicates the tetromino that the block was
     * originally a part of, which may affect how it is represented graphically.
     * 
     * The current block (that the payer is still controlling as it falls) is not contained in the grid.
     * 
     * Note that the top two rows of the grid should not be visible.
     * 
     * The value of this field is not modified; in fact it should preferably be an unmodifiable list.
     */
    @NonNull
    private final List<Tetromino> grid;
    /** Width of the grid. */
    private final int width;
    /** Current block. May be null, if the game is in the process of showing a cutscene (like a disappearing line). */
    private final Tetromino currentBlock;
    /** The current block's location. May be null if currentBlock is null as well. */
    private final Point currentBlockLocation;
    /** The current block's orientation. May be null if currentBlock is null as well. */
    private final Orientation currentBlockOrientation;
    /** The next block in line. */
    @NonNull
    // TODO replace by a queue of blocks, in case we want to be able to display multiple "next" blocks
    private final Tetromino nextBlock;

    /** Constructor for a (new) game with a completely empty grid of default size. */
    public GameState() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor for a (new) game with a completely empty grid.
     * 
     * @param width
     *            width of the basin, should be at least 1
     * @param height
     *            height of the basin, should be at least 3
     */
    public GameState(int width, int height) {
        super();

        checkWidth(width);
        checkHeight(height);

        this.width = width;
        ArrayList<Tetromino> tempGrid = new ArrayList<>(width * height);
        while (tempGrid.size() != width * height) {
            tempGrid.add(null);
        }
        this.grid = Collections.unmodifiableList(tempGrid);
        this.currentBlock = null;
        this.currentBlockLocation = null;
        this.currentBlockOrientation = null;
        this.nextBlock = Tetromino.I; // TODO determine randomly?
    }
    
    /**
     * Constructor.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param currentBlock
     *            current block
     * @param currentBlockLocation
     *            location of the current block
     * @param currentBlockOrientation
     *            orientation of the current block
     * @param nextBlock
     *            next block
     */
    public GameState(@NonNull List<Tetromino> grid, int width, Tetromino currentBlock, Point currentBlockLocation,
            Orientation currentBlockOrientation, @NonNull Tetromino nextBlock) {
        super();

        checkWidth(width);
        if (grid.size() % width != 0) {
            throw new IllegalArgumentException("Unexpected grid size, should be a multiple of width " + width
                    + ", was: " + grid.size());
        }

        this.grid = grid;
        this.width = width;

        checkHeight(getHeight());

        this.currentBlock = currentBlock;
        this.currentBlockLocation = currentBlockLocation;
        this.currentBlockOrientation = currentBlockOrientation;
        this.nextBlock = nextBlock;
    }
    
    /**
     * Constructor.
     * 
     * If currentBlock is not null, it is spawned at the default spawn location.
     * 
     * @param grid
     *            grid
     * @param width
     *            width of the grid
     * @param currentBlock
     *            current block
     * @param nextBlock
     *            next block
     */
    public GameState(@NonNull List<Tetromino> grid, int width, Tetromino currentBlock, @NonNull Tetromino nextBlock) {
        this(grid, width, currentBlock, currentBlock == null ? null : new Point(width / 2 - 2, computeHeight(grid,
                width) - 6), Orientation.getDefault(), nextBlock);
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
     * @param grid grid
     * @param width width of the grid
     */
    private static int computeHeight(List<Tetromino> grid, int width) {
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
     * @return block at x, y; null if there is no block there
     */
    public Tetromino getBlock(int x, int y) {
        if (x < 0 || getWidth() <= x) {
            throw new IndexOutOfBoundsException(String.format(
                    "x coordinate in (%s, %s) is out of bounds, should be between 0 and %s.",
                    Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(width)));
        }
        if (y < 0 || getHeight() <= y) {
            throw new IndexOutOfBoundsException(String.format(
                    "y coordinate in (%s, %s) is out of bounds, should be between 0 and %s.",
                    Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(getHeight())));
        }

        return this.grid.get(x + y * this.width);
    }
    
    /**
     * Indicates whether the game is topped, that is, the game is over.
     * 
     * The game is topped out in case the currently active block overlaps with blocks already in the grid, and when
     * there are blocks in the vanish zone (the top two rows).
     * 
     * See the <a href="http://tetris.wikia.com/wiki/Top_out">Tetris Wiki</a> for more details.
     * 
     * @return whether the game is topped
     */
    public boolean isTopped() {
        return isBlockInVanishZone() || containsBlock(getCurrentActiveBlockPoints());
    }
    
    /**
     * Determines whether there is a block in the vanish zone.
     * 
     * @return whether the grid contains at least one block in the vanish zone
     */
    private boolean isBlockInVanishZone() {
        boolean result = false;
        int height = getHeight();
        for (int x = 0; !result && x != width; x++) {
            for (int y = height - 2; !result && y != height; y++) {
                result = result || getBlock(x, y) != null;
            }
        }
        return result;
    }
    
    /**
     * Indicates whether any of the given points in the grid contain a non-null block.
     * 
     * Only the grid is inspected, not the current active block.
     * 
     * @param points points to be checked
     * @return whether any of the points contain a block
     */
    private boolean containsBlock(Set<Point> points) {
        boolean result = false;
        for (Point point: points) {
            result = result || getBlock(point.getX(), point.getY()) != null;
        }
        return result;
    }
    
    /**
     * Computes the points occupied by the currently active block.
     * 
     * @return points occupied by the currently active block; empty set if there is no currently active block
     */
    private Set<Point> getCurrentActiveBlockPoints() {
        Set<Point> result;
        if (currentBlock == null) {
            result = Collections.emptySet();
        } else {
            result = new HashSet<>(4);
            for (Point point:  currentBlock.getPoints(currentBlockOrientation)) {
                result.add(
                    new Point(currentBlockLocation.getX() + point.getX(), currentBlockLocation.getY() + point.getY()));
            }
        }
        return result;
    }
    
    /**
     * Indicates whether the current active block can be moved one position to the left.
     * 
     * @return whether the current active block can be moved left
     * @throws IllegalStateException if there is no active block
     */
    public boolean canMoveLeft() {
        return false; // TODO
    }
    
    /**
     * Indicates whether the current active block can be moved one position to the right.
     * 
     * @return whether the current active block can be moved right
     * @throws IllegalStateException if there is no active block
     */
    public boolean canMoveRight() {
        return false; // TODO
    }
    
    /**
     * Indicates whether the current active block can be moved one position down.
     * 
     * @return whether the current active block can be moved down
     * @throws IllegalStateException if there is no active block
     */
    public boolean canMoveDown() {
        return false; // TODO
    }
    
    /**
     * Creates an ascii representation of the grid.
     * 
     * @return string representation of the grid
     */
    private String gridToAscii() {
        StringBuilder result = new StringBuilder();
        
        Set<Point> currentBlockPoints = getCurrentActiveBlockPoints();
        
        for (int y = getHeight() - 1; y != -1; y--) {
            result.append("|");
            for (int x = 0; x != width; x++) {
                if (currentBlockPoints.contains(new Point(x, y))) {
                    result.append(currentBlock);
                } else {
                    Tetromino block = getBlock(x, y);
                    if (block == null) {
                        result.append(" ");
                    } else {
                        result.append(block);
                    }
                }
            }
            result.append("|\n");
        }
        
        result.append("+");
        for (int i = 0; i != width; i++) {
            result.append("-");
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
        return "GameState (width=" + width + ", currentBlock=" + currentBlock + ", currentBlockLocation="
                + currentBlockLocation + ", currentBlockOrientation = " + currentBlockOrientation + ", nextBlock="
                + nextBlock + ", grid=\n" + gridToAscii() + ")";
    }
}
