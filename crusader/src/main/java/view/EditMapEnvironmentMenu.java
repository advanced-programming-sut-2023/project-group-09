package view;

import controller.MapController;
import enumeration.Textures;
import enumeration.commands.MapCommands;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;

import java.util.Scanner;
import java.util.regex.Matcher;

public class EditMapEnvironmentMenu {
    public static void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            Matcher setTextureM = MapCommands.SET_TEXTURE.getMatcher(input);
            Matcher dropTreeM = MapCommands.DROP_TREE.getMatcher(input);
            Matcher dropRockM = MapCommands.DROP_ROCK.getMatcher(input);
            Matcher clearTileM = MapCommands.CLEAR_TILE.getMatcher(input);

            if (setTextureM.matches()) runSetTexture(setTextureM);
            else if (dropTreeM.matches()) runDropTree(dropTreeM);
            else if (dropRockM.matches()) runDropRock(dropRockM);
            else if (clearTileM.matches()) MapMenu.runShowMapOrShowDetailsOrClearLand(clearTileM, 2);
            else if (MapCommands.CONTINUE.getMatcher(input).matches()) return;
            else System.out.println("invalid command");
        }
    }

    private static void runSetTexture(Matcher matcher) {
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

        Textures texture = Textures.getTextureByName(typeM.group("type"));
        if (texture == null) {
            System.out.println("invalid texture type");
            return;
        }

        System.out.println(MapController.setTexture(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y")), texture));
    }

    private static void runDropTree(Matcher matcher) {
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

        Trees tree = Trees.getTreeByName(typeM.group("type"));
        if (tree == null) {
            System.out.println("invalid tree type");
            return;
        }

        System.out.println(MapController.dropTree(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y")), tree));
    }

    private static void runDropRock(Matcher matcher) {
        String content = matcher.group("content");
        Matcher xM = MapCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapCommands.Y_COORDINATE.getMatcher(content);
        Matcher directionM = MapCommands.DIRECTION.getMatcher(content);

        if (!directionM.find()) {
            System.out.println("invalid command");
            return;
        }
        if (directionM.group("direction").isEmpty()) {
            System.out.println("direction field is empty");
            return;
        }

        String validation = MapMenu.validateCoordinates(xM, yM);
        if (!validation.isEmpty()) {
            System.out.println(validation);
            return;
        }

        RockDirections direction = RockDirections.getRockByDirection(directionM.group("direction"));
        if (direction == null) {
            System.out.println("invalid rock direction");
            return;
        }

        System.out.println(MapController.dropRock(Integer.parseInt(xM.group("x")), Integer.parseInt(yM.group("y")), direction));
    }
}
