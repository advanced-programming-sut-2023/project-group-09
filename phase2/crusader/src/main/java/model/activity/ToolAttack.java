package model.activity;

import controller.GameController;
import controller.MapController;
import enumeration.MoveStates;
import model.building.Building;
import model.building.castlebuildings.MainCastle;
import model.game.Tile;
import model.game.Tuple;
import model.human.civilian.Civilian;
import model.human.military.Engineer;
import model.human.military.Military;
import model.tools.Tool;

import java.util.ArrayList;


public class ToolAttack {

    public Tool tool;
    public Building targetBuilding;
    public Tuple attackPoint;

    public ToolAttack(Tool tool) {
        this.tool = tool;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    //methods
    public boolean isInRange(int x, int y, int range) {
        if (Math.abs(tool.getX() - x) > range) {
            return false;
        }

        return Math.abs(tool.getY() - y) <= range;
    }

    public void setAttackPoint(Tuple attackPoint) {
        this.attackPoint = attackPoint;
    }

    public void attackToMilitary(Military enemy) {
        if (enemy == null) {
            return;
        }

        if (tool.isUseStone() && !tool.chargeStone()) {
            return;
        }

        int enemyHp = enemy.takeDamage(tool.getDamage());
        if (enemy.getAttack().enemy == null && enemy.getAttack().isInRange(tool.getX(), tool.getY(), enemy.getShootingRange())) {
            enemy.getAttack().setTool(tool);
        }
        if (enemyHp <= 0) {
            MapController.deleteMilitary(enemy.getX(), enemy.getY(), enemy);
            enemy.setGovernment(null);
        }
    }


    public boolean buildingIsInRange(Building building) {
        if (building == null) {
            return false;
        }
        if (tool.isCanAirAttack()) {
            return isInRange(building.getStartX(), building.getStartY(), tool.getShootingRange());
        }
        return building.getNeighborTiles().contains(new Tuple(tool.getY(), tool.getX()));
    }

    public void attackToBuilding(Building building) {
        if (targetBuilding instanceof MainCastle){
            return;
        }
        if (tool.getName().equals("siegeTower")) {
            tool.setCanMove(false);
            return;
        }
        int hp = building.takeDamage(tool.getDamage());
        if (hp <= 0) {
            MapController.deleteBuilding(building);
            building.setGovernment(null);
            if (building == targetBuilding) {
                targetBuilding = null;
            }
        }
    }

    public void attackToTool(Tool tool) {
        int hp = tool.takeDamage(tool.getDamage());
        if (hp < 0) {
            MapController.deleteTool(tool.getX(), tool.getY(), tool);
            tool.setGovernment(null);
        }
    }

    public void attackToCivilian(Civilian civilian) {
        MapController.deleteHuman(civilian.getX(), civilian.getY(), civilian);
        civilian.setGovernment(null);
    }

    public boolean attackToPoint() {
        Tile tile = GameController.getGame().getMap().getTile(attackPoint.getX(), attackPoint.getY());
        Building building;
        Tool tool;
        int count = 0;
        if (this.tool.isAttackToBuilding()) {
            if ((building = tile.getBuilding()) != null && !building.getGovernment().equals(this.tool.getGovernment())) {
                attackToBuilding(building);
                count++;
            }
        }

        if (this.tool.isAttackToHuman()) {
            if ((tool = tile.getTool()) != null) {
                attackToTool(tool);
                count++;
            }
            ArrayList<Military> militaries= new ArrayList<>(tile.getMilitaries());
            for (Military military : militaries) {
                if (military.getGovernment() != null &&!military.getGovernment().equals(this.tool.getGovernment())) {
                    if (military instanceof Engineer && military.isInvisible()){
                        continue;
                    }
                    military.setInvisible(false);
                    attackToMilitary(military);
                    count++;

                }
            }
            ArrayList<Civilian> civilians = new ArrayList<>(tile.getCivilian());
            for (Civilian civilian : civilians) {
                if (!civilian.getGovernment().equals(this.tool.getGovernment())) {
                    attackToCivilian(civilian);
                    count++;
                }

            }
        }
        return count != 0;
    }

    public void doAttack() {

        if (!tool.isCanAttack() || !tool.isActive()) {
            return;
        }


        if (targetBuilding != null && targetBuilding.getGovernment() == null) {
            targetBuilding = null;
        }


        ToolMove move = tool.getToolMove();
        //building attack
        if (targetBuilding != null && buildingIsInRange(targetBuilding)) {
            attackToBuilding(targetBuilding);
            move.stopMove();
            return;
        }


        if (move != null && !move.getMoveState().equals(MoveStates.STOP.getState())) {
            return;
        }

        //attack
        if (attackPoint != null) {
            if (tool.isUseStone() && tool.getStoneNumber() == 0) {
                return;
            }
            if (attackToPoint()) {
                tool.decreaseStoneNumber(1);
            }
        }
    }

}
