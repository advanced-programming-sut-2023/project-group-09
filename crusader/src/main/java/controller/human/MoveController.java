package controller.human;

import controller.GameController;
import controller.MapController;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.MainCastle;
import model.building.castlebuildings.Wall;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.human.military.Military;
import model.tools.Tool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MoveController extends HumanController {
    public static boolean[][] checkArray;

    public static LinkedList<Tuple> getPath(Tuple startPair, Tuple endPair, Human human) {
        Map map = GameController.getGame().getMap();
        int[][] wave = new int[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];

        ArrayList<Tuple> firstPairs = new ArrayList<>();
        ArrayList<Tuple> secondPairs;


        int depth = 2;
        int y = startPair.getY();
        int x = startPair.getX();
        wave[y][x] = 1;
        checkArray[y][x] = true;
        startPair.setOverhead(checkIsPathOverhead(x, y, human, startPair));
        firstPairs.add(startPair);


        boolean receiveEnd = false;
        while (firstPairs.size() != 0 && !receiveEnd) {
            secondPairs = makeNextPairs(firstPairs, human);
            firstPairs = new ArrayList<>();

            for (Tuple pair : secondPairs) {
                y = pair.getY();
                x = pair.getX();
                if (checkArray[y][x]) {
                    continue;
                }

                pair.setOverhead(checkIsPathOverhead(x, y, human, pair));
                wave[y][x] = depth;
                checkArray[y][x] = true;
                firstPairs.add(pair);
                if (y == endPair.getY() && x == endPair.getX()) {
                    receiveEnd = true;
                }
            }
            depth++;
        }
        return makePath(wave, startPair, endPair);
    }

    public static LinkedList<Tuple> getPathForBuilding(Tuple startPair, Building building, Human human) {

        Map map = GameController.getGame().getMap();
        int[][] wave = new int[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];
        Tuple endPair = null;

        ArrayList<Tuple> secondPairs;
        ArrayList<Tuple> firstPairs = new ArrayList<>();

        int depth = 2;
        int y = startPair.getY();
        int x = startPair.getX();
        wave[y][x] = 1;
        checkArray[y][x] = true;
        startPair.setOverhead(checkIsPathOverhead(x, y, human, startPair));
        firstPairs.add(startPair);


        boolean receiveEnd = false;
        while (firstPairs.size() != 0 && !receiveEnd) {
            secondPairs = makeNextPairs(firstPairs, human);
            firstPairs = new ArrayList<>();

            for (Tuple pair : secondPairs) {
                y = pair.getY();
                x = pair.getX();
                if (checkArray[y][x]) {
                    continue;
                }

                pair.setOverhead(checkIsPathOverhead(x, y, human, pair));
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
        return makePath(wave, startPair, endPair);
    }

    //detect path from wave array
    public static LinkedList<Tuple> makePath(int[][] wave, Tuple start, Tuple end) {
        LinkedList<Tuple> result = new LinkedList<>();
        Tuple nextPair = new Tuple(end.getY(), end.getX());
        result.add(nextPair);
        while (!Objects.equals(nextPair.getX(), start.getX()) || !Objects.equals(nextPair.getY(), start.getY())) {
            nextPair = findNextNode(nextPair, wave, end);
            if (nextPair == null) {
                return null;
            }
            result.addFirst(nextPair);
        }
        for (Tuple pair : result) {
            System.out.println(pair.getX() + " " + pair.getY());
        }

        return result;
    }


    public static double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    //this used in makePath and detect next node
    public static Tuple findNextNode(Tuple nextPair, int[][] wave, Tuple end) {
        int x = nextPair.getX();
        int y = nextPair.getY();

        int minDepth = 10000;
        double minDistance = 10000;
        if (wave[y][x] == 0) {
            return null;
        }
        if (y != 0) {
            double distance = getDistance(end.getX(), end.getY(), x, y - 1);
            if (wave[y - 1][x] > 0 && (wave[y - 1][x] < minDepth || (wave[y - 1][x] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y - 1, x);
                minDepth = wave[y - 1][x];
                minDistance = distance;
            }
        }

        if (x != 0) {
            double distance = getDistance(end.getX(), end.getY(), x - 1, y);
            if (wave[y][x - 1] > 0 && (wave[y][x - 1] < minDepth || (wave[y][x - 1] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y, x - 1);
                minDepth = wave[y][x - 1];
                minDistance = distance;
            }
        }
        if (x != 0 && y != 0) {
            double distance = getDistance(end.getX(), end.getY(), x - 1, y - 1);
            if (wave[y - 1][x - 1] > 0 && (wave[y - 1][x - 1] < minDepth || (wave[y - 1][x - 1] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y - 1, x - 1);
                minDepth = wave[y - 1][x - 1];
                minDistance = distance;
            }
        }
        if (x != GameController.getGame().getMap().getWidth() - 1) {
            double distance = getDistance(end.getX(), end.getY(), x + 1, y);
            if (wave[y][x + 1] > 0 && (wave[y][x + 1] < minDepth || (wave[y][x + 1] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y, x + 1);
                minDepth = wave[y][x + 1];
                minDistance = distance;
            }
        }
        if (y != GameController.getGame().getMap().getWidth() - 1) {
            double distance = getDistance(end.getX(), end.getY(), x, y + 1);
            if (wave[y + 1][x] > 0 && (wave[y + 1][x] < minDepth || (wave[y + 1][x] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y + 1, x);
                minDepth = wave[y + 1][x];
                minDistance = distance;
            }
        }
        if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {

            double distance = getDistance(end.getX(), end.getY(), x + 1, y + 1);
            if (wave[y + 1][x + 1] > 0 && (wave[y + 1][x + 1] < minDepth || (wave[y + 1][x + 1] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y + 1, x + 1);
                minDepth = wave[y + 1][x + 1];
                minDistance = distance;

            }
        }
        if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
            double distance = getDistance(end.getX(), end.getY(), x - 1, y + 1);
            if (wave[y + 1][x - 1] > 0 && (wave[y + 1][x - 1] < minDepth || (wave[y + 1][x - 1] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y + 1, x - 1);
                minDepth = wave[y + 1][x - 1];
                minDistance = distance;
            }
        }
        if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
            double distance = getDistance(end.getX(), end.getY(), x + 1, y - 1);
            if (wave[y - 1][x + 1] > 0 && (wave[y - 1][x + 1] < minDepth || (wave[y - 1][x + 1] == minDepth && distance < minDistance))) {
                nextPair = new Tuple(y - 1, x + 1);
                minDepth = wave[y - 1][x + 1];
                minDistance = distance;
            }
        }

        if (minDepth == 10000) {
            return null;
        }
        return nextPair;
    }

    //this used in getPath to make wave array
    public static ArrayList<Tuple> makeNextPairs(ArrayList<Tuple> firstPairs, Human human) {
        Map map = GameController.getGame().getMap();
        ArrayList<Tuple> secondPairs = new ArrayList<>();
        for (Tuple pair : firstPairs) {
            int y = pair.getY();
            int x = pair.getX();
            boolean isOverHead = pair.isOverhead();
            if (y != 0) {
                if (map.getTile(x, y - 1).isPassable(human, isOverHead) && !checkArray[y - 1][x]) {
                    secondPairs.add(new Tuple(y - 1, x, isOverHead));
                }
            }

            if (x != 0 && !checkArray[y][x - 1]) {
                if (map.getTile(x - 1, y).isPassable(human, isOverHead)) {
                    secondPairs.add(new Tuple(y, x - 1, isOverHead));
                }
            }
            if (x != 0 && y != 0) {
                if (map.getTile(x - 1, y - 1).isPassable(human, isOverHead) && !checkArray[y - 1][x - 1]) {
                    secondPairs.add(new Tuple(y - 1, x - 1, isOverHead));
                }
            }
            if (x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y).isPassable(human, isOverHead) && !checkArray[y][x + 1]) {
                    secondPairs.add(new Tuple(y, x + 1, isOverHead));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x, y + 1).isPassable(human, isOverHead) && !checkArray[y + 1][x]) {
                    secondPairs.add(new Tuple(y + 1, x, isOverHead));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y + 1).isPassable(human, isOverHead) && !checkArray[y + 1][x + 1]) {
                    secondPairs.add(new Tuple(y + 1, x + 1, isOverHead));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                if (map.getTile(x - 1, y + 1).isPassable(human, isOverHead) && !checkArray[y + 1][x - 1]) {
                    secondPairs.add(new Tuple(y + 1, x - 1, isOverHead));
                }
            }
            if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y - 1).isPassable(human, isOverHead) && !checkArray[y - 1][x + 1]) {
                    secondPairs.add(new Tuple(y - 1, x + 1, isOverHead));
                }
            }
        }
        return secondPairs;
    }

    //find path for usual troop
    public static LinkedList<Tuple> checkHasPath(Tuple startPair, Tuple endPair) {
        return getPath(startPair, endPair, null);
    }

    //find path for troops who can climb ladder
    public static LinkedList<Tuple> checkHasLadderPath(Tuple startPair, Tuple endPair, LinkedList<Tuple> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.isUsesLadder() && !military.getName().equals("ladderman")) {
                return MoveController.getPath(startPair, endPair, military);
            }
        }
        return null;
    }

    //find path for assassin
    public static LinkedList<Tuple> checkAssassinPath(Tuple startPair, Tuple endPair, LinkedList<Tuple> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.getName().equals("assassin")) {
                return MoveController.getPath(startPair, endPair, military);
            }
        }
        return null;
    }


    public static LinkedList<Tuple> checkHasPathForBuilding(Tuple startPair, Building building) {
        return getPathForBuilding(startPair, building, null);
    }

    //find path for troops who can climb ladder
    public static LinkedList<Tuple> checkHasLadderPathForBuilding(Tuple startPair, Building building, LinkedList<Tuple> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.isUsesLadder() && !military.getName().equals("ladderman")) {
                return MoveController.getPathForBuilding(startPair, building, military);
            }
        }
        return null;
    }

    //find path for assassin
    public static LinkedList<Tuple> checkAssassinPathForBuilding(Tuple startPair, Building building, LinkedList<Tuple> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.getName().equals("assassin")) {
                return MoveController.getPathForBuilding(startPair, building, military);
            }
        }
        return null;
    }


    public static Tuple getStartPair() {
        Military firstMilitary = militaries.get(0);
        return new Tuple(firstMilitary.getX(), firstMilitary.getY());
    }

    public static boolean checkPatrolPath(Tuple startPair, Tuple endPair) {
        LinkedList<Tuple> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Tuple> assassinPath = checkHasLadderPath(startPair, endPair, path);
        LinkedList<Tuple> ladderPath = checkAssassinPath(startPair, endPair, path);
        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        return true;
    }

    public static boolean checkIsPathOverhead(int x, int y, Human human, Tuple tuple) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        Building building = tile.getBuilding();
        if (building instanceof Wall wall && wall.getHeight() == 1) {
            return !tuple.isOverhead();
        }
        if (building instanceof Gatehouse gatehouse && gatehouse.isOpen() && !gatehouse.getName().equals("drawBridge")) {
            return true;
        }
        if (building instanceof MainCastle) {
            return true;
        }
        if (!(human instanceof Military military)) {
            return tuple.isOverhead();
        }
        Tool tool = tile.getTool();
        if( tool != null && tool.getName().equals("siegeTower") && !tool.isCanMove()){
            return !tuple.isOverhead();
        }
        if (!military.isUsesLadder() || military.getName().equals("ladderman")){
            List<Military> militaries = getActiveLadderMans(x, y, military.getGovernment());
            if (militaries.size() != 0 && military.isUsesLadder()) {
                return !tuple.isOverhead();
            }
        }

        return tuple.isOverhead();
    }

    public static List<Military> getActiveLadderMans(int x, int y, Government government){
        ArrayList<Military> militaries = new ArrayList<>();
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        for (Military military : tile.getMilitaries()) {
            String color = military.getGovernment().getColor();
            if (color.equals(government.getColor()) && military.getName().equals("ladderman") && military.isUsesLadder()) {
                militaries.add(military);
            }
        }
        return militaries;
    }


}
