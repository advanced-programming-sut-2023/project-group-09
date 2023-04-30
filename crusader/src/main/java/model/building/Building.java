package model.building;

import enumeration.BuildingStates;
import enumeration.Textures;
import model.Government;
import model.Permission;
import model.human.Human;

import java.util.ArrayList;
import java.util.HashMap;

public class Building implements Cloneable {
    private Government government = null;
    private ArrayList<Permission> landPermissions;
    private ArrayList<Permission> activityPermissions;
    private HashMap<String, Integer> cost = new HashMap<>();
    private ArrayList<Human> requiredHumans = new ArrayList<>();

    public void setPrice(int price) {
        this.price = price;
    }

    private int price = 0;
    private int numberOfRequiredWorkers;
    private int numberOfRequiredEngineers;
    private BuildingStates state;

    private int buildingImpassableLength = -1;
    private boolean hasSpecialTexture = false;
    private ArrayList<Textures> suitableTextures = new ArrayList<>();
    private boolean shouldBeOne = false;

    private String name;

    public BuildingStates getState() {
        return state;
    }

    public void setState(BuildingStates state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    private int maxHp;
    private int hp;
    private int startX, startY;
    private int endX, endY;
    private int width, length;

    public Building(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                    String name, int maxHp, int width, int length) {
        this.numberOfRequiredWorkers = numberOfRequiredWorkers;
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.width = width;
        this.length = length;
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

    public int getMaxHp() {
        return maxHp;
    }

    public Government getGovernment() {
        return government;
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

    public void changeShouldBeOne() {
        shouldBeOne = !shouldBeOne;
    }

    public void addCost(String key, int value) {
        cost.put(key, value);
    }

    public ArrayList<Textures> getSuitableTextures() {
        return suitableTextures;
    }

    public boolean getHasSpecialTexture() {
        return hasSpecialTexture;
    }

    public void addTexture(Textures texture) {
        suitableTextures.add(texture);
    }

    public void enableHasSpecialTexture() {
        hasSpecialTexture = true;
    }

    public void setBuildingImpassableLength(int buildingNumber) {
        buildingImpassableLength = buildingNumber;
    }

    public int getBuildingImpassableLength() {
        return buildingImpassableLength;
    }

    @Override
    public Building clone() throws CloneNotSupportedException {
        return (Building) super.clone();
    }

    public boolean isShouldBeOne() {
        return shouldBeOne;
    }
}
