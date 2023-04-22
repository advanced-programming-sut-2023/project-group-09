package model.building.producerbuildings;


import controller.GovernmentController;
import controller.gamestructure.GameHumans;

import model.Government;
import model.building.Building;
import model.human.military.EuropeanTroop;
import model.human.military.Military;

import java.util.ArrayList;

public class Barrack extends Building{

    private final ArrayList<String> units = new ArrayList<>();

    private int BuildingNumber = 4;

    public Barrack(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers,numberOfRequiredEngineers, name,maxHp, width, length);
        this.changeShouldBeOne();
    }

    public ArrayList<String> getUnits() {
        return units;
    }

    public void addUnit(String unit){
        units.add(unit);
    }

    public Military makeUnit(String name){
        try {
            if(!checkRequired(name)){
                return null;
            }
            return (Military) GameHumans.getUnit(name).clone();
        }catch (CloneNotSupportedException e){
            System.out.println("An error occurred.[make unit]");
        }
        return null;
    }

    public void setBuildingNumber(int buildingNumber) {
        BuildingNumber = buildingNumber;
    }

    public int getBuildingNumber() {
        return BuildingNumber;
    }

    public boolean checkRequired(String name){
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();

        if (price > this.getGovernment().getGold()){
            return false;
        }
        if(military instanceof EuropeanTroop troop){
            for (String armour : troop.getArmours()){
                Government government = this.getGovernment();
                if(government.getPropertyAmount(armour)  == 0){
                    return false;
                }
            }


            for (String armour : troop.getArmours()){
                Government government = this.getGovernment();
                if(government.getPropertyAmount(armour)  == 0){
                    GovernmentController.consumeProduct(this.getGovernment(),armour,1);
                }
            }
        }

        this.getGovernment().addGold(price);
        return true;
    }
}
