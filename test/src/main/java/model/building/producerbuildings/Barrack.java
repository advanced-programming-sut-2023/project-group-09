package model.building.producerbuildings;


import controller.GameController;
import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameHumans;
import model.Government;
import model.building.Building;
import model.game.Tile;
import model.human.military.EuropeanTroop;
import model.human.military.Military;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Barrack extends Building {

    private ArrayList<String> units = new ArrayList<>();


    public Barrack(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.changeShouldBeOne();
        this.setBuildingImpassableLength(5);
    }

    public Barrack(Barrack barrack) {
        super(barrack.getNumberOfRequiredWorkers(), barrack.getNumberOfRequiredEngineers(), barrack.getName(),
                barrack.getMaxHp(), barrack.getWidth(), barrack.getLength());
        this.changeShouldBeOne();
        this.units = barrack.units;
        this.setBuildingImpassableLength(barrack.getBuildingImpassableLength());
        super.setCost(barrack.getCost());
    }

    public ArrayList<String> getUnits() {
        return units;
    }

    public void addUnit(String unit) {
        units.add(unit);
    }

    public void makeUnit(String name) throws IOException {
        if (!checkRequired(name)) {
            return;
        }
        int[] coordinate = makePositionOfUnit();
        int x = coordinate[0];
        int y = coordinate[1];
        if (!checkRequired(name)) {
            return;
        }
        consumeRequired(name);
        int id = makeID();
        MapController.dropMilitary(x, y, name, getGovernment(),id);
        GameController.sendDropUnit(x , y , name , getGovernment(),id);
    }

    public static void makeUnitThroughNetwork(int x , int y , String name , Government government,int id) {
        if (!checkRequired(name , government)) {
            return;
        }
        consumeRequired(name , government);
        MapController.dropMilitary(x , y , name , government,id);
        government.updatePopulationWithRemove(government.getPopulation() - 1);
    }

    public static boolean checkRequired(String name , Government government) {
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();
        if (government.getUnemployedCount() == 0) {
            return false;
        }
        if (price > government.getGold()) {
            return false;
        }
        if (military instanceof EuropeanTroop troop) {
            for (String armour : troop.getArmours()) {
                if (government.getPropertyAmount(armour) == 0) {
                    return false;
                }
            }
            if (military.isUsesHorse()) {
                if (government.getPropertyAmount("horse") == 0) {
                    return false;
                }
            }
            if (military.getWeapon() != null) {
                return government.getPropertyAmount(military.getWeapon()) != 0;
            }
        }
        return true;
    }


    public boolean checkRequired(String name) {
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();
        if (getGovernment().getUnemployedCount() == 0) {
            return false;
        }
        if (price > this.getGovernment().getGold()) {
            return false;
        }
        if (military instanceof EuropeanTroop troop) {
            Government government = this.getGovernment();
            for (String armour : troop.getArmours()) {

                if (government.getPropertyAmount(armour) == 0) {
                    return false;
                }
            }
            if (military.isUsesHorse()) {
                if (government.getPropertyAmount("horse") == 0) {
                    return false;
                }
            }
            if (military.getWeapon() != null) {
                return government.getPropertyAmount(military.getWeapon()) != 0;
            }
        }
        return true;
    }

    public boolean checkGold(String name) {
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();

        if (price > this.getGovernment().getGold()) {
            return false;
        }
        return true;
    }

    public boolean consumeRequired(String name) {
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();
        if (price > this.getGovernment().getGold()) {
            return false;
        }
        if (military instanceof EuropeanTroop troop) {
            Government government = this.getGovernment();
            for (String armour : troop.getArmours()) {
                if (government.getPropertyAmount(armour) == 0) {
                    GovernmentController.consumeProduct(this.getGovernment(), armour, 1);
                }
            }
            if (military.isUsesHorse()) {
                if (government.getPropertyAmount("horse") == 0) {
                    return false;
                } else {
                    GovernmentController.consumeProduct(this.getGovernment(), "horse", 1);
                }
            }
            if (military.getWeapon() != null) {
                GovernmentController.consumeProduct(this.getGovernment(), military.getWeapon(), 1);
            }

        }

        this.getGovernment().addGold(-price);
        return true;
    }

    public static boolean consumeRequired(String name , Government government) {
        Military military = GameHumans.getUnit(name);
        int price = military.getPrice();
        if (price > government.getGold()) {
            return false;
        }
        if (military instanceof EuropeanTroop troop) {
            for (String armour : troop.getArmours()) {
                if (government.getPropertyAmount(armour) == 0) {
                    GovernmentController.consumeProduct(government, armour, 1);
                }
            }
            if (military.isUsesHorse()) {
                if (government.getPropertyAmount("horse") == 0) {
                    return false;
                } else {
                    GovernmentController.consumeProduct(government, "horse", 1);
                }
            }
            if (military.getWeapon() != null) {
                GovernmentController.consumeProduct(government, military.getWeapon(), 1);
            }

        }

        government.addGold(-price);
        return true;
    }

    public int[] makePositionOfUnit() {

        ArrayList<Tile> tiles = GameController.getNeighborTiles(getEndX(), getEndY(), getBuildingImpassableLength(), getBuildingImpassableLength());
        ArrayList<Tile> wholeTile = GameController.getNeighborTiles(getEndSpecialX(), getEndSpecialY(), getWidth(), getLength());


        Random random = new Random();
        int i = random.nextInt(wholeTile.size());
        while (tiles.contains(wholeTile.get(i))) {
            i = random.nextInt(wholeTile.size());
        }
        return new int[]{wholeTile.get(i).x, wholeTile.get(i).y};
    }


    public int makeID(){
        Random random = new Random();
        int id = random.nextInt(100000);
        while (GameController.getGame().humans.get(id) != null){
            id = random.nextInt(100000);
        }
        return id;
    }
}
