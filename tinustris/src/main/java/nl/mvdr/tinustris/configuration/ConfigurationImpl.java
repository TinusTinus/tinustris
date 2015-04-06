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

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.gui.GraphicsStyle;

/**
 * Implementation of {@link Configuration}.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class ConfigurationImpl implements Configuration {
    /** Configuration per player. */
    @NonNull
    private List<PlayerConfiguration> playerConfigurations;
    /** Graphical style for the blocks in the grid. */
    @NonNull
    private final GraphicsStyle graphicsStyle;
    /** Behavior. */
    @NonNull
    private final Behavior behavior;
    /** Starting level. */
    private final int startLevel;
    /** Random seed for the gap generator. */
    private final long gapRandomSeed;
    /** Random seed for the tetromino generator. */
    private final long tetrominoRandomSeed;
}
