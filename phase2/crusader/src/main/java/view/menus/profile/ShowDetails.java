package view.menus.profile;

import controller.UserController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.User;
import model.menugui.MenuBox;
import model.menugui.MenuFingerBack;
import view.controllers.ViewController;
import view.menus.LoginMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ShowDetails extends Application {
    public static Stage stage;
    public static StackPane root;
    public static MenuBox menuBox;
    public static Rectangle profileImage;
    public static Label username;
    public static Label nickname;
    public static Label email;
    public static Label slogan;
    public static Label highScore;
    public static MenuFingerBack back;
    public static Label rank;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        ShowDetails.stage = stage;
        user = controller.Application.getCurrentUser();
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/showDetails.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();
        profileImage= new Rectangle(0,-200,100,100);
        profileImage.setFill(new ImagePattern(new Image(String.valueOf(new File(user.getPath()).toURI()))));
        profileImage.setArcHeight(100);
        profileImage.setArcWidth(100);
        profileImage.setStroke(Color.DARKRED);
        profileImage.setStrokeWidth(5);
        profileImage.setTranslateY(-80);

        profileImage.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeAvatar().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        menuBox = new MenuBox("profile", 0, 0, 500, 400);
        menuBox.box.setStyle("-fx-fill: #fff");
        username = new Label("username: " + user.getUsername());
        nickname = new Label("nickname: " + user.getNickname());
        email = new Label("email: " + user.getEmail());
        slogan = new Label("slogan: " + ((user.getSlogan() == null || user.getSlogan().equals("")) ? "Slogan is empty!" : user.getSlogan()));
        highScore = new Label("highscore: " + user.getHighScore());
        rank = new Label("rank: " + UserController.getRank());
        setEvents();
        menuBox.getChildren().addAll(profileImage,username,nickname,email,slogan,rank,highScore);
        back = new MenuFingerBack(-400,300);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        username.setTranslateY(0);
        nickname.setTranslateY(30);
        email.setTranslateY(60);
        slogan.setTranslateY(90);
        highScore.setTranslateY(120);
        rank.setTranslateY(150);
        root.getChildren().add(menuBox);
        root.getChildren().add(back);
        stage.show();
    }

    public void setEvents() {
        username.getStyleClass().add("btn-label");
        nickname.getStyleClass().add("btn-label");
        email.getStyleClass().add("btn-label");
        slogan.getStyleClass().add("btn-label");
        highScore.getStyleClass().add("btn-label");
        rank.getStyleClass().add("btn-label");
        username.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeUsername().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        nickname.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeNickname().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        email.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeEmail().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        highScore.setOnMouseClicked(mouseEvent -> {
            try {
                new Scoreboard().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        rank.setOnMouseClicked(mouseEvent -> {
            try {
                new Scoreboard().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "01.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }
}
