package view;

import controller.DBController;
import view.controllers.CaptchaController;
import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.controllers.ViewController;


import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewController.playMenuMusic();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBController.saveAllUsers();
            DBController.saveCurrentUser();
        }));
        MainController.run(stage);
    }
}