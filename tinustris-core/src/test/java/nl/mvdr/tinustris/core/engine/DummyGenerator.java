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

import java.util.Collections;
import java.util.List;

import nl.mvdr.tinustris.core.engine.Generator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Dummy implementation of {@link Generator}.
 * 
 * @param <S> value type
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DummyGenerator<S> implements Generator<S> {
    /** Tetrominoes to be returned by the get method. */
    private final List<S> values;

    /** Constructor. */
    DummyGenerator() {
        this(Collections.emptyList());
    }
    
    /**
     * {@inheritDoc}
     * 
     * Dummy implementation which just returns values i from the list passed into the constructor. Throws an
     * IndexOutOfBoundsException if i is not in range of the list.
     */
    @Override
    public S get(int i) {
        return values.get(i);
    }
}
