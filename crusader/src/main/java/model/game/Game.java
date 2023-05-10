package model.game;

import model.Government;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    private Map map;
    private final ArrayList<Government> governments = new ArrayList<>();
    private Government currentGovernment;
    private Government winner;
    private int round;
    private int currentMapX;
    private int currentMapY;
    private boolean endGame = false;

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public Government getWinner() {
        return winner;
    }

    public void setWinner() {
        this.winner = null;
        Iterator itr = getGovernments().iterator();
        while (itr.hasNext()) {
            Government government = (Government) itr.next();
            if (government.isAlive())
                this.winner = government;
        }
    }

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
        if (currentGovernment == null) {
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
        if (index + 1 == governments.size()) {
            index = -1;
        }
        currentGovernment = governments.get(index + 1);
        if (!currentGovernment.isAlive())
            changeTurn();
    }

    public String getNicknameOfNextGovernment() {
        int index = governments.indexOf(currentGovernment);
        if (index + 1 == governments.size()) {
            index = -1;
        }
        if (!governments.get(index + 1).isAlive())
            return getNicknameOfNextGovernment();
        return governments.get(index + 1).getUser().getNickname();
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

    public void setScores() {
        for (Government government : getGovernments()) {
            government.getUser().addHighScore(government.getHowManyTurnsSurvive() * 100);
        }
    }
}
