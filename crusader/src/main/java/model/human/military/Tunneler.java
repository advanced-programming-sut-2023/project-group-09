package model.human.military;

import controller.MapController;
import model.building.Building;

public class Tunneler extends EuropeanTroop {

    Building targetBuilding;
    Building targetTunnel;

    public Tunneler(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }

    public Tunneler(EuropeanTroop europeanTroop) {
        super(europeanTroop.getSpeed(), europeanTroop.getDefenseRating(),
                europeanTroop.getShootingRange(), europeanTroop.getAttackRating(), europeanTroop.getPrice());
        super.setArmours(europeanTroop.getArmours());
        super.setName(europeanTroop.getName());
        super.setWeapon(europeanTroop.getWeapon());
        super.setMilitaryState(europeanTroop.getMilitaryState());
        super.setDefenseRange(europeanTroop.getDefenseRange());
        super.setAggressiveRange(europeanTroop.getAggressiveRange());
        super.setUsesLadder(europeanTroop.isUsesLadder());
        super.setDigsMoat(europeanTroop.isDigsMoat());
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    public Building getTargetTunnel() {
        return targetTunnel;
    }

    public void setTargetTunnel(Building targetTunnel) {
        this.targetTunnel = targetTunnel;
    }

    public void digTunnel(){
        MapController.deleteBuilding(targetTunnel);
        int hp = targetBuilding.takeDamage(getAttackRating());

        if (hp <= 0){
            MapController.deleteBuilding(targetBuilding);
            targetBuilding.setGovernment(null);
        }
        targetTunnel = null;
        targetBuilding = null;
    }
}
