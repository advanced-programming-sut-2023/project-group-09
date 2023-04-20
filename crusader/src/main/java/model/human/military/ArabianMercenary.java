package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import model.Government;

public class ArabianMercenary extends Military {
    public ArabianMercenary(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange, attackRating, price);
    }
}
