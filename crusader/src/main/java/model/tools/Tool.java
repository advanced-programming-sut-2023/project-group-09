package model.tools;

import controller.GovernmentController;
import controller.MapController;
import model.Government;
import model.activity.ToolAttack;
import model.activity.ToolMove;
import model.human.military.Engineer;

import java.util.ArrayList;

public class Tool {
    private Government government = null;

    private boolean canMove;
    private boolean canAttack = false;
    private boolean canAirAttack = false;

    private boolean attackToBuilding = false;
    private boolean attackToHuman = false;
    private boolean useStone = false;
    private String name;

    private int x, y;

    private int numberOfRequiredEngineers;
    private int speed;

    private ArrayList<Engineer> engineers = new ArrayList<>();
    private int shootingRange;
    private int damage;
    private int maxHp = 20;
    private int hp = 20;
    private int stoneNumber = 0;
    private ToolMove toolMove;
    private ToolAttack toolAttack;

    public Tool(String name, int numberOfRequiredEngineers, int speed, int shootingRange, int damage) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.speed = speed;
        this.shootingRange = shootingRange;
        this.damage = damage;
        this.name = name;
        this.stoneNumber = 20;
        this.toolAttack = new ToolAttack(this);
    }

    public Tool(Tool tool) {
        this.numberOfRequiredEngineers = tool.numberOfRequiredEngineers;
        this.speed = tool.getSpeed();
        this.shootingRange = tool.shootingRange;
        this.damage = tool.damage;
        this.canAirAttack = tool.canAirAttack;
        this.canAttack = tool.canAttack;
        this.canMove = tool.canMove;
        this.name = tool.name;
        this.attackToHuman = tool.attackToHuman;
        this.attackToBuilding = tool.attackToBuilding;
        this.useStone = tool.useStone;
        this.toolAttack = new ToolAttack(this);
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
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

    public void setEngineers(ArrayList<Engineer> engineers) {
        this.engineers = engineers;
    }

    public void addEngineer(Engineer engineer) {
        if (engineers.size() == numberOfRequiredEngineers) {
            return;
        }
        this.engineers.add(engineer);
    }

    public int getStoneNumber() {
        return stoneNumber;
    }

    public void addStone(int amount) {
        this.stoneNumber += amount;
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

    public boolean isUseStone() {
        return useStone;
    }

    public void setUseStone(boolean useStone) {
        this.useStone = useStone;
    }

    public void setStoneNumber(int stoneNumber) {
        this.stoneNumber = stoneNumber;
    }

    public void decreaseStoneNumber(int stoneNumber) {
        this.stoneNumber -= stoneNumber;
    }

    public boolean chargeStone() {
        if (GovernmentController.consumeProduct(government, "stone", 10)) {
            stoneNumber += 10;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        engineers.removeIf(i -> i.getGovernment() == null);
        return engineers.size() == numberOfRequiredEngineers;
    }

    public int takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            for (Engineer engineer : engineers) {
                MapController.deleteMilitary(engineer.getX(), engineer.getY(), engineer);
                engineer.setGovernment(null);
            }
        }
        return hp;
    }

    public boolean isAttackToBuilding() {
        return attackToBuilding;
    }

    public void setAttackToBuilding(boolean attackToBuilding) {
        this.attackToBuilding = attackToBuilding;
    }

    public boolean isAttackToHuman() {
        return attackToHuman;
    }

    public void setAttackToHuman(boolean attackToHuman) {
        this.attackToHuman = attackToHuman;
    }

}
