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
            boolean isOverHead = pair.isOverhead();
            if (y != 0) {
                if (map.getTile(x, y - 1).isPassable() && !checkArray[y - 1][x]) {
                    secondPairs.add(new Tuple(y - 1, x));
                }
            }

            if (x != 0 && !checkArray[y][x - 1]) {
                if (map.getTile(x - 1, y).isPassable()) {
                    secondPairs.add(new Tuple(y, x - 1, isOverHead,pair));
                }
            }
            if (x != 0 && y != 0) {
                if (map.getTile(x - 1, y - 1).isPassable() && !checkArray[y - 1][x - 1]) {
                    secondPairs.add(new Tuple(y - 1, x - 1));
                }
            }
            if (x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y).isPassable() && !checkArray[y][x + 1]) {
                    secondPairs.add(new Tuple(y, x + 1, isOverHead,pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x, y + 1).isPassable() && !checkArray[y + 1][x]) {
                    secondPairs.add(new Tuple(y + 1, x, isOverHead,pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y + 1).isPassable() && !checkArray[y + 1][x + 1]) {
                    secondPairs.add(new Tuple(y + 1, x + 1, isOverHead,pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                if (map.getTile(x - 1, y + 1).isPassable() && !checkArray[y + 1][x - 1]) {
                    secondPairs.add(new Tuple(y + 1, x - 1, isOverHead,pair));
                }
            }
            if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y - 1).isPassable() && !checkArray[y - 1][x + 1]) {
                    secondPairs.add(new Tuple(y - 1, x + 1, isOverHead,pair));
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
}
