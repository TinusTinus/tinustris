package nl.mvdr.tinustris.gui;

import javafx.scene.Group;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.model.GameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link GameOverRenderer}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class GameOverRendererTest {
    /** Test case where the game is not over. */
    @Test
    public void testNotTopped() {
        GameState state = new GameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(false);
        
        renderer.render(state);
        
        Assert.assertFalse(group.isVisible());
    }
    
    /** Test case where the parent is already visibly. */
    @Test
    public void testNotToppedVisible() {
        GameState state = new GameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(true);
        
        renderer.render(state);
        
        Assert.assertTrue(group.isVisible());
    }
    
    /** Test case where the game is over. */
    @Test
    public void testTopped() {
        GameState state = createToppedGameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(false);
        
        renderer.render(state);
        
        Assert.assertTrue(group.isVisible());
    }
    
    /** Test case where the parent is already visibly. */
    @Test
    public void testToppedVisible() {
        GameState state = createToppedGameState();
        GameOverRenderer renderer = createRenderer();
        Group group = new Group(renderer);
        group.setVisible(true);
        
        renderer.render(state);
        
        Assert.assertTrue(group.isVisible());
    }

    /**
     * Creates a new renderer.
     * 
     * @return renderer
     */
    private GameOverRenderer createRenderer() {
        return new GameOverRenderer() {
            /** 
             * Mock implementation which just executes the runnable on the current thread.
             * 
             * @param runnable runnable to be executed
             */
            @Override
            protected void runOnJavaFXThread(Runnable runnable) {
                log.info("Executing runnable: " + runnable);
                runnable.run();
            }
        };
    }
    
    /**
     * Creates a topped game state.
     * 
     * @return game state
     */
    private GameState createToppedGameState() {
        return new GameState() {
            /** Dummy implementation which returns true. */
            @Override
            public boolean isTopped() {
                return true;
            }
        };
    }
}