package game;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static game.EntityType.*;

public class PlatformerApp extends GameApplication {

    private Entity player;
    private Entity key;
    private boolean hasKey = false;

    private static final int END_LEVEL = 4;
    public int count = 0;
    public int level_count = 0;

    public int map_level = 1;
    public Highscores highscoreManager = new Highscores();

    private final String TMX_LEVEL = "tmx/level";

    private final String WIN_MESSAGE = "Je tijd: %.2f sec!";

    public int[][][] exitDoorsTeleportLocations = {
            {
                    {80, 330},
                    {80, 550},
                    {500, 80},
                    {700, 330},
                    {700, 550},
            },
            {
                    {390, 330},
                    {430, 80},
                    {950, 330},
                    {850, 80},
                    {80, 600},
            },
            {

                    {380, 550},
                    {650, 300},
                    {80, 500},
                    {600, 100},
                    {630, 500},
                    {830, 500},

            },
            {
                    {680, 100},
                    {980, 380},
                    {750, 520},
            },

    };

    public int doors = exitDoorsTeleportLocations[level_count].length - 1;;

    private UserAction createUserAction(String name, KeyCode key, VirtualButton button, Runnable onAction) {
        return new UserAction(name) {
            @Override
            protected void onAction() {
                onAction.run();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        };
    }

    
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1500);
        settings.setHeight(720);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setManualResizeEnabled(false);
        settings.setTitle("Escape HSLEIDEN");
        settings.setVersion("1");

        settings.setSceneFactory(new MySceneFactory());
    }

//        settings.setSceneFactory(new SceneFactory() {
//            @Override
//            public LoadingScene newLoadingScene() {
//                return new MainLoadingScene();
//            }
//        });


//    private LazyValue<LevelEndScene> levelEndScene = new LazyValue<>(() -> new LevelEndScene());

    @Override
    protected void initInput() {
        UserAction leftAction = createUserAction("Left", KeyCode.A, VirtualButton.LEFT, () -> player.getComponent(PlayerComponent.class).left());
        UserAction rightAction = createUserAction("Right", KeyCode.D, VirtualButton.RIGHT, () -> player.getComponent(PlayerComponent.class).right());

        getInput().addAction(leftAction, KeyCode.A, VirtualButton.LEFT);
        getInput().addAction(rightAction, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
                FXGL.play("jump.wav");
            }
        }, KeyCode.SPACE, VirtualButton.A);

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", level_count);
        vars.put("levelTime", 0.0);
        vars.put("score", 0);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.25);
        loopBGM("bob2v2.wav");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformerFactory());
        getSettings().setGlobalSoundVolume(0.30);

        player = spawn("player", 50, 50);

        setLevelFromMap("tmx/level" + map_level  + ".tmx");

        spawn("background");

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-250, -20, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);

        spawnKey();

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, END_DOOR) {
            @Override
            protected void onCollision(Entity player, Entity end_door) {
        //                End the game
                if(hasKey){
                    map_level = 1;
                    level_count = 0;
                    count = 0;
                    hasKey = false;
                    doors = exitDoorsTeleportLocations[level_count].length - 1;
                    FXGL.play("level_complete.wav");
                    Duration userTime = Duration.seconds(getd("levelTime"));
//                showMessage(String.format(WIN_MESSAGE, userTime.toSeconds()));
                    String s = String.format("%.2f", userTime.toSeconds());
                    getDialogService().showInputBox("Je tijd is " + s + "seconden. \n Voer je naam in: ", answer -> {
                        highscoreManager.addScore(answer, userTime.toSeconds());
                        getGameController().gotoMainMenu();
                    });
                    end_door.removeFromWorld();
//                getGameController().gotoMainMenu();
                }
            }
        });


        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.DOOR) {
            @Override
            protected void onCollision(Entity player, Entity door) {
                if (count > doors) {
                    if(hasKey){
                        FXGL.play("level_complete.wav");
                        nextLevel();
                        hasKey = false;
                    }
                    return;
                }

                player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(exitDoorsTeleportLocations[level_count][count][0],
                        exitDoorsTeleportLocations[level_count][count][1]));
                count+= 1;
//                System.out.println(count);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.KEY) {
            @Override
            protected void onCollision(Entity player, Entity key) {
                FXGL.play("Item_Get.wav");
                key.removeFromWorld();
                hasKey = true;
            }
        });

    }

    private void nextLevel(){
        count = 0;
        level_count += 1;
        map_level += 1;
        player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
        doors = exitDoorsTeleportLocations[level_count].length - 1;
        player.setZIndex(Integer.MAX_VALUE);

        setLevelFromMap(TMX_LEVEL+ map_level + ".tmx");
        spawnKey();

    }

    private void spawnKey(){
        switch(map_level){
            case 1:
                key = spawn("key", 100,450);
                break;
            case 2:
                key = spawn("key", 450,380);
                break;
            case 3:
                key = spawn("key", 450,0);
                break;
            case 4:
                key = spawn("key", 370,340);
                break;
        }
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void onUpdate(double tpf) {
        inc("levelTime", tpf);

        if (player.getY() > getAppHeight()) {
            onPlayerDied();
        }
    }

    public void onPlayerDied() {
        setLevelFromMap(TMX_LEVEL + map_level  + ".tmx");
        player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
        count = 0;
        spawnKey();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
