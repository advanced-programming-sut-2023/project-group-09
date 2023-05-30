package view.menus;

import controller.GameController;
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

import java.net.MalformedURLException;

public class LoginMenu extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        Pane loginPane = FXMLLoader.load(LoginMenu.class.getResource("/FXML/loginMenu.fxml"));
        Scene scene = new Scene(loginPane);
        Captcha captcha = new Captcha();
        captcha.setTranslate(200 , 200);
        loginPane.getChildren().add(captcha);
        MenuButton menuButton = new MenuButton("Start");
        loginPane.getChildren().add(menuButton);
        stage.setTitle("Login Menu");
        stage.setScene(scene);


        stage.show();
    }


    public void forgotPassword(MouseEvent mouseEvent) {
    }

    public void login(MouseEvent mouseEvent) {
    }
}
