package view;

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
        MainController.run(stage);
    }
}