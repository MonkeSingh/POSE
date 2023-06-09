package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import java.util.Map;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
public class EscapeHSL extends GameApplication {
    private static Entity player;
    private Entity guard;
    private Entity key;
    private Entity door;
    private int currentFloor;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Escape HSLEIDEN");
        gameSettings.addEngineService(PlayerNameService.class);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setManualResizeEnabled(false);
        gameSettings.setWidth(900);
        gameSettings.setHeight(900);

    }

    @Override
    protected void initInput() {

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

        FXGL.onKey(KeyCode.E, () -> {
            if (guard.isColliding(door)) {
                currentFloor--;
                if (currentFloor >= 0) {
                    loadFloor(currentFloor);
                } else {
                    FXGL.showMessage("You have completed all the floors!");
                }
            }
        });

    }

    private void loadFloor(int currentFloor) {
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new EscapeFactory());
        player = spawn("player", 100, 100);
        currentFloor = 4;
        loadFloor(currentFloor);

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