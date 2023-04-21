package model.building.killingbuildings.castlebuildings;

public class Tower extends CastleBuilding {
    private int fireRange;
    private int defendRange;

    public Tower(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp,
                 int width, int length, int fireRange, int defendRange) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }

    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public void setDefendRange(int defendRange) {
        this.defendRange = defendRange;
    }
}
