package model.game;

import model.Government;

import java.util.ArrayList;

public class Game {
    private Map map;
    private final ArrayList<Government> governments = new ArrayList<>();
    private Government currentGovernment;
    private int round;
    private int currentMapX;
    private int currentMapY;

    public Game(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Government> getGovernments() {
        return governments;
    }

    public void addGovernment(Government government) {
        if(currentGovernment == null){
            currentGovernment = government;
        }
        this.governments.add(government);
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public void setCurrentGovernment(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void changeTurn() {
        int index = governments.indexOf(currentGovernment);
        if(index + 1  == governments.size()){
            index = -1;
        }
        currentGovernment = governments.get(index + 1);
    }

    public int getCurrentMapX() {
        return currentMapX;
    }

    public void setCurrentMapX(int currentMapX) {
        this.currentMapX = currentMapX;
    }

    public int getCurrentMapY() {
        return currentMapY;
    }

    public void setCurrentMapY(int currentMapY) {
        this.currentMapY = currentMapY;
    }
}
