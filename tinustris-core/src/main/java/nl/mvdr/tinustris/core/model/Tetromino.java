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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;

/**
 * Representation of a Tetromino; that is, one of the seven groups of four blocks that can fall into the basin.
 * 
 * @author Martijn van de Rijdt
 */
public enum Tetromino {
    /**
     * I, or straight shape.
     * 
     * <pre>
     * ++++
     * </pre>
     */
    I(Block.I, createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)),
            createSet(new Point(2, 0), new Point(2, 1), new Point(2, 2), new Point(2, 3)),
            createSet(new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)),
            createSet(new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3))),
    /**
     * O, or square shape.
     * 
     * <pre>
     * ++
     * ++
     * </pre>
     */
    O(Block.O, createSet(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(2, 1), new Point(2, 2))),
    /**
     * T shape.
     * 
     * <pre>
     *  +
     * +++
     * </pre>
     */
    T(Block.T, createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(1, 3)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 2)),
            createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(1, 1)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(0, 2))),
    /**
     * J shape.
     * 
     * <pre>
     * +
     * +++
     * </pre>
     */
    J(Block.J, createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(0, 3)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 3)),
            createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(2, 1)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(0, 1))),
    /**
     * L shape.
     * 
     * <pre>
     *   +
     * +++
     * </pre>
     */
    L(Block.L, createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(2, 3)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(2, 1)),
            createSet(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(0, 1)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(1, 3), new Point(0, 3))),
    /**
     * S skew shape.
     * 
     * <pre>
     *  ++
     * ++
     * </pre>
     */
    S(Block.S, createSet(new Point(0, 2), new Point(1, 2), new Point(1, 3), new Point(2, 3)),
            createSet(new Point(1, 3), new Point(1, 2), new Point(2, 2), new Point(2, 1)),
            createSet(new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(2, 2)),
            createSet(new Point(0, 3), new Point(0, 2), new Point(1, 2), new Point(1, 1))),
    /**
     * Z skew shape.
     * 
     * <pre>
     * ++
     *  ++
     * </pre>
     */
    Z(Block.Z, createSet(new Point(0, 3), new Point(1, 3), new Point(1, 2), new Point(2, 2)),
            createSet(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 3)),
            createSet(new Point(0, 2), new Point(1, 2), new Point(1, 1), new Point(2, 1)),
            createSet(new Point(0, 1), new Point(0, 2), new Point(1, 2), new Point(1, 3)));

    /** Block type corresponding to this Tetromino. */
    @Getter
    private final Block block;
    
    /**
     * Per orientation: a list containing the (four) points where the tetrominoes actual blocks are located in a 4*4
     * grid.
     */
    private final Map<Orientation, Set<Point>> points;
    
    /**
     * Constructor.
     * 
     * @param block
     *            block type corresponding to this tetromino
     * @param pointsFlatDown
     *            list containing the points for the Flat Down orientation
     * @param pointsFlatLeft
     *            list containing the points for the Flat Left orientation
     * @param pointsFlatUp
     *            list containing the points for the Flat Up orientation
     * @param pointsFlatRight
     *            list containing the points for the Flat Right orientation
     */
    private Tetromino(Block block, Set<Point> pointsFlatDown, Set<Point> pointsFlatLeft, Set<Point> pointsFlatUp,
            Set<Point> pointsFlatRight) {
        this.block = block;
        
        this.points = new EnumMap<>(Orientation.class);
        this.points.put(Orientation.FLAT_DOWN,  pointsFlatDown);
        this.points.put(Orientation.FLAT_LEFT,  pointsFlatLeft);
        this.points.put(Orientation.FLAT_UP,    pointsFlatUp);
        this.points.put(Orientation.FLAT_RIGHT, pointsFlatRight);
    }

    /**
     * Retrieves the list of points for the given orientation.
     * 
     * @param key
     *            orientation value
     * @return a set containing the (four) points where the tetrominoes actual blocks are located in a 4*4 grid
     * @see java.util.Map#get(java.lang.Object)
     */
    public Set<Point> getPoints(@NonNull Orientation key) {
        return points.get(key);
    }
    
    /**
     * Constructs an unmodifiable set with the given values.
     * 
     * @param values
     *            contents of the set to be constructed
     * @return set of the given values
     */
    private static Set<Point> createSet(Point... values) {
        return Collections.unmodifiableSet(
                Stream.of(values)
                .collect(Collectors.toSet()));
    }
}
