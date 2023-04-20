package model.building.castlebuildings;

import model.Government;

public class Wall extends CastleBuilding {
    private int height;

    public Wall(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                String type, int maxHp, int width, int length, int height) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.height = height;
    }
}
