package controller;

import view.MainMenu;
import view.PrimaryMenu;
import view.ProfileMenu;

import java.util.Scanner;

public class MainController {

    public static void loadGame(){
        DBController.loadAllUsers();
        DBController.loadCurrentUser();
    }

    public static void run(){
        Scanner scanner = new Scanner(System.in);
        loadGame();
        while (true){
            if (Application.getCurrentUser() == null){
                PrimaryMenu.run(scanner);
            }else{
                ProfileMenu.run(scanner);
            }
        }
    }

    public static void exitCrusader(){
        DBController.saveAllUsers();
        DBController.saveCurrentUser();
        System.out.println("App closed :(");
        System.exit(0);
    }
}
