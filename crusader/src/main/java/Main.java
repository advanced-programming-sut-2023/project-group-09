import controller.DBController;
import controller.MainController;
import view.LoginMenu;
import controller.DBController;
import view.LoginMenu;

import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        DBController.loadAllUsers();
        LoginMenu.run(new Scanner(System.in));
    }
}
