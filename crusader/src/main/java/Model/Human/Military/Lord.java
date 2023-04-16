package Model.Human.Military;

import Enumeration.AttackRating;
import Enumeration.DefenseRating;
import Model.Government;
import Model.Human.Human;

public class Lord extends Military {
    public Lord(Government government, int speed, int x, int y, DefenseRating defenseRating, int health, int shootingRange, AttackRating attackRating) {
        super(government, speed, x, y, defenseRating, health, shootingRange, attackRating);
    }
}
