package model;

import enumeration.dictionary.Colors;
import enumeration.dictionary.Foodstuffs;
import enumeration.dictionary.RawMaterials;
import model.building.Building;
import model.goods.Goods;
import model.human.Human;
import model.human.military.ArabianMercenary;
import model.human.military.EuropeanTroop;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User user;

    private HashMap<String,Trade> trades = new HashMap<>();

    private HashMap<String,Trade> newTrades = new HashMap<>();



    private HashMap<Foodstuffs, Integer> foods = new HashMap<>();
    private HashMap<Goods, Integer> weapons = new HashMap<>();
    private HashMap<RawMaterials, Integer> resources = new HashMap<>();

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<EuropeanTroop> europeanTroops = new ArrayList<>();
    private ArrayList<ArabianMercenary> arabianMercenaries = new ArrayList<>();
    private ArrayList<Human> society = new ArrayList<>();

    private int foodRate;
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

    public HashMap<Foodstuffs, Integer> getFoods() {
        return foods;
    }

    public void setFoods(HashMap<Foodstuffs, Integer> foods) {
        this.foods = foods;
    }

    public HashMap<Goods, Integer> getWeapons() {
        return weapons;
    }

    public void setWeapons(HashMap<Goods, Integer> weapons) {
        this.weapons = weapons;
    }

    public HashMap<RawMaterials, Integer> getResources() {
        return resources;
    }

    public void setResources(HashMap<RawMaterials, Integer> resources) {
        this.resources = resources;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
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
