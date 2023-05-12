package model.activity;

import controller.GameController;
import controller.MapController;
import controller.ToolsController;
import enumeration.MoveStates;
import model.building.Building;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.tools.Tool;

import java.util.Collections;
import java.util.LinkedList;


public class ToolMove {
    private final boolean isDestinationConstant;
    private final Tool tool;
    int indexOfPath = 0;
    private Tuple startPair;
    private Tuple endPair;
    private Tuple patrolPair;
    private boolean isAttacking = false;
    private boolean isPatrolStart = false;
    private String moveState;
    private LinkedList<Tuple> path;
    private Building building;


    public ToolMove(int startX, int startY, Tuple endPair, boolean isDestinationConstant, Tool tool) {
        this.startPair = new Tuple(startY, startX);
        this.endPair = endPair;
        this.isDestinationConstant = isDestinationConstant;
        this.tool = tool;
    }


    public ToolMove(int startX, int startY, Building building, boolean isDestinationConstant, Tool tool) {
        this.startPair = new Tuple(startY, startX);
        this.building = building;
        this.isDestinationConstant = isDestinationConstant;
        this.tool = tool;
    }

    public int getStartX() {
        return startPair.getX();
    }

    public int getStartY() {
        return startPair.getY();
    }

    public int getEndX() {
        return endPair.getX();
    }


    public int getEndY() {
        return endPair.getY();
    }

    public void setMoveState() {
        if (path == null) {
            moveState = MoveStates.STOP.getState();
            return;
        }
        moveState = MoveStates.MOVING.getState();
    }

    public void setMovePatrol(Tuple patrolPair) {
        moveState = MoveStates.PATROL.getState();
        this.patrolPair = patrolPair;
        indexOfPath = 0;
    }

    public void setPath(LinkedList<Tuple> path) {
        this.path = path;
        setMoveState();
    }

    public boolean checkIsPathValid() {

        if (building != null && building.getGovernment() == null) {
            stopMove();
            building = null;
            return true;
        }


        Map map = GameController.getGame().getMap();
        for (int i = indexOfPath; i <= indexOfPath + tool.getSpeed(); i++) {
            if (path == null || path.size() < i + 1) {
                return true;
            }
            Tuple pair = path.get(i);
            Tile tile = map.getTile(pair.getX(), pair.getY());
            if (!tile.isPassable() || (tile.getTool() != null && !tool.equals(tile.getTool()))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDestination() {
        Tuple lastPair = path.getLast();
        return endPair.getX() != lastPair.getX() || endPair.getY() != lastPair.getY();
    }

    public void moveOneTurn() {
        if (moveState.equals(MoveStates.STOP.getState()) || isAttacking) {
            return;
        }


        if (path != null && !checkIsPathValid()) {
            updatePath();
        }
        if (path != null && checkDestination()) {
            updatePath();
        }

        if (path == null) {
            return;
        }

        if (MoveStates.MOVING.getState().equals(moveState) || MoveStates.PATROL.getState().equals(moveState)) {
            if (indexOfPath + tool.getSpeed() < path.size() - 1) {
                moveBeforeDestination();
                return;
            }
        }

        if (MoveStates.MOVING.getState().equals(moveState)) {
            indexOfPath = 0;
            MapController.moveTool(endPair.getX(), endPair.getY(), tool);
            moveState = MoveStates.STOP.getState();
        }

        if (MoveStates.PATROL.getState().equals(moveState) && isPatrolStart) {
            indexOfPath = 0;
            MapController.moveTool(endPair.getX(), endPair.getY(), tool);
            Collections.reverse(path);
            Tuple temp = new Tuple(endPair.getY(), endPair.getX());
            endPair = new Tuple(startPair.getY(), startPair.getX());
            startPair = new Tuple(temp.getY(), temp.getX());
        }

        if (MoveStates.PATROL.getState().equals(moveState) && !isPatrolStart) {
            indexOfPath = 0;
            MapController.moveTool(endPair.getX(), endPair.getY(), tool);
            startPair = new Tuple(endPair.getY(), endPair.getX());
            endPair = new Tuple(patrolPair.getY(), patrolPair.getX());
            isPatrolStart = true;
        }
    }


    public void updatePath() {
        Tuple startPair = new Tuple(tool.getY(), tool.getX());
        if (building == null) {
            path = ToolsController.getPathTools(startPair, endPair);
        } else {
            path = ToolsController.getPathForBuilding(startPair, building, tool);
        }

        indexOfPath = 0;
        if (path == null) {
            moveState = MoveStates.STOP.getState();
        }
    }

    public String getMoveState() {
        return moveState;
    }

    public void moveBeforeDestination() {
        Tuple pair = path.get(indexOfPath + tool.getSpeed());
        indexOfPath += tool.getSpeed();
        MapController.moveTool(pair.getX(), pair.getY(), tool);
    }


    public void stopMove() {
        moveState = MoveStates.STOP.getState();
        path.clear();
        startPair = null;
        endPair = null;
        indexOfPath = 0;
    }

    public boolean isMoving() {
        return !moveState.equals(MoveStates.STOP.getState());
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public void setPatrolStart(boolean check) {
        isPatrolStart = check;
    }
}
