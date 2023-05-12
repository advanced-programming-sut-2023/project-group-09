package model.tools;

import controller.GameController;
import model.game.Tile;
import model.human.military.Engineer;

public class SiegeTent extends Tool{
    Tool convertTool;
    public SiegeTent() {
        super("siegeTent",0,0,0,0);
        this.setHp(1);
    }

    public Tool getConvertTool() {
        return convertTool;
    }

    public void setConvertTool(Tool convertTool) {
        this.convertTool = convertTool;
        this.setNumberOfRequiredEngineers(convertTool.getNumberOfRequiredEngineers());
    }

    public void addEngineer(Engineer engineer) {
        super.addEngineer(engineer);
        if (this.getEngineers().size() == this.getNumberOfRequiredEngineers()) {
            Tile tile = GameController.getGame().getMap().getTile(this.getX(),this.getY());
            tile.setTool(convertTool);
            convertTool.setEngineers(this.getEngineers());
        }
    }
}
