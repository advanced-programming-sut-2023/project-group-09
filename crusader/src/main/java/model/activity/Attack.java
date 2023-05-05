package model.activity;

import controller.GameController;
import controller.MapController;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.MilitaryStates;
import enumeration.MoveStates;
import model.building.Building;
import model.game.Map;
import model.game.Tile;
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

    public void setEnemy(Military enemy) {
        targetBuilding = null;
        this.enemy = enemy;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }


    //methods
    public boolean isInRange(int x, int y, int range) {
        if (Math.abs(military.getX() - x) > range) {
            return false;
        }

        return Math.abs(military.getY() - y) <= range;
    }

    /*
    find enemies who are in range and if target enemy exist
    choose him else between the nearest enemies choose one random
    enemy return true if it finds enemy
    */
    public boolean shouldAttack(int range) {
        if (enemy != null) {
            return true;
        }

        ArrayList<Military> militaries = getEnemyOfRange(military.getX(), military.getY(), range);
        if (militaries.size() == 0) {
            return false;
        }

        Military targetTroop = military.getMove().getEnemy();
        if (targetTroop != null && militaries.contains(targetTroop)) {
            this.enemy = militaries.get(militaries.indexOf(targetTroop));
            return true;
        }


        ArrayList<Military> enemies = new ArrayList<>();
        double minDistance = military.getShootingRange();
        for (Military enemy : militaries) {
            double distance = MoveController.getDistance(enemy.getX(), enemy.getY(), military.getX(), military.getY());
            if (distance < minDistance && Math.abs(minDistance - distance) > 0.5) {
                minDistance = distance;
                enemies.clear();
                enemies.add(enemy);
            }
        }

        Random random = new Random();
        int index = random.nextInt(enemies.size());

        this.enemy = enemies.get(index);
        return true;
    }


    /*
    if enemy exists decrease hp of enemy and if enemy can damage him set him as enemy's enemy
    if enemy dead delete him and set government null
    */
    public void attackEnemy() {
        if (enemy == null) {
            return;
        }
        int enemyHp = enemy.takeDamage(military.getAttackRating());
        if (enemy.getAttack().enemy == null && enemy.getAttack().isInRange(military.getX(), military.getY(), enemy.getShootingRange())) {
            enemy.getAttack().setEnemy(military);
        }

        if (enemyHp <= 0) {
            MapController.deleteMilitary(enemy.getX(), enemy.getY(), enemy);
            enemy.setGovernment(null);
            enemy = null;
        }

    }

    //set attack according type of enemy(he can air attack or not)
    public void attack() {
        if (military.canAirAttack()) {
            attackEnemy();
        } else {
            HumanController.attack(enemy);
            enemy = null;
        }
    }

    //it determines range of attack according military state
    public boolean shouldAttack() {
        if (military.getMilitaryState().equals(MilitaryStates.AGGRESSIVE_STANCE.getState())) {
            return shouldAttack(military.getAggressiveRange());
        }

        if (military.getMilitaryState().equals(MilitaryStates.DEFENSIVE_STANCE.getState())) {
            return shouldAttack(military.getDefenseRange());
        }
        return shouldAttack(military.getShootingRange());
    }

    public boolean buildingIsInRange(Building building) {
        if(building == null){
            return false;
        }
        if (military.canAirAttack()) {
            return isInRange(building.getStartX(), building.getStartY(), military.getShootingRange());
        }
        Tile tile = GameController.getGame().getMap().getTile(military.getX(), military.getY());
        return building.getNeighborTiles().contains(tile);
    }

    public void attackToBuilding() {
        if (military.getName().equals("ladderman")){
            military.setUsesLadder(true);
        }
        int hp = targetBuilding.takeDamage(military.getAttackRating());
        if (hp < 0) {
            MapController.deleteBuilding(targetBuilding);
            targetBuilding.setGovernment(null);
            targetBuilding = null;
        }
    }


    //this should use in nextTurn for each troop if troop has government so far
    public void doAttack() {

        //if enemy is very near to military
        Move move = military.getMove();
        if (shouldAttack(2)) {
            if (move != null && move.isMoving()) {
                move.setAttacking(true);
            }
            attackEnemy();
            return;
        }


        //building attack
        if (buildingIsInRange(targetBuilding)) {
            if (move != null && move.isMoving()) {
                move.setAttacking(true);
            }
            attackToBuilding();
            return;
        }


        //continue moving if you don't need to defend yourself
        if (move != null && !move.getMoveState().equals(MoveStates.STOP.getState())) {
            if (move.isAttacking()) {
                move.setAttacking(false);
            }
            return;
        }

        //attack
        if (shouldAttack()) {
            attack();
        }
    }

}
