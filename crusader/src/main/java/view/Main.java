package view;

import controller.DBController;
import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.menugui.game.LoadGameMap;
import view.controllers.ViewController;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        ViewController.playMenuMusic();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBController.saveAllUsers();
            DBController.saveCurrentUser();
        }));
//        LoadGameMap loadGameMap1 = new LoadGameMap(1);
//        LoadGameMap loadGameMap2 = new LoadGameMap(2);
//        loadGameMap1.start();
//        loadGameMap2.start();
        MainController.run(stage);
    }
}