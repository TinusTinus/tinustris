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
package nl.mvdr.tinustris.core.configuration;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Game configuration.
 * 
 * Sensible defaults have been included for all configuration properties, using default methods.
 * 
 * @author Martijn van de Rijdt
 * 
 * @param <T> type of graphics configuration
 */
@FunctionalInterface
public interface Configuration<T extends GraphicsConfiguration> {
    /** 
     * Configuration for each of the players in this game. Should contain at least one value.
     * 
     * @return configurations
     */
    default List<PlayerConfiguration> getPlayerConfigurations() {
        // default configuration with an empty player name
        return Collections.singletonList((PlayerConfiguration) () -> "");
    }
    
    /**
     * Graphical style of the blocks in the grid.
     * 
     * @return style
     */
    T getGraphicsStyle();
    
    /**
     * Specification of the behavior of the actual gameplay.
     * 
     * @return behavior definition
     */
    default Behavior getBehavior() {
        return Behavior.defaultBehavior();
    }
    
    /**
     * Default starting level. Only relevant for certain behaviors.
     * 
     * @return starting level
     */
    default int getStartLevel() {
        return 0;
    }
    
    /** @return random seed for the gap generator */
    default long getGapRandomSeed() {
        return new Random().nextLong();
    }
    
    /** @return random seed for the tetromino generator */
    default long getTetrominoRandomSeed() {
        return new Random().nextLong();
    }
}
