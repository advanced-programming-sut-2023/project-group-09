package model.building.storagebuildings;

import model.building.Building;
import model.Government;

import javax.naming.InsufficientResourcesException;
import java.util.HashMap;

public class StorageBuilding extends Building {
    private String itemType;
    private int capacity;
    private int amount;
    private HashMap<String , Integer> items = new HashMap<>();

    public StorageBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                           int maxHp, int width, int length, String itemType, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.itemType = itemType;
        this.capacity = capacity;
    }

    public String getItemType() {
        return itemType;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }
    public void addAmount(int amountAdded){
        this.amount += amountAdded;
    }
    public int remained() {
        return this.capacity - this.amount;
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
    public void deleteStorage() {
        Government government = this.getGovernment();
        int amountOfAllRemoved = 0;
        for (String itemName : items.keySet()) {
            int amountRemoved = items.get(itemName);
            amountRemoved -= amountRemoved;
            government.getProperties().put(itemName , government.getProperties().get(itemName) - amountRemoved);
        }
        government.getStorages().get(itemType).addCapacity(-this.capacity);
        government.getStorages().get(itemType).addAmount(-amountOfAllRemoved);
    }
}
