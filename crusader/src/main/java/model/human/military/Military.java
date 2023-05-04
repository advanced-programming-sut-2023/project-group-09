package model.human.military;


import controller.human.MoveController;
import model.human.Human;

import java.util.ArrayList;

public abstract class Military extends Human implements Cloneable {
    private int attackRating;
    private String militaryState;
    private boolean usesHorse = false;
    private boolean usesLadder = false;
    private boolean digsMoat = false;
    private String weapon;
    private final ArrayList<String> armours = new ArrayList<>();
    private int price;
    private int defenseRange;
    private int aggressiveRange;


    //
    public Military(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange);
        this.attackRating = attackRating;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public ArrayList<String> getArmours() {
        return armours;
    }

    public void addArmour(String armour) {
        this.armours.add(armour);
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

    public boolean canAirAttack() {
        if (armours.contains("bow") || armours.contains("crossBow")) {
            return true;
        }
        return false;
    }

    public void attack() {
        //
    }

    public void attack(int x, int y) {
        if (canAirAttack()) {
            double distance = MoveController.getDistance(x, y, this.getX(), this.getY());
            if (distance < this.getShootingRange() ||Math.abs(this.getShootingRange() - distance) < 0.5){

            }
        }
    }

    public int getAggressiveRange() {
        return aggressiveRange;
    }

    public void setAggressiveRange(int aggressiveRange) {
        this.aggressiveRange = aggressiveRange;
    }

    @Override

    public Military clone() throws CloneNotSupportedException {
        return (Military) super.clone();
    }
}
