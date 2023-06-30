package view;

import controllers.DBController;
import controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
//        ViewController.playMenuMusic();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBController.saveAllUsers();
            DBController.saveCurrentUser();
        }));
        MainController.run(stage);
    }
}