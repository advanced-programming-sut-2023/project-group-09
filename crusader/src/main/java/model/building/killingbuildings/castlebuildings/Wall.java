package model.building.killingbuildings.castlebuildings;

public class Wall extends CastleBuilding {
    private int height;

    public Wall(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                String name, int maxHp, int width, int length, int height) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.height = height;
    }
}
