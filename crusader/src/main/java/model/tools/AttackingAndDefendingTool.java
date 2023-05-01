package model.tools;

//import jdk.incubator.vector.VectorOperators;
import model.Government;
import model.Permission;
import model.human.military.Engineer;

import java.util.ArrayList;

public class AttackingAndDefendingTool implements Cloneable {
    private Government government = null;
    private int width, length;

    private String name;
    private int startX, startY;
    private int endX, endY;
    private int numberOfRequiredEngineers;
    private int speed;
    private ArrayList<Permission> permissions = new ArrayList<>();
    private ArrayList<Engineer> engineers = new ArrayList<>();
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

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public void addEngineer(Engineer engineer) {
        this.engineers.add(engineer);
    }

    public int getShootingRange() {
        return shootingRange;
    }

    public void setShootingRange(int shootingRange) {
        this.shootingRange = shootingRange;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public AttackingAndDefendingTool clone() throws CloneNotSupportedException {
        return (AttackingAndDefendingTool) super.clone();
    }

}
