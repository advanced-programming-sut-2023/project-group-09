package model.building.producerbuildings;

public class Quarry extends ProducerBuilding{

    public Quarry(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length, int rate, String nameOfStorage, String itemType, String itemName) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length, rate, nameOfStorage, itemType, itemName);
    }

    @Override
    public void addProduct(){
        //
    }
}
