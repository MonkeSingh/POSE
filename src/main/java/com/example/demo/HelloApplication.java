package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.setWidth(900);
//        stage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch();
//    }
//}

public class HelloApplication extends GameApplication{
    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("TEST1");
    }

    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.D, () ->
                player.translateX(5));


        FXGL.onKey(KeyCode.A, () ->
            player.translateX(-5));

        FXGL.onKey(KeyCode.W, () ->
                player.translateY(-5));

        FXGL.onKey(KeyCode.S, () ->
                player.translateY(5));

    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder().at(100, 100).view("test2.png").buildAndAttach();
    }


    public static void main(String[] args){
        launch(args);
    }

}