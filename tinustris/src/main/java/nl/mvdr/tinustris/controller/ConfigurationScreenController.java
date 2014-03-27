package nl.mvdr.tinustris.controller;

import java.util.stream.Stream;

import nl.mvdr.tinustris.gui.GraphicsStyle;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for a configuration screen.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
public class ConfigurationScreenController {
    /** Radio button for 2D graphics. */
    @FXML
    private RadioButton graphics2DRadioButton;
    /** Radio button for 3D graphics. */
    @FXML
    private RadioButton graphics3DRadioButton;
    
    /** Initialisation method. */
    @FXML
    private void initialize() {
        log.info("Initialising.");
        
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton ->
                radioButton.setOnAction(event -> log.info("Activated " + radioButton.getText())));
        Stream.of(graphics2DRadioButton, graphics3DRadioButton)
            .forEach(radioButton -> radioButton.setDisable(!toGraphicsStyleValue(radioButton).isAvailable()));
        
        // TODO further initialisation
        
        log.info("Initialisation complete.");
    }

    /**
     * Returns the graphics style value corresponding to the given ui component.
     * 
     * @param radioButton ui component; must be one of the radio buttons corresponding to a graphical style
     * @return graphics style
     */
    private GraphicsStyle toGraphicsStyleValue(RadioButton radioButton) {
        GraphicsStyle result;
        if (radioButton == graphics2DRadioButton) {
            result = GraphicsStyle.TWO_DIMENSIONAL;
        } else if (radioButton == graphics3DRadioButton) {
            result = GraphicsStyle.THREE_DIMENSIONAL;
        } else {
            throw new IllegalArgumentException("Unexpected parameter: " + radioButton);
        }
        return result;
    }
}
