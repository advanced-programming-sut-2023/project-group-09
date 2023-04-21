package model.building.castlebuildings;

import controller.GameController;
import model.Government;
import model.building.Building;
import model.game.Game;
import model.game.Map;

public class Gatehouse extends CastleBuilding {
    private boolean isRightSide;
    private boolean isOpen = true;
    private String typeOfGate;

    public boolean isRightSide() {
        return isRightSide;
    }

    public void setRightSide(boolean rightSide) {
        isRightSide = rightSide;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Gatehouse getDrawBridge() {
        return drawBridge;
    }

    public void setDrawBridge(Gatehouse drawBridge) {
        this.drawBridge = drawBridge;
    }

    private Gatehouse drawBridge; // if gatehouse has drawbridge

    public String getTypeOfGate() {
        return typeOfGate;
    }

    public void setTypeOfGate(String typeOfGate) {
        this.typeOfGate = typeOfGate;
    }

    public Gatehouse(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                     String type, int maxHp, int width, int length, int capacity , boolean isRightSide) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.capacity = capacity;
        this.isRightSide = isRightSide;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Gatehouse canDropDrawBridge(int x , int y) { // it returned the gatehouse that connects to new draw bridge
        Map map = GameController.getGame().getMap();
        Building building = map.getTile(x+1 , y).getBuilding();
        if (building instanceof Gatehouse &&
                (((Gatehouse) building).getTypeOfGate().equals("gatehouse")) &&
                !((Gatehouse)building).isRightSide) {
            return (Gatehouse) building;
        }
        building = map.getTile(x-1 , y).getBuilding();
        if (building instanceof Gatehouse &&
                (((Gatehouse) building).getTypeOfGate().equals("gatehouse")) &&
                !((Gatehouse)building).isRightSide) {
            return (Gatehouse) building;
        }
        building = map.getTile(x , y+1).getBuilding();
        if (building instanceof Gatehouse &&
                (((Gatehouse) building).getTypeOfGate().equals("gatehouse")) &&
                ((Gatehouse)building).isRightSide) {
            return (Gatehouse) building;
        }
        building = map.getTile(x , y-1).getBuilding();
        if (building instanceof Gatehouse &&
                (((Gatehouse) building).getTypeOfGate().equals("gatehouse")) &&
                ((Gatehouse)building).isRightSide) {
            return (Gatehouse) building;
        }
        return null;
    }
}
