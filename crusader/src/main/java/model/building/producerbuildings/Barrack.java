package model.building.producerbuildings;


import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameHumans;

import model.Government;
import model.building.Building;
import model.human.military.EuropeanTroop;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.Random;

public class Barrack extends Building{

    private final ArrayList<String> units = new ArrayList<>();


    public Barrack(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers,numberOfRequiredEngineers, name,maxHp, width, length);
        this.changeShouldBeOne();
        this.setBuildingImpassableLength(4);
    }

    public Barrack(Barrack barrack) {
        super(barrack.getNumberOfRequiredWorkers(),barrack.getNumberOfRequiredEngineers(), barrack.getName(),
                barrack.getMaxHp(), barrack.getWidth(), barrack.getLength());
        this.changeShouldBeOne();
        this.setBuildingImpassableLength(4);
        super.setCost(barrack.getCost());
    }
    public ArrayList<String> getUnits() {
        return units;
    }

    public void addUnit(String unit){
        units.add(unit);
    }

    public void makeUnit(String name){

        consumeRequired(name);
        int[] coordinate = makePositionOfUnit();
        int x = coordinate[0];
        int y = coordinate[1];
        if(!checkRequired(name)) {
            return;
        }
        Military military = GameHumans.getUnit(name, this.getGovernment(), x, y);
        MapController.addMilitary(x,y,military);
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
        }
        return true;
    }

    public boolean checkGold(String name){
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();

        if (price > this.getGovernment().getGold()){
            return false;
        }
        return true;
    }
    public boolean consumeRequired(String name){
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();
        if (price > this.getGovernment().getGold()){
            return false;
        }
        if(military instanceof EuropeanTroop troop){
            for (String armour : troop.getArmours()){
                Government government = this.getGovernment();
                if(government.getPropertyAmount(armour)  == 0){
                    GovernmentController.consumeProduct(this.getGovernment(),armour,1);
                }
            }
        }

        this.getGovernment().addGold(-price);
        return true;
    }

    public int[] makePositionOfUnit(){
        Random random = new Random();
        int x = random.nextInt(this.getWidth());
        int y = random.nextInt(this.getLength());
        while (x < this.getBuildingImpassableLength() && y < this.getBuildingImpassableLength()){
            x = random.nextInt(this.getWidth());
            y = random.nextInt(this.getLength());
        }
        return new int[]{x,y};
    }
}
