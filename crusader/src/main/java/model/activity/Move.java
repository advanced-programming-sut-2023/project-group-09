package model.activity;

import controller.GameController;
import controller.MapController;
import controller.human.MoveController;
import enumeration.MoveStates;
import model.building.Building;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Military;
import model.tools.Tool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class Move {
    private Tuple startPair;
    private Tuple endPair;
    private Tuple patrolPair;
    private final boolean isDestinationConstant;
    private final Human human;
    private Tool tool;
    private boolean isAttacking = false;
    private boolean isPatrolStart = false;
    private String moveState;
    private LinkedList<Tuple> path;

    private boolean shouldConnectToTool = false;
    private Building building;
    int indexOfPath = 0;


    Military enemy;


    public Move(int startX, int startY, Tuple endPair, boolean isDestinationConstant, Human human) {
        this.startPair = new Tuple(startY, startX);
        this.endPair = endPair;
        this.isDestinationConstant = isDestinationConstant;
        this.human = human;
    }

    public Move(int startX, int startY, Military enemy, boolean isDestinationConstant, Human human) {
        this.startPair = new Tuple(startY, startX);
        this.endPair = new Tuple(enemy.getY(), enemy.getX());
        this.enemy = enemy;
        this.isDestinationConstant = isDestinationConstant;
        this.human = human;
    }

    public Move(int startX, int startY, Building building, boolean isDestinationConstant, Human human) {
        this.startPair = new Tuple(startY, startX);
        this.building = building;
        this.isDestinationConstant = isDestinationConstant;
        this.human = human;
    }
    public Move(int startX, int startY, Tool tool, boolean isDestinationConstant, Human human) {
        this.startPair = new Tuple(startY, startX);
        this.building = building;
        this.isDestinationConstant = isDestinationConstant;
        this.human = human;
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
        Map map = GameController.getGame().getMap();
        boolean beforeSate = false;
        for (int i = indexOfPath; i <= indexOfPath + human.getSpeed(); i++) {
            Tuple pair = path.get(i);


            if (i != 0) {
                pair.setOverhead(beforeSate);
            }
            pair.setOverhead(MoveController.checkIsPathOverhead(pair.getX(), pair.getY(), human, pair));


            Tile tile = map.getTile(pair.getX(), pair.getY());
            if (!tile.isPassable(human, pair.isOverhead())) {
                return false;
            }

            beforeSate = pair.isOverhead();
        }
        return true;
    }

    public boolean checkDestination() {

        if (!isDestinationConstant) {
            if (enemy != null && endPair.getX() != enemy.getX() && endPair.getY() != enemy.getY()) {
                endPair = new Tuple(enemy.getY(), enemy.getX());
                return true;
            }
            if (tool != null && endPair.getX() != tool.getX() && endPair.getY() != tool.getY()) {
                endPair = new Tuple(tool.getY(), tool.getX());
                return true;
            }
        }

        Tuple lastPair = path.getLast();
        if (!Objects.equals(endPair.getX(), lastPair.getX()) && !Objects.equals(endPair.getY(), lastPair.getY())) {
            endPair = new Tuple(enemy.getY(), enemy.getX());
            return true;
        }
        return false;
    }

    public void moveOneTurn() {
        if (moveState.equals(MoveStates.STOP.getState()) || isAttacking) {
            return;
        }


        if (checkDestination() || !checkIsPathValid()) {
            updatePath();
        }

        if (MoveStates.MOVING.getState().equals(moveState) || MoveStates.PATROL.getState().equals(moveState)) {
            if (indexOfPath + human.getSpeed() < path.size() - 1) {
                moveBeforeDestination();
                return;
            }
        }

        if (MoveStates.MOVING.getState().equals(moveState)) {
            indexOfPath = 0;
            if (human instanceof Military) {
                MapController.moveMilitary(endPair.getX(), endPair.getY(), (Military) human);
            } else {
                MapController.moveHuman(endPair.getX(), endPair.getY(), (Civilian) human);
            }
            moveState = MoveStates.STOP.getState();
            if(tool != null && shouldConnectToTool){
                //TODO connect to tool
            }
        }

        if (MoveStates.PATROL.getState().equals(moveState) && isPatrolStart) {
            indexOfPath = 0;
            if (human instanceof Military) {
                MapController.moveMilitary(endPair.getX(), endPair.getY(), (Military) human);
            } else {
                MapController.moveHuman(endPair.getX(), endPair.getY(), (Civilian) human);
            }
            Collections.reverse(path);
            Tuple temp = new Tuple(endPair.getY(), endPair.getX());
            endPair = new Tuple(startPair.getY(), startPair.getX());
            startPair = new Tuple(temp.getY(), temp.getX());
        }

        if (MoveStates.PATROL.getState().equals(moveState) && !isPatrolStart) {
            indexOfPath = 0;
            if (human instanceof Military) {
                MapController.moveMilitary(endPair.getX(), endPair.getY(), (Military) human);
            } else {
                MapController.moveHuman(endPair.getX(), endPair.getY(), (Civilian) human);
            }

            startPair = new Tuple(endPair.getY(), endPair.getX());
            endPair = new Tuple(patrolPair.getY(), patrolPair.getX());
            isPatrolStart = true;
        }
    }


    public void updatePath() {
        Tuple startPair = new Tuple(human.getY(), human.getX());
        if (building == null) {
            path = MoveController.getPath(startPair, endPair, human);
        } else {
            path = MoveController.getPathForBuilding(startPair, building, human);
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
        Tuple pair = path.get(indexOfPath + human.getSpeed());
        indexOfPath += human.getSpeed();
        if (human instanceof Military) {
            MapController.moveMilitary(pair.getX(), pair.getY(), (Military) human);
        } else {
            MapController.moveHuman(pair.getX(), pair.getY(), (Civilian) human);
        }
    }


    public void stopMove() {
        moveState = MoveStates.STOP.getState();
        path.clear();
        startPair = null;
        endPair = null;
        indexOfPath = 0;
    }

    public boolean isMoving() {
        return !moveState.equals(MoveStates.STOP.getState()) && !isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public Military getEnemy() {
        return enemy;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setEnemy(Military enemy) {
        this.enemy = enemy;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isShouldConnectToTool() {
        return shouldConnectToTool;
    }

    public void setShouldConnectToTool(boolean shouldConnectToTool) {
        this.shouldConnectToTool = shouldConnectToTool;
    }
}
