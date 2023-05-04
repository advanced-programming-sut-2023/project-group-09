package model.activity;

import controller.GameController;
import controller.MapController;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.MilitaryStates;
import enumeration.MoveStates;
import model.building.Building;
import model.game.Map;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.Random;

public class Attack {
    Building targetBuilding;
    Military military;
    Military enemy;

    public Attack(Military military) {
        this.military = military;
    }

    public ArrayList<Military> getEnemyOfRange(int x, int y, int range) {
        int startX = x - range;
        int startY = y - range;
        int endX = x + range;
        int endY = y + range;
        Map map = GameController.getGame().getMap();
        if (x - range < 0) {
            startX = 0;
        }

        if (y - range < 0) {
            startY = 0;
        }

        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }

        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }
        return getEnemiesOfArea(startX, startY, endX, endY);
    }

    public ArrayList<Military> getEnemiesOfArea(int startX, int startY, int endX, int endY) {
        ArrayList<Military> enemies = new ArrayList<>();
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                enemies.addAll(MapController.getMilitariesOfOtherGovernment(i, j, military.getGovernment()));
            }
        }
        return enemies;
    }

    public boolean isInRange(int x, int y, int range) {
        if (Math.abs(military.getX() - x) > range) {
            return false;
        }

        if (Math.abs(military.getY() - y) > range) {
            return false;
        }
        return true;
    }

    public boolean shouldAttack(int range) {
        if (enemy != null) {
            return true;
        }

        ArrayList<Military> militaries = getEnemyOfRange(military.getX(), military.getY(), range);
        if (militaries.size() == 0) {
            return false;
        }
        ArrayList<Military> enemies = new ArrayList<>();
        double minDistance = military.getShootingRange();
        for (Military enemy : militaries) {
            double distance = MoveController.getDistance(enemy.getX(), enemy.getY(), military.getX(), military.getY());
            if (distance < minDistance) {
                minDistance = distance;
                enemies.clear();
                enemies.add(enemy);
            }
        }

        Military targetTroop = military.getMove().getEnemy();

        if(targetTroop != null && enemies.contains(targetTroop)){
            this.enemy = enemies.get(enemies.indexOf(targetTroop));
            return true;
        }

        Random random = new Random();
        int index = random.nextInt(enemies.size());

        this.enemy = enemies.get(index);
        return true;
    }

    public void setEnemy(Military enemy) {
        this.enemy = enemy;
    }

    public void attackEnemy() {
        if(enemy == null){
            return;
        }
        int enemyHp = enemy.takeDamage(military.getAttackRating());
        if(enemy.getAttack().enemy == null && enemy.getAttack().isInRange(military.getX(),military.getY(),enemy.getShootingRange())){
            enemy.getAttack().setEnemy(military);
        }

        if (enemyHp <= 0) {
            MapController.deleteMilitary(enemy.getX(), enemy.getY(), enemy);
            enemy.setGovernment(null);
            enemy = null;
        }

    }

    public void attack() {
        if(military.canAirAttack()){
            attackEnemy();
        }else{
            HumanController.attack(enemy);
            enemy = null;
        }
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    public boolean shouldAttack(){
        if(military.getMilitaryState().equals(MilitaryStates.AGGRESSIVE_STANCE.getState())){
            return shouldAttack(military.getAggressiveRange());
        }

        if(military.getMilitaryState().equals(MilitaryStates.DEFENSIVE_STANCE.getState())){
            return shouldAttack(military.getDefenseRange());
        }
        return shouldAttack(military.getShootingRange());
    }

    public void doAttack() {
        Move move = military.getMove();
        if (shouldAttack(2)) {
            if (military.getMove().isMoving()) {
                military.getMove().setAttacking(true);
            }
            attackEnemy();
            return;
        }

        if (move != null && !move.getMoveState().equals(MoveStates.STOP.getState())) {
            if(move.isAttacking()){
                move.setAttacking(false);
            }
            return;
        }

        if(shouldAttack()){
            attack();
        }
    }

}
