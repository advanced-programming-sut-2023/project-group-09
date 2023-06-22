package view.menus;

import controller.GameController;
import controller.MapController;
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
import model.human.military.Military;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import view.controllers.GameViewController;
import view.controllers.ViewController;

import java.net.URL;
import java.util.*;

//TODO 1- add color of buildings and government
public class GameMenu extends Application {
    public static Stage stage;
    public static Scene scene;
    public static StackPane root;
    public static MiniMap miniMap;
    public static GameMap gameMap;
    public static Pane menuBar;
    public static Text hoveringBarStateText;
    public static Rectangle selectedArea;
    public static Set<Tile> selectedTilesTroop = new HashSet<>();
    public static Set<GameTile> selectedTiles = new HashSet<>();
    public static Rectangle selectCursor;
    public static ImageView barImage;
    public static ArrayList<Transition> transitions = new ArrayList<>();
    public static ArrayList<Timeline> timelines = new ArrayList<>();
    public static GameTile currentTile;
    public static Timeline cursorTimeLine;
    public static boolean selectedUnit = false;
    public static GameTile startSelectionTile;
    public static GameTile endSelectionTile;
    public static boolean selectDone = false;
    public static boolean isSelected = false;
    public static String movingState = UnitMovingState.NORMAL.getState();

    public static Rectangle downRight;
    public static Rectangle upRight;
    public static Rectangle upLeft;
    public static Rectangle downLeft;
    public static Rectangle right;
    public static Rectangle up;
    public static Rectangle left;
    public static Rectangle down;
    //---------------------------------
    public static HashMap<String, Integer> unitsCount = new HashMap<>();
    public static HashSet<Military> selectedTroops = new HashSet<>();

    private static final double ZOOM_FACTOR = 1.1;

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


        MapController.dropMilitary(10, 5, "archerBow", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(12, 5, "slinger", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(13, 10, "slave", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(14, 5, "horseArcher", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(14, 9, "ladderman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(15, 5, "swordsman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(19, 11, "spearman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(15, 5, "engineer", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(13, 5, "archer", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(13, 7, "maceman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(13, 8, "pikeman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(15, 8, "fireThrower", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(15, 11, "tunneler", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(17, 5, "crossbowman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(17, 5, "blackmonk", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(17, 10, "knight", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(17, 12, "slinger", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(19, 10, "lord", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(14, 5, "arabianSwordsman", GameController.getGame().getCurrentGovernment());


        setEventListeners();
        GameViewController.setCenterOfBar();
        GameViewController.createBorderRectangles(gameMap, miniMap);

        root.setOnMouseClicked(mouseEvent -> {
            if (isSelected && !selectedUnit && mouseEvent.getScreenY() >= scene.getHeight() - 200) {
                for (GameTile gameTile : selectedTiles) {
                    gameTile.deselectTile();
                }
                GameMenu.startSelectionTile = null;
                GameMenu.endSelectionTile = null;
                GameMenu.isSelected = false;
                GameMenu.selectedTiles.clear();
            }
        });
        createSelectedArea();
        stage.show();
    }

    public static void createGameBar(int state) {
//        state: 0=buildings  /  1=nemidunam(farzam midune)  /  2=menu
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

        Rectangle clipRectangle = new Rectangle(1200, 800);
        root.setClip(clipRectangle);

        menuBar.setViewOrder(-2000);
        if (state == 0)
            GameViewController.createShortcutBars(menuBar, hoveringButton);
        else if (state == 1)
            GameViewController.createShortcutBars2(menuBar, hoveringButton);
        else if (state == 2) {
            ImageView menuBox = new ImageView(GameMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "menuEmptyBar.png");
            menuBox.setTranslateX(247);
            menuBox.setTranslateY(55);
            menuBox.setScaleY(1.08);
            menuBar.getChildren().add(menuBox);
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
            } else {
                root.getChildren().remove(selectCursor);
            }

        });
        gameMap.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() > 0 ? ZOOM_FACTOR : 1 / ZOOM_FACTOR;
            if (gameMap.getScaleX() * zoomFactor > 1 && gameMap.getScaleX() * zoomFactor < 1.5) {
                // Apply the scale and translation adjustments
                double newWidth = gameMap.getWidth() * gameMap.getScaleX() * zoomFactor;
                double newHeight = gameMap.getHeight() * gameMap.getScaleY() * zoomFactor;
                double lastWidth = gameMap.getWidth() * gameMap.getScaleX();
                double lastHeight = gameMap.getHeight() * gameMap.getScaleY();
                double translateX = (newWidth - lastWidth)/2;
                double translateY = (newHeight - lastHeight)/2;

                gameMap.setScaleX(gameMap.getScaleX() * zoomFactor);
                gameMap.setScaleY(gameMap.getScaleY() * zoomFactor);
                gameMap.setTranslateX(gameMap.getTranslateX() + translateX);
                gameMap.setTranslateY(gameMap.getTranslateY() + translateY);
                miniMap.updatePointer(gameMap.getScaleX());


            }

            event.consume();
        });
        scene.setOnKeyPressed(keyEvent -> {
            String keyName = keyEvent.getCode().getName();
            if (keyName.equals("N")) {
                movingState = UnitMovingState.NORMAL.getState();
            }
            if (keyName.equals("M")) {
                movingState = UnitMovingState.MOVE.getState();
            }
            if (keyName.equals("T")) {
                movingState = UnitMovingState.ATTACK.getState();
            }

            if (keyName.equals("A")) {
                movingState = UnitMovingState.AIR_ATTACK.getState();
            }

            if (keyName.equals("Down")) {
                if (GameViewController.lastType != null && GameViewController.lastType.count != 0) {
                    GameViewController.lastType.count--;
                    GameViewController.lastType.text.setText(GameViewController.lastType.count + "");
                    GameViewController.setSelectedUnits();
                }
            }

            if (keyName.equals("Up")) {
                if (GameViewController.lastType != null && GameViewController.lastType.count < unitsCount.get(GameViewController.lastType.name)) {
                    GameViewController.lastType.count++;
                    GameViewController.lastType.text.setText(GameViewController.lastType.count + "");
                    GameViewController.setSelectedUnits();
                }
            }

            if (keyName.equals("0")) {
                if (GameViewController.lastType != null) {
                    GameViewController.lastType.count = 0;
                    GameViewController.lastType.text.setText(GameViewController.lastType.count + "");
                    GameViewController.setSelectedUnits();
                }
            }

            if (keyName.equals("X")) {
                if (GameViewController.lastType != null) {
                    GameViewController.lastType.count = unitsCount.get(GameViewController.lastType.name);
                    GameViewController.lastType.text.setText(GameViewController.lastType.count + "");
                    GameViewController.setSelectedUnits();
                }
            }
        });
    }

    public static void setMouseCursorOnSelect() {
        if (cursorTimeLine != null) {
            cursorTimeLine.stop();
        }
        cursorTimeLine = new Timeline(new KeyFrame(Duration.millis(300), actionEvent -> {
            if (!selectedUnit) {
                cursorTimeLine.stop();
            }
            if (currentTile != null) {
                GameViewController.setSelectCursorState(currentTile);
            }

        }));
        timelines.add(cursorTimeLine);
        cursorTimeLine.setCycleCount(-1);
        cursorTimeLine.play();
    }

    private void createSelectedArea() {
        selectedArea = new Rectangle(0, 0);
        selectedArea.setViewOrder(-600);
        selectedArea.setVisible(false);
        selectedArea.setStyle("-fx-fill: white; -fx-opacity: 0.3;");

        final double[] startX = {0.0};
        final double[] startY = {0.0};
        gameMap.setOnDragDetected(mouseDragEvent -> {
            if (!selectedUnit) {
                if (GameMenu.isSelected) {
                    GameViewController.unselectTiles();
                }
                startSelectionTile = currentTile;
                startX[0] = mouseDragEvent.getScreenX();
                startY[0] = mouseDragEvent.getScreenY();
                if (startY[0] >= scene.getHeight() - 200) {
                    selectedArea.setWidth(0);
                    selectedArea.setHeight(0);
                    startX[0] = -1;
                    startY[0] = -1;
                    return;
                }
                if (startX[0] > 0) {
                    selectedArea.setTranslateX(startX[0] - scene.getWidth() / 2);
                    selectedArea.setTranslateY(startY[0] - scene.getHeight() / 2);
                }
            }

        });

        gameMap.setOnMouseDragged(mouseEvent -> {
            if (!selectedUnit) {
                if (GameMenu.isSelected) {
                    GameViewController.unselectTiles();
                }
                if (startX[0] < 0) {
                    selectedArea.setWidth(0);
                    selectedArea.setHeight(0);
                    return;
                }
                double endX = mouseEvent.getScreenX();
                double endY = mouseEvent.getScreenY();
                selectedArea.setWidth(Math.abs(endX - startX[0]));
                selectedArea.setHeight(Math.abs(endY - startY[0]));
                double rectangleX = Math.min(endX, startX[0]);
                double rectangleY = Math.min(endY, startY[0]);
                selectedArea.setTranslateX(rectangleX - scene.getWidth() / 2 + selectedArea.getWidth() / 2);
                selectedArea.setTranslateY(rectangleY - scene.getHeight() / 2 + selectedArea.getHeight() / 2);
                selectedArea.setVisible(true);

                gameMap.setOnMouseReleased(mouseEvent1 -> {
                    if (startX[0] > 0) {
                        selectDone = true;
                    }
                });
            }
        });

        Timeline selectDoneTimeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            if (selectDone) {
                endSelectionTile = currentTile;
                selectedArea.setVisible(false);
                selectedArea.setWidth(0);
                selectedArea.setHeight(0);
                Set<GameTile> tiles = GameController.getSelectedAreaTiles(startSelectionTile, endSelectionTile);
                if (tiles != null) {
                    if (tiles.size() == 0) {
                        selectDone = false;
                        return;
                    }
                    selectedTiles = tiles;
                    for (GameTile gameTile : selectedTiles) {
                        gameTile.selectTile();
                    }
                    selectDone = false;
                    isSelected = true;
                    if (selectedUnit) {
                        if (unitsCount.get("lord") == null || unitsCount.get("lord") != 1 || selectedTroops.size() != 1) {
                            if (unitsCount.get("lord") != null && unitsCount.get("lord") != 0) {
                                selectedTroops.removeIf(i -> i.getName().equals("lord"));
                                unitsCount.put("lord", 0);
                            }
                            GameMenu.hoveringBarStateText.setText("Unit Menu");
                            GameViewController.setCenterOfBar();
                        } else {
                            GameViewController.addTypes();
                            if (GameViewController.selectedMilitaries.size() != 0) {
                                Military military = GameViewController.selectedMilitaries.get(0);
                                HumanController.militaries.clear();
                                HumanController.militaries.add(military);
                            }
                        }
                    }
                }

            }
        }));
        selectDoneTimeline.setCycleCount(-1);
        selectDoneTimeline.play();

        root.getChildren().add(selectedArea);
    }

    @FXML
    public void initialize() {
    }
}
