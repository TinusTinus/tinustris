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

import java.util.function.Supplier;

import nl.mvdr.tinustris.core.configuration.GraphicsConfiguration;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The style for blocks in the grid.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GraphicsStyle implements GraphicsConfiguration {
    /** 2D graphics. */
    TWO_DIMENSIONAL("2D", () -> new RectangleBlockCreator(), true),
    /** Real-time 3D graphics. May not be supported in all runtimes. */
    THREE_DIMENSIONAL("3D", () -> new BoxBlockCreator(), Platform.isSupported(ConditionalFeature.SCENE3D));
    
    /** Name of this enum value. */
    @Getter
    private final String name;
    /** Factory for {@link BlockCreator}. */
    private final Supplier<BlockCreator> blockCreatorFactory;
    /** Indicator whether this graphical style is available at runtime. */
    @Getter
    private final boolean available;
    
    /** @return default value */
    public static GraphicsStyle defaultStyle() {
        // default is 2D since it does not depend on conditional features.
        return TWO_DIMENSIONAL;
    }
    
    /**
     * Creates a new {@link BlockCreator} instance for this graphical style.
     * 
     * @return new block creator instance
     */
    BlockCreator makeBlockCreator() {
        return blockCreatorFactory.get();
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getName();
    }
}
