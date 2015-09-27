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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.Node;
import nl.mvdr.tinustris.model.OnePlayerGameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Shows the upcoming block.
 * 
 * @author Martijn van de Rijdt
 */
class NextBlockRenderer extends BlockGroupRenderer {
    /** Previous value for the next tetromino field, currently being displayed. Initially empty. */
    private Optional<Tetromino> previousValue;
    
    /**
     * Constructor.
     * 
     * @param blockCreator creator
     */
    NextBlockRenderer(BlockCreator blockCreator) {
        super(blockCreator);
        previousValue = Optional.empty();
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        super.render(gameState);
        previousValue = Optional.of(gameState.getNext());
    }
    
    /** {@inheritDoc} */
    @Override
    List<Optional<Group>> createGroups(OnePlayerGameState gameState) {
        Optional<Group> group;
        
        Tetromino nextBlock = gameState.getNext();
        if (previousValue.filter(t -> t == nextBlock).isPresent()) {
            // Active block location is unchanged; no need to update.
            group = Optional.empty();
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            group = Optional.of(new Group());
            for (Point point : nextBlock.getPoints(Orientation.getDefault())) {
                // for aesthetics, center the tetromino
                if (nextBlock != Tetromino.O) {
                    point = point.translate(0, -1);
                }
                Node node = createBlock(point.getX(), point.getY(), 4, nextBlock.getBlock(), BlockStyle.NEXT,
                        gameState.getNumFramesUntilLinesDisappear(), gameState.getNumFramesSinceLastLock());
                group.get().getChildren().add(node);
            }
        }
        
        return Collections.singletonList(group);
    }
}
