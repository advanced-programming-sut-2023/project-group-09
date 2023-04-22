package model.tools;

import enumeration.AttackRating;
import enumeration.Speed;
import model.Government;
import model.human.Human;
import model.Permission;

import java.util.ArrayList;

public class AttackingAndDefendingTool {
    private Government government = null;
    private int width, length;
    private int startX, startY;
    private int endX, endY;
    private int numberOfRequiredEngineers;
    private int speed;
    private ArrayList<Permission> permissions = new ArrayList<>();
    private ArrayList<Human> engineers = new ArrayList<>();
    private int shootingRange;
    private int damage;

    public AttackingAndDefendingTool(int numberOfRequiredEngineers, int speed, int shootingRange, int damage, int width, int length) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.speed = speed;
        this.shootingRange = shootingRange;
        this.damage = damage;
        this.width = width;
        this.length = length;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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
