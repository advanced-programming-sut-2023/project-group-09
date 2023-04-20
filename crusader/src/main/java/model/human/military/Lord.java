package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import model.Government;

public class Lord extends Military {
    public Lord(int speed, int defenseRating, int health, int shootingRange, int attackRating) {
        super(speed, defenseRating, shootingRange, attackRating);
    }
}
