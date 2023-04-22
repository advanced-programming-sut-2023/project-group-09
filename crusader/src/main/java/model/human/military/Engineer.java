package model.human.military;

import model.game.Tile;
import model.tools.Moat;

public class Engineer extends EuropeanTroop {
    int destinationX, destinationY;

    public Engineer(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }

    public void digMoat() {
        for (int i = 0; i < Moat.getTilesToBeMoat().size(); i++) {
            Tile tile = Moat.getTilesToBeMoat().get(i);
//            TODO: move to (x, y)
            tile.setMoat(true);
        }
//        TODO: refresh graphics
    }
}
