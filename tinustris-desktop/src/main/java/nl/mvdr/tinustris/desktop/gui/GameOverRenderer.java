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

import javafx.application.Platform;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Component showing whether the game is over. Should be part of an invisible group by default.
 * 
 * @author Martijn van de Rijdt
 */
class GameOverRenderer extends GreenTextLabel implements GameRenderer<OnePlayerGameState> {
    /** Constructor. */
    GameOverRenderer() {
        super("GAME OVER");
    }
    
    /** {@inheritDoc} */
    @Override
    public void render(OnePlayerGameState gameState) {
        if (getParent().isVisible() != gameState.isTopped()) {
            runOnJavaFXThread(() -> getParent().setVisible(gameState.isTopped()));
        }
    }
    
    /**
     * Runs the given runnable on the JavaFX thread.
     * 
     * @param runnable runnable
     */
    // default visibility as an extension point for unit tests
    void runOnJavaFXThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
