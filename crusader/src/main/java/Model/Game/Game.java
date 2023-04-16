package Model.Game;

import Model.Government;

import java.util.ArrayList;

public class Game {
    private Map map;
    private ArrayList<Government> governments = new ArrayList<>();
    private Government currentGovernment;
    private int round;

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

    public void setGovernments(ArrayList<Government> governments) {
        this.governments = governments;
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
    }
}
