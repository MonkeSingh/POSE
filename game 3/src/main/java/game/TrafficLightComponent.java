package game;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class TrafficLightComponent extends Component {

    private Rectangle light;
    private boolean isGreen = false;
    private double timeElapsed = 0.0;
    private double switchInterval;
    private Random random;

    public TrafficLightComponent(double switchInterval) {
        this.switchInterval = switchInterval;
        this.random = new Random();
    }

    @Override
    public void onAdded() {
        light = new Rectangle(150, 140, Color.RED);
        entity.getViewComponent().addChild(light);
    }

    @Override
    public void onUpdate(double tpf) {
        timeElapsed += tpf;

        if (timeElapsed >= switchInterval) {
            timeElapsed = 0.0;

            if (isGreen) {
                light.setFill(Color.RED);
                isGreen = false;
            } else {
                light.setFill(Color.GREEN);
                isGreen = true;
            }
        }
    }

    public boolean isGreen() {
        return isGreen;
    }
}