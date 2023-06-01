package view.menus;

import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.menugui.MenuButton;
import view.controllers.GameViewController;
import view.controllers.ViewController;

import java.net.URL;
import java.util.Objects;

public class GameMenu extends Application {
    public static Stage stage;
    public static Pane gamePane;
    public static Text hoveringBarStateText;

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        Pane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/gameMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        gamePane = pane;



        createGameBar();
        stage.show();
    }

    private void createGameBar() {
        ImageView barImage = new ImageView(new Image(LoginMenu.class.getResource
                (Paths.BAR_IMAGES.getPath()).toExternalForm() + "bar.png"));
        barImage.setTranslateX(0);
        barImage.setTranslateY(605);
        gamePane.getChildren().add(barImage);

        Text hoveringButton = new Text("");
        hoveringButton.setTranslateX(425);
        hoveringButton.setTranslateY(665);
        hoveringButton.setFill(Color.WHITE);
        hoveringButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        hoveringButton.setStrokeWidth(0.5);
        hoveringButton.setStroke(Color.BLACK);
        gamePane.getChildren().add(hoveringButton);
        hoveringBarStateText = hoveringButton;

        GameViewController.createShortcutBars(gamePane , hoveringButton);
    }




    @FXML
    public void initialize() {
    }
}
