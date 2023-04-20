package model.human.military;


import model.human.Human;

public abstract class Military extends Human {
    private int attackRating;
    private String militaryState;

    public Military(int speed, int defenseRating, int shootingRange, int attackRating) {
        super(speed, defenseRating, shootingRange);
        this.attackRating = attackRating;
    }

    public int getAttackRating() {
        return attackRating;
    }

    public void setAttackRating(int attackRating) {
        this.attackRating = attackRating;
    }

    public String getMilitaryState() {
        return militaryState;
    }

    public void setMilitaryState(String militaryState) {
        this.militaryState = militaryState;
    }
}
