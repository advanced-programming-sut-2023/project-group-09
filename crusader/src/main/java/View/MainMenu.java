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
                    "2.profile menu");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                //Tile[][] tiles = new Tile(Textures.IRON_TEXTURE)[][];
                //GameMenu.run(scanner, new Game(new Map(1 , 1 , tiles)));
            } else if (input.equals("2")) {
                ProfileMenu.run(scanner);
            } else {
                System.out.println(Answers.INVALID_COMMAND.getValue());
            }
        }
    }
}
