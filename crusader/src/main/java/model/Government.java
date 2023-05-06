package model;

import controller.MapController;
import controller.gamestructure.GameGoods;
import enumeration.dictionary.Colors;
import model.building.Building;
import model.building.castlebuildings.MainCastle;
import model.building.producerbuildings.ProducerBuilding;
import model.buildinghandler.BuildingCounter;
import model.buildinghandler.Storage;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Lord;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Government {
    private User user;

    private LinkedHashMap<String, Trade> receivedTrades = new LinkedHashMap<>();
    private LinkedHashMap<String, Trade> newReceivedTrades = new LinkedHashMap<>();
    private LinkedHashMap<String, Trade> sentTrades = new LinkedHashMap<>();

    public HashMap<String, Integer> getProperties() {
        return properties;
    }

    public HashMap<String, Storage> getStorages() {
        return storages;
    }

    private HashMap<String, Integer> properties = new HashMap<>();
    private HashMap<String, Storage> storages = new HashMap<>();
    private HashMap<String, BuildingCounter> buildings = new HashMap<>();

    private ArrayList<Military> troops = new ArrayList<>();
    private ArrayList<Human> society = new ArrayList<>();
    private MainCastle mainCastle;

    public void setUser(User user) {
        this.user = user;
    }

    public void setProperties(HashMap<String, Integer> properties) {
        this.properties = properties;
    }

    public void setStorages(HashMap<String, Storage> storages) {
        this.storages = storages;
    }

    public void setBuildings(HashMap<String, BuildingCounter> buildings) {
        this.buildings = buildings;
    }

    public void setTroops(ArrayList<Military> troops) {
        this.troops = troops;
    }

    public MainCastle getMainCastle() {
        return mainCastle;
    }

    public void setMainCastle(MainCastle mainCastle) {
        this.mainCastle = mainCastle;
    }

    private int foodRate;

    public HashMap<String, BuildingCounter> getBuildings() {
        return buildings;
    }

    private int fearRate;
    private int religionRate;
    private int taxRate;
    private int gold;
    private int population, maxPopulation;
    private int castleX, castleY;
    private Colors color;
    private Lord lord;

    public Lord getLord() {
        return lord;
    }

    public void setLord(Lord lord) {
        this.lord = lord;
        ((MainCastle) (this.getBuildings().get("MainCastle").getBuildings().get(0))).setLord(lord);
    }

    public Government(User user, int castleX, int castleY, Colors color) {
        this.user = user;
        this.castleX = castleX;
        this.castleY = castleY;
        this.color = color;
        properties = GameGoods.getHashMapOfGovernment();
    }

    public void addAmountToProperties(String itemName, String itemType, int amount) {
        this.getProperties().put(itemName, this.getProperties().get(itemName) + amount);
        if (itemType != null) {
            this.getStorages().get(itemType).addAmount(amount);
        }
    }


    public ArrayList<Human> getSociety() {
        return society;
    }

    public void setSociety(ArrayList<Human> society) {
        this.society = society;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getReligionRate() {
        return religionRate;
    }

    public void setReligionRate(int religionRate) {
        this.religionRate = religionRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
        ((MainCastle) (this.getBuildings().get("MainCastle").getBuildings().get(0))).setTaxRate(taxRate);
    }

    public void addGold(int amount) {
        this.gold += amount;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public int getCastleX() {
        return castleX;
    }

    public void setCastleX(int castleX) {
        this.castleX = castleX;
    }

    public int getCastleY() {
        return castleY;
    }

    public void setCastleY(int castleY) {
        this.castleY = castleY;
    }

    public String getColor() {
        return color.getName();
    }

    public String getColorRgb() {
        return color.getRgb();
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public void changePopulation() {
    }

    public int getPopularity() {
        return 0;
    }

    public int getPropertyAmount(String name) {
        return properties.get(name);
    }

    public User getUser() {
        return user;
    }

    public HashMap<String, Trade> getReceivedTrades() {
        return receivedTrades;
    }

    public HashMap<String, Trade> getNewReceivedTrades() {
        return newReceivedTrades;
    }

    public ArrayList<Military> getTroops() {
        return troops;
    }

    public void addReceivedTrade(Trade trade) {
        receivedTrades.put(trade.getId(), trade);
        newReceivedTrades.put(trade.getId(), trade);
    }

    public void addSentTrade(Trade trade) {
        this.sentTrades.put(trade.getId(), trade);
    }

    public LinkedHashMap<String, Trade> getSentTrades() {
        return sentTrades;
    }

    public void clearTradeCash() {
        newReceivedTrades.clear();
    }

    public int getPopularityOfReligion() {
        int countOfBuilding = 0;
        countOfBuilding += buildings.get("cathedral").getNumber();
        countOfBuilding += buildings.get("church").getNumber();
        return countOfBuilding * 2;
    }

    public int getPopularityOfAleCoverage() {
        int countOfBuilding = buildings.get("inn").getNumber();
        double coverage = Math.min(countOfBuilding * 5 / population, 1);
        return (int) Math.ceil(coverage * 3);

    }

    public int getVarietyOfFood() {
        int var = 0;
        for (String food : GameGoods.foods.keySet()) {
            if (getPropertyAmount(food) != 0) {
                var++;
            }
        }
        if (var == 0) {
            this.foodRate = -2;
            return 1;
        }
        return var;
    }

    public void addMilitary(Military military) {
        troops.add(military);
    }

    public BuildingCounter getBuildingData(String name) {
        return buildings.get(name);
    }

    public void removeMilitary(Military military) {
        troops.remove(military);
    }

    public void addHuman(Civilian civilian) {
        society.add(civilian);
    }

    public void removeHuman(Civilian civilian) {
        society.remove(civilian);
    }

    public void updateAllBuildings() {
        for (String name : this.buildings.keySet()) {
            BuildingCounter bc = buildings.get(name);
            Iterator itr = bc.getBuildings().iterator();
            while (itr.hasNext()) {
                Building building = (Building) itr.next();
                if (building.isDestroyed()) {
                    MapController.deleteBuilding(building);
                }
            }
        }
    }

    public void updateAllHumans() {
        Iterator itr = this.society.iterator();
        while (itr.hasNext()) {
            Human human = (Human)itr.next();
            if (human.getHealth() <= 0) {
                MapController.deleteHuman(human.getX() , human.getY(), (Civilian) human);
            }
        }
        itr = this.troops.iterator();
        while (itr.hasNext()) {
            Military military = (Military) itr.next();
            if (military.getHealth() <= 0) {
                MapController.deleteMilitary(military.getX() , military.getY() , military);
            }
        }
    }

    public void updateAfterTurn() {
        this.updateAllHumans();
        this.updateAllHumans();
        this.mainCastle.taxDistribution();
        this.updateCowAndHorseNumber();
        this.producerBuildingsAction();
        this.outOfStockNotification();
        this.updateAllBuildings();
        this.updateAllHumans();
        this.workersNeededNotification();
    }

    public void updateCowAndHorseNumber() {
        int cows = buildings.get("dairyProducts").getNumber() * 3;
        int horses = buildings.get("stable").getNumber() * 4;
        this.addAmountToProperties("cow", "cow", cows - this.getPropertyAmount("cows"));
        this.addAmountToProperties("horse", "horse", horses - this.getPropertyAmount("horse"));
    }

    public void outOfStockNotification() {
        for (String name : storages.keySet()) {
            if (storages.get(name).isFull()) {
                System.out.println("Lord " + this.getUser().getNickname() + " : Storage " + name + " is full!");
            }
        }
    }

    public void workersNeededNotification() {
        int workersNeeded = 0;
        for (String name : buildings.keySet()) {
            for (Building building : buildings.get(name).getBuildings()) {
                workersNeeded += building.getNumberOfRequiredWorkers() - building.howManyWorkersHave();
            }
        }
        System.out.println("Lord " + this.getUser().getNickname() + " : " + workersNeeded + " workers we need!");
    }

    public void producerBuildingsAction() {
        for (String name : this.buildings.keySet()) {
            for (Building building : this.buildings.get(name).getBuildings()) {
                if (!(building instanceof ProducerBuilding)) {
                    break;
                }
                ((ProducerBuilding) building).doAction();
            }
        }
    }
}
