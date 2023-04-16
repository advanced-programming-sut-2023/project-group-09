package Model.Building.ProducerBuildings;

import Model.Building.Building;
import Model.Government;
import Model.Human.Human;

import java.util.ArrayList;
import java.util.HashMap;

public class ProducerBuilding extends Building {
    public ProducerBuilding(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                            String type, int maxHp, int startX, int startY, int endX, int endY) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
    }
}
