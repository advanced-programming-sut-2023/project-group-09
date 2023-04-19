package controller;

import enumeration.answers.BuildingAnswers;
import enumeration.Textures;
import model.game.Tile;
import model.human.Human;

public class MapController {
    public static String setTexture(int x, int y, Textures type) {
        GameController.getGame().getMap().getTile(x, y).setTexture(type);
        return "texture of tile (" + x + ", " + y + ") changed to " + type.getTextureName() + " successfully";
    }

    public static String clearTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        tile.setBuilding(null);
        tile.getHuman().clear();
        tile.setTexture(null);
        return "tile (" + x + ", " + y + ") cleared successfully";
    }

    public static String dropRock(int x, int y, String direction) {
        return "";
    }

    public static String dropTree(int x, int y, String type) {
        return "";
    }
    public static String dropBuilding(String x , String y , String type) {
        int xCoord , yCoord;
        try {
            xCoord = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_X_COORD_ERROR);
        }
        try {
            yCoord = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_Y_COORD_ERROR);
        }
        // TODO: dropping building in game and checking type of building
        return "";
    }

    public static String dropCastleBuildings(int x, int y, String type) {
        return ""; // *********************************
    }

    public static String dropUnit(int x, int y, String type, int count) {
        return ""; // *********************************
    }

    public static String showMap(int x, int y) {
        return "";
    }

    public static String moveMap(int up, int left, int down, int right) {
        return "";
    }

    public static String showDetailsOfTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);

        String details = "tile (" + x + ", " + y + ") details:\n";
        details += "texture type: " + tile.getTexture().getTextureName();

        if (tile.getBuilding() != null) {
            details += "building " + tile.getBuilding().getType() + " from government " + tile.getBuilding().getGovernment() +
                    " | HP: " + tile.getBuilding().getHp() + "/" + tile.getBuilding().getMaxHp() + "\n";
        } else details += "there is no building on this tile\n";

        for (int i = 0; i < tile.getHuman().size(); i++) {
            Human human = tile.getHuman().get(i);
//            TODO: add human type and government
        }
        if (tile.getHuman().size() > 0) details += "total number of humans: " + tile.getHuman().size();
        else details += "there are no humans on this tile";

        return details;
    }
}
