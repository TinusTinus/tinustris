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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Types of blocks that can make up a Tetris grid.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Block {
    /** Block that makes up the I shape. */
    I("I"),
    /** Block that makes up the O shape. */
    O("O"),
    /** Block that makes up the T shape. */
    T("T"),
    /** Block that makes up the J shape. */
    J("J"),
    /** Block that makes up the L shape. */
    L("L"),
    /** Block that makes up the S skew shape. */
    S("S"),
    /** Block that makes up the Z skew shape. */
    Z("Z"),
    /** Garbage block. */
    GARBAGE("G");
    
    /** String representation. */
    private final String stringRepresentation;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return stringRepresentation;
    }
}
