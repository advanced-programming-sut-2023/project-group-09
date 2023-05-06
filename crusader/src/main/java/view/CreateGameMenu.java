package view;

import controller.*;
import controller.gamestructure.GameMaps;
import enumeration.Pair;
import enumeration.commands.MapCommands;
import enumeration.dictionary.Colors;
import model.Government;
import model.User;
import model.game.Game;
import model.game.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class CreateGameMenu {
    public static int mapSize;
    public static Map map;
    public static int governmentsCount;
    private static ArrayList<Colors> colors = new ArrayList<>();
    private static ArrayList<Pair<Integer, Integer>> castles = new ArrayList<>();
    private static ArrayList<String> users = new ArrayList<>();

    public static void run(Scanner scanner) {
        System.out.println("<< Create Game Menu >>");

        while (true) {
            System.out.println("choose map size:\n1.200 x 200\n2.400 x 400\n3.exit");
            int sizeNumber = 0;
            try {
                sizeNumber = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("please enter a number!");
                continue;
            }
            if (sizeNumber < 1 || sizeNumber > 3) {
                System.out.println("invalid number");
                continue;
            }
            if (sizeNumber == 3) return;
            mapSize = (sizeNumber == 1) ? 200 : 400;
            break;
        }

        while (true) {
            int mapNumber;
            ArrayList<Map> maps = (mapSize == 200) ? GameMaps.smallMaps : GameMaps.largeMaps;
            int numberOfMaps = maps.size();
            System.out.println("there are " + numberOfMaps + " default maps available\nchoose one of them to preview:");
            try {
                mapNumber = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("please enter a number!");
                continue;
            }
            if (mapNumber < 1 || mapNumber > numberOfMaps) {
                System.out.println("invalid number");
                continue;
            }
            PixelCanvas.drawMap(maps.get(mapNumber - 1));

            boolean mapChosen = false;
            while (true) {
                System.out.println("1.choose\n2.back");
                int choiceNumber;
                try {
                    choiceNumber = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("please enter a number!");
                    continue;
                }
                if (choiceNumber != 1 && choiceNumber != 2) {
                    System.out.println("invalid number");
                    continue;
                }
                mapChosen = choiceNumber == 1;
                break;
            }
            if (mapChosen) {
                map = maps.get(mapNumber - 1);
                break;
            }
        }
        MapController.map = map;
        EditMapEnvironmentMenu.run(scanner);
        Game game = new Game(map);
        GameController.setGame(game);

        while (true) {
            System.out.println("enter the number of governments (2 to 8):");
            String input = scanner.nextLine();
            if (input.equals("exit")) return;
            try {
                governmentsCount = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("please enter a number!");
                continue;
            }
            if (governmentsCount < 2 || governmentsCount > 8) {
                System.out.println("invalid number of governments");
                continue;
            }
            break;
        }

        Colors.getColorsList(colors);
        castles = map.getDefaultCastles();
        for (int i = 0; i < governmentsCount; i++) {
            User lord = null;
            boolean getUsername = true;
            if (i == 0) {
                lord = Application.getCurrentUser();
                System.out.println("lord of government 1: " + lord.getUsername());
                users.add(lord.getUsername());
                getUsername = false;
            }
            while (getUsername) {
                System.out.println("enter the lord's username of government " + (i + 1) + ":");
                String lordUsername = scanner.nextLine();
                if (!Application.isUserExistsByName(lordUsername)) {
                    System.out.println("username doesn't exist!");
                    continue;
                } else if (users.contains(lordUsername)) {
                    System.out.println("this username has already been added to this game");
                    continue;
                }
                lord = Application.getUserByUsername(lordUsername);
                break;
            }

            String output = "choose the color of government " + (i + 1) + "\n";
            for (int j = 0; j < colors.size(); j++) {
                Colors color = colors.get(j);
                output += (j + 1) + "." + color.getName() + "\n";
            }
            System.out.println(output.substring(0, output.length() - 1));
            int colorNumber;
            while (true) {
                try {
                    colorNumber = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("please enter a number!");
                    continue;
                }
                if (colorNumber < 1 || colorNumber > colors.size()) {
                    System.out.println("invalid number!");
                    continue;
                }

                break;
            }

            output = "choose the castle of government " + (i + 1) + "\n";
            for (int j = 0; j < castles.size(); j++) {
                int x = castles.get(j).getFirst();
                int y = castles.get(j).getSecond();
                output += (j + 1) + ".castle in (" + x + ", " + y + ")\n";
            }
            System.out.println(output.substring(0, output.length() - 1));
            int castleNumber;
            while (true) {
                try {
                    castleNumber = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("please enter a number!");
                    continue;
                }
                if (castleNumber < 1 || castleNumber > castles.size()) {
                    System.out.println("invalid number!\nreenter a number:");
                    continue;
                }
                break;
            }
            int x = castles.get(castleNumber - 1).getFirst();
            int y = castles.get(castleNumber - 1).getSecond();
            Government government = new Government(lord, x, y, colors.get(colorNumber - 1));
            game.addGovernment(government);
            castles.remove(castleNumber - 1);
            colors.remove(colorNumber - 1);
            EditMapMenu.currentGovernment = government;
            EditMapMenu.run(scanner);
        }

        GameMenu.run(scanner, game);
    }
}
