package model.activity;

import controller.GameController;
import controller.MapController;
import enumeration.MoveStates;
import model.building.Building;
import model.game.Tile;
import model.human.military.Military;
import model.tools.Tool;


public class ToolAttack {
    Building targetBuilding;
    Tool tool;
    Military enemy;

    public ToolAttack(Tool tool) {
        this.tool = tool;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    //methods
    public boolean isInRange(int x, int y,int range) {
        if (Math.abs(tool.getX() - x) > range) {
            return false;
        }

        return Math.abs(tool.getY() - y) <= range;
    }


    public void attack() {
        if (enemy == null) {
            return;
        }
        int enemyHp = enemy.takeDamage(tool.getDamage());
        if (enemy.getAttack().enemy == null && enemy.getAttack().isInRange(tool.getX(), tool.getY(), enemy.getShootingRange())) {
            enemy.getAttack().setTool(tool);
        }
        if (enemyHp <= 0) {
            MapController.deleteMilitary(enemy.getX(), enemy.getY(), enemy);
            enemy.setGovernment(null);
            enemy = null;
        }
        attackToBuilding();

    }


    public boolean buildingIsInRange(Building building) {
        if(building == null){
            return false;
        }
        if (tool.isCanAirAttack()) {
            return isInRange(building.getStartX(), building.getStartY(), tool.getShootingRange());
        }
        Tile tile = GameController.getGame().getMap().getTile(tool.getX(), tool.getY());
        return building.getNeighborTiles().contains(tile);
    }

    public void attackToBuilding() {
        if (tool.getName().equals("siegeTower")){
            tool.setCanMove(false);
        }
        int hp = targetBuilding.takeDamage(tool.getDamage());
        if (hp < 0) {
            MapController.deleteBuilding(targetBuilding);
            targetBuilding.setGovernment(null);
            targetBuilding = null;
        }
    }



    public void doAttack() {

        if(!tool.isCanAttack() || !tool.isActive()){
            return;
        }

        ToolMove move = tool.getToolMove();

        //building attack
        if (targetBuilding != null && buildingIsInRange(targetBuilding)) {
            move.stopMove();
            attackToBuilding();
            return;
        }


        if (move != null && !move.getMoveState().equals(MoveStates.STOP.getState())) {
            return;
        }

        //attack
        if (enemy != null) {
            attack();
        }
    }

}
