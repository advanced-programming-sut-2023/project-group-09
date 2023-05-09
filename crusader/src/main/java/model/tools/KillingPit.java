package model.tools;

import controller.GameController;
import enumeration.Textures;
import model.Government;
import model.game.Tile;

public class KillingPit {
    public void addKillingPit(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (tile.getBuilding() == null && !tile.isPit() && !tile.isMoat() &&
                (tile.getTexture().equals(Textures.EARTH)) || (tile.getTexture().equals(Textures.EARTH_AND_SAND))
                || (tile.getTexture().equals(Textures.BOULDER)) || (tile.getTexture().equals(Textures.GRASS))
                || (tile.getTexture().equals(Textures.THICK_GRASS)) || (tile.getTexture().equals(Textures.OASIS_GRASS))
                || (tile.getTexture().equals(Textures.BEACH)))
            tile.setPit(true);
            tile.setPitGovernment(GameController.getGame().getCurrentGovernment());
//        TODO: refresh graphics
    }

    public void useKillingPit(int x, int y) {
        GameController.getGame().getMap().getTile(x, y).setPit(false);
        GameController.getGame().getMap().getTile(x, y).setPitGovernment(null);
//        TODO: refresh graphics
    }
}
