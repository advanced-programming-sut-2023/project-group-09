package viewphase1;

import controllers.MapController;
import enumeration.Textures;
import enumeration.commands.MapCommands;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import model.game.Map;

import java.util.Scanner;
import java.util.regex.Matcher;

public class EditMapEnvironmentMenu {
    public static void run(Scanner scanner) {
        System.out.println("<< Edit Map Environment Menu >>");

        while (true) {
            String input = scanner.nextLine();
            Matcher setTextureM = MapCommands.SET_TEXTURE.getMatcher(input);
            Matcher dropTreeM = MapCommands.DROP_TREE.getMatcher(input);
            Matcher dropRockM = MapCommands.DROP_ROCK.getMatcher(input);
            Matcher clearTileM = MapCommands.CLEAR_TILE.getMatcher(input);
            Matcher showMapM = MapCommands.SHOW_MAP.getMatcher(input); //////////////////////

            if (setTextureM.matches()) runSetTexture(setTextureM);
            else if (dropTreeM.matches()) runDropTree(dropTreeM);
            else if (dropRockM.matches()) runDropRock(dropRockM);
            else if (clearTileM.matches()) MapMenu.runShowMapOrShowDetailsOrClearLand(clearTileM, 2);
            else if (MapCommands.CONTINUE.getMatcher(input).matches()) return;
            else if (showMapM.matches()) {
                MapMenu.runShowMapOrShowDetailsOrClearLand(showMapM, 10);
                MapMenu.run(scanner);
            } else System.out.println("invalid command!");
        }
    }

    private static void runSetTexture(Matcher matcher) {
        String content = matcher.group("content");
        Matcher xM = MapCommands.X_COORDINATE.getMatcher(content);
        Matcher yM = MapCommands.Y_COORDINATE.getMatcher(content);
        Matcher x1M = MapCommands.X1_COORDINATE.getMatcher(content);
        Matcher x2M = MapCommands.X2_COORDINATE.getMatcher(content);
        Matcher y1M = MapCommands.Y1_COORDINATE.getMatcher(content);
        Matcher y2M = MapCommands.Y2_COORDINATE.getMatcher(content);
        Matcher typeM = MapCommands.TYPE.getMatcher(content);

        if (!typeM.find()) {
            System.out.println("invalid command");
            return;
        }
        if (typeM.group("type").isEmpty()) {
            System.out.println("type field is empty");
            return;
        }

        String validation1 = MapMenu.validateCoordinates(xM, yM);
        String validation2 = validateDoubleCoordinates(x1M, x2M, y1M, y2M);
        if (!validation1.isEmpty() && !validation2.isEmpty()) {
            System.out.println(validation1);
            return;
        }

        Textures texture = Textures.getTextureByName(typeM.group("type"));
        if (texture == null) {
            System.out.println("invalid texture type");
            return;
        }

        if (validation1.isEmpty())
            System.out.println(MapController.setTexture(Integer.parseInt(xM.group("x")) - 1, Integer.parseInt(yM.group("y")) - 1, texture));
        else {
            if (texture.equals(Textures.LARGE_POND) || texture.equals(Textures.SMALL_POND)) {
                System.out.println("size of ponds are fixed");
                return;
            }
            int x1 = Integer.parseInt(x1M.group("x1")) - 1;
            int x2 = Integer.parseInt(x2M.group("x2")) - 1;
            int y1 = Integer.parseInt(y1M.group("y1")) - 1;
            int y2 = Integer.parseInt(y2M.group("y2")) - 1;
            System.out.println(MapController.setTexture(x1, x2, y1, y2, texture));
        }
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

        System.out.println(MapController.dropTree(Integer.parseInt(xM.group("x")) - 1,
                Integer.parseInt(yM.group("y")) - 1, tree));
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
        if (directionM.group("direction").equals("random"))
            direction = RockDirections.getRandomDirection();
        else if (direction == null) {
            System.out.println("invalid rock direction");
            return;
        }

        System.out.println(MapController.dropRock(Integer.parseInt(xM.group("x")) - 1,
                Integer.parseInt(yM.group("y")) - 1, direction));
    }

    private static String validateDoubleCoordinates(Matcher x1M, Matcher x2M, Matcher y1M, Matcher y2M) {
        if (!x1M.find() || !x2M.find() || !y1M.find() || !y2M.find()) return "invalid command";

        String result = "";
        if (x1M.group("x1").isEmpty()) result += "x1 coordinate field is empty\n";
        if (x2M.group("x2").isEmpty()) result += "x2 coordinate field is empty\n";
        if (y1M.group("y1").isEmpty()) result += "y1 coordinate field is empty\n";
        if (y2M.group("y2").isEmpty()) result += "y2 coordinate field is empty\n";
        if (!result.isEmpty()) return result.substring(0, result.length() - 1);

        int x1 = Integer.parseInt(x1M.group("x1"));
        int x2 = Integer.parseInt(x2M.group("x2"));
        int y1 = Integer.parseInt(y1M.group("y1"));
        int y2 = Integer.parseInt(y2M.group("y2"));
        Map map = CreateGameMenu.map;
        if (x1 < 1 || x2 < 1 || y1 < 1 || y2 < 1 || x1 > map.getWidth() || x2 > map.getWidth() ||
                y1 > map.getLength() || y2 > map.getLength())
            return "invalid coordinates";

        return "";
    }
}
