package Model.Human;

import Enumeration.DefenseRating;
import Enumeration.HumanStates;
import Model.Government;

public abstract class Human {
    private Government government = null;
    private int speed;
    private int x, y;
    private DefenseRating defenseRating;
    private int health;
    private HumanStates state;
    private int shootingRange;

    public Human(Government government, int speed, int x, int y, DefenseRating defenseRating, int health, int shootingRange) {
        this.government = government;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.defenseRating = defenseRating;
        this.health = health;
        this.shootingRange = shootingRange;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DefenseRating getDefenseRating() {
        return defenseRating;
    }

    public void setDefenseRating(DefenseRating defenseRating) {
        this.defenseRating = defenseRating;
    }
}
