package nl.mvdr.tinustris.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.tinustris.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.configuration.PlayerConfigurationImpl;
import nl.mvdr.tinustris.input.Input;
import nl.mvdr.tinustris.input.InputAndMapping;
import nl.mvdr.tinustris.input.InputMapping;
import nl.mvdr.tinustris.input.JInputControllerConfiguration;

/**
 * Controller for the player configuration user interface component.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@ToString
public class PlayerConfigurationController {
    /** Text field for player name. */
    @FXML
    private TextField nameTextField;
    /** Table view showing off all of the inputs. */
    @FXML
    private TableView<InputAndMapping> inputTable;
    
    /** Configuration for the input controller. */
    private JInputControllerConfiguration inputConfiguration;

    /** Constructor. */
    public PlayerConfigurationController() {
        super();
        
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
            .collect(Collectors.toMap(Function.identity(), input -> Collections.emptySet()));
        this.inputConfiguration = new JInputControllerConfiguration(mapping, Collections.emptySet());
    }
    
    /**
     * Constructor which initialises all fields. Intended for unit tests, since at runtime the user interface components
     * will be injected after initialisation.
     * 
     * @param nameTextField
     *            text field for player name
     * @param inputTable
     *            table displaying all inputs
     */
    PlayerConfigurationController(TextField nameTextField, TableView<InputAndMapping> inputTable) {
        this();
        
        this.nameTextField = nameTextField;
        this.inputTable = inputTable;
    }
    
    /** Performs controller initialisation. */
    @FXML
    void initialize() {
        log.info("Initialising.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }

        updateInputTable();
        
        log.info("Initialisation complete.");
        if (log.isDebugEnabled()) {
            log.debug(this.toString());
        }
    }
    
    /**
     * Sets the given value and updates the table.
     * 
     * @param configuration configuration
     */
    void updateInputConfiguration(JInputControllerConfiguration configuration) {
        this.inputConfiguration = configuration;
        updateInputTable();
    }

    /** Updates the contents of inputTable based on the value of inputConfiguration. */
    private void updateInputTable() {
        inputTable.setItems(FXCollections.observableList(
            inputConfiguration.getMapping()
                .entrySet()
                .stream()
                .map(entry -> new InputAndMapping(entry.getKey(), entry.getValue()))
                .sorted((left, right) -> left.getInput().compareTo(right.getInput()))
                .collect(Collectors.toList())));
    }
    
    /** @return player name property */
    StringProperty nameProperty() {
        return this.nameTextField.textProperty();
    }
    
    /**
     * Creates a PlayerConfiguration based on the values entered by the user.
     * 
     * @return configuration for this player
     */
    PlayerConfiguration buildConfiguration() {
        return new PlayerConfigurationImpl(nameProperty().getValue(), inputConfiguration); 
    }
    
    /** Handler for the input configuration button. */
    @FXML
    private void startButtonConfiguration() {
        log.info("Configure buttons activated for " + nameProperty().get());
        // TODO
    }
}
