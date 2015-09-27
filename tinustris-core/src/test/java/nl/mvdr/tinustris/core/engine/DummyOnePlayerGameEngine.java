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
package nl.mvdr.tinustris.core.engine;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.game.engine.GameEngine;
import nl.mvdr.game.input.InputState;
import nl.mvdr.tinustris.core.input.Input;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;

/**
 * Dummy implementation of {@link GameEngine} for {@link OnePlayerGameState}.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class DummyOnePlayerGameEngine implements GameEngine<OnePlayerGameState, Input> {
    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState initGameState() {
        log.info("Initialising new game state.");
        return new OnePlayerGameState();
    }

    /** {@inheritDoc} */
    @Override
    public OnePlayerGameState computeNextState(OnePlayerGameState previousState, List<InputState<Input>> inputStates) {
        log.info("Computing next state.");
        return previousState;
    }

}
