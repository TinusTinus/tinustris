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
package nl.mvdr.tinustris.core.configuration;

import java.util.function.IntFunction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.mvdr.tinustris.core.engine.level.ClassicLevelSystem;
import nl.mvdr.tinustris.core.engine.level.LevelSystem;
import nl.mvdr.tinustris.core.engine.level.TheGrandMasterLevelSystem;
import nl.mvdr.tinustris.core.engine.speedcurve.ConstantSpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.GameBoySpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.NESSpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.SpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.TheGrandMaster2MasterSpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.TheGrandMaster2NormalSpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.TheGrandMasterSpeedCurve;
import nl.mvdr.tinustris.core.engine.speedcurve.TinustrisSpeedCurve;

/**
 * Specifies the behavior of the game. This includes things like gravity, lock delay and the way levels work.
 * 
 * @author Martijn van de Rijdt
 */
@RequiredArgsConstructor
public enum Behavior {
    /** Custom behavior. */
    TINUSTRIS("Tinustris", startLevel -> new ClassicLevelSystem(startLevel), true, new TinustrisSpeedCurve()),
    /** Behavior inspired by Game Boy Tetris. */
    GAME_BOY("Game Boy Tetris", startLevel -> new ClassicLevelSystem(startLevel), true, new GameBoySpeedCurve()),
    /** Behavior inspired by NES Tetris. */
    NES("NES Tetris", startLevel -> new ClassicLevelSystem(startLevel), true, new NESSpeedCurve()),
    /** Behavior inspired by Tetris: The Grandmaster. */
    THE_GRANDMASTER("Tetris: The Grandmaster", startLevel -> new TheGrandMasterLevelSystem(), false,
            new TheGrandMasterSpeedCurve()),
    /** Behavior inspired by Tetris: The Grandmaster 2, Normal Mode. */
    THE_GRANDMASTER_2_NORMAL("Tetris: The Grandmaster 2 Normal Mode", startLevel -> new TheGrandMasterLevelSystem(),
            false, new TheGrandMaster2NormalSpeedCurve()),
    /** Behavior inspired by Tetris: The Grandmaster 2, Master Mode. */
    THE_GRANDMASTER_2_MASTER("Tetris: The Grandmaster 2 Master Mode", startLevel -> new TheGrandMasterLevelSystem(),
            false, new TheGrandMaster2MasterSpeedCurve()),
    /** Behavior which starts at 20 G and does not let up. */
    ALWAYS_20_G("20 G Forever", startLevel -> new TheGrandMasterLevelSystem(), false,
            new ConstantSpeedCurve(5120, 30, 30, 41));

    /** Name of this behavior value. */
    @Getter
    private final String name;
    /** Factory which can create snew LevelSystem instances. */
    private final IntFunction<LevelSystem> levelSystemFactory;
    /** Indicates whether the level system supports a starting level. */
    @Getter
    private final boolean startLevelSupported;
    /** Speed curve. */
    @Getter
    private final SpeedCurve speedCurve;

    /** @return default value */
    public static Behavior defaultBehavior() {
        return TINUSTRIS;
    }

    /**
     * Creates a new level system. Note that some level systems do not support starting levels. For these, the
     * startLevel parameter is ignored.
     * 
     * @param startLevel
     *            starting level
     * @return level system
     */
    public LevelSystem createLevelSystem(int startLevel) {
        return levelSystemFactory.apply(startLevel);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getName();
    }
}
