package controller;

import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameImages;
import controller.gamestructure.GameMaps;
import javafx.stage.Stage;
import view.Main;
import view.controllers.ImageLoader;
import view.menus.LoginMenu;
import view.menus.MainMenu;
import view.menus.profile.FriendMenu;


public class MainController {


    public static void loadGame(){
        DBController.loadAllUsers();
        DBController.loadCurrentUser();
        DBController.loadGoods();
        DBController.loadMilitary();
        DBController.loadBuildings();
        DBController.loadTools();
        new ImageLoader().start();
        GameMaps.createMaps();
    }

    public static void run(Stage stage) throws Exception {
        loadGame();
        if (Main.connection.getToken() == null){
            new LoginMenu().start(stage);
        }else{
            Application.setStayLoggedIn(true);
            new FriendMenu().start(stage);
        }
    }

    public static void exitCrusader(){
        if (Application.isStayLoggedIn()) {
            DBController.saveCurrentUser();
        }
        else {
            Application.setCurrentUser(null);
            DBController.saveCurrentUser();
        }
        DBController.saveAllUsers();
        System.out.println("App closed :(");
        System.exit(0);
    }
}
