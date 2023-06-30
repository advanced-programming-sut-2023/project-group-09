package view.menus.profile;

import controller.UserController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;
import model.menugui.*;
import view.controllers.ViewController;
import view.menus.LoginMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ChangeSlogan extends Application {
    public static Stage stage;
    public static StackPane root;
    public static MenuBox menuBox;
    public static MenuTextField slogan;
    public static MenuButton submit;
    public static MenuFingerBack back;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        ChangeSlogan.stage = stage;
        user = controller.Application.getCurrentUser();
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/changeSlogan.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();
        menuBox = new MenuBox("change slogan",0,0,500,400);
        slogan = new MenuTextField(menuBox,"slogan...","slogan",50,-50, 300);
        submit = new MenuButton("save",menuBox,0,50,false);
        slogan.setText(user.getSlogan());
        menuBox.getChildren().addAll(slogan,submit);
        setEvents();
        back = new MenuFingerBack(-400,300);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().add(menuBox);
        root.getChildren().add(back);
        stage.show();
    }

    public void setEvents(){
        submit.setOnMouseClicked(mouseEvent -> {
            MenuPopUp menuPopUp = new MenuPopUp(root, 400, 400,
                    "success", UserController.changeSlogan(slogan.getText()));
            slogan.setText(user.getSlogan());
            root.getChildren().add(menuPopUp);
        });
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
