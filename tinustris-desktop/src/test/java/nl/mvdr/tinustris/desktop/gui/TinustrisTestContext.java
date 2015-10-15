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
package nl.mvdr.tinustris.desktop.gui;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.core.configuration.Behavior;
import nl.mvdr.tinustris.core.configuration.Configuration;
import nl.mvdr.tinustris.core.configuration.ConfigurationImpl;
import nl.mvdr.tinustris.core.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.core.input.NoSuitableControllerException;
import nl.mvdr.tinustris.core.logging.Logging;
import nl.mvdr.tinustris.desktop.configuration.DefaultControllerConfiguration;
import nl.mvdr.tinustris.desktop.configuration.LocalPlayerConfiguration;

/**
 * Main class, whose main method simply starts Tinustris with a default configuration.
 * 
 * This class relies on JInput's native libraries. These are not available by default. If you want to run this
 * class, make sure that the java.library.path system property contains target/natives in this project directory.
 * 
 * In Eclipse, you can do this by opening the Run Configuration, opening the arguments tab and pasting the following
 * into the "VM Arguments" text area:
 * 
 * <pre>
 * -Djava.library.path=${project_loc}/target/natives
 * </pre>
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class TinustrisTestContext extends Application {
    /** Tinustris instance. */
    private final Tinustris tinustris;
    
    /** Constructor. */
    public TinustrisTestContext() {
        super();
        this.tinustris = new Tinustris();
    }
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        log.info("Starting application.");
        Logging.setUncaughtExceptionHandler();
        
        try {
            PlayerConfiguration playerConfiguration = new LocalPlayerConfiguration("", DefaultControllerConfiguration.get());
            List<PlayerConfiguration> playerConfigurations = Collections.singletonList(playerConfiguration);
            Configuration<GraphicsStyle> configuration = new ConfigurationImpl<>(playerConfigurations,
                    GraphicsStyle.defaultStyle(), Behavior.defaultBehavior(), 0, new Random().nextLong(),
                    new Random().nextLong());
            tinustris.start(stage, configuration);
        } catch (NoSuitableControllerException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public void stop() throws Exception {
        log.info("Stopping the application.");
        
        tinustris.stopGameLoop();
        
        super.stop();
        log.info("Stopped.");
    }
    
    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
        log.info("Starting Tinustris.");

        Logging.logVersionInfo();
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();
        
        // Launch the application!
        launch(args);
    }
}
