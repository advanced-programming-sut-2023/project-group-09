package view;

import enumeration.dictionary.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CreateGameMenu {
    public static int mapSize;
    public static int governmentsCount;
    private static ArrayList<Colors> colors = new ArrayList<>();
    public static LinkedHashMap<String, Colors> governments = new LinkedHashMap<>();

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
        for (int i = 0; i < governmentsCount; i++) {
            System.out.println("enter the name of government " + (i + 1));
            String governmentName = scanner.nextLine();
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
            governments.put(governmentName, colors.get(colorNumber - 1));
            colors.remove(colorNumber - 1);
        }
    }
}
