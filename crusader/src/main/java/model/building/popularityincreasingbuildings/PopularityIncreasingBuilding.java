package model.building.popularityincreasingbuildings;

import model.building.Building;
import model.Government;

public class PopularityIncreasingBuilding extends Building {
    private int increaseRate;

    public PopularityIncreasingBuilding(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                                        int maxHp, int width, int length, int increaseRate) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.increaseRate = increaseRate;
    }

    public int getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(int increaseRate) {
        this.increaseRate = increaseRate;
    }
}
