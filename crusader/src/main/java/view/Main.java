package view;

import controller.DBController;
import controller.MainController;
import controller.gamestructure.GameMaps;
import enumeration.Paths;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.game.Map;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
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
        MainController.run(stage);
    }
}