package model.building;

import enumeration.BuildingStates;
import model.Government;
import model.human.Human;
import model.Permission;

import java.util.ArrayList;
import java.util.HashMap;

public class Building {
    private Government government = null;
    private ArrayList<Permission> landPermissions;
    private ArrayList<Permission> activityPermissions;
    private HashMap<String, Integer> cost = new HashMap<>();
    private ArrayList<Human> requiredHumans = new ArrayList<>();
    private int numberOfRequiredWorkers;
    private int numberOfRequiredEngineers;
    private BuildingStates state;

    private String type;
    private int maxHp;
    private int hp;
    private int startX, startY;
    private int endX, endY;

    public Building(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                    String type, int maxHp, int startX, int startY, int endX, int endY) {
        this.government = government;
        this.numberOfRequiredWorkers = numberOfRequiredWorkers;
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.type = type;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public ArrayList<Permission> getLandPermissions() {
        return landPermissions;
    }

    public void setLandPermissions(ArrayList<Permission> landPermissions) {
        this.landPermissions = landPermissions;
    }

    public ArrayList<Permission> getActivityPermissions() {
        return activityPermissions;
    }

    public void setActivityPermissions(ArrayList<Permission> activityPermissions) {
        this.activityPermissions = activityPermissions;
    }

    public HashMap<String, Integer> getCost() {
        return cost;
    }

    public void setCost(HashMap<String, Integer> cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public ArrayList<Human> getRequiredHumans() {
        return requiredHumans;
    }

    public void setRequiredHumans(ArrayList<Human> requiredHumans) {
        this.requiredHumans = requiredHumans;
    }

    public int getNumberOfRequiredWorkers() {
        return numberOfRequiredWorkers;
    }

    public void setNumberOfRequiredWorkers(int numberOfRequiredWorkers) {
        this.numberOfRequiredWorkers = numberOfRequiredWorkers;
    }

    public int getNumberOfRequiredEngineers() {
        return numberOfRequiredEngineers;
    }

    public void setNumberOfRequiredEngineers(int numberOfRequiredEngineers) {
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
    }
}
