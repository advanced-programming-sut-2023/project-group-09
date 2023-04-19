import controller.DBController;
import controller.MainController;
import controller.UserController;
import view.PrimaryMenu;

import java.util.Scanner;

public class Main {
    //new comment
    public static void main(String[] args) {
        DBController.loadAllUsers();
        PrimaryMenu.run(new Scanner(System.in));
    }
}
