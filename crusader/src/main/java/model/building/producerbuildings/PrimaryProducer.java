package model.building.producerbuildings;

import model.Government;

public class PrimaryProducer extends model.building.producerbuildings.ProducerBuilding {
    private String primaryType;

    public PrimaryProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                           int maxHp, int width, int length, String primaryType) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.primaryType = primaryType;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }
}
