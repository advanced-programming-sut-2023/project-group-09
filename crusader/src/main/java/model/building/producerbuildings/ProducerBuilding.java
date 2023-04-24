package model.building.producerbuildings;

import enumeration.Textures;
import controller.GovernmentController;
import model.Government;
import model.building.Building;
import model.building.storagebuildings.StorageBuilding;

import java.util.ArrayList;
import java.util.HashMap;

public class ProducerBuilding extends Building {


    private final String nameOfStorage;
    private String itemName;
    private final String itemType;
    private final int rate;
    private int inUse; // it just uses for stable //TODO: handle it.

    private int countOfRoundsToProduce = 0;


    public void setRequired(HashMap<String, Integer> required) {
        this.required = required;
    }

    private HashMap<String, Integer> required = new HashMap<>();

    public ProducerBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                            String name, int maxHp, int width, int length, int rate, String nameOfStorage,
                            String itemType, String itemName) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.rate = rate;
        this.nameOfStorage = nameOfStorage;
        this.itemType = itemType;
        this.itemName = itemName;
    }

    public void doAction(){
        if (countOfRoundsToProduce == 0){
            addProduct();
            computeActionTurn();
        }
        countOfRoundsToProduce--;
    }

    public void addProduct() {
        if (!hasRequired()) {
            return;
        }
        consumeRequired();
        int amount = this.rate;
        Government government = this.getGovernment();
        ArrayList<Building> storages = government.getBuildings().get(this.nameOfStorage).getBuildings();
        for (Building building : storages) {
            if (amount == 0) {
                break;
            }
            StorageBuilding storage = (StorageBuilding) building;
            int saved = Math.min(amount, storage.remained());
            amount -= saved;
            storage.addAmount(saved);
            storage.addItem(itemName, saved);
        }
        government.addAmountToProperties(this.itemName, this.itemType, this.rate - amount);
    }

    public void addRequired(String name, int amount) {
        required.put(name, amount);
    }

    public void setItemName(String name) {itemName = name;}

    public boolean hasRequired() {
        Government government = this.getGovernment();
        for (String product : required.keySet()) {
            if (government.getPropertyAmount(product) < required.get(product)) {
                return false;
            }
        }
        return true;
    }

    public void consumeRequired() {
        Government government = this.getGovernment();
        for (String product : required.keySet()) {
            GovernmentController.consumeProduct(government, product, required.get(product));
        }

    }

    public void computeActionTurn(){
        //-----
    }

}
