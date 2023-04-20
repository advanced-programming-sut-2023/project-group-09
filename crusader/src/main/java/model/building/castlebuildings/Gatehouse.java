package model.building.castlebuildings;

import model.Government;

public class Gatehouse extends CastleBuilding {
    private int capacity;

    public Gatehouse(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                     String type, int maxHp, int width, int length, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
