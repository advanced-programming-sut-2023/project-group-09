package view;

import controller.GameController;
import enumeration.commands.ToolMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ToolMenu {
    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();

            Matcher moveM = ToolMenuCommands.MOVE.getMatcher(input);
            Matcher patrolM = ToolMenuCommands.PATROL.getMatcher(input);

            if (moveM.matches()) {
                String content = moveM.group("content");
                Matcher xM = ToolMenuCommands.X_COORDINATE.getMatcher(content);
                Matcher yM = ToolMenuCommands.Y_COORDINATE.getMatcher(content);

                String validation = validateCoordinates(xM, yM);
                if (!validation.isEmpty()) {
                    System.out.println(validation);
                    continue;
                }

//                TODO: move tool
            } else if (ToolMenuCommands.DISBAND.getMatcher(input).matches()) {
//                TODO: delete tool
//                TODO: disband engineers
//                TODO: update graphics
            } else if (ToolMenuCommands.STOP.getMatcher(input).matches()) {
//                TODO: stop patrol or attack
            } else if (patrolM.matches()) {
                String content = patrolM.group("content");
                Matcher xM = ToolMenuCommands.X_COORDINATE.getMatcher(content);
                Matcher yM = ToolMenuCommands.Y_COORDINATE.getMatcher(content);

                String validation = validateCoordinates(xM, yM);
                if (!validation.isEmpty()) {
                    System.out.println(validation);
                    continue;
                }

//                TODO: patrol tool
            }
        }
    }

    public static String validateCoordinates(Matcher xM, Matcher yM) {
        if (!xM.find() || !yM.find()) return "invalid command";

        String result = "";
        if (xM.group("x").isEmpty()) result += "x coordinate field is empty\n";
        if (yM.group("y").isEmpty()) result += "y coordinate field is empty\n";
        if (!result.isEmpty()) return result.substring(0, result.length() - 1);

        int x = Integer.parseInt(xM.group("x"));
        int y = Integer.parseInt(yM.group("y"));
        if (x < 1 || y < 1 || x > GameController.getGame().getMap().getWidth() || y > GameController.getGame().getMap().getLength())
            return "invalid coordinates";

        return "";
    }
}
