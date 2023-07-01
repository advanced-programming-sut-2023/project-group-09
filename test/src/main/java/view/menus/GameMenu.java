package view.menus;

import controller.DBController;
import controller.GameController;
import controller.GovernmentController;
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
import model.Government;
import model.game.Map;
import model.game.Tile;
import model.human.military.EuropeanTroop;
import model.human.military.Military;
import model.menugui.MenuHoverBox;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;
import view.controllers.ViewController;
import view.menus.chat.ChatMenu;

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
    public static boolean selectedUnit = false;
    public static GameTile startSelectionTile;
    public static GameTile endSelectionTile;
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
        gameMap.loadMap();
        miniMap = new MiniMap(125, 143, 0, 0);


//        TODO: revert comment
        for (Government government : GameController.getGame().getGovernments()) {
            MapController.dropMilitary(government.getCastleX(), government.getCastleY() + 2, "lord", government);
            EuropeanTroop lordMilitary = (EuropeanTroop) GameController.getGame().getMap().
                    getTile(government.getCastleX(), government.getCastleY() + 2).getMilitaries().get(0);
            lordMilitary.setGovernment(government);
            government.setLord(lordMilitary);
            government.getMainCastle().makeUnemployed(10);
        }

        menuBar = new Pane();
        menuBar.setMaxWidth(1200);
        menuBar.setMaxHeight(220);
        root.getChildren().addAll(gameMap, menuBar);

        selectCursor = new Rectangle(50, 75);
//        TODO: revert comment
        selectCursor.setFill(new ImagePattern(GameImages.imageViews.get("selectMove")));
        Rectangle clipRectangle = new Rectangle(1200, 800);
        root.setClip(clipRectangle);
        MapController.dropMilitary(14, 5, "arabianSwordsman", GameController.getGame().getCurrentGovernment());
        MapController.dropMilitary(11, 5, "slave", GameController.getGame().getCurrentGovernment());

//        MapController.dropMilitary(20, 5, "slave", GameController.getGame().getGovernments().get(1));
//        MapController.dropMilitary(21, 5, "fireThrower", GameController.getGame().getGovernments().get(1));
//        MapController.dropMilitary(21, 5, "arabianSwordsman", GameController.getGame().getGovernments().get(1));
//        MapController.dropMilitary(22, 5, "arabianSwordsman", GameController.getGame().getGovernments().get(1));
//        MapController.dropMilitary(22, 5, "arabianSwordsman", GameController.getGame().getGovernments().get(1));

        //MapController.dropCivilian(10,10,GameController.getGame().getCurrentGovernment(),false);
        setEventListeners();
        GameViewController.setCenterOfBar();
        GameViewController.createBorderRectangles(gameMap, miniMap);
        attacking = new ImageView(new Image(GameTile.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm() +
                "icons/attacking.gif"));
        attacking.setViewOrder(-2000);
        attacking.setTranslateY(-350);
        attacking.setTranslateX(-550);

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

        ChatMenu chatMenu = new ChatMenu();
        GameMenu.root.getChildren().add(chatMenu);

        stage.show();
    }

    public static void setShieldsForGovernments() {
        int index = 0;
        for (Government government : GameController.getGame().getGovernments()) {
            ImageView shield = new ImageView(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                    + government.getColor() + "Flag.png");
            GameMenu.menuBar.getChildren().add(shield);
            shield.setScaleX(2);
            shield.setScaleY(2);
            shield.setTranslateY(100 + (index / 4) * 50);
            shield.setTranslateX(10 + 50 * (index % 4));
            shield.setOnMouseEntered(e -> {
                hoveringBarStateText.setText("Lord " + government.getUser().getNickname());
                if (government.isAlive()) {
                    shield.setImage(new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                            + government.getColor() + "BrightFlag.png"));
                } else {
                    shield.setImage(new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                            + "transparent" + "BrightFlag.png"));
                }
            });
            shield.setOnMouseExited(e -> {
                hoveringBarStateText.setText("");
                if (government.isAlive()) {
                    shield.setImage(new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                            + government.getColor() + "Flag.png"));
                } else {
                    shield.setImage(new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                            + "transparent" + "Flag.png"));
                }
            });
            shield.setOnMouseClicked(e -> {
                if (government.isAlive()) {
                    GovernmentController.setCurrentGovernment(government);
                    GameController.getGame().setCurrentGovernment(government);
                    GameMenu.menuBar.getChildren().clear();
                    createGameBar(0);
                    GameViewController.setCenterToCastleBuildings();
                }
            });
            index++;
        }
    }

    public static void createGameBar(int state) {
//        state: 0=buildings  /  1=nemidunam(farzam midune)  /  2=menu  /  3=mercenaryPost  /  4=barrack
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
        GameViewController.setBarForCurrentGovernment();                       // TODO: revert comment
        if (state == 0) {
            GameViewController.createShortcutBars(menuBar, hoveringButton);
            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(70);
        } else if (state == 1) {
            GameViewController.createShortcutBars2(menuBar, hoveringButton);
            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(70);
        } else if (state == 2) {
            ImageView menuBox = new ImageView(GameMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "menuEmptyBar.png");
            menuBox.setTranslateX(247);
            menuBox.setTranslateY(55);
            menuBox.setScaleY(1.08);
            menuBar.getChildren().add(menuBox);
            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(45);
        } else if (state == 3) {
            ImageView menuBox = new ImageView(GameMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "mercenaryPostBar.png");
            menuBox.setTranslateX(175);
            menuBox.setTranslateY(-30);
            menuBar.getChildren().add(menuBox);
            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(45);
        } else if (state == 4) {
            ImageView menuBox = new ImageView(GameMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "barrackBar.png");
            menuBox.setTranslateX(175);
            menuBox.setTranslateY(-25);
            System.out.println("width:" + menuBox.getImage().getWidth());
            menuBar.getChildren().add(menuBox);
            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(45);
        }
    }


    public static void setEventListeners() {
        root.setOnMouseMoved(mouseEvent -> {
            if (selectedUnit) {
                if (!root.getChildren().contains(selectCursor)) {
                    root.getChildren().add(selectCursor);
                }
                if (currentTile != null) {
                    HumanViewController.setSelectCursorState(currentTile);
                }
                selectCursor.setTranslateY(mouseEvent.getY() - 400 - selectCursor.getHeight() / 2);
                selectCursor.setTranslateX(mouseEvent.getX() - 600);
            } else {
                root.getChildren().remove(selectCursor);
            }

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

            if (keyName.equals("P")) {
                movingState = UnitMovingState.PATROL.getState();
            }
            if (keyName.equals("S")) {
                DBController.saveMap(GameController.getGame().getMap(), "src/main/resources/savedmaps/myMap.json");
            }

            if (keyName.equals("Down")) {
                if (HumanViewController.lastType != null && HumanViewController.lastType.count != 0) {
                    HumanViewController.lastType.count--;
                    HumanViewController.lastType.text.setText(HumanViewController.lastType.count + "");
                    HumanViewController.setSelectedUnits();
                }
            }

            if (keyName.equals("Up")) {
                if (HumanViewController.lastType != null && HumanViewController.lastType.count < unitsCount.get(HumanViewController.lastType.name)) {
                    HumanViewController.lastType.count++;
                    HumanViewController.lastType.text.setText(HumanViewController.lastType.count + "");
                    HumanViewController.setSelectedUnits();
                }
            }

            if (keyName.equals("0")) {
                if (HumanViewController.lastType != null) {
                    HumanViewController.lastType.count = 0;
                    HumanViewController.lastType.text.setText(HumanViewController.lastType.count + "");
                    HumanViewController.setSelectedUnits();
                }
            }

            if (keyName.equals("X")) {
                if (HumanViewController.lastType != null) {
                    HumanViewController.lastType.count = unitsCount.get(HumanViewController.lastType.name);
                    HumanViewController.lastType.text.setText(HumanViewController.lastType.count + "");
                    HumanViewController.setSelectedUnits();
                }
            }
        });
    }

    public static void showAttacking() {

        if (GameController.getGame().getCurrentGovernment().getNumberOfTroopInAttack().size() > 0) {
            if (root.getChildren().contains(attacking)) {
                return;
            }
            root.getChildren().add(attacking);
        } else {
            root.getChildren().remove(attacking);
        }
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
                            HumanViewController.addTypes();
                            if (HumanViewController.selectedMilitaries.size() != 0) {
                                Military military = HumanViewController.selectedMilitaries.get(0);
                                HumanController.militaries.clear();
                                HumanController.militaries.add(military);
                            }
                        }
                    }
                }
//                MenuHoverBox details = new MenuHoverBox(root, endSelectionTile.getX() - 450,
//                        endSelectionTile.getY() - 250, 300, 300, "Hello world");
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
