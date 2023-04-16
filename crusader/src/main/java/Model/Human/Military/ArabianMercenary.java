package Model.Human.Military;

import Enumeration.AttackRating;
import Enumeration.DefenseRating;
import Model.Government;
import Model.Human.Military.Military;

public class ArabianMercenary extends Military {
    public ArabianMercenary(Government government, int speed, int x, int y, DefenseRating defenseRating, int health, int shootingRange, AttackRating attackRating) {
        super(government, speed, x, y, defenseRating, health, shootingRange, attackRating);
    }
}
