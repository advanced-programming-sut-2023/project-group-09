package model.menugui;

import client.Packet;
import com.google.gson.GsonBuilder;
import controller.GameController;
import controller.MapController;
import enumeration.Paths;
import enumeration.dictionary.Colors;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import model.User;
import model.game.Game;
import model.game.Map;
import org.controlsfx.control.ToggleSwitch;
import view.Main;
import view.menus.CreateGameMenu;
import view.menus.LobbyMenu;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class MakeNewGame extends Pane {

    public TextField gameName;
    public ComboBox<Integer> countOfPlayer;
    public ToggleSwitch publicGame;

    public Label error;

    private ArrayList<Colors> colors = new ArrayList<>();
    public ArrayList<MenuFlag> castleFlags = new ArrayList<>();
    public MenuChoiceBox mapsField;



    public int id;
    public Button makeGame;
    public PreviewMap previewMap;

    public MakeNewGame() {
        this.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        this.setMaxWidth(800);
        this.setMinWidth(800);
        this.setMaxHeight(600);
        this.setMinHeight(600);
        this.setTranslateX(150);
        this.setTranslateY(100);
        Label label10 = new Label("New Game");
        label10.getStyleClass().add("request-label");
        label10.setMaxWidth(800);
        label10.setMinWidth(800);
        label10.setMaxHeight(40);
        label10.setMaxHeight(40);
        label10.setTranslateY(15);
        label10.setTextAlignment(TextAlignment.CENTER);
        label10.setAlignment(Pos.CENTER);
        label10.setPadding(new Insets(10, 0, 10, 0));
        Rectangle backBtn = new Rectangle(20, 20);
        backBtn.setFill(new ImagePattern(new Image(
                getClass().getResource(Paths.ICONS.getPath()).toExternalForm() + "back.png"
        )));
        backBtn.setOnMouseClicked(mouseEvent -> {
            LobbyMenu.stackPane.getChildren().remove(LobbyMenu.makeNewGamePane);
        });
        backBtn.setTranslateX(20);
        backBtn.setTranslateY(20);
        this.getChildren().addAll(label10, backBtn);
        makeForm();
    }

    public void makeForm(){
        error = new Label();
        error.setTranslateX(100);
        error.setTranslateY(100);
        error.setStyle("-fx-text-fill: red");

        Label label1 = new Label("name: ");
        gameName = new TextField();
        gameName.setPromptText("game name...");
        countOfPlayer = new ComboBox<>();
        countOfPlayer.getItems().addAll(
                2,3,4,5,6,7,8
        );
        Label label = new Label("private: ");
        publicGame = new ToggleSwitch();
        gameName.getStyleClass().add("game-name");
        gameName.setTranslateX(200);
        gameName.setTranslateY(150);
        label1.setTranslateX(100);
        label1.setTranslateY(162.5);
        countOfPlayer.setTranslateX(250);
        countOfPlayer.setTranslateY(250);
        Label label2 = new Label("Number of players: ");
        label2.setTranslateX(100);
        label2.setTranslateY(250);
        publicGame.setTranslateX(200);
        publicGame.setTranslateY(300);

        label.setTranslateX(100);
        label.setTranslateY(300);
        makeGame = new Button("create");
        makeGame.getStyleClass().add("new-game-btn");
        makeGame.setTranslateX(700);
        makeGame.setTranslateY(500);
        this.getChildren().addAll(gameName,countOfPlayer,label,label1,label2,publicGame,makeGame,error);
    }

    public void submit(){
        if (gameName.getText() == null || gameName.getText().length() ==0){
            error.setText("please enter a name for your game");
        }else if (countOfPlayer.getValue() == null){
            error.setText("please select the number of players");
        }else{
            error.setText("");
        }
    }

    private void checkSelectedMap() {
//        mapsField.valueProperty().addListener((observableValue, oldValue, newValue) -> {
//
//                Packet currentUser = new Packet("current user");
//                try {
//                    currentUser.setToken(Main.connection.getToken());
//                    currentUser.sendPacket();
//                    CreateGameMenu.currentUser = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).
//                            create().fromJson((String) Packet.receivePacket().
//                                    getAttribute("user"), User.class);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                governmentUsernames.get(0).setText(CreateGameMenu.currentUser.getUsername());
//                governmentUsernames.get(0).setEditable(false);
//                addGovernment.setDisable(false);
//                castles = new ArrayList<>();
//                Map selectedMap = null;
//                try {
//                    selectedMap = MapController.getMapFromServer(mapsField.getValue());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//                menuBox.getChildren().remove(previewMap);
//                previewMap = new PreviewMap(selectedMap, 230, -120);
//                menuBox.getChildren().add(previewMap);
//                for (int i = 0; i < 8; i++) {
//                    castleFlags.get(i).setTranslateX(selectedMap.getDefaultCastles().get(i).getFirst() - selectedMap.getWidth() / 2);
//                    castleFlags.get(i).setTranslateY(selectedMap.getDefaultCastles().get(i).getSecond() - selectedMap.getWidth() / 2);
//                    castleFlags.get(i).setVisible(true);
//                    previewMap.getChildren().add(castleFlags.get(i));
//                    castles.add("Castle " + (i + 1));
//                    selectCastle(castleFlags.get(i));
//                }
//                castleNumbers.get(0).setItems(FXCollections.observableArrayList(castles));
//                MapController.map = selectedMap;
//                game = new Game(selectedMap);
//                Packet newGame = new Packet("new game");
//                try {
//                    newGame.addAttribute("mapNumber", mapsField.getValue());
//                    newGame.addAttribute("game", new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create().toJson(game));
//                    newGame.sendPacket();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                GameController.setGame(game);
//            }
//        });
    }

}
