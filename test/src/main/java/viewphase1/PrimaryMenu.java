package viewphase1;

import controller.Application;
import controller.DBController;
import controller.MainController;
import enumeration.answers.Answers;
import enumeration.commands.Commands;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class PrimaryMenu {
    public static void run(Scanner scanner) throws IOException {
        String input;
        Matcher exitGameMatcher;
        while (true) {
            printSelectMenuMessage();
            input = scanner.nextLine();
            exitGameMatcher = Commands.getMatcher(input, Commands.EXIT_CRUSADER);
            if (input.equals("1") || input.equals("login menu")) {
                LoginMenu.run(scanner);
            } else if (input.equals("2") || input.equals("signup menu")) {
                SignupMenu.run(scanner);
            } else if (input.equals("3") || exitGameMatcher.matches()) {
                Application.setCurrentUser(null);
                Application.setStayLoggedIn(false);
                DBController.saveCurrentUser();
                MainController.exitCrusader();
                return;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }

    public static void printSelectMenuMessage() {
        System.out.println("Select one of the following menus:");
        System.out.println("1.login menu");
        System.out.println("2.signup menu");
        System.out.println("3.exit");
    }
}
