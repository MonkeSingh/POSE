package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

public class EscapeHSL extends GameApplication{
    private Entity player;
    private Entity guard;
    private Entity key;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Escape HSLEIDEN");
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setManualResizeEnabled(true);
    }

    @Override
    protected void initInput(){

        FXGL.onKey(KeyCode.W, () ->
                player.translateY(-5));

        FXGL.onKey(KeyCode.A, () ->
                player.translateX(-5));

        FXGL.onKey(KeyCode.S, () ->
                player.translateY(5));

        FXGL.onKey(KeyCode.D, () ->
                player.translateX(5));

    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder().at(100, 100)
                .viewWithBBox("test2.png")
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        key = FXGL.entityBuilder()
                .at(100, 100)
                .viewWithBBox("key.png")
                .scale(0.5, 0.5)
                .with(new CollidableComponent(true))
                .type(EntityTypes.KEY)
                .buildAndAttach();

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.KEY) {
            @Override
            protected void onCollision(Entity player, Entity key) {
                key.removeFromWorld();
            }
        });
    }


    public static void main(String[] args){
        launch(args);
    }

}