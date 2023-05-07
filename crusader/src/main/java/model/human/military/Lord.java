package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import model.Government;

public class Lord extends Military {
    public Lord(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, 0);
    }

    public Lord(Lord lord) {
        super(lord.getSpeed(), lord.getDefenseRating(), lord.getShootingRange(), lord.getAttackRating(), 0);
        super.setArmours(lord.getArmours());
        super.setName(lord.getName());
        super.setWeapon(lord.getWeapon());
        super.setMilitaryState(lord.getMilitaryState());
        super.setDefenseRange(lord.getDefenseRange());
        super.setAggressiveRange(lord.getAggressiveRange());
    }
}
