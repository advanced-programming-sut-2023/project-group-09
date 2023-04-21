package model.building.producerbuildings;

import enumeration.Textures;
import model.building.Building;
import model.Government;
import model.building.storagebuildings.StorageBuilding;

import java.util.ArrayList;
import java.util.Iterator;

public class ProducerBuilding extends Building {
    private ArrayList<Textures> suitableTextures = new ArrayList<>();

    private String nameOfStorage;
    private String itemName;
    private String itemType;
    private int rate;
    public ProducerBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                            String type, int maxHp, int width, int length , int rate, String nameOfStorage ,
                            String itemType, String itemName) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.rate = rate;
        this.nameOfStorage = nameOfStorage;
        this.itemType = itemType;
        this.itemName = itemName;
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
            int saved = Math.min(amount , storage.remained());
            amount -= saved;
            storage.addAmount(saved);
        }
        government.addAmountToProperties(this.itemName , this.itemType,this.rate - amount);
    }
}
