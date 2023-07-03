package view;

import controller.DBController;
import controller.MainController;
import controller.gamestructure.GameMaps;
import javafx.application.Application;
import javafx.stage.Stage;
import server.Server;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Main.stage = stage;
//        ViewController.playMenuMusic();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBController.saveAllUsers();
            DBController.saveCurrentUser();
            DBController.saveRooms();
            DBController.saveMessages();
        }));
        GameMaps.createMaps();
        MainController.loadGame();
        Server server = new Server(8080);
        //MainController.run(stage);
    }
}