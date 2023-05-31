package view.menus;

import controller.GameController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.captcha.Captcha;
import model.menugui.MenuBox;
import model.menugui.MenuButton;
import model.menugui.MenuPasswordField;
import model.menugui.MenuTextField;
import view.controllers.CaptchaController;
import view.controllers.ViewController;

import java.io.IOException;
import java.net.MalformedURLException;

public class LoginMenu extends Application {
    public static Stage stage;
    public static Pane loginPane;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/FXML/loginMenu.fxml"));

        loginPane = ViewController.makePaneScreen(stage , pane , 1000 , -1);
        Scene scene = new Scene(pane);

        MenuBox menuBox = new MenuBox("Login" , 300 , -20 , 500 , 500);

        MenuTextField userNameField = new MenuTextField(menuBox , "username" ,
                "Username : " ,  50, -150);
        menuBox.getChildren().add(userNameField);

        MenuPasswordField passwordField = new MenuPasswordField(menuBox , "password" ,
                "Passowrd : " , 50 , -70);
        menuBox.getChildren().add(passwordField);

        Hyperlink forgotPassword = new Hyperlink();
        forgotPassword.setText("forgot my password");
        forgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                forgotPassword(mouseEvent);
            }
        });

        forgotPassword.setTranslateY(-20);
        forgotPassword.setBorder(Border.EMPTY);
        forgotPassword.setStyle("-fx-font-size: 15");


        menuBox.getChildren().add(forgotPassword);

        Captcha captcha = new Captcha(menuBox  , 0 , 40);

        MenuButton loginButton = new MenuButton("Login" , menuBox , 0 , 170);
        menuBox.getChildren().add(loginButton);
        menuBox.requestFocus();

        pane.getChildren().add(menuBox);

        stage.setTitle("Login Menu");
        stage.setScene(scene);
        stage.show();
    }


    public void forgotPassword(MouseEvent mouseEvent) {
    }

    public void login(MouseEvent mouseEvent) {
    }
}
