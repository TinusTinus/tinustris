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
package nl.mvdr.tinustris.desktop.controller;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.game.jinput.InputMapping;
import nl.mvdr.tinustris.core.input.Input;

/**
 * Container holding an input and its mapping.
 * 
 * @author Martijn van de Rijdt
 */
// This class is public, because reducing visibility to package seems to break JavaFX initialisation.
@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class InputAndMapping {
    /** Input. */
    @NonNull
    private final Input input;
    /** Mapping. */
    @NonNull
    private final Set<InputMapping> mapping;
    
    /** @return formatted version of the input mapping */
    public String getMappingFormatted() {
        Set<String> descriptions = mapping.stream()
            .map(InputMapping::toString)
            .collect(Collectors.toSet());
        return String.join(", ", descriptions);
    }
}
