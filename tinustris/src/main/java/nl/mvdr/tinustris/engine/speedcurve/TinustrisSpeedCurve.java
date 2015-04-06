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
 * Speed curve based on the original Tetris for the Nintendo Game Boy.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class TinustrisSpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;
    
    /** Constructor. */
    public TinustrisSpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{
            put(Integer.valueOf(0), Integer.valueOf(5));
            put(Integer.valueOf(1), Integer.valueOf(6));
            put(Integer.valueOf(2), Integer.valueOf(7));
            put(Integer.valueOf(3), Integer.valueOf(8));
            put(Integer.valueOf(4), Integer.valueOf(9));
            put(Integer.valueOf(5), Integer.valueOf(11));
            put(Integer.valueOf(6), Integer.valueOf(14));
            put(Integer.valueOf(7), Integer.valueOf(20));
            put(Integer.valueOf(8), Integer.valueOf(32));
            put(Integer.valueOf(9), Integer.valueOf(43));
            put(Integer.valueOf(10), Integer.valueOf(51));
            put(Integer.valueOf(13), Integer.valueOf(64));
            put(Integer.valueOf(16), Integer.valueOf(85));
            put(Integer.valueOf(19), Integer.valueOf(128));
            put(Integer.valueOf(29), Integer.valueOf(256));  //  1G
            put(Integer.valueOf(34), Integer.valueOf(512));  //  2G
            put(Integer.valueOf(39), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(44), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(49), Integer.valueOf(1280)); //  5G
            put(Integer.valueOf(54), Integer.valueOf(1024)); //  4G
            put(Integer.valueOf(59), Integer.valueOf(768));  //  3G
            put(Integer.valueOf(64), Integer.valueOf(5120)); // 20G
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
        return Math.max(30, 256 / computeInternalGravity(state));
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(OnePlayerGameState state) {
        return 10;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(OnePlayerGameState state) {
        return 30;
    }
}
