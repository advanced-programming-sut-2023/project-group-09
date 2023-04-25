package model.activity;

import model.human.military.Military;

public class Move {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean isDestinationConstant;
    private String moveState;

    Military enemy;

    public Move(int startX, int startY, int endX, int endY, boolean isDestinationConstant) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isDestinationConstant = isDestinationConstant;
    }
    public Move(int startX, int startY, Military enemy, boolean isDestinationConstant) {
        this.startX = startX;
        this.startY = startY;
        this.enemy = enemy;
        this.isDestinationConstant = isDestinationConstant;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public boolean isDestinationConstant() {
        return isDestinationConstant;
    }

    public void setDestinationConstant(boolean destinationConstant) {
        isDestinationConstant = destinationConstant;
    }

    public Military getEnemy() {
        return enemy;
    }

    public void setEnemy(Military enemy) {
        this.enemy = enemy;
    }
    public String getMoveState() {
        return moveState;
    }

    public void setMoveState(String moveState) {
        this.moveState = moveState;
    }
}
