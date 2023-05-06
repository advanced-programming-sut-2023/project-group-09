package controller;

import controller.human.MoveController;
import model.building.Building;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;

public class ToolsController {
    public static boolean[][] checkArray;
    Tool tool;

    public static LinkedList<Tuple> getPathTools(Tuple startPair, Tuple endPair) {
        ArrayList<Tuple> firstPairs = new ArrayList<>();
        ArrayList<Tuple> secondPairs;

        Map map = GameController.getGame().getMap();
        int[][] wave = new int[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];

        int depth = 2;
        int y = startPair.getY();
        int x = startPair.getX();
        wave[y][x] = 1;
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

                wave[y][x] = depth;
                checkArray[y][x] = true;
                firstPairs.add(pair);
                if (y == endPair.getY() && x == endPair.getX()) {
                    receiveEnd = true;
                }
            }
            depth++;
        }
        return MoveController.makePath(wave, startPair, endPair);
    }

    public static ArrayList<Tuple> makeNextPairsTools(ArrayList<Tuple> firstPairs) {
        Map map = GameController.getGame().getMap();
        ArrayList<Tuple> secondPairs = new ArrayList<>();
        for (Tuple pair : firstPairs) {
            int y = pair.getY();
            int x = pair.getX();
            boolean isOverHead = pair.isOverhead();
            if (y != 0) {
                if (map.getTile(x, y - 1).isPassable() && !checkArray[y - 1][x]) {
                    secondPairs.add(new Tuple(y - 1, x));
                }
            }

            if (x != 0 && !checkArray[y][x - 1]) {
                if (map.getTile(x - 1, y).isPassable()) {
                    secondPairs.add(new Tuple(y, x - 1, isOverHead));
                }
            }
            if (x != 0 && y != 0) {
                if (map.getTile(x - 1, y - 1).isPassable() && !checkArray[y - 1][x - 1]) {
                    secondPairs.add(new Tuple(y - 1, x - 1));
                }
            }
            if (x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y).isPassable() && !checkArray[y][x + 1]) {
                    secondPairs.add(new Tuple(y, x + 1, isOverHead));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x, y + 1).isPassable() && !checkArray[y + 1][x]) {
                    secondPairs.add(new Tuple(y + 1, x, isOverHead));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y + 1).isPassable() && !checkArray[y + 1][x + 1]) {
                    secondPairs.add(new Tuple(y + 1, x + 1, isOverHead));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                if (map.getTile(x - 1, y + 1).isPassable() && !checkArray[y + 1][x - 1]) {
                    secondPairs.add(new Tuple(y + 1, x - 1, isOverHead));
                }
            }
            if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y - 1).isPassable() && !checkArray[y - 1][x + 1]) {
                    secondPairs.add(new Tuple(y - 1, x + 1, isOverHead));
                }
            }
        }
        return secondPairs;
    }

    public static LinkedList<Tuple> getPathForBuilding(Tuple startPair, Building building, Tool tool) {
        ArrayList<Tuple> secondPairs;
        ArrayList<Tuple> firstPairs = new ArrayList<>();
        Map map = GameController.getGame().getMap();
        int[][] wave = new int[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];
        Tuple endPair = null;

        int depth = 2;
        int y = startPair.getY();
        int x = startPair.getX();
        wave[y][x] = 1;
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

                wave[y][x] = depth;
                checkArray[y][x] = true;
                firstPairs.add(pair);
                Tile tile = map.getTile(x, y);
                if (building.getNeighborTiles().contains(tile)) {
                    endPair = pair;
                    receiveEnd = true;
                }
            }
            depth++;
        }
        if (endPair == null) {
            return null;
        }
        return MoveController.makePath(wave, startPair, endPair);
    }
}
