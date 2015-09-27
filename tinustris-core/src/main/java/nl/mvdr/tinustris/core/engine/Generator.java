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
 * Used to generate a value.
 * 
 * @param <S> type of objects to be generated
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface Generator<S> {
    /**
     * Returns value i.
     * 
     * If get is invoked multiple times with the same index, the same value is returned every time.
     * 
     * @param i
     *            index of the value; must be at least 0
     * @return value
     */
    S get(int i);
}
