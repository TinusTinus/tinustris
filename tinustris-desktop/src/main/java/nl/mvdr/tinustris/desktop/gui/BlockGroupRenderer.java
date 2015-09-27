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
package nl.mvdr.tinustris.desktop.gui;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.tinustris.model.Block;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * JavaFX node that contains a group of blocks.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
abstract class BlockGroupRenderer extends Group implements GameRenderer<OnePlayerGameState> {
    /** Size of a tetromino block. */
    static final int BLOCK_SIZE = 30;
    
    /** Block creator. */
    private final BlockCreator blockCreator;
    
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull OnePlayerGameState gameState) {
        final List<Optional<Group>> groups = createGroups(gameState);
        
        if (groups.stream().anyMatch(value -> value != null)) {
            // (re-)render
            runOnJavaFXThread(() -> update(groups));
        }
    }
    
    /**
     * Creates all groups of blocks based on the given game state. In case a group would be equal to the group already
     * rendered, an empty value may be inserted into the list. In this case the old group is not rerendered.
     * 
     * @param gameState new game state
     * @return list of groups; may contain null values
     */
    abstract List<Optional<Group>> createGroups(OnePlayerGameState gameState);
    
    /**
     * Runs the given runnable on the JavaFX thread.
     * 
     * @param runnable
     *            runnable
     */
    // Default visibility as an extension point for unit tests.
    void runOnJavaFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
    
    /**
     * Updates the view. This method must be called from the JavaFX thread.
     * 
     * @param groups
     *            list of groups; elements may be null, in which case the corresponding child group is not updated
     */
    private void update(List<Optional<Group>> groups) {
        if (getChildren().isEmpty()) {
            // first frame
            groups.stream()
                .map(Optional<Group>::get)
                .forEach(group -> getChildren().add(group));
        } else {
            // update
            IntStream.range(0, groups.size()).forEach(i -> 
                groups.get(i).ifPresent(group -> getChildren().set(i, group))
            );
        }
    }

    /**
     * Creates a block.
     * 
     * @param x
     *            x coordinate in the grid
     * @param y
     *            y coordinate in the grid
     * @param height
     *            height of the (visible part of the) grid
     * @param block
     *            block to be drawn
     * @param style
     *            style in which to render the block
     * @param numFramesUntilLinesDisappear
     *            the numFramesUntilLinesDisappear property from the game state
     * @param numFramesSinceLastLock
     *            the numFramesSinceLastLock property from the game state
     * @return new block
     */
    Node createBlock(int x, int y, int height, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        int xCoordinate = x * BLOCK_SIZE;
        int yCoordinate = (height - y - 1) * BLOCK_SIZE;
        
        return blockCreator.createBlock(xCoordinate, yCoordinate, BLOCK_SIZE, block, style,
                numFramesUntilLinesDisappear, numFramesSinceLastLock);
    }
}
