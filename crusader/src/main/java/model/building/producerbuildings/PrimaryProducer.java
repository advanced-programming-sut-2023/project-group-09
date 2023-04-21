package model.building.producerbuildings;

import model.Government;

public class PrimaryProducer extends model.building.producerbuildings.ProducerBuilding {
    private String primaryType;

    public PrimaryProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                           String type, int maxHp, int width,
                           int length, int rate, String nameOfStorage, String itemType,
                           String itemName, String primaryType) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width,
                length, rate, nameOfStorage, itemType, itemName);
        this.primaryType = primaryType;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }
}
