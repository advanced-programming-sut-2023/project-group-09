package view;

import client.Connection;
import controller.DBController;
import controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import server.Connection;
import server.Server;

public class Main extends Application {
    public static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
//        ViewController.playMenuMusic();
        /*Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBController.saveAllUsers();
            DBController.saveCurrentUser();
        }));*/
        Connection connection = new Connection("localhost",8080);
        MainController.run(stage);
    }
}