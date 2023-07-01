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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
import model.human.military.Military;
import model.menugui.MenuButton;
import model.menugui.MenuPopUp;
import model.menugui.MenuTextField;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;
import view.controllers.ViewController;
import view.menus.chat.ChatMenu;

import java.io.IOException;
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

        public static ImageView shieldIcon;
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
        public static MenuPopUp menuPopUp;

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

            scene.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode().getName().equals("S")) {
                    if (SharedMapMenu.selectedMap.getIndex() != 8) {
                        hoveringBarStateText.setText("You must select 8 government places!");
                    } else {
                        menuPopUp = new MenuPopUp(root , 400 , 400 , "Save?" ,
                                "Do you want to save your map?");
                        MenuButton saveButton = menuPopUp.addButton("Save" , 0 , 130);
                        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                menuPopUp.closePopUp();
                                menuPopUp = new MenuPopUp(root , 400 , 400 , "Select Name" ,
                                        "Select Map name");
                                root.getChildren().add(menuPopUp);
                                MenuButton menuButton = menuPopUp.addButton("Save" , 0 , 150);
                                MenuTextField menuTextField = menuPopUp.addTextField(0 , 50);
                                menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {
                                        try {
                                            ArrayList<String> mapNames = MapController.getMapNamesFromServer();
                                            if (mapNames.contains(menuTextField.getText())) {
                                                menuPopUp.changeMessage("This name exists!");
                                            } else {
                                                MapController.saveMap(menuTextField.getText());
                                            }
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }

                                    }
                                });
                            }
                        });

                        MenuButton dontSaveButton = menuPopUp.addButton("Don't save and close" ,
                                0 , 170);
                        dontSaveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                try {
                                    SharedMapMenu.selectedMap = null;
                                    new SharedMapMenu().start(stage);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        root.getChildren().add(menuPopUp);
                    }
                }
            });

            root.setOnMouseEntered(mouseEvent -> scene.setCursor(Cursor.DEFAULT));
            root.setOnMouseExited(mouseEvent -> scene.setCursor(Cursor.NONE));

            Map map = SharedMapMenu.selectedMap;
            gameMap = new GameMap(map, 0, 0, 30, 18 , true);
            gameMap.loadMap2();
            miniMap = new MiniMap(125, 143, 0, 0  ,true);
            miniMap.setGameMap(gameMap);
            menuBar = new Pane();
            menuBar.setMaxWidth(1200);
            menuBar.setMaxHeight(220);
            createGameBar();
            root.getChildren().addAll(gameMap, menuBar);
            Rectangle clipRectangle = new Rectangle(1200, 800);
            root.setClip(clipRectangle);
            GameViewController.createBorderRectangles(gameMap, miniMap,root);
            stage.show();
        }


        public static void createGameBar() {
            System.out.println("game bar" + GameImages.imageViews.get("bar"));
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
            GameViewController.createShortcutBars3(menuBar, hoveringButton);
            setShield();
            hoveringButton.setTranslateX(275);
            hoveringButton.setTranslateY(70);
        }

    public static void setShield() {
        ImageView shield = new ImageView(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm() + "transparentFlag.png");
        EditMapMenu.menuBar.getChildren().add(shield);
        shield.setScaleX(2);
        shield.setScaleY(2);
        shield.setTranslateY(100);
        shield.setTranslateX(10);
        shield.setOnMouseEntered(e -> {
            hoveringBarStateText.setText("Select Main Castle Position");
            shield.setImage(new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                    + "transparent" + "BrightFlag.png"));
        });
        shield.setOnMouseExited(e -> {
            hoveringBarStateText.setText("");
            shield.setImage(new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                    + "transparent" + "Flag.png"));
        });

        Image image = new Image(GameMenu.class.getResource(Paths.FLAG_IMAGES.getPath()).toExternalForm()
                + "transparent" + "Flag.png");
        ImageView imageView = new ImageView(image);
        imageView.setViewOrder(-500);
        shield.setOnMouseDragged(mouseEvent -> {
            if (!EditMapMenu.gameMap.getChildren().contains(imageView))
                EditMapMenu.gameMap.getChildren().add(imageView);
            imageView.setTranslateX(EditMapMenu.gameMap.getCameraX() * GameMap.tileWidth +
                    mouseEvent.getScreenX() - (EditMapMenu.scene.getWidth() - 1200) / 2 - image.getWidth() *
                    (0.5));
            imageView.setTranslateY(EditMapMenu.gameMap.getCameraY() * GameMap.tileHeight / 2 +
                    mouseEvent.getScreenY() - (EditMapMenu.scene.getHeight() - 800) / 2 - image.getHeight());
            imageView.setOpacity(0.6);
            shield.setOnMouseReleased(mouseEvent1 -> {
                EditMapMenu.gameMap.getChildren().remove(imageView);
                GameViewController.dropShieldAfterSelectingTile(mouseEvent1);
            });
        });

    }

        @FXML
        public void initialize() {
        }
    }