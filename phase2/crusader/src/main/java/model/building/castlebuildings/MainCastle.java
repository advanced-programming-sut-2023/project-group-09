package model.building.castlebuildings;

import controllers.GameController;
import controllers.MapController;
import model.Government;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.EuropeanTroop;

import java.util.ArrayList;
import java.util.Random;

public class MainCastle extends CastleBuilding {
    private EuropeanTroop lord;
    private int taxRate;
    private int totalTax = 0;
    private Tile door;

    public MainCastle(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.capacity = 10;
        this.setBuildingImpassableLength(5);
        this.setHeight(6);
    }

    public MainCastle(MainCastle mainCastle) {
        super(mainCastle.getNumberOfRequiredWorkers(), mainCastle.getNumberOfRequiredEngineers(), mainCastle.getName(),
                mainCastle.getMaxHp(), mainCastle.getWidth(), mainCastle.getLength());
        this.capacity = 10;
        super.setCost(mainCastle.getCost());
        this.setBuildingImpassableLength(5);
        this.setHeight(6);
    }

    public Tile getDoor() {
        return door;
    }

    public void setDoor(Tile door) {
        this.door = door;
    }
    public void taxDistribution() {
        double goldPerPerson = 0.6 + (0.2) * (Math.abs(this.taxRate) - 1);
        Government government = this.getGovernment();
        int population = government.getPopulation();
        if (this.taxRate < 0) {
            int money = (int) Math.ceil(goldPerPerson * population);
            if (money > government.getGold()) {
                government.setTaxRate(0);
            } else {
                government.addGold(-money);
            }
            totalTax = money;
        } else if (this.taxRate > 0) {
            government.addGold((int) Math.floor(goldPerPerson * population));
            totalTax = (int) Math.floor(goldPerPerson * population);
        }
    }

    public void setTaxRate(int rate) {
        this.taxRate = rate;
    }

    public void setLord(EuropeanTroop lord) {
        this.lord = lord;
    }

    public void makeUnemployed() {
        Tuple tuple = makePositionOfUnit();
        int x = tuple.getX();
        int y = tuple.getY();
        MapController.dropCivilian(x, y,getGovernment(),false);
    }

    public void makeUnemployed(int numberToAdd) {
        for (int i = 0; i < numberToAdd; i++) {
            this.makeUnemployed();
        }
    }

    public Tuple makePositionOfUnit() {
        ArrayList<Tile> tiles = GameController.getNeighborTiles(getEndX(),getEndY(),getBuildingImpassableLength(),getBuildingImpassableLength());
        ArrayList<Tile> wholeTile = GameController.getNeighborTiles(getEndSpecialX(),getEndSpecialY(),getWidth(),getLength());



        Random random = new Random();
        int i = random.nextInt(wholeTile.size());
        while (tiles.contains(wholeTile.get(i))) {
            i = random.nextInt(wholeTile.size());
        }
        return new Tuple(wholeTile.get(i).y, wholeTile.get(i).x);
    }

    public int getTotalTax() {
        double goldPerPerson = 0.6 + (0.2) * (Math.abs(this.taxRate) - 1);
        Government government = this.getGovernment();
        int population = government.getPopulation();
        if (this.taxRate < 0) {
            int money = (int) Math.ceil(goldPerPerson * population);
            if (money > government.getGold()) {
                government.setTaxRate(0);
            }
            totalTax = -money;
        } else if (this.taxRate > 0) {
            totalTax = (int) Math.floor(goldPerPerson * population);
        }
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }
}
