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
package nl.mvdr.tinustris.core.engine;

/**
 * Generator for determining the gap in garbage lines in a game of Tetris.
 * 
 * @author Martijn van de Rijdt
 */
public class GapGenerator extends RandomGenerator<Integer> {
    /**
     * Constructor.
     * 
     * @param width
     *            width of the playing field
     */
    public GapGenerator(int width) {
        super(width, Integer::valueOf, "Gap");
    }

    /**
     * Constructor.
     * 
     * @param randomSeed
     *            seed for the random generator
     * @param width
     *            width of the playing field
     */
    public GapGenerator(long randomSeed, int width) {
        super(randomSeed, width, Integer::valueOf, "Gap");
    }
}
