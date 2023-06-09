package model.building;

import controller.GameController;
import model.Government;
import model.Permission;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Engineer;

import java.util.ArrayList;
import java.util.HashMap;


public class Building implements Cloneable {
    private Government government = null;
    private ArrayList<Permission> landPermissions;
    private ArrayList<Permission> activityPermissions;
    private HashMap<String, Integer> cost = new HashMap<>();
    private ArrayList<Human> requiredHumans = new ArrayList<>();

    private int price = 0;
    private int numberOfRequiredWorkers;
    private int numberOfRequiredEngineers;

    private int buildingImpassableLength = -1;
    private boolean hasSpecialTexture = false;
    private ArrayList<String> suitableTextures = new ArrayList<>();
    private boolean shouldBeOne = false;

    private String name;

    private final ArrayList<Tuple> neighborTiles = new ArrayList<>();
    private int maxHp;
    private int hp;
    private int startX, startY;
    private int endX, endY;
    private int width, length;
    private int endSpecialX, endSpecialY;

    private boolean isBurning = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public Building(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                    String name, int maxHp, int width, int length) {
        this.numberOfRequiredWorkers = numberOfRequiredWorkers;
        this.numberOfRequiredEngineers = numberOfRequiredEngineers;
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.width = width;
        this.length = length;
        isBurning = false;
    }

    public Building(Building building) {
        this.numberOfRequiredWorkers = building.numberOfRequiredWorkers;
        this.numberOfRequiredEngineers = building.numberOfRequiredEngineers;
        this.name = building.name;
        this.maxHp = building.maxHp;
        this.hp = maxHp;
        this.width = building.width;
        this.length = building.length;
        isBurning = false;
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

    public int getEndSpecialX() {
        return endSpecialX;
    }

    public void setEndSpecialX(int endSpecialX) {
        this.endSpecialX = endSpecialX;
    }

    public int getEndSpecialY() {
        return endSpecialY;
    }

    public void setEndSpecialY(int endSpecialY) {
        this.endSpecialY = endSpecialY;
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

    public ArrayList<String> getSuitableTextures() {
        return suitableTextures;
    }

    public boolean getHasSpecialTexture() {
        return hasSpecialTexture;
    }

    public void addTexture(String texture) {
        suitableTextures.add(texture);
    }

    public void setSuitableTextures(ArrayList<String> textures) {
        suitableTextures = textures;
    }

    public void setHasSpecialTexture(boolean hasSpecialTexture) {
        this.hasSpecialTexture = hasSpecialTexture;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isShouldBeOne() {
        return shouldBeOne;
    }

    public ArrayList<Tuple> getNeighborTiles() {
        return neighborTiles;
    }

    public void setNeighborTiles() {
        ArrayList<Tile> tiles = GameController.getDirectNeighborTiles(this);
        for (Tile tile : tiles) {
            System.out.println("tile : " + tile.x + " " + tile.y);
            Tuple tuple = new Tuple(tile.y, tile.x);
            neighborTiles.add(tuple);
        }
    }

    public int takeDamage(int damage) {
        int newHp = this.getHp() - damage;
        this.setHp(newHp);
        return newHp;
    }

    public void addHuman(Human human) {
        this.requiredHumans.add(human);
    }

    public int howManyWorkersHave() {
        int counterOfWorkers = 0;
        for (Human human : requiredHumans) {
            if (human instanceof Civilian)
                counterOfWorkers++;
        }
        return counterOfWorkers;
    }

    public int getNumberOfWorkers() {
        int number = 0;
        for (Human human : requiredHumans) {
            if (!(human instanceof Engineer)) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfEngineers() {
        int number = 0;
        for (Human human : requiredHumans) {
            if (human instanceof Engineer) {
                number++;
            }
        }
        return number;
    }

    public boolean isDestroyed() {
        return this.hp <= 0;
    }

    public boolean isActive() {
        requiredHumans.removeIf(i -> i.getGovernment() == null);
        if (numberOfRequiredEngineers > 0) {
            return requiredHumans.size() == numberOfRequiredEngineers;
        }
        if (numberOfRequiredWorkers > 0) {
            return requiredHumans.size() == numberOfRequiredWorkers;
        }
        return true;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBurning() {
        return isBurning;
    }

    public void setBurning(boolean burning) {
        isBurning = burning;
    }
}
