package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.dictionary.Weapons;
import model.Government;

import java.util.ArrayList;

public class EuropeanTroop extends Military {
    ArrayList<Weapons> weapons;
    public EuropeanTroop(int speed, DefenseRating defenseRating, int health, int shootingRange, AttackRating attackRating , ArrayList<Weapons> weapons) {
        super(speed, defenseRating, health, shootingRange, attackRating);
        this.weapons = weapons;
    }
}
