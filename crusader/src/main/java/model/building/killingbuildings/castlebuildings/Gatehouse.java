package model.building.killingbuildings.castlebuildings;

public class Gatehouse extends CastleBuilding {
    private int capacity;

    public Gatehouse(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                     String name, int maxHp, int width, int length, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
