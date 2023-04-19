package controller;

import controller.Application;
import controller.DBController;
import model.User;
import view.MainMenu;
import view.PrimaryMenu;

import java.util.Scanner;

public class MainController {

    public static void loadGame(){
        DBController.loadCurrentUser();
        DBController.loadAllUsers();
    }

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        loadGame();
        if (Application.getCurrentUser() == null){
            PrimaryMenu.run(scanner);
        }else{
            MainMenu.run(scanner);
        }
    }

    public static void exitCrusader(){
        DBController.saveAllUsers();
        DBController.saveCurrentUser();
        System.out.println("App closed :(");
        System.exit(0);
    }
}
