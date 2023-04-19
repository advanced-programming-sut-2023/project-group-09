import Controller.DBController;
import Controller.MainController;
import View.LoginMenu;

import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        DBController.loadAllUsers();
        LoginMenu.run(new Scanner(System.in));
    }
}
