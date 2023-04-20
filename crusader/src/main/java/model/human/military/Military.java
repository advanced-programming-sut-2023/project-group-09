package model.human.military;


import model.goods.Goods;
import model.human.Human;

public abstract class Military extends Human {
    private int attackRating;
    private String militaryState;
    private boolean usesHorse = false;
    private boolean usesLadder = false;
    private boolean digsMoat = false;
    private Goods weapon;


    public Military(int speed, int defenseRating, int shootingRange, int attackRating) {
        super(speed, defenseRating, shootingRange);
        this.attackRating = attackRating;
    }

    public void setUsesHorse(boolean usesHorse) {
        this.usesHorse = usesHorse;
    }

    public void setUsesLadder(boolean usesLadder) {
        this.usesLadder = usesLadder;
    }

    public void setDigsMoat(boolean digsMoat) {
        this.digsMoat = digsMoat;
    }

    public Goods getWeapon() {
        return weapon;
    }

    public void setWeapon(Goods weapon) {
        this.weapon = weapon;
    }

    public boolean isUsesHorse() {
        return usesHorse;
    }

    public void enableUsesHorse() {
        this.usesHorse = true;
    }

    public boolean isUsesLadder() {
        return usesLadder;
    }

    public void enableUsesLadder() {
        this.usesLadder = true;
    }

    public boolean isDigsMoat() {
        return digsMoat;
    }

    public void enableDigsMoat() {
        this.digsMoat = true;
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
