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
package nl.mvdr.tinustris.configuration;

import nl.mvdr.game.input.JInputControllerConfiguration;
import nl.mvdr.tinustris.input.DefaultControllerConfiguration;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.NoSuitableControllerException;

/**
 * Configuration for a player.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface PlayerConfiguration {
    /** @return player name */
    String getName();
    
    /** @return configuration for JInputController for this player */
    default JInputControllerConfiguration<Input> getJInputControllerConfiguration() {
        JInputControllerConfiguration<Input> result;
        try {
            result = DefaultControllerConfiguration.get();
        } catch (NoSuitableControllerException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }
}
