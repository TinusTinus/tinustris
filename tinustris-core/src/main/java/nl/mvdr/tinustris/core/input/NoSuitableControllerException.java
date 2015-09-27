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
package nl.mvdr.tinustris.core.input;

/**
 * Exception class that there are no suitable controllers for JInput.
 * 
 * @author Martijn van de Rijdt
 */
@SuppressWarnings("serial") // not serialised
public class NoSuitableControllerException extends Exception {
    /** Constructor. */
    public NoSuitableControllerException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public NoSuitableControllerException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param cause cause
     */
    public NoSuitableControllerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     * @param cause cause
     */
    public NoSuitableControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
