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
package nl.mvdr.tinustris.core.logging;

import lombok.extern.slf4j.Slf4j;
import nl.mvdr.game.logging.Logging;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Logging helper class.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class TinustrisLogging extends Logging {
    /** Installs a bridge for java.util.logging to slf4j. */
    public void installSlf4jBridge() {
        log.info("Installing java.util.logging to slf4j bridge.");
        
        // remove existing handlers attached to java.util.logging root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // add SLF4JBridgeHandler to java.util.logging's root logger
        SLF4JBridgeHandler.install();
    }
}
