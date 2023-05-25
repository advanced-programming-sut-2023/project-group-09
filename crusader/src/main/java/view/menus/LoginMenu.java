package view.menus;

import controller.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.captcha.Captcha;
import view.controllers.CaptchaController;

import java.net.MalformedURLException;

public class LoginMenu extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        BorderPane loginPane = FXMLLoader.load(LoginMenu.class.getResource("/FXML/loginMenu.fxml"));
        Scene scene = new Scene(loginPane);
        Captcha captcha = new Captcha();
        loginPane.getChildren().add(captcha);
        CaptchaController.getCaptcha().getRefreshButton().requestFocus();

        stage.setTitle("Signup Menu");
        stage.setScene(scene);


        stage.show();
    }




}
