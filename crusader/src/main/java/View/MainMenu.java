package View;

import Enumeration.Answers.Answers;
import Enumeration.Textures;
import Model.Game.Game;
import Model.Game.Map;
import Model.Game.Tile;

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
                // go to pish menu
            } else if (input.equals("4")) {
                break;
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
