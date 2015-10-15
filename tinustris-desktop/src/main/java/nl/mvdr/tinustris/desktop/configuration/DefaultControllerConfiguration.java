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
package nl.mvdr.tinustris.desktop.configuration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Button;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Component.POV;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;
import nl.mvdr.game.jinput.InputMapping;
import nl.mvdr.game.jinput.JInputControllerConfiguration;
import nl.mvdr.tinustris.core.input.Input;
import nl.mvdr.tinustris.core.input.NoSuitableControllerException;

/**
 * Default controller configurations for Tinustris.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultControllerConfiguration {
    /**
     * Creates a default configuration mapping, using keyboard controls, where all movement options are bound to the
     * arrow keys and other actions to Z, X and C. At least one keyboard controller must be present.
     * 
     * @return default configuration
     * @throws NoSuitableControllerException in case there is no suitable controller
     */
    public static JInputControllerConfiguration<Input> get() throws NoSuitableControllerException {
        return keyboard();
    }

    /**
     * Creates a configuration mapping, using keyboard controls, where all movement options are bound to the arrow keys
     * and other actions to Z, X and C. At least one keyboard controller must be present.
     * 
     * @return default keyboard configuration
     * @throws NoSuitableControllerException in case there is no keyboard
     */
    public static JInputControllerConfiguration<Input> keyboard() throws NoSuitableControllerException {
        Controller[] controllersFromEnvironment = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllersFromEnvironment.length == 0) {
            throw new NoSuitableControllerException(
                    "No controllers present."
                    + " This may mean JInput is not present on java.library.path,"
                    + " or that JInput could not find any input devices.");
        }
        
        // find the keyboard controller(s)
        Set<Controller> controllers = Stream.of(controllersFromEnvironment)
            .peek(DefaultControllerConfiguration::log)
            .filter(controller -> controller.getType() == Type.KEYBOARD)
            .collect(Collectors.toSet());
        if (controllers.isEmpty()) {
            throw new NoSuitableControllerException("No keyboard present!");
        }
        log.info("Using keyboard controllers: " + controllers);
        
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
                .collect(Collectors.toMap(Function.identity(), input -> new HashSet<>()));
        for (Controller keyboard : controllers) {
            mapping.get(Input.LEFT)
                .add(new InputMapping(getComponent(keyboard, Key.LEFT), 1.0f));
            mapping.get(Input.RIGHT)
                .add(new InputMapping(getComponent(keyboard, Key.RIGHT), 1.0f));
            mapping.get(Input.SOFT_DROP)
                .add(new InputMapping(getComponent(keyboard, Key.DOWN), 1.0f));
            mapping.get(Input.HARD_DROP)
                .add(new InputMapping(getComponent(keyboard, Key.UP), 1.0f));
            mapping.get(Input.TURN_LEFT)
                .add(new InputMapping(getComponent(keyboard, Key.Z), 1.0f));
            mapping.get(Input.TURN_RIGHT)
                .add(new InputMapping(getComponent(keyboard, Key.X), 1.0f));
            mapping.get(Input.HOLD)
                .add(new InputMapping(getComponent(keyboard, Key.C), 1.0f));
        }
        log.info("Created input mapping: " + mapping);
        
        return new JInputControllerConfiguration<>(mapping, controllers);
    }
    
    /**
     * Creates a configuration mapping, using gamepad controls, where all movement options are bound to the D-pad / left
     * analog stick and other actions to face buttons. At least one joypad controller must be present. Tested using a
     * wireless XBox 360 controller.
     * 
     * @return default gamepad configuration
     * @throws NoSuitableControllerException in case there is no gamepad
     */
    public static JInputControllerConfiguration<Input> gamepad() throws NoSuitableControllerException {
        Controller[] controllersFromEnvironment = ControllerEnvironment.getDefaultEnvironment().getControllers();
        if (controllersFromEnvironment.length == 0) {
            throw new NoSuitableControllerException(
                    "No controllers present."
                    + " This may mean JInput is not present on java.library.path,"
                    + " or that JInput could not find any input devices.");
        }
        
        // find the gamepad controller(s)
        Set<Controller> controllers = Stream.of(controllersFromEnvironment)
            .peek(DefaultControllerConfiguration::log)
            .filter(controller -> controller.getType() == Type.GAMEPAD)
            .collect(Collectors.toSet());
        if (controllers.isEmpty()) {
            throw new NoSuitableControllerException("No gamepad present!");
        }
        log.info("Using gamepad controllers: " + controllers);
        
        Map<Input, Set<InputMapping>> mapping = Stream.of(Input.values())
                .collect(Collectors.toMap(Function.identity(), input -> new HashSet<>()));
                
        for (Controller gamepad : controllers) {
            // analogue stick
            mapping.get(Input.LEFT)
                .add(new InputMapping(getComponent(gamepad, Axis.X), -1.0f));
            mapping.get(Input.RIGHT)
                .add(new InputMapping(getComponent(gamepad, Axis.X), 1.0f));
            mapping.get(Input.SOFT_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.Y), 1.0f));
            mapping.get(Input.HARD_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.Y), -1.0f));
            // d-pad
            mapping.get(Input.LEFT)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), POV.LEFT));
            mapping.get(Input.RIGHT)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), POV.RIGHT));
            mapping.get(Input.SOFT_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), POV.DOWN));
            mapping.get(Input.HARD_DROP)
                .add(new InputMapping(getComponent(gamepad, Axis.POV), POV.UP));
            // face buttons
            mapping.get(Input.TURN_LEFT)
                .add(new InputMapping(getComponent(gamepad, Button._2), 1.0f));
            mapping.get(Input.TURN_RIGHT)
                .add(new InputMapping(getComponent(gamepad, Button._0), 1.0f));
            mapping.get(Input.HOLD)
                .add(new InputMapping(getComponent(gamepad, Button._3), 1.0f));
        }
        log.info("Created input mapping: " + mapping);
        
        return new JInputControllerConfiguration<>(mapping, controllers);
    }
    
    /**
     * Logs the given controller.
     * 
     * @param controller
     *            controller
     */
    private static void log(Controller controller) {
        log.info("Controller found: {}, type: {}, class: {}", controller, controller.getType(), controller.getClass()
                .getName());
    }

    /**
     * Returns the component with the given identifier.
     * 
     * @param controller
     *            controller
     * @param identifier
     *            component identifier
     * @return component corresponding to the given identifier
     * @throws IllegalStateException
     *             in case the key cannot be found
     */
    private static Component getComponent(Controller controller, Identifier identifier) {
        Component component = controller.getComponent(identifier);
        if (component == null) {
            throw new IllegalStateException("Unable to find identifier: " + identifier);
        }
        return component;
    }
}
