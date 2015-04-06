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

import org.junit.Test;

/**
 * Test class for {@link GameBoySpeedCurve}.
 * 
 * @author Martijn van de Rijdt
 */
public class GameBoySpeedCurveTest extends SpeedCurveTester {
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel0() {
        testLevel(0, 5);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel1() {
        testLevel(1, 5);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel2() {
        testLevel(2, 6);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel3() {
        testLevel(3, 6);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel4() {
        testLevel(4, 7);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel5() {
        testLevel(5, 8);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel6() {
        testLevel(6, 9);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel7() {
        testLevel(7, 12);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel8() {
        testLevel(8, 15);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel9() {
        testLevel(9, 23);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel10() {
        testLevel(10, 26);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel11() {
        testLevel(11, 28);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel12() {
        testLevel(12, 32);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel13() {
        testLevel(13, 37);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel14() {
        testLevel(14, 43);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel15() {
        testLevel(15, 43);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel16() {
        testLevel(16, 51);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel17() {
        testLevel(17, 51);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel18() {
        testLevel(18, 64);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel19() {
        testLevel(19, 64);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel20() {
        testLevel(20, 85);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel21() {
        testLevel(21, 85);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel200() {
        testLevel(200, 85);
    }
    
    /** Test case which tests {@link GameBoySpeedCurve#computeInternalGravity(nl.mvdr.tinustris.model.OnePlayerGameState)}. */
    @Test
    public void testLevel1984() {
        testLevel(1984, 85);
    }

    /** {@inheritDoc} */
    @Override
    SpeedCurve createSpeedCurve() {
        return new GameBoySpeedCurve();        
    }
}
