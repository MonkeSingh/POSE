package game;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    private AnimatedTexture texture;

    private AnimationChannel animIdle, animWalk;

    private int jumps = 1;

    public PlayerComponent() {

        Image image = image("Larry1.png");

        animIdle = new AnimationChannel(image, 4, 48, 73, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(image, 4, 48, 73, Duration.seconds(0.66), 0, 3);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 1;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        AnimationChannel currentChannel = texture.getAnimationChannel();
        AnimationChannel targetChannel;

        if (physics.isMovingX()) {
            targetChannel = animWalk;
        } else {
            targetChannel = animIdle;
        }

        if (currentChannel != targetChannel) {
            texture.loopAnimationChannel(targetChannel);
        }
    }

    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-170);
    }

    public void right() {
        getEntity().setScaleX(1);
        physics.setVelocityX(170);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-300);

        jumps--;
    }

    public void reset(){
        physics.setLinearVelocity(0, 0);
        physics.setAngularVelocity(0);
    }
}
