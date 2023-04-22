package model.building.castlebuildings;

import model.Government;
import model.building.killingbuildings.castlebuildings.CastleBuilding;
import model.human.military.Lord;

public class MainCastle extends CastleBuilding {
    private Lord lord;
    private int taxRate;

    public MainCastle(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type, int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.capacity = 10;
    }

    public void taxDistribution() {
        double goldPerPerson = 0.6 + (0.2)*(Math.abs(this.taxRate)-1);
        Government government = this.getGovernment();
        int population = government.getPopulation();
        if (this.taxRate < 0) {
            int money = (int)Math.ceil(goldPerPerson*population);
            if (money > government.getGold()) {
                government.setTaxRate(0);
            } else {
                government.addGold(-money);
            }
        } else if (this.taxRate > 0){
            government.addGold((int)Math.floor(goldPerPerson*population));
        }
    }
    public void setTaxRate(int rate) {
        this.taxRate = rate;
    }

    public void setLord(Lord lord) {
        this.lord = lord;
    }
}
