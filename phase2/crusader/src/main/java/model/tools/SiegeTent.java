package model.tools;

import controller.MapController;
import model.activity.ToolAttack;
import model.human.military.Engineer;

public class SiegeTent extends Tool {
    Tool convertTool;

    public SiegeTent() {
        super("siegeTent", 0, 0, 0, 0);
        this.setHp(1);
        this.setToolAttack(new ToolAttack(this));
    }

    public void setConvertTool(Tool convertTool) {
        this.convertTool = convertTool;
        this.setNumberOfRequiredEngineers(convertTool.getNumberOfRequiredEngineers());
    }

    public void addEngineer(Engineer engineer) {
        super.addEngineer(engineer);
        if (this.getEngineers().size() == this.getNumberOfRequiredEngineers()) {
            MapController.deleteTool(getX(), getY(), this);
            MapController.addTool(getX(), getY(), convertTool);
            convertTool.setEngineers(this.getEngineers());
        }
    }
}
