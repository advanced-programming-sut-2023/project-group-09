package model.human;


import model.Government;
import model.activity.Move;

public abstract class Human implements Cloneable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    private String name;
    private Government government = null;
    private int speed;
    private int x, y;
    private int defenseRating;
    private int health;

    private int shootingRange;

    private boolean isInvisible = false;
     private Move move;

    public Human(int speed, int defenseRating, int shootingRange) {
        this.speed = speed;
        this.defenseRating = defenseRating;
        this.health = defenseRating;
        this.shootingRange = shootingRange;
    }


    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisible(boolean invisible) {
        isInvisible = invisible;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }


    public void enableInvisible() {
        this.isInvisible = true;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getShootingRange() {
        return shootingRange;
    }

    public void setShootingRange(int shootingRange) {
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

    public int getDefenseRating() {
        return defenseRating;
    }

    public void setDefenseRating(int defenseRating) {
        this.defenseRating = defenseRating;
    }

}
