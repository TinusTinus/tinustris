package nl.mvdr.tinustris.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import nl.mvdr.tinustris.model.GameState;
import nl.mvdr.tinustris.model.Orientation;
import nl.mvdr.tinustris.model.Point;
import nl.mvdr.tinustris.model.Tetromino;

/**
 * Shows the upcoming block.
 * 
 * @author Martijn van de Rijdt
 */
class NextBlockRenderer extends BlockGroupRenderer {
    /** Previous value for the next tetromino field, currently being displayed. Initially null. */
    private Tetromino previousValue = null;
    
    /** {@inheritDoc} */
    @Override
    public void render(GameState gameState) {
        super.render(gameState);
        previousValue = gameState.getNextBlock();
    }
    
    /** {@inheritDoc} */
    @Override
    List<Group> createGroups(GameState gameState) {
        Group nextBlock;
        
        if (previousValue != null && previousValue == gameState.getNextBlock()) {
            // Active block location is unchanged; no need to update.
            nextBlock = null;
        } else {
            // This is the first frame, or the active block's location has changed.
            // Render the group.
            nextBlock = new Group();
            
            Set<Point> points = gameState.getNextBlock().getPoints(Orientation.getDefault());
            for (Point point : points) {
                Rectangle block = createBlock(point.getX(), point.translate(0, -1).getY(), 4, gameState.getNextBlock(), 
                        BlockStyle.NEXT);
                nextBlock.getChildren().add(block);
            }
        }
        
        return Arrays.asList(nextBlock);
    }
}
