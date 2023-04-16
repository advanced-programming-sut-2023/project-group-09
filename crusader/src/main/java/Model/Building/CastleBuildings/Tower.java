package Model.Building.CastleBuildings;

import Model.Government;
import Model.Human.Human;

import java.util.ArrayList;
import java.util.HashMap;

public class Tower extends CastleBuilding {
    private int fireRange;
    private int defendRange;

    public Tower(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                 String type, int maxHp, int startX, int startY, int endX, int endY, int fireRange, int defendRange) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }

    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public void setDefendRange(int defendRange) {
        this.defendRange = defendRange;
    }
}
