package controller.human;


import controller.GameController;
import controller.MapController;
import enumeration.MilitaryStates;
import enumeration.MoveStates;
import model.Government;
import model.activity.Move;
import model.building.Building;
import model.building.castlebuildings.Wall;
import model.game.Map;
import model.game.Tuple;
import model.human.civilian.Civilian;
import model.human.military.Engineer;
import model.human.military.Military;
import model.tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class HumanController {
    public static ArrayList<Military> militaries;

    public static boolean move(Tuple endPair) {

        Tuple startPair = MoveController.getStartPair();
        MoveController.shouldCheckOtherPath();
        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Tuple> assassinPath = MoveController.checkHasLadderPath(startPair, endPair, path);
        LinkedList<Tuple> ladderPath = MoveController.checkAssassinPath(startPair, endPair, path);


        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        if (path == null) {
            for (Military military : militaries) {
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(ladderPath);
                    military.setMove(move);

                }

                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(assassinPath);
                    military.setMove(move);

                }
            }
        } else {
            for (Military military : militaries) {
                Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                move.setPath(path);
                military.setMove(move);
            }
        }
        return true;
    }

    public static boolean attack(Military enemy) {
        Tuple startPair = MoveController.getStartPair();
        Tuple endPair = new Tuple(enemy.getY(), enemy.getX());
        MoveController.shouldCheckOtherPath();

        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Tuple> assassinPath = MoveController.checkHasLadderPath(startPair, endPair, path);
        LinkedList<Tuple> ladderPath = MoveController.checkAssassinPath(startPair, endPair, path);

        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        int count = 0;

        if (path == null) {
            for (Military military : militaries) {
                if (military.canAirAttack() || military.getName().equals("engineer") || military.getName().equals("ladderman")) {
                    continue;
                }
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), enemy, false, military);
                    move.setPath(ladderPath);
                    military.setMove(move);
                    count++;
                }

                if (military.getName().equals("assassin")) {
                    Move move = new Move(military.getX(), military.getY(), enemy, false, military);
                    move.setPath(assassinPath);
                    military.setMove(move);
                    count++;
                }
            }
        } else {
            for (Military military : militaries) {
                if (military.canAirAttack() || military.getName().equals("engineer") || military.getName().equals("ladderman")) {
                    continue;
                }
                Move move = new Move(military.getX(), military.getY(), enemy, false, military);
                move.setPath(path);
                military.setMove(move);
                count++;
            }
        }
        if (count == 0) {
            return false;
        }
        return true;
    }

    public static boolean attack(Building building) {
        Tuple startPair = MoveController.getStartPair();
        MoveController.shouldCheckOtherPath();
        LinkedList<Tuple> path = MoveController.checkHasPathForBuilding(startPair, building);
        LinkedList<Tuple> assassinPath = MoveController.checkHasLadderPathForBuilding(startPair, building, path);
        LinkedList<Tuple> ladderPath = MoveController.checkAssassinPathForBuilding(startPair, building, path);

        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        int count = 0;

        if (path == null) {
            for (Military military : militaries) {
                if (military.canAirAttack() || military.getName().equals("engineer") || military.getName().equals("ladderman")) {
                    continue;
                }
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), building, true, military);
                    move.setPath(ladderPath);
                    military.setMove(move);
                    military.getAttack().setTargetBuilding(building);
                    count++;
                }

                if (military.getName().equals("assassin")) {
                    Move move = new Move(military.getX(), military.getY(), building, true, military);
                    move.setPath(assassinPath);
                    military.setMove(move);
                    military.getAttack().setTargetBuilding(building);
                    count++;
                }
            }
        } else {
            for (Military military : militaries) {
                if (military.canAirAttack() || military.getName().equals("engineer")) {
                    continue;
                }

                if (military.getName().equals("ladderman") && !(building instanceof Wall)) {
                    continue;
                }

                Move move = new Move(military.getX(), military.getY(), building, true, military);
                move.setPath(path);
                military.setMove(move);
                military.getAttack().setTargetBuilding(building);
                count++;
            }
        }
        if (count == 0) {
            return false;
        }
        return true;
    }

    public static boolean attack(Tool tool) {
        Tuple startPair = MoveController.getStartPair();
        Tuple endPair = new Tuple(tool.getY(), tool.getX());
        MoveController.shouldCheckOtherPath();
        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Tuple> assassinPath = MoveController.checkHasLadderPath(startPair, endPair, path);
        LinkedList<Tuple> ladderPath = MoveController.checkAssassinPath(startPair, endPair, path);

        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        int count = 0;

        if (path == null) {
            for (Military military : militaries) {
                if (military.canAirAttack() || military.getName().equals("engineer") || military.getName().equals("ladderman")) {
                    continue;
                }
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), tool, false, military);
                    move.setPath(ladderPath);
                    military.setMove(move);
                    count++;
                }

                if (military.getName().equals("assassin")) {
                    Move move = new Move(military.getX(), military.getY(), tool, false, military);
                    move.setPath(assassinPath);
                    military.setMove(move);
                    count++;
                }
            }
        } else {
            for (Military military : militaries) {
                if (military.canAirAttack() || military.getName().equals("engineer") || military.getName().equals("ladderman")) {
                    continue;
                }
                Move move = new Move(military.getX(), military.getY(), tool, false, military);
                move.setPath(path);
                military.setMove(move);
                count++;
            }
        }
        if (count == 0) {
            return false;
        }
        return true;
    }

    public static boolean useTool(Tool tool) {
        Tuple startPair = MoveController.getStartPair();
        Tuple endPair = new Tuple(tool.getY(), tool.getX());
        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);

        if (path == null) {
            return false;
        }
        int count = 0;
        for (Military military : militaries) {
            if (!military.getName().equals("engineer")) {
                continue;
            }
            Move move = new Move(military.getX(), military.getY(), tool, false, military);
            move.setPath(path);
            move.setShouldConnectToTool(true);
            military.setMove(move);
            count++;
        }
        return count != 0;
    }

    public static boolean airAttack(int x, int y, List<Military> enemies) {

        int countOfTroop = 0;
        for (Military military : militaries) {
            if (military.canAirAttack() && military.getAttack().isInRange(x, y, military.getShootingRange())) {
                countOfTroop++;
                Random random = new Random();
                int index = random.nextInt(enemies.size());
                Military enemy = enemies.get(index);
                military.getAttack().setEnemy(enemy);
            }
        }
        if (countOfTroop == 0) {
            return false;
        }
        return true;
    }

    public static boolean airAttack(Building building) {

        int countOfTroop = 0;
        for (Military military : militaries) {
            if (military.canAirAttack() && military.getAttack().buildingIsInRange(building)) {
                countOfTroop++;
                military.getAttack().setTargetBuilding(building);
            }
        }
        if (countOfTroop == 0) {
            return false;
        }
        return true;
    }

    public static boolean airAttack(Tool tool) {

        int countOfTroop = 0;
        for (Military military : militaries) {
            if (military.canAirAttack() && military.getAttack().isInRange(tool.getX(), tool.getY(), military.getShootingRange())) {
                countOfTroop++;
                military.getAttack().setTool(tool);
            }
        }
        if (countOfTroop == 0) {
            return false;
        }
        return true;
    }

    public static boolean patrolUnit(int x1, int y1, int x2, int y2) {

        Tuple patrolPair = new Tuple(y2, x2);
        Tuple endPair = new Tuple(y1, x1);
        Tuple startPair = MoveController.getStartPair();
        MoveController.shouldCheckOtherPath();
        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Tuple> assassinPath = MoveController.checkHasLadderPath(startPair, endPair, path);
        LinkedList<Tuple> ladderPath = MoveController.checkAssassinPath(startPair, endPair, path);

        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }

        if (!MoveController.checkPatrolPath(endPair, patrolPair)) {
            return false;
        }
        if (path == null) {
            for (Military military : militaries) {
                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(ladderPath);
                    move.setMovePatrol(patrolPair);
                    military.setMove(move);
                }

                if (military.isUsesLadder()) {
                    Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                    move.setPath(assassinPath);
                    move.setMovePatrol(patrolPair);
                    military.setMove(move);

                }
            }
        } else {
            for (Military military : militaries) {
                Move move = new Move(military.getX(), military.getY(), endPair, true, military);
                move.setPath(path);
                move.setMovePatrol(patrolPair);
                military.setMove(move);
            }
        }
        return true;
    }

    public static boolean deactivatePatrol() {
        int counter = 0;
        for (Military military : militaries) {
            if (military.getMove().getMoveState().equals(MoveStates.PATROL.getState())) {
                counter++;
                military.getMove().stopMove();
            }
        }
        return counter != 0;
    }

    public static void disbandUnits(ArrayList<Military> militaries) {
        for (Military military : militaries) {
            MapController.deleteMilitary(military.getX(), military.getY(), military);
            Civilian civilian = new Civilian(military.getX(), military.getY(), false);
            MapController.addHuman(military.getX(), military.getY(), civilian);
        }
    }

    public static void pourOilDirection(Engineer engineer, String direction, String state) {
        ArrayList<Military> enemies = null;
        if (direction != null) {
            switch (direction) {
                case "e":
                    enemies = getEnemyOfRight(engineer.getX(), engineer.getY(), engineer.getGovernment());
                    if (enemies.size() != 0) {
                        attackWithOil(enemies);
                    }
                    break;
                case "w":
                    enemies = getEnemyOfLeft(engineer.getX(), engineer.getY(), engineer.getGovernment());
                    if (enemies.size() != 0) {
                        attackWithOil(enemies);
                    }
                    break;
                case "n":
                    enemies = getEnemyOfUp(engineer.getX(), engineer.getY(), engineer.getGovernment());
                    if (enemies.size() != 0) {
                        attackWithOil(enemies);
                    }
                    break;
                case "s":
                    enemies = getEnemyOfDown(engineer.getX(), engineer.getY(), engineer.getGovernment());
                    if (enemies.size() != 0) {
                        attackWithOil(enemies);
                    }
                    break;
                default:
                    return;
            }
            getOil(engineer);
        } else {
            int enemiesCount = 0;
            int maxEnemy = 0;
            ArrayList<Military> directionEnemy;
            directionEnemy = getEnemyOfRight(engineer.getX(), engineer.getY(), engineer.getGovernment());
            enemiesCount += directionEnemy.size();

            if (directionEnemy.size() > maxEnemy) {
                enemies = new ArrayList<>(directionEnemy);
            }


            directionEnemy = getEnemyOfLeft(engineer.getX(), engineer.getY(), engineer.getGovernment());
            enemiesCount += directionEnemy.size();

            if (directionEnemy.size() > maxEnemy) {
                enemies = new ArrayList<>(directionEnemy);
            }

            directionEnemy = getEnemyOfDown(engineer.getX(), engineer.getY(), engineer.getGovernment());
            enemiesCount += directionEnemy.size();

            if (directionEnemy.size() > maxEnemy) {
                enemies = new ArrayList<>(directionEnemy);
            }
            directionEnemy = getEnemyOfUp(engineer.getX(), engineer.getY(), engineer.getGovernment());
            enemiesCount += directionEnemy.size();

            if (directionEnemy.size() > maxEnemy) {
                enemies = new ArrayList<>(directionEnemy);
            }

            if (state.equals(MilitaryStates.AGGRESSIVE_STANCE.getState()) && enemiesCount > 0) {
                attackWithOil(enemies);
                getOil(engineer);
            } else if (state.equals(MilitaryStates.DEFENSIVE_STANCE.getState()) && enemiesCount > 3) {
                attackWithOil(enemies);
                getOil(engineer);
            }
        }

    }

    public static void getOil(Engineer engineer) {
        engineer.setHasOil(false);
        Building targetBuilding = null;
        LinkedList<Tuple> path = null;
        for (Building building : engineer.getGovernment().getBuildingData("oilSmelter").getBuildings()) {
            if (building.isActive()) {

                Tuple start = new Tuple(engineer.getY(), engineer.getX());
                path = MoveController.getPathForBuilding(start, building, engineer);
                if (path != null) {
                    break;
                }
            }
        }
        if (path != null) {
            Move move = new Move(engineer.getX(), engineer.getY(), targetBuilding, true, engineer);
            move.setPath(path);
            move.setShouldGetOil(true);
            engineer.setMove(move);
        }
    }

    public static void attackWithOil(ArrayList<Military> militaries) {
        for (Military military : militaries) {
            MapController.deleteMilitary(military.getX(), military.getY(), military);
            military.setGovernment(null);
        }
    }

    public static ArrayList<Military> getEnemyOfRight(int x, int y, Government government) {
        int startY = y - 5;
        int endX = x + 5;
        int endY = y + 5;
        Map map = GameController.getGame().getMap();
        if (y - 5 < 0) {
            startY = 0;
        }

        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }

        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }
        return HumanController.getEnemiesOfArea(x, startY, endX, endY, government);
    }

    public static ArrayList<Military> getEnemyOfLeft(int x, int y, Government government) {
        int startX = x - 5;
        int startY = y - 5;
        int endY = y + 5;
        Map map = GameController.getGame().getMap();
        if (x - 5 < 0) {
            startX = 0;
        }

        if (y - 5 < 0) {
            startY = 0;
        }
        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }
        return HumanController.getEnemiesOfArea(startX, startY, x, endY, government);
    }

    public static ArrayList<Military> getEnemyOfUp(int x, int y, Government government) {
        int startX = x - 5;
        int startY = y - 5;
        int endX = x + 5;
        Map map = GameController.getGame().getMap();
        if (x - 5 < 0) {
            startX = 0;
        }

        if (y - 5 < 0) {
            startY = 0;
        }

        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }
        return HumanController.getEnemiesOfArea(startX, startY, endX, y, government);
    }

    public static ArrayList<Military> getEnemyOfDown(int x, int y, Government government) {
        int startX = x - 5;
        int endX = x + 5;
        int endY = y + 5;
        Map map = GameController.getGame().getMap();
        if (x - 5 < 0) {
            startX = 0;
        }
        if (endX + 1 >= map.getWidth()) {
            endX = map.getWidth() - 1;
        }

        if (endY + 1 >= map.getLength()) {
            endY = map.getLength() - 1;
        }
        return HumanController.getEnemiesOfArea(startX, y, endX, endY, government);
    }

    public static ArrayList<Military> getEnemiesOfArea(int startX, int startY, int endX, int endY, Government government) {
        ArrayList<Military> enemies = new ArrayList<>();
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                enemies.addAll(MapController.getMilitariesOfOtherGovernment(i, j, government));
            }
        }
        return enemies;
    }

    public static void setState(String state, ArrayList<Military> militaries) {
        for (Military military : militaries) {
            if (military.getMove().getMoveState().equals(MoveStates.PATROL.getState())) {
                military.setMilitaryState(state);
            }
        }
    }
}
