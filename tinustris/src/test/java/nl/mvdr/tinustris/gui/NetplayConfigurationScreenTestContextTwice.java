package nl.mvdr.tinustris.gui;

import java.io.IOException;

import nl.mvdr.tinustris.logging.Logging;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * Starts the Tinustris netcode configuration screen twice, both instances with the same testing configuration. Useful
 * for netcode tests.
 * 
 * This class relies on JInput's native libraries. These are not available by default. If you want to run this class,
 * make sure that the java.library.path system property contains target/natives in this project directory.
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
public class NetplayConfigurationScreenTestContextTwice extends NetplayConfigurationScreen {
    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // first window
        super.start(primaryStage);
        
        // second window
        Stage secondStage = new Stage();
        super.start(secondStage);
        secondStage.setX(secondStage.getX() + secondStage.getWidth());
    }
    
    /**
     * Main method.
     * 
     * @param args
     *            command-line parameters
     */
    public static void main(String[] args) {
        log.info("Starting the Tinustris netcode configuration screen... TWICE!");
        
        Logging.logVersionInfo();
        
        Logging.setUpHazelcastLogging();
        
        // JInput uses java.util.logging; redirect to slf4j.
        Logging.installSlf4jBridge();
        
        launch(args);
    }
}
