package model.human.military;

import enumeration.AttackRating;
import enumeration.DefenseRating;
import enumeration.StateOfMilitary;
import model.Government;
import model.human.Human;

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
