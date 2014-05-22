package nl.mvdr.tinustris.model;

/**
 * Holds one or more game state values.
 * 
 * @param <S> game state type
 * 
 * @author Martijn van de Rijdt
 */
public interface GameStateHolder<S extends GameState> {
    /**
     * Adds a game state.
     * 
     * @param state state to be held
     */
    void addGameState(S state);
    
    /** @return gets the latest game state; not guaranteed to be the same one as passed into the last call of addGameState */
    S retrieveLatestGameState();
}
