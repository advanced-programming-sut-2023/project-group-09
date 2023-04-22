package model.tools;

import controller.GameController;
import controller.gamestructure.GameTools;
import enumeration.Textures;
import model.game.Tile;

import java.util.ArrayList;

public class Moat {
    private static ArrayList<Tile> tilesToBeMoat = new ArrayList<>();

    public void addTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (tile.getBuilding() == null && (tile.getTexture().equals(Textures.EARTH) || tile.getTexture().equals(Textures.EARTH_AND_SAND)
                || tile.getTexture().equals(Textures.GRASS) || tile.getTexture().equals(Textures.THICK_GRASS)
                || tile.getTexture().equals(Textures.OASIS_GRASS) || tile.getTexture().equals(Textures.BEACH)))
            tilesToBeMoat.add(tile);
    }

    public static ArrayList<Tile> getTilesToBeMoat() {
//        TODO: better to sort them based on distance
        return tilesToBeMoat;
    }

    public void removeTile(int x, int y) {
        tilesToBeMoat.remove(GameController.getGame().getMap().getTile(x, y));
    }

    public void deleteMoat(int x, int y) {
        GameController.getGame().getMap().getTile(x, y).setMoat(false);
//        TODO: refresh graphics
    }
}
