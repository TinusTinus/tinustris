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
package nl.mvdr.tinustris.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Representation of a value consisting of x and y coordinates.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Point {
    /** X coordinate. */
    private final int x;
    /** Y coordinate. */
    private final int y;
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "(" + x + ", " + y  + ")";
    }
    
    /**
     * Returns a point equal to this point, translated with the given delta. Note that this method does not modify this
     * point instance, instead a new instance is returned.
     * 
     * @param deltaX
     *            amount to be added to x
     * @param deltaY
     *            amount to be added to y
     * @return new point
     */
    public Point translate(int deltaX, int deltaY) {
        return new Point(x + deltaX, y + deltaY);
    }
}
