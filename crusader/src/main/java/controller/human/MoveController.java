package controller.human;

import controller.GameController;
import controller.gamestructure.GameHumans;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.CastleBuilding;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.MainCastle;
import model.building.castlebuildings.Wall;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.Human;
import model.human.military.Military;
import model.tools.Tool;
import view.UnitMenu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoveController extends HumanController {
    public static boolean[][] checkArray;
    public static boolean checkLadderPath = true;
    public static boolean checkAssassinPath = true;
    public static boolean checkUsualPath = true;


    public static LinkedList<Tuple> getPath(Tuple startPair, Tuple endPair, Human human) {
        ArrayList<Tuple> firstPairs = new ArrayList<>();
        ArrayList<Tuple> secondPairs;

        Map map = GameController.getGame().getMap();
        Tuple[][] wave = new Tuple[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];


        startPair.setOverhead(setOverHeadOfCoordinate(startPair));
        int y = startPair.getY();
        int x = startPair.getX();
        checkArray[y][x] = true;
        startPair.setOverhead(checkIsPathOverhead(x, y, human, startPair));
        firstPairs.add(startPair);

        boolean receiveEnd = false;
        while (firstPairs.size() != 0 && !receiveEnd) {
            secondPairs = makeNextPairs(firstPairs, human);
            firstPairs = new ArrayList<>();

            for (Tuple pair : secondPairs) {

                x = pair.getX();
                y = pair.getY();
                if (checkArray[y][x]) {
                    continue;
                }

                pair.setOverhead(checkIsPathOverhead(x, y, human, pair));
                wave[y][x] = pair.getParentPair();
                checkArray[y][x] = true;
                firstPairs.add(pair);
                if (y == endPair.getY() && x == endPair.getX()) {
                    receiveEnd = true;
                }
            }
        }
        return makePath(wave, startPair, endPair);
    }

    public static LinkedList<Tuple> getPathForBuilding(Tuple startPair, Building building, Human human) {

        Tuple endPair = null;
        Map map = GameController.getGame().getMap();
        Tuple[][] wave = new Tuple[map.getWidth()][map.getWidth()];
        checkArray = new boolean[map.getWidth()][map.getWidth()];
        ArrayList<Tuple> secondPairs;
        ArrayList<Tuple> firstPairs = new ArrayList<>();

        int y = startPair.getY();
        int x = startPair.getX();
        checkArray[y][x] = true;
        startPair.setOverhead(setOverHeadOfCoordinate(startPair));
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
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (wave[i][j] == null) {
                    System.out.print("null      ");
                    continue;
                }
                System.out.format("(%3d,%3d) ", wave[i][j].getX(), wave[i][j].getY());
            }
            System.out.println();
        }
        System.out.println();
        return makePath(wave, startPair, endPair);
    }

    //detect path from wave array
    public static LinkedList<Tuple> makePath(Tuple[][] wave, Tuple start, Tuple end) {
        LinkedList<Tuple> result = new LinkedList<>();
        Tuple nextPair = end;
        result.add(nextPair);
        while (!nextPair.equals(start)) {
            nextPair = findNextNode(nextPair, wave);
            if (nextPair == null) {
                return null;
            }

            result.addFirst(nextPair);
        }
        return result;
    }


    public static double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    //this used in makePath and detect next node
    public static Tuple findNextNode(Tuple nextPair, Tuple[][] wave) {
        int x = nextPair.getX();
        int y = nextPair.getY();
        if (wave[y][x] == null) {
            return null;
        }

        return wave[y][x];
    }

    //this used in getPath to make wave array
    public static ArrayList<Tuple> makeNextPairs(ArrayList<Tuple> firstPairs, Human human) {
        Map map = GameController.getGame().getMap();
        ArrayList<Tuple> secondPairs = new ArrayList<>();
        for (Tuple pair : firstPairs) {
            int y = pair.getY();
            int x = pair.getX();
            boolean isOverHead = pair.isOverhead();
            Tile tile = map.getTile(x, y);
            if (checkOverHead(tile.getBuilding())) {
                if (y != 0) {
                    if (map.getTile(x, y - 1).isPassable(human, !isOverHead) && !checkArray[y - 1][x]) {
                        secondPairs.add(new Tuple(y - 1, x, !isOverHead, pair));
                    }
                }
                if (x != 0 && !checkArray[y][x - 1]) {
                    if (map.getTile(x - 1, y).isPassable(human, !isOverHead) && !checkArray[y][x - 1]) {
                        secondPairs.add(new Tuple(y, x - 1, !isOverHead, pair));
                    }
                }
                if (x != 0 && y != 0) {
                    if (map.getTile(x - 1, y - 1).isPassable(human, !isOverHead) && !checkArray[y - 1][x - 1]) {
                        secondPairs.add(new Tuple(y - 1, x - 1, !isOverHead, pair));
                    }
                }
                if (x != GameController.getGame().getMap().getWidth() - 1) {
                    if (map.getTile(x + 1, y).isPassable(human, !isOverHead) && !checkArray[y][x + 1]) {
                        secondPairs.add(new Tuple(y, x + 1, !isOverHead, pair));
                    }
                }
                if (y != GameController.getGame().getMap().getWidth() - 1) {
                    if (map.getTile(x, y + 1).isPassable(human, !isOverHead) && !checkArray[y + 1][x]) {
                        secondPairs.add(new Tuple(y + 1, x, !isOverHead, pair));
                    }
                }
                if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                    if (map.getTile(x + 1, y + 1).isPassable(human, !isOverHead) && !checkArray[y + 1][x + 1]) {
                        secondPairs.add(new Tuple(y + 1, x + 1, !isOverHead, pair));
                    }
                }
                if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                    if (map.getTile(x - 1, y + 1).isPassable(human, !isOverHead) && !checkArray[y + 1][x - 1]) {
                        secondPairs.add(new Tuple(y + 1, x - 1, !isOverHead, pair));
                    }
                }
                if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                    if (map.getTile(x + 1, y - 1).isPassable(human, !isOverHead) && !checkArray[y - 1][x + 1]) {
                        secondPairs.add(new Tuple(y - 1, x + 1, !isOverHead, pair));
                    }
                }
            }
            if (y != 0) {
                if (map.getTile(x, y - 1).isPassable(human, isOverHead) && !checkArray[y - 1][x]) {
                    secondPairs.add(new Tuple(y - 1, x, isOverHead, pair));
                }
            }
            if (x != 0 && !checkArray[y][x - 1]) {
                if (map.getTile(x - 1, y).isPassable(human, isOverHead) && !checkArray[y][x - 1]) {
                    secondPairs.add(new Tuple(y, x - 1, isOverHead, pair));
                }
            }
            if (x != 0 && y != 0) {
                if (map.getTile(x - 1, y - 1).isPassable(human, isOverHead) && !checkArray[y - 1][x - 1]) {
                    secondPairs.add(new Tuple(y - 1, x - 1, isOverHead, pair));
                }
            }
            if (x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y).isPassable(human, isOverHead) && !checkArray[y][x + 1]) {
                    secondPairs.add(new Tuple(y, x + 1, isOverHead, pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x, y + 1).isPassable(human, isOverHead) && !checkArray[y + 1][x]) {
                    secondPairs.add(new Tuple(y + 1, x, isOverHead, pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y + 1).isPassable(human, isOverHead) && !checkArray[y + 1][x + 1]) {
                    secondPairs.add(new Tuple(y + 1, x + 1, isOverHead, pair));
                }
            }
            if (y != GameController.getGame().getMap().getWidth() - 1 && x != 0) {
                if (map.getTile(x - 1, y + 1).isPassable(human, isOverHead) && !checkArray[y + 1][x - 1]) {
                    secondPairs.add(new Tuple(y + 1, x - 1, isOverHead, pair));
                }
            }
            if (y != 0 && x != GameController.getGame().getMap().getWidth() - 1) {
                if (map.getTile(x + 1, y - 1).isPassable(human, isOverHead) && !checkArray[y - 1][x + 1]) {
                    secondPairs.add(new Tuple(y - 1, x + 1, isOverHead, pair));
                }
            }
        }
        return secondPairs;
    }

    public static boolean checkOverHead(Building building) {
        if (building == null) {
            return false;
        }
        if (building instanceof Gatehouse gatehouse && gatehouse.isOpen() && !gatehouse.getName().equals("drawBridge"))
            return true;
        if (building instanceof MainCastle) return true;
        return false;
    }

    public static void shouldCheckOtherPath() {

        MoveController.checkUsualPath = true;
        MoveController.checkLadderPath = true;
        MoveController.checkAssassinPath = true;
        if (UnitMenu.type != null) {
            String type = UnitMenu.type;
            Military military = GameHumans.getUnit(type);
            if (military.isUsesLadder() && !military.getName().equals("ladderman")) {
                checkAssassinPath = false;
                checkUsualPath = false;
                return;
            }
            if (type.equals("assassin")) {
                checkLadderPath = false;
                checkUsualPath = false;
                return;
            }
            checkAssassinPath = false;
            checkLadderPath = false;
            return;
        }

        int countOfUseLadder = 0;
        int countOfAssassin = 0;

        for (Military military : militaries) {
            if (military.isUsesLadder() && !military.getName().equals("ladderman")) {
                countOfUseLadder++;
            }
            if (military.getName().equals("assassin")) {
                countOfAssassin++;
            }
        }

        if (countOfAssassin == militaries.size()) {
            checkLadderPath = false;
            checkUsualPath = false;
            return;
        }
        if (countOfUseLadder == militaries.size()) {
            checkAssassinPath = false;
            checkUsualPath = false;
            return;
        }

        if (countOfAssassin == 0) {
            checkAssassinPath = false;
        }
        if (countOfUseLadder == 0) {
            checkLadderPath = false;
        }
    }

    //find path for usual troop
    public static LinkedList<Tuple> checkHasPath(Tuple startPair, Tuple endPair) {
        if (checkUsualPath) {
            return getPath(startPair, endPair, null);
        }
        return null;
    }

    //find path for troops who can climb ladder
    public static LinkedList<Tuple> checkHasLadderPath(Tuple startPair, Tuple endPair, LinkedList<Tuple> path) {
        if (path != null) {
            checkLadderPath = true;
            return null;
        }
        if (checkLadderPath) {

            for (Military military : militaries) {
                if (military.isUsesLadder() && !military.getName().equals("ladderman")) {
                    return getPath(startPair, endPair, military);
                }
            }

        }
        return null;
    }

    //find path for assassin
    public static LinkedList<Tuple> checkAssassinPath(Tuple startPair, Tuple endPair, LinkedList<Tuple> path) {
        if (path != null) {
            checkAssassinPath = true;
            return null;
        }
        if (checkAssassinPath) {
            for (Military military : militaries) {
                if (military.getName().equals("assassin")) {
                    return getPath(startPair, endPair, military);
                }
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
        Military firstMilitary = HumanController.militaries.get(0);
        return new Tuple(firstMilitary.getY(), firstMilitary.getX());
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
        if (tool != null && tool.getName().equals("siegeTower") && !tool.isCanMove()) {
            return !tuple.isOverhead();
        }
        if (military.isUsesLadder() && !military.getName().equals("ladderman")) {
            List<Military> militaries = getActiveLadderMans(x, y, military.getGovernment());
            if (militaries.size() != 0) {
                return !tuple.isOverhead();
            }
        }

        return tuple.isOverhead();
    }

    public static List<Military> getActiveLadderMans(int x, int y, Government government) {
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

    public static boolean setOverHeadOfCoordinate(Tuple tuple) {
        Tile tile = GameController.getGame().getMap().getTile(tuple.getX(), tuple.getY());
        Building building = tile.getBuilding();
        return building instanceof CastleBuilding castleBuilding && !castleBuilding.getName().equals("drawBridge");
    }
}
