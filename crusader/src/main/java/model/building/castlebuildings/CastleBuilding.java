package model.building.castlebuildings;

import model.building.Building;

public class CastleBuilding extends Building {
    protected int capacity;
    private int height;

    public CastleBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                          String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
