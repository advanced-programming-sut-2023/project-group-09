package controller;

import controller.gamestructure.GameMaps;
import javafx.stage.Stage;
import view.controllers.ImageLoader;
import view.menus.LoginMenu;
import view.menus.MainMenu;


public class MainController {


    public static void loadGame() {
        DBController.loadAllUsers();
        DBController.loadCurrentUser();
        DBController.loadGoods();
        DBController.loadMilitary();
        DBController.loadBuildings();
        DBController.loadTools();
        GameMaps.createMaps();
    }

    public static void run(Stage stage) throws Exception {
        loadGame();
        if (Application.getCurrentUser() == null) {
            new LoginMenu().start(stage);
        } else {
            Application.setStayLoggedIn(true);
            new MainMenu().start(stage);
        }
    }

    public static void exitCrusader() {
        if (Application.isStayLoggedIn()) {
            DBController.saveCurrentUser();
        } else {
            Application.setCurrentUser(null);
            DBController.saveCurrentUser();
        }
        DBController.saveAllUsers();
        System.out.println("App closed :(");
        System.exit(0);
    }
}
