package model.building.producerbuildings;

import model.Government;

import java.util.ArrayList;

public class MilitaryProducer extends model.building.producerbuildings.ProducerBuilding {
    private ArrayList<String> militaryTypes = new ArrayList<>();

    public MilitaryProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                            int maxHp, int width, int length, int rate, String nameOfStorage,
                            String itemType, String itemName, ArrayList<String> militaryTypes) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type,
                maxHp, width, length, rate, nameOfStorage, itemType, itemName);
        this.militaryTypes = militaryTypes;
    }

    public ArrayList<String> getMilitaryTypes() {
        return militaryTypes;
    }

    public void setMilitaryTypes(ArrayList<String> militaryTypes) {
        this.militaryTypes = militaryTypes;
    }
}
