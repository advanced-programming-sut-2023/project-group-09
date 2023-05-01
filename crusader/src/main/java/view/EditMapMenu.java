package view;

import controller.GameController;
import controller.MapController;
import controller.gamestructure.GameHumans;
import enumeration.Textures;
import enumeration.commands.MapCommands;
import model.Government;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class EditMapMenu {
    public static Government currentGovernment = null;

    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Matcher dropBuildingM = MapCommands.DROP_BUILDING.getMatcher(input);
            Matcher dropUnitM = MapCommands.DROP_UNIT.getMatcher(input);

            if (dropBuildingM.matches()) runDropBuilding(dropBuildingM);
            else if (dropUnitM.matches()) runDropUnit(dropUnitM);
            else if (MapCommands.CONTINUE.getMatcher(input).matches()) return;
            else System.out.println("invalid command");
        }
    }

    private static void runDropBuilding(Matcher matcher) {
        String content = matcher.group("content");
        Matcher xM = MapCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapCommands.Y_COORDINATE.getMatcher(content);
        Matcher typeM = MapCommands.TYPE.getMatcher(content);

        if (!typeM.find()) {
            System.out.println("invalid command");
            return;
        }
        if (typeM.group("type").isEmpty()) {
            System.out.println("type field is empty");
            return;
        }

        String validation = MapMenu.validateCoordinates(xM, yM);
        if (!validation.isEmpty()) {
            System.out.println(validation);
            return;
        }

        int x = Integer.parseInt(xM.group("x")) - 1;
        int y = Integer.parseInt(yM.group("y")) - 1;
        String type = typeM.group("type");
        if (MapController.checkCanPutBuilding(x, y, type, currentGovernment))
            MapController.dropBuilding(x, y, type, currentGovernment);
    }

    private static void runDropUnit(Matcher matcher) {
        String content = matcher.group("content");
        Matcher xM = MapCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapCommands.Y_COORDINATE.getMatcher(content);
        Matcher typeM = MapCommands.TYPE.getMatcher(content);
        Matcher countM = MapCommands.COUNT.getMatcher(content);

        if (!typeM.find()) {
            System.out.println("invalid command");
            return;
        }
        if (typeM.group("type").isEmpty()) {
            System.out.println("type field is empty");
            return;
        }

        String validation = MapMenu.validateCoordinates(xM, yM);
        if (!validation.isEmpty()) {
            System.out.println(validation);
            return;
        }

        if (!countM.find()) {
            System.out.println("invalid command");
            return;
        }
        int count;
        try {
            count = Integer.parseInt(countM.group("count"));
        } catch (Exception e) {
            System.out.println("please enter a number!");
            return;
        }
        if (count < 0) {
            System.out.println("invalid number!");
            return;
        }

        int x = Integer.parseInt(xM.group("x"));
        int y = Integer.parseInt(yM.group("y"));
        String type = typeM.group("type");
        if (MapController.checkCanPutMilitary(x - 1, y - 1, type, currentGovernment)) {
            for (int i = 0; i < count; i++) {
                MapController.dropMilitary(x - 1, y - 1, type, currentGovernment);
            }
            System.out.println("unit dropped successfully!");
        } else System.out.println("error");
    }
}
