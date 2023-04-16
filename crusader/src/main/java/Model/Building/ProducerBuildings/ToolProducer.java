package Model.Building.ProducerBuildings;

import Model.AttackingAndDefendingTools.AttackingAndDefendingTool;
import Model.Building.Building;
import Model.Government;
import Model.Human.Human;

import java.util.ArrayList;
import java.util.HashMap;

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
