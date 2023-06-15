package view.animations;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.human.military.Military;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import model.menugui.game.Troop;

import java.util.ArrayList;

public class MoveTroop extends Transition {
    Troop troop;
    Military military;
    int counter = 0;

    public MoveTroop(Troop troop) {
        this.troop = troop;
        this.military = troop.getMilitary();
        System.out.println(military.getMove().getPath().size());
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(1000));
        this.setInterpolator(Interpolator.LINEAR);
    }

    @Override
    protected void interpolate(double v) {

        if (!military.getMove().isMoving()) {
            this.stop();
        }
        if (counter == military.getSpeed() * 20) {
            counter = 0;


            military.getMove().moveOneTurn();
            System.out.println("troop position: " + military.getX() + " " + military.getY());
            GameTile next = GameMap.getGameTile(military.getX(), military.getY());
            troop.changeGameTile(next);
            if (GameMap.gameTroops[military.getY()][military.getX()] == null) {
                GameMap.gameTroops[military.getY()][military.getX()] = new ArrayList<>();
            }
            GameMap.gameTroops[military.getY()][military.getX()].add(troop);
        }
        counter++;
    }
}
