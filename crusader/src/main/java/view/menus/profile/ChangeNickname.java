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
import view.menus.MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ChangeNickname extends Application {
    public static Stage stage;
    public static StackPane root;
    public static MenuBox menuBox;
    public static MenuTextField nickname;
    public static MenuButton submit;
    public static MenuFingerBack back;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        ChangeNickname.stage = stage;
        user = controller.Application.getCurrentUser();
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/changeNickname.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();
        menuBox = new MenuBox("change nickname",0,0,500,400);
        nickname = new MenuTextField(menuBox,"nickname...","nickname",50,-50);
        submit = new MenuButton("save",menuBox,0,50,false);
        nickname.setText(user.getNickname());
        menuBox.getChildren().addAll(nickname,submit);
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
        nickname.textProperty().addListener((observableValue, o, n) -> {
            if (UserController.checkNullFields(n)) {
                nickname.handlingError("nickname field is required!");
            }
            nickname.handlingError("");
        });
        submit.setOnMouseClicked(mouseEvent -> {
            if (UserController.checkNullFields(nickname.getText())) {
                MenuPopUp menuPopUp = new MenuPopUp(root, 400, 400,
                        "error", "nickname field is required!");
                root.getChildren().add(menuPopUp);
            }else{
                MenuPopUp menuPopUp = new MenuPopUp(root, 400, 400,
                        "success", UserController.changeNickname(nickname.getText()));
                root.getChildren().add(menuPopUp);
            }
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
