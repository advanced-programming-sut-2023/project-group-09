package view.menus.profile;

import controller.network.UsersController;
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
import view.controllers.UserController;
import view.controllers.ViewController;
import view.menus.LoginMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ChangeEmail extends Application {
    public static Stage stage;
    public static StackPane root;
    public static MenuBox menuBox;
    public static MenuTextField email;
    public static MenuButton submit;
    public static MenuFingerBack back;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        ChangeEmail.stage = stage;
        user = controller.Application.getCurrentUser();
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/changeEmail.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();
        menuBox = new MenuBox("change email",0,0,500,400);
        email = new MenuTextField(menuBox,"email...","email",50,-50, 300);
        submit = new MenuButton("save",menuBox,0,50,false);
        email.setText(UsersController.getEmail());
        menuBox.getChildren().addAll(email,submit);
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
        email.textProperty().addListener((observableValue, o, n) -> {
            String massage = UserController.validateEmail(n);
            email.handlingError(massage);
        });
        submit.setOnMouseClicked(mouseEvent -> {
            String massage = null;
            try {
                massage = controller.UserController.validateEmail(email.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (massage == null || massage.equals("")) {
                MenuPopUp menuPopUp = null;
                try {
                    menuPopUp = new MenuPopUp(root, 400, 400,
                            "success", controller.UserController.changeEmail(email.getText()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                root.getChildren().add(menuPopUp);
            }else{
                MenuPopUp menuPopUp = new MenuPopUp(root, 400, 400,
                        "error", massage);
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
