package com.example.challangeweek;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GamePage {

    public void show(Stage primaryStage) {
        Pane gamePane = createGameScreen();
        Scene gameScene = new Scene(gamePane, 800, 600);
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    private Pane createGameScreen() {
        Pane gamePane = new Pane();

        // Create a simple player character
        Rectangle player = new Rectangle(50, 550, 20, 40);
        player.setFill(Color.BLUE);
        gamePane.getChildren().add(player);

        // Basic game loop using AnimationTimer
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Game logic goes here
            }
        };
        gameLoop.start();

        gamePane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.setX(player.getX() - 10);
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.setX(player.getX() + 10);
            } else if (e.getCode() == KeyCode.UP) {
                player.setY(player.getY() - 10);
            } else if (e.getCode() == KeyCode.DOWN) {
                player.setY(player.getY() + 10);
            }
        });

        return gamePane;
    }
}
