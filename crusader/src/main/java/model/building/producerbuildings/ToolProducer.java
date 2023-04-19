package model.building.producerbuildings;

import model.tools.AttackingAndDefendingTool;
import model.building.producerbuildings.ProducerBuilding;

import model.Government;

import java.util.ArrayList;

public class ToolProducer extends ProducerBuilding {
    private ArrayList<AttackingAndDefendingTool> tool;

    public ToolProducer(Government government, int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                        int maxHp, int startX, int startY, int endX, int endY) {
        super(government, numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, startX, startY, endX, endY);
    }

    public ArrayList<AttackingAndDefendingTool> getTool() {
        return tool;
    }

    public void setTool(ArrayList<AttackingAndDefendingTool> tool) {
        this.tool = tool;
    }
}
