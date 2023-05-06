package model.human.military;

import model.tools.Tool;

public class Engineer extends EuropeanTroop {
    private Tool target;

    public Engineer(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }

    public Tool getTarget() {
        return target;
    }

    public void setTarget(Tool target) {
        this.target = target;
    }
}
