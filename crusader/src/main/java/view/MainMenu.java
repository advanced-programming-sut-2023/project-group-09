package view;

import controller.Application;
import controller.DBController;
import controller.MainController;
import enumeration.answers.Answers;

import java.util.Scanner;

public class MainMenu {
    public static void run(Scanner scanner) {
        while (true) {
            System.out.println("1.make game\n" +
                    "2.profile menu\n" +
                    "3.logout\n" +
                    "4.exit");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                //MapMenu.run();
            } else if (input.equals("2")) {
                ProfileMenu.run(scanner);
            } else if (input.equals("3")) {
                Application.setCurrentUser(null);
                DBController.saveCurrentUser();
                PrimaryMenu.run(scanner);
            } else if (input.equals("4")) {
                if (!Application.isStayLoggedIn()) {
                    Application.setCurrentUser(null);
                    DBController.saveCurrentUser();
                }
                MainController.exitCrusader();
                break;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
