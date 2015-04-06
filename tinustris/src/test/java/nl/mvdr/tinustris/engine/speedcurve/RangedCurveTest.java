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
package nl.mvdr.tinustris.engine.speedcurve;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link RangedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class RangedCurveTest {
    /** Tests the {@link RangedCurve#toString()} method. */
    @Test
    public void testToString() {
        @SuppressWarnings("serial") // not to be serialised
        RangedCurve curve = new RangedCurve(new HashMap<Integer, Integer>(){{
            put(0, 5);
            put(4, 7);
            put(7, 23);
            put(8, 24);
        }});
        
        String string = curve.toString();
        
        Assert.assertNotNull(string);
        log.info(string);
    }
}
