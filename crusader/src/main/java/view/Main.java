package view;

import view.controllers.CaptchaController;
import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("wow");
        try {
            CaptchaController.createCaptcha();
            System.out.println(CaptchaController.getCaptcha().getNumber());
        } catch (Exception e) {

        }
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainController.run(stage);
    }
}