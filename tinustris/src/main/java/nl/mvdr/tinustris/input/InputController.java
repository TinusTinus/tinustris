package nl.mvdr.tinustris.input;

/**
 * Provides current input states.
 * 
 * @author Martijn van de Rijdt
 */
@FunctionalInterface
public interface InputController {
    /**
     * Retrieves the current input state.
     * 
     * @return input state
     */
    InputState getInputState();
}
