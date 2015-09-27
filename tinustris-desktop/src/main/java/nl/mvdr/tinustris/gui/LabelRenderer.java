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
package nl.mvdr.tinustris.gui;

import javafx.application.Platform;
import lombok.NonNull;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.game.state.GameState;

/**
 * Label containing (part of) the game state.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
abstract class LabelRenderer<S extends GameState> extends GreenTextLabel implements GameRenderer<S> {
    /** {@inheritDoc} */
    @Override
    public void render(@NonNull S gameState) {
        final String newText = toText(gameState);
        if (!newText.equals(getText())) {
            runOnJavaFXThread(() -> setText(newText));
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
    
    /**
     * Creates the text for this label based on the given game state.
     * 
     * @param state game state to be represented
     * @return string representation of the given state; may not be null
     */
    protected abstract String toText(S state);
}
