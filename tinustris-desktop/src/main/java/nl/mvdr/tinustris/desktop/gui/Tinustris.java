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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.mvdr.game.engine.GameEngine;
import nl.mvdr.game.engine.GameLoop;
import nl.mvdr.game.gui.CompositeRenderer;
import nl.mvdr.game.gui.GameRenderer;
import nl.mvdr.game.input.InputController;
import nl.mvdr.tinustris.core.configuration.Configuration;
import nl.mvdr.tinustris.core.configuration.PlayerConfiguration;
import nl.mvdr.tinustris.core.engine.GapGenerator;
import nl.mvdr.tinustris.core.engine.Generator;
import nl.mvdr.tinustris.core.engine.MultiplayerEngine;
import nl.mvdr.tinustris.core.engine.OnePlayerEngine;
import nl.mvdr.tinustris.core.engine.RandomTetrominoGenerator;
import nl.mvdr.tinustris.core.gui.MultiplayerGameRenderer;
import nl.mvdr.tinustris.core.input.Input;
import nl.mvdr.tinustris.core.model.MultiplayerGameState;
import nl.mvdr.tinustris.core.model.OnePlayerGameState;
import nl.mvdr.tinustris.core.model.Tetromino;

/**
 * Class which can start a game of Tinustris.
 * 
 * @author Martijn van de Rijdt
 */
@Slf4j
@RequiredArgsConstructor
public class Tinustris {
    /** Size of the margin between the display for each player in a multiplayer game. */
    private static final int MARGIN = 20;

    /** Game loop. */
    private GameLoop<?, Input> gameLoop;

    /**
     * Starts the game.
     * 
     * @param stage stage in which the game should be shown
     * @param configuration game configuration
     */
    public void start(Stage stage, Configuration<GraphicsStyle> configuration) {
        log.info("Starting game with configuration: " + configuration);
        
        BlockCreator blockCreator = configuration.getGraphicsStyle().makeBlockCreator();
        
        int widthInBlocks = OnePlayerGameState.DEFAULT_WIDTH;
        int heightInBlocks = OnePlayerGameState.DEFAULT_HEIGHT - OnePlayerGameState.VANISH_ZONE_HEIGHT;
        
        List<OnePlayerGameRenderer> onePlayerRenderers = configuration.getPlayerConfigurations().stream()
            .map(PlayerConfiguration::getName)
            .map(name -> new OnePlayerGameRenderer(name, widthInBlocks, heightInBlocks, blockCreator))
            .collect(Collectors.toList());
        
        FlowPane parent = new FlowPane(MARGIN, MARGIN);
        parent.getChildren().addAll(onePlayerRenderers);
        parent.setBackground(Background.EMPTY);

        Scene scene = new Scene(parent, Color.GRAY);

        if (configuration.getGraphicsStyle() == GraphicsStyle.THREE_DIMENSIONAL) {
            scene.setCamera(new PerspectiveCamera());
            onePlayerRenderers.get(0).getChildren().add(createLight(150, 500, -250));
        }

        stage.setTitle("Tinustris");
        stage.setScene(scene);
        stage.show();
        log.info("Stage shown.");
        
        initAndStartGameLoop(onePlayerRenderers, configuration);
    }

    /**
     * Initialises and starts the main game loop.
     * 
     * @param onePlayerRenderers
     *            renderer for each player
     */
    private void initAndStartGameLoop(List<OnePlayerGameRenderer> onePlayerRenderers, Configuration<GraphicsStyle> configuration) {
        
        int numPlayers = onePlayerRenderers.size();
        
        List<InputController<Input>> inputControllers = configuration.getPlayerConfigurations()
                .stream()
                .map(PlayerConfiguration::createInputController)
                .collect(Collectors.toList());
        
        Generator<Tetromino> tetrominoGenerator = new RandomTetrominoGenerator(configuration.getTetrominoRandomSeed());
        Generator<Integer> gapGenerator = new GapGenerator(configuration.getGapRandomSeed(), OnePlayerGameState.DEFAULT_WIDTH);
        GameEngine<OnePlayerGameState, Input> onePlayerEngine = new OnePlayerEngine(tetrominoGenerator,
                configuration.getBehavior(), configuration.getStartLevel(), gapGenerator);
        
        if (numPlayers == 1) {
            // single player game
            gameLoop = new GameLoop<>(inputControllers, onePlayerEngine, onePlayerRenderers.get(0));
        } else {
            // multiplayer game
            GameEngine<MultiplayerGameState, Input> gameEngine = new MultiplayerEngine(numPlayers, onePlayerEngine);
            List<GameRenderer<MultiplayerGameState>> multiplayerRenderers = IntStream.range(0, numPlayers)
                    .mapToObj(i -> new MultiplayerGameRenderer(onePlayerRenderers.get(i), i))
                    .collect(Collectors.toList());
            GameRenderer<MultiplayerGameState> gameRenderer = new CompositeRenderer<>(multiplayerRenderers);
            
            gameLoop = new GameLoop<>(inputControllers, gameEngine, gameRenderer);
        }

        log.info("Ready to start game loop: " + gameLoop);
        gameLoop.start();
        log.info("Game loop started in separate thread.");
    }

    /**
     * Creates a light at (around) the given location.
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     * @param z
     *            z coordinate
     * @return light
     */
    private PointLight createLight(double x, double y, double z) {
        PointLight light = new PointLight(Color.WHITE);
        
        TranslateTransition transition = new TranslateTransition(new Duration(5_000), light);
        transition.setFromX(x - 50);
        transition.setFromY(y - 50);
        transition.setFromZ(z - 50);
        transition.setToX(x + 50);
        transition.setToY(y + 50);
        transition.setToZ(z + 50);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
        
        return light;
    }

    /** Stops the game loop. */
    public void stopGameLoop() {
        if (gameLoop != null) {
            log.info("Stopping the game loop.");
            gameLoop.stop();
        } else {
            log.info("No game loop to stop.");
        }
    }
}
