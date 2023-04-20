package model.human.military;


import enumeration.dictionary.Weapons;

import java.util.ArrayList;

public class EuropeanTroop extends Military {
    ArrayList<Weapons> weapons;
    public EuropeanTroop(int speed, int defenseRating, int shootingRange, int attackRating) {
        super(speed, defenseRating, shootingRange, attackRating);
    }
}
