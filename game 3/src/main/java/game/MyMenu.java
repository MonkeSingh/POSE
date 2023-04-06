package game;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameController;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class MyMenu extends FXGLMenu {

    public Highscores highscoreManager = new Highscores();

    public String message;


    public MyMenu(MenuType type) {

        super(MenuType.GAME_MENU);

        ImageView imageView = new ImageView(new Image("assets/textures/backgroundMenu.png"));
        imageView.setFitHeight(FXGL.getAppHeight());
        imageView.setFitWidth(FXGL.getAppWidth());


        Font font = Font.font(20);
        Button btnstart = new Button("New Game");
        btnstart.setOnAction(e -> fireNewGame());
        btnstart.setFont(font);

        Button btnscore = new Button("Scoreboard");
        List<Highscores.Score> a = highscoreManager.getHighScores();

//        for (int i = 0; i < a.size(); i++){
//            String name = a.get(i).getName();
//            double time = a.get(i).getSeconds();
//            System.out.println(name);
//
//            message += "- " + name + ": " +  time + "seconden\n";
//        }
//
//        message = message.replace("null", "");

        VBox content = new VBox(
                getUIFactoryService().newText(getHighScores(a))

        );

        Button btnClose = getUIFactoryService().newButton("Press me to close");

        btnClose.setOnAction(e -> getGameController().gotoMainMenu());
        btnClose.setPrefWidth(350);

        btnscore.setOnAction(e -> FXGL.getDialogService().showBox("**HIGHSCORES**", content, btnClose));

//        System.out.println(a);
//        btnscore.setOnAction(e -> FXGL.showMessage("*** Highscores ***\n" + message));

        btnscore.setFont(font);

        Button btnexit = new Button("Exit Game");
        btnexit.setOnAction(e -> fireExit());
        btnexit.setFont(font);

        VBox vbox = new VBox(btnstart, btnscore, btnexit);

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        getContentRoot().getChildren().addAll(new StackPane(imageView, vbox));

    }

    private String getHighScores(List<Highscores.Score> a){

        for (int i = 0; i < a.size(); i++){
            String name = a.get(i).getName();
            double time = a.get(i).getSeconds();
            System.out.println(name);

            message += "- " + name + ": " +  time + "seconden\n";
        }

        message = message.replace("null", "");
        return message;

    }

}