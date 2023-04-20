package model.building.castlebuildings;

import model.building.Building;
import model.Government;

public class CastleBuilding extends Building {
    public CastleBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                          String type, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
    }
}
