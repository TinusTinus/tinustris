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
package nl.mvdr.tinustris.gui;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Label with light green, fairly large text.
 * 
 * @author Martijn van de Rijdt
 */
public class GreenTextLabel extends Label {
    /** Font size. */
    private static final int FONT_SIZE = 30;

    /** Constructor. */
    GreenTextLabel() {
        super();

        setFont(new Font(FONT_SIZE));
        setTextFill(Color.LIGHTGREEN);
    }
    
    /**
     * Constructor.
     * 
     * @param text
     *            initial text
     */
    GreenTextLabel(String text) {
        super(text);

        setFont(new Font(FONT_SIZE));
        setTextFill(Color.LIGHTGREEN);
    }
}
