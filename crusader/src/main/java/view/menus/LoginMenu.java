package view.menus;

import controller.GameController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.captcha.Captcha;
import model.menugui.MenuButton;
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
        Captcha captcha = new Captcha();
        captcha.setTranslate(200 , 200);
        pane.getChildren().add(captcha);
        MenuButton menuButton = new MenuButton("Start" , loginPane);
        menuButton.setTranslateY(200);
        menuButton.setTranslateX(100);
        pane.getChildren().add(menuButton);

        stage.setTitle("Login Menu");
        stage.setScene(scene);
        stage.show();
    }


    public void forgotPassword(MouseEvent mouseEvent) {
    }

    public void login(MouseEvent mouseEvent) {
    }
}
