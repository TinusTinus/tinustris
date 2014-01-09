package nl.mvdr.tinustris.engine.speedcurve;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.mvdr.tinustris.model.GameState;

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
    private final int are;
    /** Line clear delay in frames. */
    private final int lineClearDelay;
    
    /** Convenience constructor. */
    public ConstantSpeedCurve() {
        this(4, 120, 2, 25);
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeInternalGravity(GameState state) {
        return gravity;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLockDelay(GameState state) {
        return lockDelay;
    }
    
    /** {@inheritDoc} */
    @Override
    public int computeARE(GameState state) {
        return are;
    }

    /** {@inheritDoc} */
    @Override
    public int computeLineClearDelay(GameState state) {
        return lineClearDelay;
    }
}