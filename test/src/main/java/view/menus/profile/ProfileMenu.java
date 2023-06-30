package view.menus.profile;

import enumeration.Paths;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.menugui.ChangePasswordDialog;
import model.menugui.MenuButton;
import model.menugui.MenuFingerBack;
import view.controllers.ViewController;
import view.menus.LoginMenu;
import view.menus.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ProfileMenu extends Application {
    public static Stage stage;
    public static Pane root;
    public static MenuButton changeUsername;
    public static MenuButton changeNickname;
    public static MenuButton changePassword;
    public static MenuButton profile;
    public static MenuButton changeEmail;
    public static MenuButton changeSlogan;
    public static MenuButton changeAvatar;
    public static MenuButton friends;
    public static MenuFingerBack back;


    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        makeScene();
    }

    @FXML
    public void initialize() {

    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/profileMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();
        profile = new MenuButton("profile",root,0,-300,true);
        changeUsername = new MenuButton("change username",root,0,-220,true);
        changePassword = new MenuButton("change password",root,0,-140,true);
        changeEmail = new MenuButton("change email",root,0,-60,true);
        changeSlogan = new MenuButton("change slogan",root,0,20,true);
        changeNickname = new MenuButton("change nickname",root,0,100,true);
        friends = new MenuButton("friends",root,0,180,true);
        changeAvatar = new MenuButton("change avatar",root,0,260,true);
        back = new MenuFingerBack(-400,300);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        setButtonsEvent();
        root.getChildren().add(back);
        stage.show();
    }

    public void setButtonsEvent(){
        changeUsername.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeUsername().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        changeNickname.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeNickname().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        changeSlogan.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeSlogan().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        changeEmail.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeEmail().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        changeAvatar.setOnMouseClicked(mouseEvent -> {
            try {
                new ChangeAvatar().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        friends.setOnMouseClicked(mouseEvent -> {
            try {
                new FriendMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        profile.setOnMouseClicked(mouseEvent -> {
            try {
                new ShowDetails().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        changePassword.setOnMouseClicked(mouseEvent -> {
            try {
                root.getChildren().add(new ChangePasswordDialog(root));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        root.getChildren().add(profile);
        root.getChildren().add(changeUsername);
        root.getChildren().add(changeNickname);
        root.getChildren().add(changeEmail);
        root.getChildren().add(changePassword);
        root.getChildren().add(changeSlogan);
        root.getChildren().add(friends);
        root.getChildren().add(changeAvatar);
    }
    public void setBackground(){
        BackgroundImage backgroundImage =
                new BackgroundImage( new Image( getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "01.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }
}
