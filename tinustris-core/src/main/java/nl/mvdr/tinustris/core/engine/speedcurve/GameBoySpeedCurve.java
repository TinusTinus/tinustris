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
package nl.mvdr.tinustris.core.engine.speedcurve;

import java.util.HashMap;
import java.util.Map;

import lombok.ToString;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;

/**
 * Speed curve based on the original Tetris for the Nintendo Game Boy.
 * 
 * @author Martijn van de Rijdt
 */
@ToString
public class GameBoySpeedCurve implements SpeedCurve {
    /** Internal gravity curve. */
    private final RangedCurve internalGravityCurve;
    
    /** Constructor. */
    public GameBoySpeedCurve() {
        super();
        
        @SuppressWarnings("serial") // not to be serialised
        Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{
            // Note: in the actual Gameboy Tetris gravity was expressed as frames per cell rather than as G.
            // The values returned here are an approximation.
            put(Integer.valueOf(0), Integer.valueOf(5));   // 53 frames per cell
            put(Integer.valueOf(1), Integer.valueOf(5));   // 49 frames per cell
            put(Integer.valueOf(2), Integer.valueOf(6));   // 45 frames per cell
            put(Integer.valueOf(3), Integer.valueOf(6));   // 41 frames per cell
            put(Integer.valueOf(4), Integer.valueOf(7));   // 37 frames per cell
            put(Integer.valueOf(5), Integer.valueOf(8));   // 33 frames per cell
            put(Integer.valueOf(6), Integer.valueOf(9));   // 28 frames per cell
            put(Integer.valueOf(7), Integer.valueOf(12));  // 22 frames per cell
            put(Integer.valueOf(8), Integer.valueOf(15));  // 17 frames per cell
            put(Integer.valueOf(9), Integer.valueOf(23));  // 11 frames per cell
            put(Integer.valueOf(10), Integer.valueOf(26)); // 10 frames per cell
            put(Integer.valueOf(11), Integer.valueOf(28)); //  9 frames per cell
            put(Integer.valueOf(12), Integer.valueOf(32)); //  8 frames per cell
            put(Integer.valueOf(13), Integer.valueOf(37)); //  7 frames per cell
            put(Integer.valueOf(14), Integer.valueOf(43)); //  6 frames per cell
            put(Integer.valueOf(15), Integer.valueOf(43)); //  6 frames per cell
            put(Integer.valueOf(16), Integer.valueOf(51)); //  5 frames per cell
            put(Integer.valueOf(17), Integer.valueOf(51)); //  5 frames per cell
            put(Integer.valueOf(18), Integer.valueOf(64)); //  4 frames per cell
            put(Integer.valueOf(19), Integer.valueOf(64)); //  4 frames per cell
            put(Integer.valueOf(20), Integer.valueOf(85)); //  3 frames per cell
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
        return 256 / computeInternalGravity(state);
    }

    /** {@inheritDoc} */
    @Override
    public int computeARE(OnePlayerGameState state) {
        return 2;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(OnePlayerGameState state) {
        return 93;
    }
}
