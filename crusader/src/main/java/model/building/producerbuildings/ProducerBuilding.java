package model.building.producerbuildings;

import model.building.Building;
import model.Government;

public class ProducerBuilding extends Building {
    public ProducerBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                            String type, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
    }
}
