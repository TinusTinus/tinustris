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

import javafx.scene.shape.Box;
import nl.mvdr.tinustris.core.model.Block;

/**
 * Implementation of {@link BlockCreator}, which creates 2D Rectangles.
 * 
 * @author Martijn van de Rijdt
 */
class BoxBlockCreator implements BlockCreator {
    /** {@inheritDoc} */
    @Override
    public Box createBlock(double xCoordinate, double yCoordinate, double size, Block block, BlockStyle style,
            int numFramesUntilLinesDisappear, int numFramesSinceLastLock) {
        Box result = new Box(size - 2, size - 2, size - 2);
        result.setTranslateX(xCoordinate + size / 2);
        result.setTranslateY(yCoordinate + size / 2);

        style.apply(result, block, numFramesUntilLinesDisappear, numFramesSinceLastLock);

        return result;
    }
}
