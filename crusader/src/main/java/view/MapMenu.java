package view;

import controller.GameController;
import controller.MapController;
import enumeration.Textures;
import enumeration.commands.MapCommands;
import model.game.Game;
import model.game.Map;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    public static void run(Scanner scanner) {
//        which function:
//        0: showMap - 1: showDetails - 2: clearLand

        while (true) {
            System.out.println("<< Map Menu >>");
            String input = scanner.nextLine();
            Matcher moveMapM = MapCommands.MOVE_MAP.getMatcher(input);
            Matcher showDetailsOfLandM = MapCommands.SHOW_DETAILS_OF_TILE.getMatcher(input);

            if (moveMapM.matches()) runMoveMap(moveMapM);
            else if (showDetailsOfLandM.matches()) runShowMapOrShowDetailsOrClearLand(showDetailsOfLandM, 1);
            else if (MapCommands.BACK.getMatcher(input).matches()) {
                System.out.println("<< Game Menu >>");
                return;
            } else if (MapCommands.SHOW_CURRENT_COORDINATES.getMatcher(input).matches()) {
                System.out.println("x of center: " + (GameController.getGame().getCurrentMapX() + 1) +
                        "\ny of center: " + (GameController.getGame().getCurrentMapY() + 1));
            } else System.out.println("invalid command!");
        }
    }

    public static void runShowMapOrShowDetailsOrClearLand(Matcher matcher, int whichFunction) {
        String content = matcher.group("content");
        Matcher xM = MapCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapCommands.Y_COORDINATE.getMatcher(content);

        String validation = validateCoordinates(xM, yM);
        if (!validation.isEmpty()) {
            System.out.println(validation);
            return;
        }

        if (whichFunction == 0)
            System.out.println(GameController.showMap(Integer.parseInt(xM.group("x")) - 1,
                    Integer.parseInt(yM.group("y")) - 1));
        else if (whichFunction == 1)
            System.out.println(GameController.showDetailsOfTile(Integer.parseInt(xM.group("x")) - 1,
                    Integer.parseInt(yM.group("y")) - 1));
        else if (whichFunction == 2)
            System.out.println(MapController.clearTile(Integer.parseInt(xM.group("x")) - 1,
                    Integer.parseInt(yM.group("y")) - 1));
        else if (whichFunction == 10)
            System.out.println(GameController.showMap2(Integer.parseInt(xM.group("x")) - 1,
                    Integer.parseInt(yM.group("y")) - 1, MapController.map));
    }

    private static void runMoveMap(Matcher matcher) {
        String content = matcher.group("content");
        Matcher upM = MapCommands.UP.getMatcher(content);
        Matcher downM = MapCommands.DOWN.getMatcher(content);
        Matcher leftM = MapCommands.LEFT.getMatcher(content);
        Matcher rightM = MapCommands.RIGHT.getMatcher(content);
        boolean upFound = upM.find(), downFound = downM.find(), leftFound = leftM.find(), rightFound = rightM.find();

        if (!upFound && !downFound && !leftFound && !rightFound) {
            System.out.println("invalid command");
            return;
        }

        int up, down, left, right;
        if (!upFound) up = 0;
        else if (upM.group("count") == null || upM.group("count").isEmpty()) up = 1;
        else up = Integer.parseInt(upM.group("count"));
        if (!downFound) down = 0;
        else if (downM.group("count") == null || downM.group("count").isEmpty()) down = 1;
        else down = Integer.parseInt(downM.group("count"));
        if (!leftFound) left = 0;
        else if (leftM.group("count") == null || leftM.group("count").isEmpty()) left = 1;
        else left = Integer.parseInt(leftM.group("count"));
        if (!rightFound) right = 0;
        else if (rightM.group("count") == null || rightM.group("count").isEmpty()) right = 1;
        else right = Integer.parseInt(rightM.group("count"));

        System.out.println(GameController.moveMap(up, left, down, right));
    }

    public static String validateCoordinates(Matcher xM, Matcher yM) {
        if (!xM.find() || !yM.find()) return "invalid command!";

        String result = "";
        if (xM.group("x").isEmpty()) result += "x coordinate field is empty\n";
        if (yM.group("y").isEmpty()) result += "y coordinate field is empty\n";
        if (!result.isEmpty()) return result.substring(0, result.length() - 1);

        int x = Integer.parseInt(xM.group("x"));
        int y = Integer.parseInt(yM.group("y"));
        if (x < 1 || y < 1 || x > CreateGameMenu.map.getWidth() || y > CreateGameMenu.map.getLength())
            return "invalid coordinates";

        return "";
    }
}