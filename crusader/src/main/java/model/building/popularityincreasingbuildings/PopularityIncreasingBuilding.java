package model.building.popularityincreasingbuildings;

import model.building.Building;
import model.Government;

public class PopularityIncreasingBuilding extends Building {
    private int increaseRate;

    public PopularityIncreasingBuilding(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                                        int maxHp, int startX, int startY, int endX, int endY, int increaseRate) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
        this.increaseRate = increaseRate;
    }

    public int getIncreaseRate() {
        return increaseRate;
    }

    public void setIncreaseRate(int increaseRate) {
        this.increaseRate = increaseRate;
    }
}
