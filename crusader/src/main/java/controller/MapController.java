package controller;

import enumeration.answers.BuildingAnswers;
import enumeration.Textures;
import model.building.Building;
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
}
