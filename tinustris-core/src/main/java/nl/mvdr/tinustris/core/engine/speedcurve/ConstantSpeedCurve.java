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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;

/**
 * A curve where all values are constant.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
@ToString
public class ConstantSpeedCurve implements SpeedCurve {
    /** Gravity in 1 / 256 G. */
    private final int gravity;
    /** Lock delay in frames. */
    private final int lockDelay;
    /** ARE in frames. */
    @Getter
    private final int are;
    /** Line clear delay in frames. */
    private final int lineClearDelay;
    
    /** Convenience constructor. */
    public ConstantSpeedCurve() {
        this(4, 120, 2, 25);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(OnePlayerGameState state) {
        return gravity;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(OnePlayerGameState state) {
        return lockDelay;
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeARE(OnePlayerGameState state) {
        return are;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(OnePlayerGameState state) {
        return lineClearDelay;
    }
}
