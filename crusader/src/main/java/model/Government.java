package model;

import controller.GameController;
import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameGoods;
import enumeration.dictionary.Colors;
import model.building.Building;
import model.building.castlebuildings.MainCastle;
import model.building.producerbuildings.ProducerBuilding;
import model.building.storagebuildings.StorageBuilding;
import model.buildinghandler.BuildingCounter;
import model.buildinghandler.Storage;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Lord;
import model.human.military.Military;
import model.tools.Tool;

import java.util.*;

public class Government {
    private User user;
    private int howManyTurnsSurvive = 0;

    private LinkedHashMap<String, Trade> receivedTrades = new LinkedHashMap<>();
    private LinkedHashMap<String, Trade> newReceivedTrades = new LinkedHashMap<>();
    private LinkedHashMap<String, Trade> sentTrades = new LinkedHashMap<>();

    private HashMap<String, Integer> properties;
    private HashMap<String, Storage> storages = new HashMap<>();

    private HashMap<String, BuildingCounter> buildings = new HashMap<>();

    private ArrayList<Tool> tools = new ArrayList<>();
    private ArrayList<Military> troops = new ArrayList<>();
    private ArrayList<Human> society = new ArrayList<>();
    private MainCastle mainCastle;


    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    private boolean isAlive = true;

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

    public Government(User user, int castleX, int castleY, Colors color) {
        this.user = user;
        this.castleX = castleX;
        this.castleY = castleY;
        this.color = color;
        properties = GameGoods.getHashMapOfGovernment();
        buildings = GameBuildings.getGovernmentBuildingHashMap();
    }

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


    public int getHowManyTurnsSurvive() {
        return howManyTurnsSurvive;
    }

    public void addTurnsSurvive() {
        this.howManyTurnsSurvive++;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setLord(Lord lord) {
        this.lord = lord;
        ((MainCastle) (this.getBuildings().get("MainCastle").getBuildings().get(0))).setLord(lord);
    }

    public ArrayList<Tool> getTools() {
        return tools;
    }

    public void setTools(ArrayList<Tool> tools) {
        this.tools = tools;
    }

    public HashMap<String, Integer> getProperties() {
        return properties;
    }

    public HashMap<String, Storage> getStorages() {
        return storages;
    }

    public void addAmountToProperties(String itemName, String itemType, int amount) {
        this.getProperties().put(itemName, this.getProperties().get(itemName) + amount);
        if (itemType != null && this.getStorages().get(itemType) != null) {
            this.getStorages().get(itemType).addAmount(amount);
        }
    }


    public ArrayList<Human> getSociety() {
        return society;
    }

    public void setSociety(ArrayList<Human> society) {
        this.society = society;
    }

    public Lord getLord() {
        return lord;
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
        if (population == 0){
            return 0;
        }
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
        ArrayList<Human> unemployed = new ArrayList<>();
        for (String name : this.buildings.keySet()) {
            BuildingCounter bc = buildings.get(name);
            Iterator itr = bc.getBuildings().iterator();
            while (itr.hasNext()) {
                Building building = (Building) itr.next();
                if (building.isDestroyed()) {
                    unemployed.addAll(building.getRequiredHumans());
                    MapController.deleteBuilding(building);
                }
            }
        }
        layingOffWorkers(unemployed);
    }

    public void layingOffWorkers(ArrayList<Human> humansOfBuilding) {
        Iterator itr = humansOfBuilding.iterator();
        while (itr.hasNext()) {
            Human human = (Human) itr.next();
            if (human instanceof Civilian) {
                ((Civilian) human).setHasJob(false);
            }
        }
    }

    public void updateAllHumans() {
        Iterator itr = this.society.iterator();
        while (itr.hasNext()) {
            Human human = (Human) itr.next();
            if (human.getHealth() <= 0) {
                itr.remove();
                MapController.deleteHuman(human.getX(), human.getY(), (Civilian) human);
            }
        }
        itr = this.troops.iterator();
        while (itr.hasNext()) {
            Military military = (Military) itr.next();
            if (military.getHealth() <= 0) {
                itr.remove();
                MapController.deleteMilitary(military.getX(), military.getY(), military);
                military.setGovernment(null);
            }
        }
    }

    public void updateAfterTurn() {
        this.updateAllHumans();
        this.updateAllBuildings();
        this.mainCastle.taxDistribution();
        this.updateCowAndHorseNumber();
        this.producerBuildingsAction();
        this.outOfStockNotification();
        this.updateMaxPopularity();
        this.updatePeopleAfterTurn();
        this.foodDistribution();
        this.workerDistribution();
        this.workersNeededNotification();
    }

    public void updateCowAndHorseNumber() {
        int cows = buildings.get("dairyProducts").getNumber() * 3;
        int horses = buildings.get("stable").getNumber() * 4;
        this.addAmountToProperties("cow", "cow", cows - this.getPropertyAmount("cow"));
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
                if (!building.getName().equals("castleBuildings")) {
                    workersNeeded += building.getNumberOfRequiredWorkers() - building.howManyWorkersHave();
                }
            }
        }
        if (workersNeeded != 0) {
            System.out.println("Lord " + this.getUser().getNickname() + " : " + workersNeeded + " workers we need!");
        }
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

    public void updateMaxPopularity() {
        maxPopulation = 10 + buildings.get("hovel").getNumber() * 8;
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public void removeTool(Tool tool) {
        tools.add(tool);
    }

    public void checkingGovernmentIsAlive() {
        for (Military military : this.troops) {
            if (military instanceof Lord) {
                Lord lord = (Lord) military;
                break;
            }
        }
        if (lord.getHealth() <= 0) {
            this.isAlive = false;
        }
    }

    public void updatePopulationWithAdd(int wanted) {
        int counterOfAddedPeople = wanted - population;
        this.mainCastle.makeUnemployed(counterOfAddedPeople);
    }


    public void updatePopulationWithRemove(int wanted) {
        int counterOfRemovedPeople = population - wanted;
        if (counterOfRemovedPeople != 0) {
            Iterator itr = this.society.iterator();
            while (itr.hasNext()) {
                if (counterOfRemovedPeople == 0)
                    break;
                Human human = (Human) itr.next();
                if (human instanceof Civilian && !((Civilian) human).isHasJob()) {
                    counterOfRemovedPeople++;
                    itr.remove();
                    MapController.deleteHuman(human.getX(), human.getY(), (Civilian) human);
                    human.setGovernment(null);
                }
            }
        }
        if (counterOfRemovedPeople != 0) {
            Iterator itr = this.society.iterator();
            while (itr.hasNext()) {
                if (counterOfRemovedPeople == 0)
                    break;
                Human human = (Human) itr.next();
                if (human instanceof Civilian) {
                    counterOfRemovedPeople++;
                    itr.remove();
                    MapController.deleteHuman(human.getX(), human.getY(), (Civilian) human);
                }
            }
        }
    }

    public void updatePeopleAfterTurn() {
        // maxPopularity : 25 --- minPopularity : -37
        double ratio = (double) (this.getPopularity() + 37) / (25 + 37);
        int checker = (int) ratio * 100;
        int number;
        if (checker < 20) {
            // 30%
            number = (int) (0.3f * maxPopulation);
        } else if (checker < 40) {
            // 50%
            number = (int) (0.5f * maxPopulation);
        } else if (checker < 60) {
            // 70%
            number = (int) (0.7f * maxPopulation);
        } else if (checker < 80) {
            // 90%
            number = (int) (0.9f * maxPopulation);
        } else {
            // 100%
            number = (int) (1 * maxPopulation);
        }
        if (number > population) {
            this.updatePopulationWithAdd(number);
        } else if (number < population) {
            this.updatePopulationWithRemove(number);
        }
    }

    public int getPopularity() {
        this.religionRate = 0;
        for (BuildingCounter bc : this.buildings.values()) {
            for (Building building : bc.getBuildings()) {
                if (building.getName().equals("cathedral"))
                    this.religionRate += 2;
                if (building.getName().equals("church"))
                    this.religionRate++;
            }
        }
        return this.fearRate + GovernmentController.getTaxPopularity(this.taxRate)
                + GovernmentController.getFoodPopularity(this.foodRate) + this.religionRate;
    }

    public void foodDistribution() {
        int foodsNeeded = (int) (GovernmentController.getFoodRate(this.foodRate) * population);
        int foodWeHave = this.properties.get("meat") + this.properties.get("bread") +
                this.properties.get("apple") + this.properties.get("cheese");
        if (foodsNeeded > foodWeHave) {
            this.foodRate = -2;
            System.out.println("Not enough food!");
        }
        int consumedFoods = Math.min(foodsNeeded, foodWeHave);
        consumeFoods(consumedFoods);
    }

    public int consumeSupposedFood(int amountOfFoods, String nameOfFood) {
        int amount = Math.min(amountOfFoods, this.getPropertyAmount(nameOfFood));
        GovernmentController.consumeProduct(this, nameOfFood, amount);
        amountOfFoods -= amount;
        return amountOfFoods;
    }

    public void consumeFoods(int amountOfFoods) {
        int amount = consumeSupposedFood(amountOfFoods, "bread");
        amountOfFoods -= amount;
        amount = consumeSupposedFood(amountOfFoods, "cheese");
        amountOfFoods -= amount;
        amount = consumeSupposedFood(amountOfFoods, "apple");
        amountOfFoods -= amount;
        amount = consumeSupposedFood(amountOfFoods, "meat");
        amountOfFoods -= amount;

    }

    public void workerDistribution() {
        for (BuildingCounter bc : buildings.values()) {
            for (Building building : bc.getBuildings()) {
                GameController.workerDistribution(building);
            }
        }
    }

    public void checkFirstStorage(Building building){
        String name = building.getName();
        BuildingCounter buildingCounter = getBuildingData(name);
        if (buildingCounter.getNumber() != 1 ){
            return;
        }
        StorageBuilding storageBuilding = (StorageBuilding) building;
        String type = storageBuilding.getItemType();
        Set<String> keys;

        switch (type){
            case "food":
                keys = GameGoods.foods.keySet();
                break;
            case "resource":
                keys = GameGoods.resources.keySet();
                break;
            case "weapon":
                keys = GameGoods.weapons.keySet();
                break;
            default:
                return;
        }

        for (String key : keys){
            int count = properties.get(key);
            storageBuilding.addItem(key,count);
        }
    }
}
