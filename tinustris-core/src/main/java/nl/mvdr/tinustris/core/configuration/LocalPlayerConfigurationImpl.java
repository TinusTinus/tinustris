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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.game.jinput.JInputControllerConfiguration;
import nl.mvdr.tinustris.core.input.Input;

/**
 * Implementation of {@link PlayerConfiguration}.
 * 
 * @author Martijn van de Rijdt
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LocalPlayerConfigurationImpl implements PlayerConfiguration {
    /** Player name. */
    @NonNull
    private final String name;
    /** Configuration for the JInputController for this player. */
    @NonNull
    private final JInputControllerConfiguration<Input> jInputControllerConfiguration;
}
