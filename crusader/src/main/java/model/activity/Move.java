package model.activity;

import controller.GameController;
import controller.human.HumanController;
import controller.MapController;
import controller.human.MoveController;
import enumeration.MoveStates;
import javafx.util.Pair;
import model.game.Map;
import model.game.Tile;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Military;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class Move {
    private Pair<Integer, Integer> startPair;
    private Pair<Integer, Integer> endPair;
    private Pair<Integer, Integer> patrolPair;
    private boolean isDestinationConstant;
    Human human;


    private boolean isPatrolStart = false;
    private String moveState;
    private LinkedList<Pair<Integer, Integer>> path;

    int indexOfPath = 0;
    Military enemy;

    public void setPath(LinkedList<Pair<Integer, Integer>> path) {
        this.path = path;
        if(path == null){
            moveState = MoveStates.STOP.getState();
        }
    }

    public Move(int startX, int startY, Pair<Integer, Integer> endPair, boolean isDestinationConstant, Human human) {
        this.startPair = new Pair<>(startY, startX);
        this.endPair = endPair;
        this.isDestinationConstant = isDestinationConstant;
        this.human = human;
    }

    public Move(int startX, int startY, Military enemy, boolean isDestinationConstant, Human human) {
        this.startPair = new Pair<>(startY, startX);
        this.endPair = new Pair<>(enemy.getY(), enemy.getX());
        this.enemy = enemy;
        this.isDestinationConstant = isDestinationConstant;
        this.human = human;
        path = MoveController.getPath(startPair, endPair, human);
        setMoveState();
    }

    public int getStartX() {
        return startPair.getValue();
    }

    public int getStartY() {
        return startPair.getKey();
    }

    public int getEndX() {
        return endPair.getValue();
    }


    public int getEndY() {
        return endPair.getKey();
    }

    public void setMoveState() {
        if (path == null) {
            moveState = MoveStates.STOP.getState();
            return;
        }
        moveState = MoveStates.MOVING.getState();
    }

    public void setMovePatrol(Pair<Integer, Integer> patrolPair) {
        moveState = MoveStates.PATROL.getState();
        this.patrolPair = patrolPair;
        indexOfPath = 0;
    }

    public Pair<Integer, Integer> getPatrolPair() {
        return patrolPair;
    }

    public void setPatrolPair(Pair<Integer, Integer> patrolPair) {
        this.patrolPair = patrolPair;
    }

    public boolean checkIsPathValid() {
        Map map = GameController.getGame().getMap();
        for (int i = indexOfPath; i < indexOfPath + human.getSpeed() + 1; i++) {
            Pair<Integer, Integer> pair = path.get(i);
            Tile tile = map.getTile(pair.getValue(), pair.getKey());
            if (!tile.isPassable(human)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDestination() {
        if (!isDestinationConstant) {
            if (endPair.getValue() != enemy.getX() && endPair.getKey() != enemy.getY()) {
                endPair = new Pair<>(enemy.getY(), enemy.getX());
                return true;
            }
        }
        Pair<Integer, Integer> lastPair = path.getLast();
        if (!Objects.equals(endPair.getValue(), lastPair.getValue()) && !Objects.equals(endPair.getKey(), lastPair.getKey())) {
            endPair = new Pair<>(enemy.getY(), enemy.getX());
            return true;
        }
        return false;
    }

    public void moveOneTurn() {
        if (moveState.equals(MoveStates.STOP.getState())) {
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
                MapController.moveMilitary(endPair.getValue(), endPair.getKey(), (Military) human);
            } else {
                MapController.moveHuman(endPair.getValue(), endPair.getKey(), (Civilian) human);
            }
            moveState = MoveStates.STOP.getState();
        }

        if (MoveStates.PATROL.getState().equals(moveState) && isPatrolStart) {
            indexOfPath = 0;
            if (human instanceof Military) {
                MapController.moveMilitary(endPair.getValue(), endPair.getKey(), (Military) human);
            } else {
                MapController.moveHuman(endPair.getValue(), endPair.getKey(), (Civilian) human);
            }
            Collections.reverse(path);
            Pair<Integer, Integer> temp = new Pair<>(endPair.getKey(), endPair.getValue());
            endPair = new Pair<>(startPair.getKey(), startPair.getValue());
            startPair = new Pair<>(temp.getKey(), temp.getValue());
        }

        if (MoveStates.PATROL.getState().equals(moveState) && !isPatrolStart) {
            indexOfPath = 0;
            if (human instanceof Military) {
                MapController.moveMilitary(endPair.getValue(), endPair.getKey(), (Military) human);
            } else {
                MapController.moveHuman(endPair.getValue(), endPair.getKey(), (Civilian) human);
            }

            startPair = new Pair<>(endPair.getKey(), endPair.getValue());
            endPair = new Pair<>(patrolPair.getKey(), patrolPair.getValue());
            isPatrolStart = true;
        }
    }


    public void updatePath() {
        Pair<Integer, Integer> startPair = new Pair<>(human.getX(), human.getY());
        path = MoveController.getPath(startPair, endPair, human);
        indexOfPath = 0;
        if (path == null) {
            moveState = MoveStates.STOP.getState();
        }
    }

    public String getMoveState() {
        return moveState;
    }

    public void moveBeforeDestination() {
        Pair<Integer, Integer> pair = path.get(indexOfPath + human.getSpeed());
        indexOfPath += human.getSpeed();
        if (human instanceof Military) {
            MapController.moveMilitary(pair.getValue(), pair.getKey(), (Military) human);
        } else {
            MapController.moveHuman(pair.getValue(), pair.getKey(), (Civilian) human);
        }
    }


    public void stopMove(){
        moveState = MoveStates.STOP.getState();
        indexOfPath = 0;
    }
}
