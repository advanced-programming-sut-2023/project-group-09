package model.human.military;

import model.game.Tile;
import model.tools.AttackingAndDefendingTool;
import model.tools.Moat;

public class Engineer extends EuropeanTroop {
    private AttackingAndDefendingTool target;

    public Engineer(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }

    public AttackingAndDefendingTool getTarget() {
        return target;
    }

    public void setTarget(AttackingAndDefendingTool target) {
        this.target = target;
    }
}
