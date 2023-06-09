package view.menus;

import controller.GameController;
import controller.MapController;
import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameImages;
import controller.gamestructure.GameMaps;
import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.game.Map;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import view.controllers.GameViewController;
import view.controllers.ViewController;

import java.net.URL;
import java.util.Objects;

//TODO 1- add color of buildings and government
public class GameMenu extends Application {
    public static Stage stage;
    public static Scene scene;
    public static StackPane root;
    public static MiniMap miniMap;
    public static GameMap gameMap;
    public static Pane menuBar;
    public static Text hoveringBarStateText;

    @Override
    public void start(Stage stage) throws Exception {
        GameMenu.stage = stage;
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/gameMenu.fxml")).toExternalForm()));
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        root = ViewController.makeStackPaneScreen(stage, pane, 1200, 800);

        root.setOnMouseEntered(mouseEvent -> scene.setCursor(Cursor.DEFAULT));
        root.setOnMouseExited(mouseEvent -> scene.setCursor(Cursor.NONE));
        GameMaps.createMap1();
        Map map = GameMaps.largeMaps.get(0);
        gameMap = new GameMap(map, 0, 0, 30, 18);
        miniMap = new MiniMap(125, 143, 0, 0);
        menuBar = new Pane();
        menuBar.setMaxWidth(1200);
        menuBar.setMaxHeight(220);
        root.getChildren().addAll(gameMap, menuBar);
        MapController.dropMilitary(10,5,"swordsman", GameController.getGame().getCurrentGovernment());
        GameViewController.setCenterOfBar();
        GameViewController.createBorderRectangles(gameMap, miniMap);
        stage.show();
    }

    public static void createGameBar(boolean doFirst) {
        ImageView barImage = new ImageView(new Image(LoginMenu.class.getResource
                (Paths.BAR_IMAGES.getPath()).toExternalForm() + "bar.png"));
        barImage.setFitWidth(menuBar.getMaxWidth());
        barImage.setFitHeight(menuBar.getMaxHeight());
        menuBar.setTranslateX(0);
        menuBar.setTranslateY(290);

        menuBar.getChildren().add(miniMap);
        miniMap.setTranslateY(66);
        miniMap.setTranslateX(813);
        menuBar.getChildren().add(barImage);
        Text hoveringButton = new Text("");
        hoveringButton.setTranslateX(275);
        hoveringButton.setTranslateY(70);
        hoveringButton.setFill(Color.WHITE);
        hoveringButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        hoveringButton.setStrokeWidth(0.5);
        hoveringButton.setStroke(Color.BLACK);
        menuBar.getChildren().add(hoveringButton);
        hoveringBarStateText = hoveringButton;

        Rectangle clipRectangle = new Rectangle(1200, 800);
        root.setClip(clipRectangle);

        if (doFirst) {
            GameViewController.createShortcutBars(menuBar, hoveringButton);
        } else {
            GameViewController.createShortcutBars2(menuBar , hoveringButton);
        }
    }

    @FXML
    public void initialize() {
    }
}
