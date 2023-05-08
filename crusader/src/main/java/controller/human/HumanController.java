package controller.human;


import enumeration.MoveStates;
import model.activity.Move;
import model.building.Building;
import model.building.castlebuildings.Wall;
import model.game.Tuple;
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
            if (military.canAirAttack() && military.getAttack().isInRange(tool.getX(),tool.getY(),military.getShootingRange())) {
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

    public static void setState(String state, ArrayList<Military> militaries) {
        for (Military military : militaries) {
            if (military.getMove().getMoveState().equals(MoveStates.PATROL.getState())) {
                military.setMilitaryState(state);
            }
        }
    }
}
