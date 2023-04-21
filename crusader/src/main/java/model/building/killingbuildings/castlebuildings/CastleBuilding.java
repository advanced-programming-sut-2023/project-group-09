package model.building.killingbuildings.castlebuildings;

import model.building.Building;
import model.Government;

public class CastleBuilding extends Building {
    public CastleBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                          String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
    }
}
