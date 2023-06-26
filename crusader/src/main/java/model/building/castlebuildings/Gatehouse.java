package model.building.castlebuildings;

import controller.GameController;
import controller.MapController;
import controller.human.HumanController;
import model.building.Building;
import model.game.Map;
import model.human.military.Military;

import java.util.ArrayList;

public class Gatehouse extends CastleBuilding {
    private boolean isRightSide = true;
    private boolean isOpen = true;
    private String typeOfGate;
    private Gatehouse drawBridge_gatehouse; // if gatehouse has drawbridge


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

    public Gatehouse getDrawBridge_gatehouse() {
        return drawBridge_gatehouse;
    }

    public void setDrawBridge_gatehouse(Gatehouse drawBridge) {
        this.drawBridge_gatehouse = drawBridge;
    }

    public String getTypeOfGate() {
        return typeOfGate;
    }

    public void setTypeOfGate(String typeOfGate) {
        this.typeOfGate = typeOfGate;
    }

    public Gatehouse(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                     String type, int maxHp, int width, int length, int capacity) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        this.capacity = capacity;
    }

    public Gatehouse(Gatehouse gatehouse) {
        super(gatehouse.getNumberOfRequiredWorkers(), gatehouse.getNumberOfRequiredEngineers(), gatehouse.getName(),
                gatehouse.getMaxHp(), gatehouse.getWidth(), gatehouse.getLength());
        this.capacity = gatehouse.getCapacity();
        super.setCost(gatehouse.getCost());
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static Gatehouse canDropDrawBridge(int x, int y) { // it returned the gatehouse that connects to new draw bridge
        Map map = GameController.getGame().getMap();
        Building building = map.getTile(x + 1, y).getBuilding();
        if (x < MapController.map.getWidth() - 1) {
            if (building instanceof Gatehouse gatehouse &&
                    (!gatehouse.getName().equals("drawBridge")) &&
                    !gatehouse.isRightSide) {
                int checkY = (building.getLength() + building.getStartY()) / 2;
                if (y == checkY) {
                    return (Gatehouse) building;
                }
                return null;

            }
        }

        if (0 < x) {
            building = map.getTile(x - 1, y).getBuilding();
            if (building instanceof Gatehouse gatehouse &&
                    (!gatehouse.getName().equals("drawBridge")) &&
                    !gatehouse.isRightSide) {
                int checkY = (building.getLength() + building.getStartY()) / 2;
                if (y == checkY) {
                    return (Gatehouse) building;
                }
            }
        }
        if (y < MapController.map.getLength() - 1) {
            building = map.getTile(x, y + 1).getBuilding();
            if (building instanceof Gatehouse gatehouse &&
                    (!gatehouse.getName().equals("drawBridge")) &&
                    gatehouse.isRightSide) {
                int checkX = (building.getWidth() + building.getStartX()) / 2;
                if (x == checkX) {
                    return (Gatehouse) building;
                }
            }
        }
        if (0 < y) {
            building = map.getTile(x, y - 1).getBuilding();
            if (building instanceof Gatehouse gatehouse &&
                    (!gatehouse.getName().equals("drawBridge")) &&
                    gatehouse.isRightSide) {
                int checkX = (building.getWidth() + building.getStartX()) / 2;
                if (x == checkX) {
                    return (Gatehouse) building;
                }
            }
        }

        return null;
    }

    public void openOrCloseGatehouse(boolean openIt) {
        this.setOpen(openIt);
        if (this.drawBridge_gatehouse != null)
            this.drawBridge_gatehouse.setOpen(openIt);
    }


    public void checkToCloseGateHouse() {
        int startX = getStartX() - 25;
        int startY = getStartY() - 25;
        int endX = getStartX() + 25;
        int endY = getStartY() + 25;
        Map map = GameController.getGame().getMap();
        if (getStartX() - 25 < 0) {
            startX = 0;
        }

        if (getStartY() - 25 < 0) {
            startY = 0;
        }

        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }

        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }
        ArrayList<Military> militaries = HumanController.getEnemiesOfArea(startX, startY, endX, endY, getGovernment());
        if (militaries.size() != 0) {
            openOrCloseGatehouse(false);
        }
    }
}
