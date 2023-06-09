package view.menus;

import controller.GameController;
import controller.MapController;
import controller.gamestructure.GameImages;
import controller.gamestructure.GameMaps;
import enumeration.Paths;
import enumeration.UnitMovingState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.game.Map;
import model.game.Tile;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import view.controllers.GameViewController;
import view.controllers.ViewController;

import java.net.URL;
import java.util.ArrayList;
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
    public static ArrayList<Tile> selectedTilesTroop = new ArrayList<>();
    public static Rectangle selectCursor;

    public static ArrayList<Transition> transitions = new ArrayList<>();
    public static ArrayList<Timeline> timelines = new ArrayList<>();
    public static GameTile nowTile;
    public static Timeline cursorTimeLine;
    public static boolean selectedUnit = false;

    public static String movingState = UnitMovingState.NORMAL.getState();

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
        selectCursor = new Rectangle(50, 75);
        selectCursor.setFill(new ImagePattern(GameImages.imageViews.get("selectMove")));


        MapController.dropMilitary(10, 5, "swordsman", GameController.getGame().getCurrentGovernment());
        setEventListeners();
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


    public void setEventListeners() {
        root.setOnMouseMoved(mouseEvent -> {
            if (selectedUnit) {
                if (!root.getChildren().contains(selectCursor)) {
                    root.getChildren().add(selectCursor);
                }
                selectCursor.setTranslateY(mouseEvent.getY() - 400 - selectCursor.getHeight() / 2);
                selectCursor.setTranslateX(mouseEvent.getX() - 600);
            }
        });
    }

    public void setMouseCursorOnSelect() {
        cursorTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), actionEvent -> {
            if (!selectedUnit) {
                cursorTimeLine.stop();
            }
            if (nowTile != null){
                GameViewController.setSelectCursorState(nowTile);
            }

        }));
        timelines.add(cursorTimeLine);
        cursorTimeLine.setCycleCount(-1);
        cursorTimeLine.play();
    }

    @FXML
    public void initialize() {
    }
}
