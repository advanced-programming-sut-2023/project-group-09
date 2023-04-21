package model.building.producerbuildings;

import java.util.ArrayList;

public class WeaponProducer extends ProducerBuilding{

    ArrayList<String> weapons = new ArrayList<>();


    public WeaponProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length, int rate, String nameOfStorage, String itemType, String itemName) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length, rate, nameOfStorage, itemType, itemName);
    }

    public void addWeapon(String weapon){
        weapons.add(weapon);
    }

    public void changeItemName(String name){
        this.setItemName(name);
    }

    public ArrayList<String> getWeapons() {
        return weapons;
    }
}
