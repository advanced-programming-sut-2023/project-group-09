package model.human.military;

import enumeration.MilitaryStates;
import model.activity.Attack;
import model.activity.Move;
import model.human.Human;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Military extends Human{
    private final int attackRating;
    private String militaryState = MilitaryStates.STAND_GROUND.getState();
    private boolean usesHorse = false;
    private boolean usesLadder = false;
    private boolean digsMoat = false;
    private String weapon;
    private ArrayList<String> armours = new ArrayList<>();
    private int price;
    private int defenseRange;
    private int aggressiveRange;

    private int attackStepCount = 8;
    private int airAttackStepCount = 8;
     Attack attack;


    public Military(int speed, int defenseRating, int shootingRange, int attackRating, int price) {
        super(speed, defenseRating, shootingRange);
        this.attackRating = attackRating;
        this.price = price;
        attack = new Attack(this);
    }
    public Military(Military military) {
        super(military.getSpeed(), military.getDefenseRating(), military.getShootingRange());
        this.attackRating = military.getAttackRating();
        this.price = military.getPrice();
        attack = new Attack(this);
        armours = military.getArmours();
    }

    public int getAttackStepCount() {
        return attackStepCount;
    }

    public void setAttackStepCount(int attackStepCount) {
        this.attackStepCount = attackStepCount;
    }

    public int getAirAttackStepCount() {
        return airAttackStepCount;
    }

    public void setAirAttackStepCount(int airAttackStepCount) {
        this.airAttackStepCount = airAttackStepCount;
    }

    public void setArmours(ArrayList<String> armours) {
        this.armours = armours;
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

    public void setUsesHorse(boolean usesHorse) {
        this.usesHorse = usesHorse;
    }

    public void setUsesLadder(boolean usesLadder) {
        this.usesLadder = usesLadder;
    }

    public void setDigsMoat(boolean digsMoat) {
        this.digsMoat = digsMoat;
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
//        if(this.getGovernment() != null){
//            if (this.getGovernment().getFearRate() > 0){
//                return attackRating;
//            }
//            return this.getGovernment().getFearRate() * (-1) + attackRating;
//        }
        return attackRating;
    }


    public String getMilitaryState() {
        return militaryState;
    }

    public void setMilitaryState(String militaryState) {
        this.militaryState = militaryState;
    }

    public boolean canAirAttack() {
        if (weapon != null && (weapon.equals("bow") || weapon.equals("crossBow"))) {
            return true;
        }
        return getName().equals("fireThrower");
    }

    public int getAggressiveRange() {
        return aggressiveRange;
    }

    public void setAggressiveRange(int aggressiveRange) {
        this.aggressiveRange = aggressiveRange;
    }

    public int getDefenseRange() {
        return defenseRange;
    }

    public void setDefenseRange(int defenseRange) {
        this.defenseRange = defenseRange;
    }

    public int takeDamage(int damage){
        int newHp = this.getHealth() - damage;
        this.setHealth(newHp);
        if (HumanViewController.getTroopFromMilitary(this) != null){
            Objects.requireNonNull(HumanViewController.getTroopFromMilitary(this)).showProgressBar();
            if (newHp <= 0){
                Objects.requireNonNull(HumanViewController.getTroopFromMilitary(this)).hideProgressBar();
            }
        }
        return newHp;
    }

    public Attack getAttack() {
        if(attack == null){
            attack = new Attack(this);
        }
        return attack;
    }

    @Override
    public void setMove(Move move) {
        super.setMove(move);
        attack = new Attack(this);
    }
}
