package model.building.storagebuildings;

import model.building.Building;
import model.Government;

public class StorageBuilding extends Building {
    private String itemType;
    private int capacity;

    public StorageBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                           int maxHp, int width, int length, String itemType, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.itemType = itemType;
        this.capacity = capacity;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
