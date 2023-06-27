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

import java.util.ArrayList;
import java.util.Random;

public class Barrack extends Building {

    private ArrayList<String> units = new ArrayList<>();


    public Barrack(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length);
        this.changeShouldBeOne();
        this.setBuildingImpassableLength(4);
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

    public void makeUnit(String name) {
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
        MapController.dropMilitary(x, y, name, getGovernment());
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
}
