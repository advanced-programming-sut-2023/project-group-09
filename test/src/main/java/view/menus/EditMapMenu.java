package view.menus;

import controller.DBController;
import controller.GameController;
import controller.GovernmentController;
import controller.gamestructure.GameImages;
import controller.gamestructure.GameMaps;
import controller.human.HumanController;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Government;
import model.game.Map;
import model.game.Tile;
import model.human.military.Military;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;
import view.controllers.ViewController;
import view.menus.chat.ChatMenu;

import java.net.URL;
import java.util.*;

public class EditMapMenu extends Application {
        public static Stage stage;
        public static Scene scene;
        public static StackPane root;
        public static MiniMap miniMap;
        public static GameMap gameMap;
        public static Pane menuBar;
        public static Text hoveringBarStateText;
        public static Rectangle selectedArea;
        public static Rectangle selectCursor;
        public static ImageView barImage;
        public static ArrayList<Transition> transitions = new ArrayList<>();
        public static ArrayList<Timeline> timelines = new ArrayList<>();
        public static GameTile currentTile;
        public static boolean selectedUnit = false;
        public static GameTile startSelectionTile;
        public static GameTile endSelectionTile;

        public static Set<GameTile> selectedTiles = new HashSet<>();
        public static boolean selectDone = false;
        public static boolean isSelected = false;
        public static String movingState = UnitMovingState.NORMAL.getState();

        public static ImageView standing;
        public static ImageView defensive;
        public static ImageView aggressive;

        //---------------------------------
        public static HashMap<String, Integer> unitsCount = new HashMap<>();
        public static HashSet<Military> selectedTroops = new HashSet<>();
        public static ImageView attacking;

        //-----------------------------------


        @Override
        public void start(Stage stage) throws Exception {
            view.menus.EditMapMenu.stage = stage;
            BorderPane pane = FXMLLoader.load(
                    new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/gameMenu.fxml")).toExternalForm()));
            scene = new Scene(pane);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            root = ViewController.makeStackPaneScreen(stage, pane, 1200, 800);

            root.setOnMouseEntered(mouseEvent -> scene.setCursor(Cursor.DEFAULT));
            root.setOnMouseExited(mouseEvent -> scene.setCursor(Cursor.NONE));

            Map map = SharedMapMenu.selectedMap;
            gameMap = new GameMap(map, 0, 0, 30, 18 , true);
            gameMap.loadMap2();
            miniMap = new MiniMap(125, 143, 0, 0  ,true);

            menuBar = new Pane();
            menuBar.setMaxWidth(1200);
            menuBar.setMaxHeight(220);
            root.getChildren().addAll(gameMap, menuBar);
            createGameBar();

            stage.show();
        }


        public static void createGameBar() {
            barImage = new ImageView(GameImages.imageViews.get("bar"));
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
            menuBar.setViewOrder(-2000);

            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(70);
        }

        @FXML
        public void initialize() {
        }
    }