package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

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
    private static Entity player;
    private Entity guard;
    private Entity key;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Escape HSLEIDEN");
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setWidth(900);
        gameSettings.setHeight(900);
    }

    @Override
    protected void initInput(){

        double worldWidth = getAppWidth();
        double worldHeight = getAppHeight();

        FXGL.onKey(KeyCode.W, () -> {
            if (player.getY() > 0) {
                player.translateY(-5);
            }
        });

        FXGL.onKey(KeyCode.A, () -> {
            if (player.getX() > 0) {
                player.translateX(-5);
            }
        });

        FXGL.onKey(KeyCode.S, () -> {
            if (player.getY() < worldHeight - player.getHeight()) {
                player.translateY(5);
            }
        });

        FXGL.onKey(KeyCode.D, () -> {
            if (player.getX() < worldWidth - player.getWidth()) {
                player.translateX(5);
            }
        });

    }

    @Override
    protected void initGame(){
        player = FXGL.entityBuilder().at(100, 100)
                .viewWithBBox("test2.png")
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

//        FXGL.getGameTimer().runAtInterval(() -> {
//            FXGL.entityBuilder()
//                    .at(100, 100)
//                    .viewWithBBox("key.png")
//                    .scale(0.5, 0.5)
//                    .with(new CollidableComponent(true))
//                    .type(EntityTypes.KEY)
//                    .buildAndAttach();
//
//        }, Duration.millis(2000));

        key = FXGL.entityBuilder()
                .at(100, 100)
                .viewWithBBox("key.png")
                .scale(0.5, 0.5)
                .with(new CollidableComponent(true))
                .type(EntityTypes.KEY)
                .buildAndAttach();
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.KEY) {
            @Override
            protected void onCollision(Entity player, Entity key) {
                FXGL.inc("kills", +1);
                key.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI(){
        Label text = new Label("Testing");
        text.setStyle("-fx-text-fill: white");
        text.setTranslateX(50);
        text.setTranslateY(50);

        text.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());

        FXGL.getGameScene().addUINode(text);
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
    }


    public static void main(String[] args){
        launch(args);
        System.out.println("Hello World");
    }

}