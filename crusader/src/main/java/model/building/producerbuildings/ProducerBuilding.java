package model.building.producerbuildings;

import enumeration.Textures;
import model.building.Building;
import model.Government;
import model.building.storagebuildings.StorageBuilding;
import model.human.civilian.Civilian;

import java.util.ArrayList;
import java.util.Iterator;

public class ProducerBuilding extends Building {
    private Civilian worker;

    private String nameOfStorage;
    private ArrayList<String> itemNames = new ArrayList<>();
    private String itemType;
    private int rate;

    public ProducerBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                            String type, int maxHp, int width, int length, int rate, String nameOfStorage,
                            String itemType, String itemName) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.rate = rate;
        this.nameOfStorage = nameOfStorage;
        this.itemType = itemType;
    }

    public void addItemName(String itemName) {
        this.itemNames.add(itemName);
    }

    public void addProduct() {
        int amount = this.rate;
        Government government = this.getGovernment();
        ArrayList<Building> storages = government.getBuildings().get(this.nameOfStorage).getBuildings();
        Iterator itr = storages.iterator();
        while (itr.hasNext()) {
            if (amount == 0) {
                break;
            }
            StorageBuilding storage = (StorageBuilding) itr.next();
            int saved = Math.min(amount, storage.remained());
            amount -= saved;
            storage.addAmount(saved);
        }
//        government.addAmountToProperties(this.itemName, this.itemType, this.rate - amount);
    }
}
