package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import model.Government;

public class EuropeanTroop extends Military {
    public EuropeanTroop(int speed, DefenseRating defenseRating, int health, int shootingRange, AttackRating attackRating) {
        super(speed, defenseRating, health, shootingRange, attackRating);
    }
}
