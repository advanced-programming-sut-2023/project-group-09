package model.building.producerbuildings;

import controller.GovernmentController;
import controller.human.MoveController;
import model.Government;
import model.building.Building;
import model.building.storagebuildings.StorageBuilding;
import model.buildinghandler.BuildingCounter;
import model.game.Tuple;
import model.human.Human;
import model.human.civilian.Civilian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ProducerBuilding extends Building {


    private final String nameOfStorage;
    private String itemName;
    private final String itemType;
    private final int rate;
    private int inUse; // it just uses for stable // TODO: handle it.

    private int countOfRoundsToProduce = 0;

    boolean start = false;




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
    public ProducerBuilding(ProducerBuilding producerBuilding) {
        super(producerBuilding.getNumberOfRequiredWorkers(), producerBuilding.getNumberOfRequiredEngineers(),
                producerBuilding.getName(), producerBuilding.getMaxHp(), producerBuilding.getWidth(), producerBuilding.getLength());
        this.rate = producerBuilding.getRate();
        this.nameOfStorage = producerBuilding.getNameOfStorage();
        this.itemType = producerBuilding.getItemType();
        this.itemName = producerBuilding.itemName;
        this.required = producerBuilding.getRequired();
        super.setCost(producerBuilding.getCost());
    }
    public void setRequired(HashMap<String, Integer> required) {
        this.required = required;
    }
    public void doAction() {

        if (!this.isActive()){
            return;
        }
        if (countOfRoundsToProduce == 0 && start) {
            addProduct();
            computeActionTurn();
        }
        if (!start && countOfRoundsToProduce == 0){
            start = true;
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

    public void setItemName(String name) {
        itemName = name;
    }

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

    public String getNameOfStorage() {
        return nameOfStorage;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public int getRate() {
        return rate;
    }

    public int getInUse() {
        return inUse;
    }

    public void setInUse(int inUse) {
        this.inUse = inUse;
    }

    public int getCountOfRoundsToProduce() {
        return countOfRoundsToProduce;
    }

    public void setCountOfRoundsToProduce(int countOfRoundsToProduce) {
        this.countOfRoundsToProduce = countOfRoundsToProduce;
    }

    public HashMap<String, Integer> getRequired() {
        return required;
    }

    public void computeActionTurn() {
        //-----
        BuildingCounter buildingCounter;
        Building building;
        if((buildingCounter = getGovernment().getBuildings().get(nameOfStorage))!= null && buildingCounter.getNumber() != 0){
            building = buildingCounter.getBuildings().get(0);
            double distance = MoveController.getDistance(building.getStartX(),building.getStartY(),getStartX(),getStartY());
            Civilian civilian = (Civilian) getRequiredHumans().get(0);
            countOfRoundsToProduce = (int) (distance / civilian.getSpeed());
            return;
        }
        start = false;
        countOfRoundsToProduce = 0;
    }

}
