package model.building.castlebuildings;

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

    private boolean canKeepRidingEquipment;

    public boolean isCanKeepRidingEquipment() {
        return this.canKeepRidingEquipment;
    }

    public void setCanKeepRidingEquipment(boolean canKeepRidingEquipment) {
        this.canKeepRidingEquipment = canKeepRidingEquipment;
    }

    public Tower(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp,
                 int width, int length, int fireRange, int defendRange, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.capacity = capacity;
    }

    public Tower(Tower tower) {
        super(tower.getNumberOfRequiredWorkers(), tower.getNumberOfRequiredEngineers(), tower.getName(),
                tower.getMaxHp(), tower.getWidth(), tower.getLength());
        super.setCost(tower.getCost());
        this.fireRange = tower.getFireRange();
        this.defendRange = tower.defendRange;
        this.capacity = tower.capacity;
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

    public void attackTower() {
        // TODO: this method will attack all enemies who are in the range of defense and attack
    }
}
