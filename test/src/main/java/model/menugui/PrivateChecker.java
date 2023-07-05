package model.menugui;

import client.Packet;
import enumeration.Paths;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import model.FakeGame;
import org.controlsfx.control.ToggleSwitch;
import view.menus.Lobby;
import view.menus.LobbyMenu;

import java.io.IOException;

public class PrivateChecker extends Pane {

    public LobbyPasswordField passwordField;

    public Label error;

    public int id;
    public Button submit;
    public FakeGame fakeGame;

    public PrivateChecker(FakeGame fakeGame) throws IOException {
        this.setStyle("-fx-background-color: #fff;-fx-background-radius: 10");
        this.setMaxWidth(500);
        this.setMinWidth(500);
        this.setMaxHeight(500);
        this.setMinHeight(500);
        this.setTranslateX(250);
        this.setTranslateY(150);
        Label label10 = new Label("Private Game");
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
            try {
                new LobbyMenu().start(Lobby.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.fakeGame = fakeGame;
        backBtn.setTranslateX(20);
        backBtn.setTranslateY(30);
        this.getChildren().addAll(label10, backBtn);
        makeForm();
    }

    public void makeForm() {
        error = new Label();
        error.setTranslateX(100);
        error.setTranslateY(100);
        error.setStyle("-fx-text-fill: red");
        if (passwordField != null) {
            passwordField.removeFromPane();
        }
        passwordField = new LobbyPasswordField(this, "password...", "password: ", 200, 200);
        this.getChildren().add(passwordField);
        submit = new Button("submit");
        submit.getStyleClass().add("new-game-btn");
        submit.setTranslateX(300);
        submit.setTranslateY(400);
        submit.setOnMouseClicked(mouseEvent -> {
            try {
                submit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().addAll(submit, error);
    }


    public void submit() {
        if ((passwordField.getValue() == null || passwordField.getValue().length() == 0)) {
            error.setText("please enter a password!");
        } else if (!passwordField.getValue().equals(fakeGame.getPassword())){
            error.setText("your password is wrong!");
        }else {
            Lobby.overPane.getChildren().remove(Lobby.privateChecker);
        }
    }


}
