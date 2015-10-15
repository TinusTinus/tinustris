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
package nl.mvdr.tinustris.desktop.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.game.jinput.InputMapping;
import nl.mvdr.game.jinput.JInputControllerConfiguration;
import nl.mvdr.tinustris.core.configuration.LocalPlayerConfigurationImpl;
import nl.mvdr.tinustris.core.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.core.input.Input;

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
    private JInputControllerConfiguration<Input> inputConfiguration;

    /** Constructor. */
    public PlayerConfigurationController() {
        super();
        
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
            .collect(Collectors.toMap(Function.identity(), input -> Collections.emptySet()));
        this.inputConfiguration = new JInputControllerConfiguration<>(mapping, Collections.emptySet());
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
    void updateInputConfiguration(JInputControllerConfiguration<Input> configuration) {
        log.info("Updating input configuration to: " + configuration);
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
        return new LocalPlayerConfigurationImpl(nameProperty().getValue(), inputConfiguration);
    }
    
    /**
     * Handler for the input configuration button.
     * 
     * @param event action event leading to this method call
     */
    @FXML
    private void startButtonConfiguration(ActionEvent event) {
        try {
            log.info("Configure buttons activated for " + nameProperty().get());

            Node source = (Node)event.getSource();
            Stage stage = (Stage)source.getScene().getWindow();
            
            Scene originalScene = stage.getScene();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InputConfiguration.fxml"));
            
            Region region = loader.load();
            region.setPrefSize(originalScene.getWidth(), originalScene.getHeight());
            stage.setScene(new Scene(region));
            
            InputConfigurationController controller = loader.getController();
            controller.setHandleCancelled(() -> stage.setScene(originalScene));
            controller.setHandleSuccess(configuration -> {
                updateInputConfiguration(configuration);
                stage.setScene(originalScene);
            });
            controller.registerOnHidden(stage);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load fxml definition.", e);
        }
    }
}
