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
import java.util.Map;

import lombok.ToString;
import nl.mvdr.tinustris.model.OnePlayerGameState;

/**
 * Speed curve based on Tetris: The Grand Master.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class TheGrandMasterSpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;

    /** Constructor. */
    public TheGrandMasterSpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{
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
    public int computeInternalGravity(OnePlayerGameState state) {
        int level = state.getLevel();
        return internalGravityCurve.getValue(level);
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(OnePlayerGameState state) {
        return 30;
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(OnePlayerGameState state) {
        return 30;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(OnePlayerGameState state) {
        return 41;
    }
}
