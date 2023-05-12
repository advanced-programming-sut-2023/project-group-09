package controller;

import controller.human.MoveController;
import model.activity.ToolAttack;
import model.activity.ToolMove;
import model.building.Building;
import model.game.Map;
import model.game.Tuple;
import model.tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;

public class ToolsController {
    public static boolean[][] checkArray;
    public static Tool tool;

    public static LinkedList<Tuple> getPathTools(Tuple startPair, Tuple endPair) {
        Map map = GameController.getGame().getMap();
        Tuple[][] wave = new Tuple[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];

        ArrayList<Tuple> firstPairs = new ArrayList<>();
        ArrayList<Tuple> secondPairs;


        int y = startPair.getY();
        int x = startPair.getX();
        checkArray[y][x] = true;
        firstPairs.add(startPair);

        boolean receiveEnd = false;
        while (firstPairs.size() != 0 && !receiveEnd) {
            secondPairs = makeNextPairsTools(firstPairs);
            firstPairs = new ArrayList<>();

            for (Tuple pair : secondPairs) {

                x = pair.getX();
                y = pair.getY();
                if (checkArray[y][x]) {
                    continue;
                }

                wave[y][x] = pair.getParentPair();
                checkArray[y][x] = true;
                firstPairs.add(pair);
                if (y == endPair.getY() && x == endPair.getX()) {
                    receiveEnd = true;
                }
            }
        }

        return MoveController.makePath(wave, startPair, endPair);
    }

    public static ArrayList<Tuple> makeNextPairsTools(ArrayList<Tuple> firstPairs) {
        Map map = GameController.getGame().getMap();
        ArrayList<Tuple> secondPairs = new ArrayList<>();
        for (Tuple pair : firstPairs) {
            int y = pair.getY();
            int x = pair.getX();
            if (y != 0) {
                if (map.getTile(x, y - 1).isPassable() && map.getTile(x, y - 1).getTool() == null && !checkArray[y - 1][x]) {
                    secondPairs.add(new Tuple(y - 1, x,pair));
                }
            }

            if (x != 0 && !checkArray[y][x - 1]) {
                if (map.getTile(x - 1, y).isPassable() && map.getTile(x - 1, y).getTool() == null && !checkArray[y][x - 1]) {
                    secondPairs.add(new Tuple(y, x - 1,pair));
                }
            }
            if (x != 0 && y != 0) {
                if (map.getTile(x - 1, y - 1).isPassable() && map.getTile(x - 1, y - 1).getTool() == null && !checkArray[y - 1][x - 1]) {
                    secondPairs.add(new Tuple(y - 1, x - 1,pair));
                }
            }
            if (x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y).isPassable() && map.getTile(x + 1, y).getTool() == null && !checkArray[y][x + 1]) {
                    secondPairs.add(new Tuple(y, x + 1, pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x, y + 1).isPassable() && map.getTile(x, y + 1).getTool() == null && !checkArray[y + 1][x]) {
                    secondPairs.add(new Tuple(y + 1, x, pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y + 1).isPassable() && map.getTile(x + 1, y + 1).getTool() == null && !checkArray[y + 1][x + 1]) {
                    secondPairs.add(new Tuple(y + 1, x + 1, pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                if (map.getTile(x - 1, y + 1).isPassable() && map.getTile(x - 1, y + 1).getTool() == null && !checkArray[y + 1][x - 1]) {
                    secondPairs.add(new Tuple(y + 1, x - 1, pair));
                }
            }
            if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y - 1).isPassable() && map.getTile(x + 1, y - 1).getTool() == null && !checkArray[y - 1][x + 1]) {
                    secondPairs.add(new Tuple(y - 1, x + 1, pair));
                }
            }
        }
        return secondPairs;
    }

    public static LinkedList<Tuple> getPathForBuilding(Tuple startPair, Building building, Tool tool) {
        Map map = GameController.getGame().getMap();
        Tuple[][] wave = new Tuple[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];
        Tuple endPair = null;

        ArrayList<Tuple> secondPairs;
        ArrayList<Tuple> firstPairs = new ArrayList<>();

        int y = startPair.getY();
        int x = startPair.getX();
        checkArray[y][x] = true;
        firstPairs.add(startPair);


        boolean receiveEnd = false;
        while (firstPairs.size() != 0 && !receiveEnd) {
            secondPairs = makeNextPairsTools(firstPairs);
            firstPairs = new ArrayList<>();

            for (Tuple pair : secondPairs) {
                y = pair.getY();
                x = pair.getX();
                if (checkArray[y][x]) {
                    continue;
                }
                wave[y][x] = pair.getParentPair();
                checkArray[y][x] = true;
                firstPairs.add(pair);
                if (building.getNeighborTiles().contains(pair)) {
                    endPair = pair;
                    receiveEnd = true;
                }
            }
        }
        if (endPair == null) {
            return null;
        }
        return MoveController.makePath(wave, startPair, endPair);
    }

    public static boolean patrolTool(int x1, int y1, int x2, int y2) {

        Tuple patrolPair = new Tuple(y2, x2);
        Tuple endPair = new Tuple(y1, x1);
        Tuple startPair = MoveController.getStartPair();
        boolean check = false;
        if (endPair.equals(startPair)){
            endPair = new Tuple(y2,x2);
            check = true;
        }

        MoveController.shouldCheckOtherPath();
        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);

        if (path == null) {
            return false;
        }

        if (!MoveController.checkPatrolPath(endPair, patrolPair) && !check) {
            return false;
        }

        ToolMove move = new ToolMove(tool.getX(), tool.getY(), endPair, true, tool);
        move.setPath(path);
        move.setMovePatrol(patrolPair);
        tool.setToolMove(move);
        move.setPatrolStart(check);
        return true;
    }

    public static void stop(Tool tool) {
        tool.setToolMove(null);
        tool.setToolAttack(new ToolAttack(tool));
    }
}
