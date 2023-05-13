package model.building.producerbuildings;

import enumeration.Textures;
import model.Government;
import model.building.Building;
import model.building.storagebuildings.StorageBuilding;
import model.buildinghandler.BuildingCounter;

import java.util.ArrayList;

public class Quarry extends ProducerBuilding{

    public Quarry() {
        super(3, 0, "quarry", 30, 6, 6, 12, "stockPile","resource","stone");
        this.addCost("wood",20);
        this.enableHasSpecialTexture();
        this.addTexture(Textures.BOULDER);
    }

    @Override
    public void addProduct(){
        BuildingCounter buildingCounter = getGovernment().getBuildingData("OxTether");
        if (buildingCounter.getNumber() == 0){
            return;
        }
        if (!hasRequired()) {
            return;
        }

        consumeRequired();
        int amount = buildingCounter.getNumber() * 12;
        Government government = this.getGovernment();
        ArrayList<Building> storages = government.getBuildings().get(this.getNameOfStorage()).getBuildings();
        for (Building building : storages) {
            if (amount == 0) {
                break;
            }
            StorageBuilding storage = (StorageBuilding) building;
            int saved = Math.min(amount, storage.remained());
            amount -= saved;
            storage.addAmount(saved);
            storage.addItem("stone", saved);
        }
        government.addAmountToProperties(this.getItemName(), this.getItemType(), buildingCounter.getNumber() * 12 - amount);
    }
}
