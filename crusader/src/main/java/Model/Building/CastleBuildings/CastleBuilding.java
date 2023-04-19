package model.building.castlebuildings;

import model.building.Building;
import model.Government;

public class CastleBuilding extends Building {
    public CastleBuilding(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                          String type, int maxHp, int startX, int startY, int endX, int endY) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
    }
}
