package nl.mvdr.tinustris.engine.speedcurve;

import java.util.SortedMap;
import java.util.TreeMap;

import nl.mvdr.tinustris.model.GameState;

/**
 * Speed curve based on Tetris: The Grand Master.
 * 
 * @author Martijn van de Rijdt
 */
public class TheGrandMasterSpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;

    /** Constructor. */
    public TheGrandMasterSpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        SortedMap<Integer, Integer> map = new TreeMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(4));
            put(Integer.valueOf(30), Integer.valueOf(6));
            put(Integer.valueOf(35), Integer.valueOf(8));
            put(Integer.valueOf(40), Integer.valueOf(10));
            put(Integer.valueOf(50), Integer.valueOf(12));
            put(Integer.valueOf(60), Integer.valueOf(16));
            put(Integer.valueOf(70), Integer.valueOf(32));
            put(Integer.valueOf(80), Integer.valueOf(48));
            put(Integer.valueOf(90), Integer.valueOf(64));
            put(Integer.valueOf(100), Integer.valueOf(80));
            put(Integer.valueOf(120), Integer.valueOf(96));
            put(Integer.valueOf(140), Integer.valueOf(112));
            put(Integer.valueOf(160), Integer.valueOf(128));
            put(Integer.valueOf(170), Integer.valueOf(144));
            put(Integer.valueOf(200), Integer.valueOf(4));
            put(Integer.valueOf(220), Integer.valueOf(32));
            put(Integer.valueOf(230), Integer.valueOf(64));
            put(Integer.valueOf(233), Integer.valueOf(96));
            put(Integer.valueOf(236), Integer.valueOf(128));
            put(Integer.valueOf(239), Integer.valueOf(160));
            put(Integer.valueOf(243), Integer.valueOf(192));
            put(Integer.valueOf(247), Integer.valueOf(224));
            put(Integer.valueOf(251), Integer.valueOf(256));  //  1G
            put(Integer.valueOf(300), Integer.valueOf(512));  //  2G
            put(Integer.valueOf(330), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(360), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(400), Integer.valueOf(1280)); //  5G
            put(Integer.valueOf(420), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(450), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(500), Integer.valueOf(5120)); // 20G
        }};
        this.internalGravityCurve = new RangedCurve(map);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(GameState state) {
        int level = state.getLevel();
        return internalGravityCurve.getValue(level);
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(GameState state) {
        return 30;
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(GameState state) {
        return 30;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(GameState state) {
        return 41;
    }
}