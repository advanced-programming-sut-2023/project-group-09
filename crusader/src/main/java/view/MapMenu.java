package view;

import controller.GameController;
import controller.MapController;
import enumeration.Textures;
import enumeration.commands.MapMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    public static void run(Scanner scanner) {
//        which function:
//        0: showMap - 1: showDetails - 2: clearLand

        while (true) {
            String input = scanner.nextLine();
            Matcher showMapM = MapMenuCommands.SHOW_MAP.getMatcher(input);
            Matcher moveMapM = MapMenuCommands.MOVE_MAP.getMatcher(input);
            Matcher showDetailsOfLandM = MapMenuCommands.SHOW_DETAILS_OF_TILE.getMatcher(input);
            Matcher setTextureM = MapMenuCommands.SET_TEXTURE.getMatcher(input);
            Matcher clearLandM = MapMenuCommands.CLEAR_TILE.getMatcher(input);

            if (showMapM.matches()) runShowMapOrShowDetailsOrClearLand(showMapM, 0);
            else if (moveMapM.matches()) runMoveMap(moveMapM);
            else if (showDetailsOfLandM.matches()) runShowMapOrShowDetailsOrClearLand(showDetailsOfLandM, 1);
            else if (setTextureM.matches()) runSetTexture(setTextureM);
            else if (clearLandM.matches()) runShowMapOrShowDetailsOrClearLand(clearLandM, 2);
            else System.out.println("invalid command");
        }
    }

    private static void runShowMapOrShowDetailsOrClearLand(Matcher matcher, int whichFunction) {
        String content = matcher.group("content");
        Matcher xM = MapMenuCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapMenuCommands.Y_COORDINATE.getMatcher(content);

        String validation = validateCoordinates(xM, yM);
        if (!validation.isEmpty()) {
            System.out.println(validation);
            return;
        }

        if (whichFunction == 0)
            System.out.println(MapController.showMap(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y"))));
        else if (whichFunction == 1)
            System.out.println(MapController.showDetailsOfTile(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y"))));
        else if (whichFunction == 2)
            System.out.println(MapController.clearTile(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y"))));
    }

    private static void runMoveMap(Matcher matcher) {
        String content = matcher.group("content");
        Matcher upM = MapMenuCommands.UP.getMatcher(content);
        Matcher downM = MapMenuCommands.DOWN.getMatcher(content);
        Matcher leftM = MapMenuCommands.LEFT.getMatcher(content);
        Matcher rightM = MapMenuCommands.RIGHT.getMatcher(content);

        if (!upM.find() || !downM.find() || !leftM.find() || !rightM.find()) {
            System.out.println("invalid command");
            return;
        }

        int up = (upM.group("count").isEmpty()) ? 1 : Integer.parseInt(upM.group("count"));
        int down = (downM.group("count").isEmpty()) ? 1 : Integer.parseInt(downM.group("count"));
        int left = (leftM.group("count").isEmpty()) ? 1 : Integer.parseInt(leftM.group("count"));
        int right = (rightM.group("count").isEmpty()) ? 1 : Integer.parseInt(rightM.group("count"));

//        TODO: call showMap
    }

    private static void runSetTexture(Matcher matcher) {
        String content = matcher.group("content");
        Matcher xM = MapMenuCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapMenuCommands.Y_COORDINATE.getMatcher(content);
        Matcher typeM = MapMenuCommands.TEXTURE_TYPE.getMatcher(content);

        if (!typeM.find()) {
            System.out.println("invalid command");
            return;
        }
        if (typeM.group("type").isEmpty()) {
            System.out.println("type field is empty");
            return;
        }

        String validation = validateCoordinates(xM, yM);
        if (!validation.isEmpty()) {
            System.out.println(validation);
            return;
        }

        Textures texture = Textures.getTextureByName(typeM.group("type"));
        if (texture == null) {
            System.out.println("invalid texture type");
            return;
        }

        System.out.println(MapController.setTexture(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y")), texture));
    }

    private static String validateCoordinates(Matcher xM, Matcher yM) {
        if (!xM.find() || !yM.find()) return "invalid command";

        String result = "";
        if (xM.group("x").isEmpty()) result += "x coordinate field is empty\n";
        if (yM.group("y").isEmpty()) result += "y coordinate field is empty\n";
        if (!result.isEmpty()) return result.substring(0, result.length() - 1);

        int x = Integer.parseInt(xM.group("x"));
        int y = Integer.parseInt(yM.group("y"));
        if (x < 0 || y < 0 || x > GameController.getGame().getMap().getLength() || y > GameController.getGame().getMap().getWidth())
            return "invalid coordinates";

        return "";
    }
}
