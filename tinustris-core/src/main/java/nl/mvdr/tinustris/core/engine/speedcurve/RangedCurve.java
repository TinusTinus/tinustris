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
package nl.mvdr.tinustris.core.engine.speedcurve;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Representation of a curve of values based on ranges.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
class RangedCurve {
    /**
     * Map that represents the ranges. Each range (i, j) is represented by its lower bound i, where the upper bound j is
     * the next range's lower bound, or infinity if it is the last one.
     * 
     * Should not be null or empty.
     */
    @NonNull
    private final Map<Integer, Integer> map;

    /**
     * Finds the range (i, j) where i <= key < j and returns the corresponding value.
     * 
     * @param key key; may not be less than the lower bound of the first range
     * @return value
     * @throws NoSuchElementException if key is less than the lower bound
     */
    int getValue(int key) {
        Integer mapKey = map.keySet()
            .stream()
            .filter(i -> i <= key)
            .max(Integer::compare)
            .get();

        return map.get(mapKey).intValue();
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        List<Entry<Integer, Integer>> entryList = map.entrySet()
            .stream()
            .sorted((entry0, entry1) -> entry0.getKey().compareTo(entry1.getKey()))
            .collect(Collectors.toList());
        
        return IntStream.range(0, map.size() - 1)
            .mapToObj(i -> String.format("[%s, %s): %s, ", entryList.get(i).getKey(), entryList.get(i + 1).getKey(), entryList.get(i).getValue()))
            .collect(StringBuilder::new, (builder, string) -> builder.append(string), StringBuilder::append)
            .append(String.format("[%s, infinity): %s", entryList.get(entryList.size() - 1).getKey(), entryList.get(entryList.size() - 1).getValue()))
            .toString();
    }
}
