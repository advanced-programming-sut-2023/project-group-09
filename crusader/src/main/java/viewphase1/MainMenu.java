package viewphase1;

import controller.Application;
import controller.DBController;
import controller.MainController;
import controller.UserController;
import enumeration.answers.Answers;

import java.net.MalformedURLException;
import java.util.Scanner;

public class MainMenu {
    public static void run(Scanner scanner) throws MalformedURLException {
        while (true) {
            System.out.println("1.create game\n" +
                    "2.profile menu\n" +
                    "3.show scoreboard\n" +
                    "4.logout\n" +
                    "5.exit");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                CreateGameMenu.run(scanner);
            } else if (input.equals("2")) {
                ProfileMenu.run(scanner);
            } else if (input.equals("3")) {
                System.out.println(UserController.showScoreboard());
            } else if (input.equals("4")) {
                Application.setCurrentUser(null);
                Application.setStayLoggedIn(false);
                DBController.saveCurrentUser();
                PrimaryMenu.run(scanner);
                break;
            } else if (input.equals("5")) {
                MainController.exitCrusader();
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
