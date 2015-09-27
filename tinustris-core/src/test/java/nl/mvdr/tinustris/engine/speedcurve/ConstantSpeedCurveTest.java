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

import nl.mvdr.tinustris.model.OnePlayerGameState;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link ConstantSpeedCurve}. 
 * 
 * @author Martijn van de Rijdt
 */
public class ConstantSpeedCurveTest {
    /** Test case which tests {@link ConstantSpeedCurve#ConstantSpeedCurve(int, int, int, int)}. */
    @Test
    public void testConstructor() {
        new ConstantSpeedCurve(1, 2, 3, 4);
    }
    
    /** Test case which tests {@link ConstantSpeedCurve#ConstantSpeedCurve()}. */
    @Test
    public void testDefaultConstructor() {
        new ConstantSpeedCurve();
    }   
    
    /** Test case which calls {@link ConstantSpeedCurve#computeInternalGravity(OnePlayerGameState)}. */
    @Test
    public void testGravity() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(1, curve.computeInternalGravity(new OnePlayerGameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeLockDelay(OnePlayerGameState)}. */
    @Test
    public void testLockDelay() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(2, curve.computeLockDelay(new OnePlayerGameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeARE(OnePlayerGameState)}. */
    @Test
    public void testARE() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(3, curve.computeARE(new OnePlayerGameState()));
    }
    
    /** Test case which calls {@link ConstantSpeedCurve#computeLockDelay(OnePlayerGameState)}. */
    @Test
    public void testLineClear() {
        ConstantSpeedCurve curve = new ConstantSpeedCurve(1, 2, 3, 4);
        
        Assert.assertEquals(4, curve.computeLineClearDelay(new OnePlayerGameState()));
    }
}