package model.building.producerbuildings;

import model.Government;

public class PrimaryProducer extends ProducerBuilding {
    private String primaryType;

    public PrimaryProducer(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                           int maxHp, int startX, int startY, int endX, int endY, String primaryType) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.primaryType = primaryType;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }
}
