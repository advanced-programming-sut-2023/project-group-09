package Model.Building.CastleBuildings;

import Model.Government;
import Model.Human.Human;

import java.util.ArrayList;
import java.util.HashMap;

public class Gatehouse extends CastleBuilding {
    private int capacity;

    public Gatehouse(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                     String type, int maxHp, int startX, int startY, int endX, int endY, int capacity) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
