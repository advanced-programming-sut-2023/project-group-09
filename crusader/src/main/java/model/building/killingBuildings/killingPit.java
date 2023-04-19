package model.building.killingBuildings;

import model.building.Building;
import model.Government;

public class killingPit extends Building {
    private int damage;

    public killingPit(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                      String type, int maxHp, int startX, int startY, int endX, int endY, int damage) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
