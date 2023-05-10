package model.building.storagebuildings;

import model.Government;
import model.building.Building;

import java.util.HashMap;

public class StorageBuilding extends Building {
    private final String itemType;
    private int capacity;
    private int amount;

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }

    private HashMap<String, Integer> items = new HashMap<>();

    public StorageBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name,
                           int maxHp, int width, int length, String itemType, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.itemType = itemType;
        this.capacity = capacity;
    }

    public StorageBuilding(StorageBuilding storageBuilding) {
        super(storageBuilding.getNumberOfRequiredWorkers(), storageBuilding.getNumberOfRequiredEngineers(),
                storageBuilding.getName(), storageBuilding.getMaxHp(), storageBuilding.getWidth(), storageBuilding.getLength());
        this.itemType = storageBuilding.itemType;
        this.capacity = storageBuilding.getCapacity();
    }
    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void addAmount(int amountAdded) {
        this.amount += amountAdded;
    }

    public int remained() {
        return this.capacity - this.amount;
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
            government.getProperties().put(itemName, government.getProperties().get(itemName) - amountRemoved);
        }
        government.getStorages().get(itemType).addCapacity(-this.capacity);
        government.getStorages().get(itemType).addAmount(-amountOfAllRemoved);
    }

    public void addItem(String key, int value) {
        int currentValue = items.get(key) != null ?items.get(key) : 0;
        items.put(key, currentValue + value);
    }

    public int getItemAmount(String key) {
        return items.get(key);
    }

    public String getItemType() {
        return itemType;
    }
}
