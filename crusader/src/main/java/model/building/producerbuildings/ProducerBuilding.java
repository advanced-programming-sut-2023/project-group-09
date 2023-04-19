package model.building.producerbuildings;

import model.building.Building;
import model.Government;

public class ProducerBuilding extends Building {
    public ProducerBuilding(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                            String type, int maxHp, int startX, int startY, int endX, int endY) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
    }
}
