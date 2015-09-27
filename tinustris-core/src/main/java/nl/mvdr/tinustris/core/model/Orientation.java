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
package nl.mvdr.tinustris.core.model;

/**
 * Orientation of a tetromino.
 * 
 * Indicates which direction a tetromino is facing.
 * 
 * See the <a href="http://tetris.wikia.com/wiki/Orientation">Tetris Wiki</a> for more details.
 * 
 * @author Martijn van de Rijdt
 */
public enum Orientation {
    /**
     * Flat Down. The orientation where the flat side of the tetromino is on the bottom side. Also known as Point Up.
     * The default orientation when a new tetromino spawns.
     */
    FLAT_DOWN,
    /** Flat Left. The orientation where the flat side of the tetromino is on the left side. Also known as Point Right. */
    FLAT_LEFT,
    /** Flat Up. The orientation where the flat side of the tetromino is on the top side. Also known as Point Down. */
    FLAT_UP,
    /** Flat Right. The orientation where the flat side of the tetromino is on the right side. Also known as Point Left. */
    FLAT_RIGHT;

    /** @return the default orientation */
    public static Orientation getDefault() {
        return FLAT_DOWN;
    }
    
    /** @return the next orientation if rotated clockwise */
    public Orientation getNextClockwise() {
        Orientation[] values = values();
        return values[(ordinal() + 1) % values.length];
    }

    /** @return the next orientation if rotated counter-clockwise */
    public Orientation getNextCounterClockwise() {
        Orientation[] values = values();
        return values[(values.length + ordinal() - 1) % values.length];
    }
}
