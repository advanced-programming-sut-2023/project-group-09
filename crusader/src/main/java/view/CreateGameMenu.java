package view;

import controller.Application;
import enumeration.dictionary.Colors;
import model.Government;
import model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateGameMenu {
    public static int mapSize;
    public static int map;
    public static int governmentsCount;
    private static ArrayList<Colors> colors = new ArrayList<>();
    public static LinkedHashMap<User, Colors> governments = new LinkedHashMap<>();

    public static void run(Scanner scanner) {
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
            int numberOfMaps = (mapSize == 200) ? Application.getDefaultSmallMaps().size() : Application.getDefaultLargeMaps().size();
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
//            TODO: show map preview

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
                mapChosen = (choiceNumber == 1) ? true : false;
                break;
            }
            if (mapChosen) break;
            else continue;
        }

        EditMapEnvironmentMenu.run(scanner);

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

//        Edit map environment menu

        Colors.getColorsList(colors);
        for (int i = 0; i < governmentsCount; i++) {
            User lord;
            while (true) {
                System.out.println("enter the username related to government " + (i + 1) + ":");
                String lordUsername = scanner.nextLine();
                if (!Application.isUserExistsByName(lordUsername)) {
                    System.out.println("username doesn't exist");
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
                    System.out.println("enter a number:");
                    continue;
                }
                if (colorNumber < 1 || colorNumber > colors.size()) {
                    System.out.println("invalid number");
                    continue;
                }
                break;
            }
            governments.put(lord, colors.get(colorNumber - 1));
            colors.remove(colorNumber - 1);
        }


    }
}
