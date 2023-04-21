package model;

import enumeration.dictionary.Colors;
import enumeration.dictionary.Foodstuffs;
import enumeration.dictionary.RawMaterials;
import model.building.Building;
import model.goods.Goods;
import model.human.Human;
import model.human.military.ArabianMercenary;
import model.human.military.EuropeanTroop;
import model.buildinghandler.BuildingCounter;
import model.buildinghandler.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User user;

    private HashMap<String,Trade> trades = new HashMap<>();

    private HashMap<String,Trade> newTrades = new HashMap<>();

    public HashMap<String, Integer> getProperties() {
        return properties;
    }

    public HashMap<String, Storage> getStorages() {
        return storages;
    }

    private HashMap<String , Integer> properties = new HashMap<>();
    private HashMap<String , Storage> storages = new HashMap<>();
    private HashMap<String , BuildingCounter> buildings = new HashMap<>();
    private ArrayList<EuropeanTroop> europeanTroops = new ArrayList<>();
    private ArrayList<ArabianMercenary> arabianMercenaries = new ArrayList<>();
    private ArrayList<Human> society = new ArrayList<>();

    private int foodRate;

    public HashMap<String, BuildingCounter> getBuildings() {
        return buildings;
    }

    private int fearRate;
    private int religionRate;
    private int taxRate;
    private int gold;
    private int weaponCapacity;
    private int population, maxPopulation;
    private int castleX, castleY;
    private Colors color;

    public Government(User user, int castleX, int castleY, Colors color) {
        this.user = user;
        this.castleX = castleX;
        this.castleY = castleY;
        this.color = color;
    }

    public void addAmountToProperties(String itemName , String itemType, int amount) {
        this.getProperties().put(itemName , this.getProperties().get(itemName) + amount);
        this.getStorages().get(itemType).addAmount(amount);
    }

    public ArrayList<EuropeanTroop> getEuropeanTroops() {
        return europeanTroops;
    }

    public void setEuropeanTroops(ArrayList<EuropeanTroop> europeanTroops) {
        this.europeanTroops = europeanTroops;
    }

    public ArrayList<ArabianMercenary> getArabianMercenaries() {
        return arabianMercenaries;
    }

    public void setArabianMercenaries(ArrayList<ArabianMercenary> arabianMercenaries) {
        this.arabianMercenaries = arabianMercenaries;
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
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getWeaponCapacity() {
        return weaponCapacity;
    }

    public void setWeaponCapacity(int weaponCapacity) {
        this.weaponCapacity = weaponCapacity;
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

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }


    public void changePopulation() {

    }

}
