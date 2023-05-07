package model.tools;

import model.Government;
import model.activity.ToolAttack;
import model.activity.ToolMove;
import model.human.military.Engineer;

import java.util.ArrayList;

public class Tool implements Cloneable {
    private Government government = null;

    private boolean canMove;
    private boolean isActive = false;
    private boolean canAttack = false;
    private boolean canAirAttack = false;
    private String name;

    private int x, y;

    private int numberOfRequiredEngineers;
    private int speed;

    private final ArrayList<Engineer> engineers = new ArrayList<>();
    private int shootingRange;
    private int damage;
    private int hp;
    private ToolMove toolMove;
    private ToolAttack toolAttack;

    public Tool(int numberOfRequiredEngineers, int speed, int shootingRange, int damage) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.speed = speed;
        this.shootingRange = shootingRange;
        this.damage = damage;
    }
    public Tool(Tool tool) {
        this.numberOfRequiredEngineers = tool.numberOfRequiredEngineers;
        this.speed = tool.getSpeed();
        this.shootingRange = tool.shootingRange;
        this.damage = tool.damage;
        this.canAirAttack = tool.canAirAttack;
        this.canAttack = tool.canAttack;
        this.isActive = tool.isActive;
        this.canMove = tool.canMove;
        toolAttack = new ToolAttack(this);
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

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
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

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public void addEngineer(Engineer engineer) {
        if(engineers.size() == numberOfRequiredEngineers){
            return;
        }
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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isCanAirAttack() {
        return canAirAttack;
    }

    public void setCanAirAttack(boolean canAirAttack) {
        this.canAirAttack = canAirAttack;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public ToolMove getToolMove() {
        return toolMove;
    }

    public void setToolMove(ToolMove toolMove) {
        this.toolMove = toolMove;
    }

    public ToolAttack getToolAttack() {
        return toolAttack;
    }

    public void setToolAttack(ToolAttack toolAttack) {
        this.toolAttack = toolAttack;
    }

    @Override
    public Tool clone() throws CloneNotSupportedException {
        return (Tool) super.clone();
    }

}
