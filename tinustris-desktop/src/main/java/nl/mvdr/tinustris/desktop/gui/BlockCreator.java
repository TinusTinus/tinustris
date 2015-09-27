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

import javafx.scene.Node;
import nl.mvdr.tinustris.core.model.Block;

/**
 * Creates nodes to represent blocks.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
interface BlockCreator {
    /**
     * Creates a node to represent the given block.
     * 
     * @param xCoordinate
     *            x coordinate on screen
     * @param yCoordinate
     *            y coordinate on screen
     * @param size
     *            size of the block
     * @param block
     *            block to be drawn
     * @param style
     *            style in which to render the block
     * @param numFramesUntilLinesDisappear
     *            the numFramesUntilLinesDisappear property from the game state: the number of frames until the
     *            currently disappearing lines disappear; used for correctly drawing the disappearing block animation
     * @param numFramesSinceLastLock
     *            the numFramesSinceLastLock property from the game state: the number of frames since the last time a
     *            block locked in place; used for correctly drawing the disappearing block animation
     * @return new block
     */
    Node createBlock(double xCoordinate, double yCoordinate, double size, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock);
}
