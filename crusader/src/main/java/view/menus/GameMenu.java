package view.menus;

import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.menugui.MiniMap;
import view.controllers.GameViewController;
import view.controllers.ViewController;

import java.net.URL;
import java.util.Objects;

//TODO 1- add color of buildings and government
public class GameMenu extends Application {
    public static Stage stage;
    public static Scene scene;
    public static Pane gamePane;
    public static Text hoveringBarStateText;

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        Pane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/gameMenu.fxml")).toExternalForm()));
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        gamePane = pane;

        createGameBar();
        stage.show();
    }

    private void createGameBar() {
        ImageView barImage = new ImageView(new Image(LoginMenu.class.getResource
                (Paths.BAR_IMAGES.getPath()).toExternalForm() + "bar.png"));
        double translateYValue = ViewController.getScreenHeight() - barImage.getImage().getHeight() + 50;
        barImage.setTranslateX(0);
        barImage.setTranslateY(translateYValue);

        MiniMap miniMap = new MiniMap(125,147,0,0);
        gamePane.getChildren().add(miniMap);
        miniMap.setTranslateY(translateYValue + 69);
        miniMap.setTranslateX(906);
        gamePane.getChildren().add(barImage);
        Text hoveringButton = new Text("");
        hoveringButton.setTranslateX(375);
        hoveringButton.setTranslateY(translateYValue + 70);
        hoveringButton.setFill(Color.WHITE);
        hoveringButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        hoveringButton.setStrokeWidth(0.5);
        hoveringButton.setStroke(Color.BLACK);
        gamePane.getChildren().add(hoveringButton);
        hoveringBarStateText = hoveringButton;
        GameViewController.createShortcutBars(gamePane, hoveringButton);
    }

    @FXML
    public void initialize() {
    }
}
