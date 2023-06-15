package view.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.human.military.Military;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import model.menugui.game.Troop;

public class MoveTroop extends Transition {
    Troop troop;
    Military military;
    public MoveTroop(Troop troop) {
        this.troop = troop;
        this.military = troop.getMilitary();
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(troop.getMilitary().getSpeed() * 300));
        this.setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {
        military.getMove().moveOneTurn();
        GameTile next = GameMap.getGameTile(military.getX(), military.getY());
        troop.changeGameTile(next);
    }
}
