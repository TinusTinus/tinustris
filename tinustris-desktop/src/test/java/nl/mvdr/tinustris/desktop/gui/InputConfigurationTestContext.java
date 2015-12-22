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

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.core.logging.TinustrisLogging;

/**
 * Main class which opens the input configuration screen.
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
public class InputConfigurationTestContext extends Application {
    /** Logging helper. */
    private static final TinustrisLogging logging = new TinustrisLogging();
    
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting application.");
        
        logging.setUncaughtExceptionHandler();
        
        Parent parent = FXMLLoader.load(getClass().getResource("/InputConfiguration.fxml"));
        
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    
    /**
     * Main method.
     * 
     * @param args commandline arguments; these are passed on to JavaFX
     */
    public static void main(String[] args) {
        log.info("Starting Input configuration screen.");
        
        logging.logVersionInfo();
        // JInput uses java.util.logging; redirect to slf4j.
        logging.installSlf4jBridge();

        launch(args);
    }
}
