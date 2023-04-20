package model.building.producerbuildings;

import model.tools.AttackingAndDefendingTool;

import model.Government;

import java.util.ArrayList;

public class ToolProducer extends ProducerBuilding {
    private ArrayList<AttackingAndDefendingTool> tool;

    public ToolProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String type,
                        int maxHp, int width, int length) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
    }

    public ArrayList<AttackingAndDefendingTool> getTool() {
        return tool;
    }

    public void setTool(ArrayList<AttackingAndDefendingTool> tool) {
        this.tool = tool;
    }
}
