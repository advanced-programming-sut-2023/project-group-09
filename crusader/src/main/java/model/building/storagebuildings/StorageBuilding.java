package model.building.storagebuildings;

import model.building.Building;
import model.Government;

public class StorageBuilding extends Building {
    private String itemType;
    private int capacity;

    public StorageBuilding(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                           int maxHp, int startX, int startY, int endX, int endY, String itemType, int capacity) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
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
