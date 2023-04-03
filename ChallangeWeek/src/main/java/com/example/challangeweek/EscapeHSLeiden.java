// EscapeHSLeiden.java
package com.example.challangeweek;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EscapeHSLeiden extends Application {
    private StackPane startScreen;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        startScreen = createStartScreen(primaryStage);

        Scene scene = new Scene(startScreen, 800, 600);
        primaryStage.setTitle("ESCAPE HS Leiden");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private StackPane createStartScreen(Stage primaryStage) {
        Text title = new Text("ESCAPE HS Leiden");
        title.setFont(Font.font(24));

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            GamePage gamePage = new GamePage();
            gamePage.show(primaryStage);
        });

        VBox vBox = new VBox(10, title, startButton);
        vBox.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(vBox);
        return root;
    }
}
