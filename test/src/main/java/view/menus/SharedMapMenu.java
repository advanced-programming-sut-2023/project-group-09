package view.menus;

import controller.GameController;
import controller.MapController;
import enumeration.dictionary.Colors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.game.Game;
import model.game.Map;
import model.menugui.*;
import view.controllers.ViewController;

import java.io.IOException;
import java.util.ArrayList;

public class SharedMapMenu extends Application {

        private Stage stage;
        public static Pane createGamePane;
        private Game game;
        public static Map selectedMap;
        public Label owner;
        private ArrayList<String> castles;
        private ArrayList<Colors> colors;
        public MenuBox menuBox;
        public MenuChoiceBox mapsField;
        public PreviewMap previewMap;
        public Text governmentTitle;
        public Button addGovernment;
        public static User currentUser;
        public ArrayList<MenuTextField> governmentUsernames = new ArrayList<>();
        public ArrayList<MenuChoiceBox> governmentColors = new ArrayList<>();
        public ArrayList<MenuChoiceBox> castleNumbers = new ArrayList<>();
        public ArrayList<MenuFlag> castleFlags = new ArrayList<>();
        public ArrayList<String> addedGovernments = new ArrayList<>();
        public MenuButton startGame;
        public int governmentNumber;

        @Override
        public void start(Stage stage) throws Exception {
            this.stage = stage;
            Pane root = FXMLLoader.load(getClass().getResource("/FXML/createGameMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            createGamePane = ViewController.makePaneScreen(stage, root, 1000, -1);


            menuBox = new MenuBox("Create Map", 365, 100, 800, 700);
            root.getChildren().add(menuBox);

            makeTitleStuff();
            makeGovernmentStuff();
            checkSelectedMap();
            makeBackButton();
            makeCreateButton(root);

            stage.setTitle("Create Map");

            stage.show();
        }

        private void makeCreateButton(Pane root) {
            MenuButton menuButton = new MenuButton("Create!" , root,400 , 500 , false);
            menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        new EditMapMenu().start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            root.getChildren().add(menuButton);
        }

        private void makeTitleStuff() throws IOException {
            ArrayList<String> mapNames = MapController.getMapNamesFromServer();
            mapsField = new MenuChoiceBox(menuBox, "Map", -90, -250,
                    FXCollections.observableArrayList(mapNames), 300);
            menuBox.getChildren().add(mapsField);

            owner = new Label();
            owner.setTranslateX(-90);
            owner.setTranslateY(-200);
            owner.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 25));
            menuBox.getChildren().add(owner);
        }

        private void makeGovernmentStuff() {
            for (int i = 1; i <= 8; i++) {
                MenuFlag flag = new MenuFlag("transparent");
                flag.setVisible(false);
                castleFlags.add(flag);
            }
        }

        private void makeBackButton() {
            MenuFingerBack back = new MenuFingerBack(-450, 350);
            back.setScaleX(0.7);
            back.setScaleY(0.7);
            back.setOnAction(actionEvent -> {
                MapController.map = null;
                game = null;
                GameController.setGame(null);
                MainMenu mainMenu = new MainMenu();
                try {
                    mainMenu.start(this.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            menuBox.getChildren().add(back);
        }

        private void checkSelectedMap() {
            mapsField.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue.equals("")) {
                } else {
//                TODO: better to get from server
                    try {
                        selectedMap = MapController.getMapFromServer(mapsField.getValue());
                        owner.setText("Owner: " + selectedMap.getOwner());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (selectedMap != null) {
                        menuBox.getChildren().remove(previewMap);
                        previewMap = new PreviewMap(selectedMap, 230, -120);
                        menuBox.getChildren().add(previewMap);
                        for (int i = 0; i < selectedMap.getIndex(); i++) {
                            castleFlags.get(i).setTranslateX(selectedMap.getDefaultCastleX(i) - selectedMap.getWidth() / 2);
                            castleFlags.get(i).setTranslateY(selectedMap.getDefaultCastleY(i) - selectedMap.getWidth() / 2);
                            castleFlags.get(i).setVisible(true);
                            previewMap.getChildren().add(castleFlags.get(i));
                        }
                        MapController.map = selectedMap;
                    }
                }
            });
        }


}
