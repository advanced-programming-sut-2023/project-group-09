package model.human.military;

import model.tools.Tool;

public class Engineer extends EuropeanTroop {
    private Tool target;
    private boolean isInTool = false;
    private boolean hasOil = false;
    public Engineer(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);

    }

    public Engineer(Engineer engineer) {
        super(engineer.getSpeed(), engineer.getDefenseRating(),
                engineer.getShootingRange(), engineer.getAttackRating(), engineer.getPrice());
        super.setArmours(engineer.getArmours());
        super.setName(engineer.getName());
        super.setWeapon(engineer.getWeapon());
        super.setMilitaryState(engineer.getMilitaryState());
        super.setDefenseRange(engineer.getDefenseRange());
        super.setAggressiveRange(engineer.getAggressiveRange());
        super.setUsesLadder(engineer.isUsesLadder());
        super.setDigsMoat(engineer.isDigsMoat());
    }

    public Tool getTarget() {
        return target;
    }

    public void setTarget(Tool target) {
        this.target = target;
    }

    public boolean isInTool() {
        return isInTool;
    }

    public void setInTool(boolean inTool) {
        isInTool = inTool;
    }

    public boolean isHasOil() {
        return hasOil;
    }

    public void setHasOil(boolean hasOil) {
        this.hasOil = hasOil;
    }
}
