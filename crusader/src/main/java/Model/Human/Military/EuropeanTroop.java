package Model.Human.Military;

import Enumeration.AttackRating;
import Enumeration.DefenseRating;
import Model.Government;
import Model.Human.Military.Military;

import java.util.ArrayList;

public class EuropeanTroop extends Military {
    public EuropeanTroop(Government government, int speed, int x, int y, DefenseRating defenseRating, int health, int shootingRange, AttackRating attackRating) {
        super(government, speed, x, y, defenseRating, health, shootingRange, attackRating);
    }
}
