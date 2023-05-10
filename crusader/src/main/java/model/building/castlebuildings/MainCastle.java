package model.building.castlebuildings;

import controller.MapController;
import controller.gamestructure.GameHumans;
import enumeration.Pair;
import enumeration.Speed;
import model.Government;
import model.game.Tuple;
import model.human.civilian.Civilian;
import model.human.military.Lord;
import model.human.military.Military;

import java.util.Random;

public class MainCastle extends CastleBuilding {
    private Lord lord;
    private int taxRate;

    public MainCastle(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.capacity = 10;
        this.setBuildingImpassableLength(5);
    }

    public MainCastle(MainCastle mainCastle) {
        super(mainCastle.getNumberOfRequiredWorkers(), mainCastle.getNumberOfRequiredEngineers(), mainCastle.getName(),
                mainCastle.getMaxHp(), mainCastle.getWidth(), mainCastle.getLength());
        this.capacity = 10;
        super.setCost(mainCastle.getCost());
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
        } else if (this.taxRate > 0) {
            government.addGold((int) Math.floor(goldPerPerson * population));
        }
    }

    public void setTaxRate(int rate) {
        this.taxRate = rate;
    }

    public void setLord(Lord lord) {
        this.lord = lord;
    }

    public void makeUnemployed() {
        Tuple tuple = makePositionOfUnit();
        int x = tuple.getX();
        int y = tuple.getY();
        Civilian civilian = new Civilian(x, y, false);
        civilian.setGovernment(this.getGovernment());
        MapController.addHuman(x, y, civilian);
    }

    public void makeUnemployed(int numberToAdd) {
        for (int i = 0; i != numberToAdd; i++) {
            this.makeUnemployed();
        }
    }

    public Tuple makePositionOfUnit() {
        Random random = new Random();
        int x = random.nextInt(this.getWidth());
        int y = random.nextInt(this.getLength());
        while (x < this.getBuildingImpassableLength() && y < this.getBuildingImpassableLength()) {
            x = random.nextInt(this.getWidth());
            y = random.nextInt(this.getLength());
        }
        return new Tuple(y, x);
    }
}
