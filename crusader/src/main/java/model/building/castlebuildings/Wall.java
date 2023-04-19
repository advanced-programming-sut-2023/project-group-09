package model.building.castlebuildings;

import model.Government;
public class Wall extends CastleBuilding {
    private int height;

    public Wall(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                String type, int maxHp, int startX, int startY, int endX, int endY, int height) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.height = height;
    }
}
