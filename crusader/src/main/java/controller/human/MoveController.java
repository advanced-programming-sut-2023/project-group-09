package controller.human;

import controller.GameController;
import javafx.util.Pair;
import model.game.Map;
import model.human.Human;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class MoveController extends HumanController {
    public static boolean[][] checkArray;
    public static LinkedList<Pair<Integer, Integer>> getPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair, Human human) {
        Map map = GameController.getGame().getMap();
        int[][] wave = new int[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];

        ArrayList<Pair<Integer, Integer>> firstPairs = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> secondPairs;
        firstPairs.add(startPair);

        int depth = 2;
        int y = startPair.getKey();
        int x = startPair.getValue();
        wave[y][x] = 1;
        checkArray[y][x] = true;

        boolean receiveEnd = false;
        while (firstPairs.size() != 0 && !receiveEnd) {
            secondPairs = makeNextPairs(firstPairs, human);
            firstPairs = new ArrayList<>(secondPairs);

            for (Pair<Integer, Integer> pair : secondPairs) {
                y = pair.getKey();
                x = pair.getValue();
                if (checkArray[y][x]) {
                    firstPairs.remove(pair);
                    continue;
                }
                wave[y][x] = depth;
                checkArray[y][x] = true;
                if (y == endPair.getKey() && x == endPair.getValue()) {
                    receiveEnd = true;
                }
            }
            depth++;
        }
        return makePath(wave, startPair, endPair);
    }


    public static LinkedList<Pair<Integer, Integer>> makePath(int[][] wave, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        LinkedList<Pair<Integer, Integer>> result = new LinkedList<>();
        Pair<Integer, Integer> nextPair = new Pair<>(end.getKey(), end.getValue());
        result.add(nextPair);
        while (!Objects.equals(nextPair.getValue(), start.getValue()) || !Objects.equals(nextPair.getKey(), start.getKey())) {
            nextPair = findNextNode(nextPair, wave, end);
            if (nextPair == null) {
                return null;
            }
            result.addFirst(nextPair);
        }
        for (Pair<Integer, Integer> pair : result) {
            System.out.println(pair.getValue() + " " + pair.getKey());
        }

        return result;
    }

    public static double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static Pair<Integer, Integer> findNextNode(Pair<Integer, Integer> nextPair, int[][] wave, Pair<Integer, Integer> end) {
        int x = nextPair.getValue();
        int y = nextPair.getKey();

        int minDepth = 10000;
        double minDistance = 10000;
        if(wave[y][x] == 0){
            return null;
        }
        if (y != 0) {
            double distance = getDistance(end.getValue(), end.getKey(), x, y - 1);
            if (wave[y - 1][x] > 0 && (wave[y - 1][x] < minDepth || (wave[y - 1][x] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y - 1, x);
                minDepth = wave[y - 1][x];
                minDistance = distance;
            }
        }

        if (x != 0) {
            double distance = getDistance(end.getValue(), end.getKey(), x - 1, y);
            if (wave[y][x - 1] > 0 && (wave[y][x - 1] < minDepth || (wave[y][x - 1] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y, x - 1);
                minDepth = wave[y][x - 1];
                minDistance = distance;
            }
        }
        if (x != 0 && y != 0) {
            double distance = getDistance(end.getValue(), end.getKey(), x - 1, y - 1);
            if (wave[y - 1][x - 1] > 0 && (wave[y - 1][x - 1] < minDepth || (wave[y - 1][x - 1] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y - 1, x - 1);
                minDepth = wave[y - 1][x - 1];
                minDistance = distance;
            }
        }
        if (x != GameController.getGame().getMap().getWidth() - 1) {
            double distance = getDistance(end.getValue(), end.getKey(), x + 1, y);
            if (wave[y][x + 1] > 0 && (wave[y][x + 1] < minDepth || (wave[y][x + 1] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y, x + 1);
                minDepth = wave[y][x + 1];
                minDistance = distance;
            }
        }
        if (y != GameController.getGame().getMap().getWidth() - 1) {
            double distance = getDistance(end.getValue(), end.getKey(), x, y + 1);
            if (wave[y + 1][x] > 0 && (wave[y + 1][x] < minDepth || (wave[y + 1][x] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y + 1, x);
                minDepth = wave[y + 1][x];
                minDistance = distance;
            }
        }
        if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {

            double distance = getDistance(end.getValue(), end.getKey(), x + 1, y + 1);
            if (wave[y + 1][x + 1] > 0 && (wave[y + 1][x + 1] < minDepth || (wave[y + 1][x + 1] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y + 1, x + 1);
                minDepth = wave[y + 1][x + 1];
                minDistance = distance;

            }
        }
        if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
            double distance = getDistance(end.getValue(), end.getKey(), x - 1, y + 1);
            if (wave[y + 1][x - 1] > 0 && (wave[y + 1][x - 1] < minDepth || (wave[y + 1][x - 1] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y + 1, x - 1);
                minDepth = wave[y + 1][x - 1];
                minDistance = distance;
            }
        }
        if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
            double distance = getDistance(end.getValue(), end.getKey(), x + 1, y - 1);
            if (wave[y - 1][x + 1] > 0 && (wave[y - 1][x + 1] < minDepth || (wave[y - 1][x + 1] == minDepth && distance < minDistance))) {
                nextPair = new Pair<>(y - 1, x + 1);
                minDepth = wave[y - 1][x + 1];
                minDistance = distance;
            }
        }

        if (minDepth == 10000) {
            return null;
        }
        return nextPair;
    }

    public static ArrayList<Pair<Integer, Integer>> makeNextPairs( ArrayList<Pair<Integer, Integer>> firstPairs, Human human) {
        Map map = GameController.getGame().getMap();
        ArrayList<Pair<Integer, Integer>> secondPairs = new ArrayList<>();
        for (Pair<Integer, Integer> pair : firstPairs) {
            int y = pair.getKey();
            int x = pair.getValue();

            if (y != 0) {
                if (map.getTile(x, y - 1).isPassable(human) && !checkArray[y - 1][x]) {
                    secondPairs.add(new Pair<>(y - 1, x));
                }
            }

            if (x != 0 && !checkArray[y][x - 1]) {
                if (map.getTile(x - 1, y).isPassable(human)) {
                    secondPairs.add(new Pair<>(y, x - 1));
                }
            }
            if (x != 0 && y != 0) {
                if (map.getTile(x - 1, y - 1).isPassable(human) && !checkArray[y - 1][x - 1]) {
                    secondPairs.add(new Pair<>(y - 1, x - 1));
                }
            }
            if (x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y).isPassable(human) && !checkArray[y][x + 1]) {
                    secondPairs.add(new Pair<>(y, x + 1));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x, y + 1).isPassable(human) && !checkArray[y + 1][x]) {
                    secondPairs.add(new Pair<>(y + 1, x));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y + 1).isPassable(human) && !checkArray[y + 1][x + 1]) {
                    secondPairs.add(new Pair<>(y + 1, x + 1));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                if (map.getTile(x - 1, y + 1).isPassable(human) && !checkArray[y + 1][x - 1]) {
                    secondPairs.add(new Pair<>(y + 1, x - 1));
                }
            }
            if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y - 1).isPassable(human) && !checkArray[y - 1][x + 1]) {
                    secondPairs.add(new Pair<>(y - 1, x + 1));
                }
            }
        }
        return secondPairs;
    }

    public static LinkedList<Pair<Integer, Integer>> checkHasPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair) {
        return getPath(startPair, endPair, null);
    }

    public static LinkedList<Pair<Integer, Integer>> checkHasLadderPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair, LinkedList<Pair<Integer, Integer>> path) {
        if (path != null) {
            return path;
        }
        for (Military military : militaries) {
            if (military.isUsesLadder()) {
                return MoveController.getPath(startPair, endPair, military);
            }
        }
        return null;
    }

    public static LinkedList<Pair<Integer, Integer>> checkAssassinPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair, LinkedList<Pair<Integer, Integer>> path) {
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

    public static Pair<Integer, Integer> getStartPair() {
        Military firstMilitary = militaries.get(0);
        return new Pair<>(firstMilitary.getX(), firstMilitary.getY());
    }

    public static boolean checkPatrolPath(Pair<Integer, Integer> startPair, Pair<Integer, Integer> endPair) {
        LinkedList<Pair<Integer, Integer>> path = MoveController.checkHasPath(startPair, endPair);
        LinkedList<Pair<Integer, Integer>> assassinPath = checkHasLadderPath(startPair, endPair, path);
        LinkedList<Pair<Integer, Integer>> ladderPath = checkAssassinPath(startPair, endPair, path);
        if (path == null && assassinPath == null && ladderPath == null) {
            return false;
        }
        return true;
    }
}
