package model.building.producerbuildings;

import model.tools.AttackingAndDefendingTool;

import model.Government;

import java.util.ArrayList;

public class ToolProducer extends ProducerBuilding {
    private ArrayList<AttackingAndDefendingTool> tool;

    public ToolProducer(int numberOfRequiredWorkers, int numberOfRequiredEngineers, String name,
                        int maxHp, int width, int length, int rate, String nameOfStorage, String itemType,
                        String itemName, ArrayList<AttackingAndDefendingTool> tool) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, name, maxHp, width, length,
                rate, nameOfStorage, itemType, itemName);
        this.tool = tool;
    }

    public ArrayList<AttackingAndDefendingTool> getTool() {
        return tool;
    }

    public void setTool(ArrayList<AttackingAndDefendingTool> tool) {
        this.tool = tool;
    }
}
