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