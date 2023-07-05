package model.menugui;

import client.Packet;
import com.google.gson.GsonBuilder;
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
import model.FakeGame;
import model.User;
import model.game.Map;
import org.controlsfx.control.ToggleSwitch;
import view.Main;
import view.menus.CreateGameMenu;
import view.menus.Lobby;
import view.menus.LobbyMenu;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class LobbySetting extends Pane {

    public LobbyPasswordField passwordField;
    public ToggleSwitch publicGame;

    public Label error;

    public int id;
    public Button save;
    public FakeGame fakeGame;

    public LobbySetting(FakeGame fakeGame) throws IOException {
        this.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        this.setMaxWidth(500);
        this.setMinWidth(500);
        this.setMaxHeight(500);
        this.setMinHeight(500);
        this.setTranslateX(250);
        this.setTranslateY(150);
        Label label10 = new Label("Setting");
        label10.getStyleClass().add("request-label");
        label10.setMaxWidth(500);
        label10.setMinWidth(500);
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
            Lobby.root.getChildren().remove(Lobby.overPane);
        });
        this.fakeGame = fakeGame;
        backBtn.setTranslateX(20);
        backBtn.setTranslateY(20);
        this.getChildren().addAll(label10, backBtn);
        makeForm();
    }

    public void makeForm() {
        error = new Label();
        error.setTranslateX(100);
        error.setTranslateY(100);
        error.setStyle("-fx-text-fill: red");


        Label label = new Label("private: ");
        publicGame = new ToggleSwitch();
        publicGame.setTranslateX(200);
        publicGame.setTranslateY(200);

        if (fakeGame.isPrivate()){
            publicGame.setSelected(true);
            if (passwordField != null) {
                passwordField.removeFromPane();
            }
            passwordField = new LobbyPasswordField(this, "password...", "password: ", 200, 300);
            passwordField.setText(fakeGame.getPassword());
            this.getChildren().add(passwordField);
        }
        publicGame.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (passwordField != null) {
                    passwordField.removeFromPane();
                }
                passwordField = new LobbyPasswordField(this, "password...", "password: ", 200, 300);
                this.getChildren().add(passwordField);
            } else {
                if (passwordField != null) {
                    passwordField.removeFromPane();
                }
            }
        });
        label.setTranslateX(100);
        label.setTranslateY(200);
        save = new Button("save");
        save.getStyleClass().add("new-game-btn");
        save.setTranslateX(400);
        save.setTranslateY(400);
        save.setOnMouseClicked(mouseEvent -> {
            try {
                submit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().addAll( label, publicGame, save, error);
    }


    public void submit() throws Exception {
        if (publicGame.isSelected() && (passwordField.getValue() == null || passwordField.getValue().length() == 0)) {
            error.setText("please enter a password");
        } else {
            error.setText("");
            Packet packet = new Packet("update private game", "Game");
            packet.addAttribute("id", fakeGame.getGameId());
            packet.addAttribute("private", publicGame.isSelected());
            if (passwordField != null){
                packet.addAttribute("password", passwordField.getValue());
            }else {
                packet.addAttribute("password", "");

            }
            packet.sendPacket();
            Lobby.root.getChildren().remove(Lobby.overPane);
        }
    }


}
