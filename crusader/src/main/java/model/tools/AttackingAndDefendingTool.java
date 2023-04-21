package model.tools;

import enumeration.AttackRating;
import enumeration.Speed;
import model.Government;
import model.human.Human;
import model.Permission;

import java.util.ArrayList;

public class AttackingAndDefendingTool {
    private Government government = null;
    private int x, y;
    private int numberOfRequiredEngineers;
    private int speed;
    private ArrayList<Permission> permissions = new ArrayList<>();
    private ArrayList<Human> engineers = new ArrayList<>();
    private int shootingRange;
    private int damage;

    public AttackingAndDefendingTool(int numberOfRequiredEngineers, int speed, int shootingRange, int damage) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.speed = speed;
        this.shootingRange = shootingRange;
        this.damage = damage;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
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

    public int getNumberOfRequiredEngineers() {
        return numberOfRequiredEngineers;
    }

    public void setNumberOfRequiredEngineers(int numberOfRequiredEngineers) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<Permission> permissions) {
        this.permissions = permissions;
    }

    public ArrayList<Human> getEngineers() {
        return engineers;
    }

    public void setEngineers(ArrayList<Human> engineers) {
        this.engineers = engineers;
    }

    public int getShootingRange() {
        return shootingRange;
    }

    public void setShootingRange(int shootingRange) {
        this.shootingRange = shootingRange;
    }
}
