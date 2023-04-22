package model.building.castlebuildings;

import model.Government;

public class Tower extends CastleBuilding {
    // 1000 for smallStoneCastle 10
    // 2000 for bigStoneCastle 20
    // 250 for lookout 10 ---- 60
    // 1000 for perimeter turret 10 ---- 40
    // 1200 for defense turret 15 ---- 40
    // 1600 for square tower 35 ---- 50
    // 2000 for round tower 40 ---- 50
    // 0 for walls 1
    // 0 for drawbridge 10 woods



    private int fireRange;
    private int defendRange;
    private String typeOfTower;
    private boolean canKeepRidingEquipment;

    public boolean isCanKeepRidingEquipment() {
        return this.canKeepRidingEquipment;
    }

    public void setCanKeepRidingEquipment(boolean canKeepRidingEquipment) {
        this.canKeepRidingEquipment = canKeepRidingEquipment;
    }

    public Tower(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type, int maxHp,
                 int width, int length, int fireRange, int defendRange) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
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
