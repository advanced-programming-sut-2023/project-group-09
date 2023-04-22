package model.building.killingbuildings;

import model.building.Building;
import model.Government;

public class KillingPit extends Building {
    private int damage;

    public KillingPit(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                      String name, int maxHp, int width, int length, int damage) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.damage = damage;
        this.isInvisible = true;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
