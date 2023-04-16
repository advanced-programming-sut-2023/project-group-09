package Model.Human.Military;

import Enumeration.AttackRating;
import Enumeration.DefenseRating;
import Enumeration.StateOfMilitary;
import Model.Government;
import Model.Human.Human;

public abstract class Military extends Human {
    private AttackRating attackRating;
    private StateOfMilitary militaryState;

    public Military(Government government, int speed, int x, int y, DefenseRating defenseRating, int health, int shootingRange, AttackRating attackRating) {
        super(government, speed, x, y, defenseRating, health, shootingRange);
        this.attackRating = attackRating;
    }

    public AttackRating getAttackRating() {
        return attackRating;
    }

    public void setAttackRating(AttackRating attackRating) {
        this.attackRating = attackRating;
    }

    public StateOfMilitary getMilitaryState() {
        return militaryState;
    }

    public void setMilitaryState(StateOfMilitary militaryState) {
        this.militaryState = militaryState;
    }
}
