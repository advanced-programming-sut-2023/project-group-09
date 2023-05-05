package controller;

import controller.gamestructure.GameHumans;
import controller.human.HumanController;
import enumeration.MilitaryStates;
import javafx.util.Pair;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.Wall;
import model.game.Game;
import model.game.Map;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Military;
import view.UnitMenu;

import java.util.*;

public class GameController {
    private static Game game;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameController.game = game;
    }

    public static String selectUnit(int x, int y, String type, Scanner scanner) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        ArrayList<Military> militaries;
        if (type == null) {
            militaries = MapController.getMilitariesOfGovernment(x - 1, y - 1, game.getCurrentGovernment());
        } else if (GameHumans.getUnit(type) == null) {
            return "invalid type!";
        } else {
            militaries = MapController.getOneTypeOfMilitariesOfGovernment(x - 1, y - 1, type, game.getCurrentGovernment());
        }

        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }
        HumanController.militaries = militaries;
        UnitMenu.x = x - 1;
        UnitMenu.y = y - 1;
        UnitMenu.run(scanner);
        return "";
    }

    public static String moveUnit(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Tuple destination = new Tuple(y - 1, x - 1);
        boolean check = HumanController.move(destination);
        if (!check) {
            return "can't move unit no path to destination!";
        }
        return "unit(s) moved successfully!";
    }

    public static String patrolUnit(int x1, int y1, int x2, int y2) {
        String message = validatePatrol(x1, y1, x2, y2);
        if (message != null) {
            return message;
        }

        boolean check = HumanController.patrolUnit(x1 - 1, y1 - 1, x2 - 1, y2 - 1);
        if (!check) {
            return "can't start patrol, no path to destination!";
        }
        return "patrol started successfully!";
    }
    public static String deactivatePatrolUnit() {
        boolean check = HumanController.deactivatePatrol();
        if (!check) {
            return "no troop is patrolling!";
        }
        return "patrol deactivate successfully!";
    }
    public static String setStateOfMilitary(int x, int y, String state) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }

        ArrayList<Military> militaries = MapController.getMilitariesOfGovernment(x - 1, y - 1, game.getCurrentGovernment());
        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }


        if (state.equals(MilitaryStates.STAND_GROUND.getState())) {
            HumanController.setState(MilitaryStates.STAND_GROUND.getState(), militaries);
        }

        if (state.equals(MilitaryStates.DEFENSIVE_STANCE.getState())) {
            HumanController.setState(MilitaryStates.DEFENSIVE_STANCE.getState(), militaries);
        }

        if (state.equals(MilitaryStates.AGGRESSIVE_STANCE.getState())) {
            HumanController.setState(MilitaryStates.AGGRESSIVE_STANCE.getState(), militaries);
        }

        return "invalid state!";
    }

    public static String attackEnemy(int x, int y, Scanner scanner) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Military enemy = UnitMenu.getEnemy(x, y, scanner);
        if (enemy == null) {
            return "your input is not valid please try again later!";
        }
        boolean canAttack = HumanController.attack(enemy);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack start successfully!";
    }

    public static String airAttack(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        List<Military> enemies = MapController.getMilitariesOfOtherGovernment(x, y, GameController.getGame().getCurrentGovernment());
        if (enemies.size() == 0) {
            return "there is no enemy in this position!";
        }

        boolean canAttack = HumanController.airAttack(x,y,enemies);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack start successfully!";
    }

    public static String attackBuilding(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Building building = game.getMap().getTile(x,y).getBuilding();
        if (building == null) {
            return "no building in this place";
        }
        boolean canAttack = HumanController.attack(building);
        if (!canAttack) {
            return "can't attack to enemy with this type of unit or position!";
        }
        return "attack start successfully!";
    }

    public static String airAttackBuilding(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Building building = game.getMap().getTile(x,y).getBuilding();
        if (building == null) {
            return "no building in this place";
        }

        boolean canAttack = HumanController.airAttack(building);
        if (!canAttack) {
            return "can't attack with this type of unit or position!";
        }
        return "attack start successfully!";
    }
    public static String pourOil(String direction) {
        return "";
    }

    public static String digTunnel(int x, int y) {
        return "";
    }

    public static String buildEquipment(int equipmentNumber, int x, int y) {
        return "";
    }

    public static String disbandUnit() {
        return "";
    }

    public static String dropBuilding(int x, int y, String type) {
        return "";
    }

    public static String selectBuilding(int x, int y) {
        return "";
    }


    public static String changeTurn() {
        return "";
    }


    public static Building getInstanceOfBuilding(int x, int y, String typeOfBuilding) {
        return null;
        // TODO: this method must check (x, y) and typeOfBuilding and returned correct value.
    }

    public static String dropCastleBuildings(int x, int y, String type) {
        return ""; // *********************************
    }

    public static String dropUnit(int x, int y, String type, int count) {
        return ""; // *********************************
    }

    public static String showMap(int x, int y) {
        Map map = game.getMap();
        if (y - 3 < 0) y = 3;
        else if (y + 3 >= map.getLength()) y = map.getLength() - 1;
        if (x - 9 < 0) x = 9;
        else if (x + 9 >= map.getWidth()) x = map.getWidth() - 1;
        game.setCurrentMapX(x);
        game.setCurrentMapY(y);

        String result = "";
        result += "-";
        for (int i = 0; i < 19 * 6; i++) {
            result += "-";
        }
        result += "\n";
        for (int i = y - 3; i <= y + 3; i++) {
            for (int j = 0; j < 2; j++) {
                result += "|";
                for (int k = x - 9; k <= x + 9; k++) {
                    for (int l = 0; l < 5; l++) {
                        Tile tile = map.getTile(k, i);
                        String sign = " ";
                        if (tile.getMilitaries().size() != 0) {
                            HashMap<Government, Integer> numberOfMilitariesOnTile = new HashMap<>();
                            for (int m = 0; m < game.getGovernments().size(); m++) {
                                numberOfMilitariesOnTile.put(game.getGovernments().get(m), 0);
                            }
                            for (int m = 0; m < tile.getMilitaries().size(); m++) {
                                Military military = tile.getMilitaries().get(m);
                                numberOfMilitariesOnTile.replace(military.getGovernment(), numberOfMilitariesOnTile.get(military.getGovernment()) + 1);
                            }
                            int max = Collections.max(numberOfMilitariesOnTile.values());
                            int numberOfMax = 0;
                            Government maxMilitariesGovernment = null;
                            for (Government government : numberOfMilitariesOnTile.keySet()) {
                                if (numberOfMilitariesOnTile.get(government) == max) {
                                    numberOfMax++;
                                    maxMilitariesGovernment = government;
                                }
                            }
                            if (numberOfMax == 1) sign = maxMilitariesGovernment.getColorRgb() + "S";
                            else sign = "S";
                        } else if (tile.getBuilding() != null && !(tile.getBuilding() instanceof Wall))
                            sign = tile.getBuilding().getGovernment().getColorRgb() + "B";
                        else if (tile.getBuilding() != null && tile.getBuilding() instanceof Wall)
                            sign = tile.getBuilding().getGovernment().getColorRgb() + "W";
                        else if (tile.getTree() != null) sign = "T";
                        else if (tile.getRockDirection() != null) sign = "R";
                        result += tile.getTexture().getColor() + sign + "\u001B[0m";
                    }
                    result += "|";
                }
                result += "\n";
            }
            result += "-";
            for (int j = 0; j < 19 * 6; j++) {
                result += "-";
            }
            result += "\n";
        }

        return result;
    }

    public static String showPreviewOfMap(Map map) {
        String result = "";
        for (int k = 1; k < map.getLength(); k++) {
            for (int l = 1; l < map.getWidth(); l++) {
                Tile tile = map.getTile(k, l);
                String sign = " ";
                if (tile.isDefaultCastle()) {
                    sign = "\u001B[40m" + "C";
                } else if (tile.getMilitaries().size() != 0) sign = "\uE54E";
                else if (tile.getBuilding() != null && !(tile.getBuilding() instanceof Wall)) sign = "B";
                else if (tile.getBuilding() != null && tile.getBuilding() instanceof Wall) sign = "W";
                else if (tile.getTree() != null) sign = "T";
                else if (tile.getRockDirection() != null) sign = "R";
                result += tile.getTexture().getColor() + sign + "\u001B[0m";
            }
            result += "\n";
        }
        return result;
    }

    public static String moveMap(int up, int left, int down, int right) {
        int y = down - up;
        int x = right - left;
        return showMap(GameController.getGame().getCurrentMapX() + x, GameController.game.getCurrentMapY() + y);
    }

    public static String showDetailsOfTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);

        String details = "tile (" + (x + 1) + ", " + (y + 1) + ") details:\n";
        details += "texture type: " + tile.getTexture().getTextureName() + "\n";
        if (tile.getTree() != null) {
            details += "Tree : " + tile.getTree().getTreeName() + "\n";
        }
        if (tile.getRockDirection() != null) {
            details += "Rock : " + tile.getRockDirection().getDirection() + "\n";
        }
        if (tile.getBuilding() != null) {
            details += "building " + tile.getBuilding().getName() + " from Lord " +
                    tile.getBuilding().getGovernment().getUser().getNickname() +
                    " | HP: " + tile.getBuilding().getHp() + "/" +
                    tile.getBuilding().getMaxHp() + "\n";
        } else details += "there is no building on this tile\n";
        details += "Civilian number : " + tile.getCivilians().size() + "\n";
        if (tile.getCivilians().size() != 0) {
            details += "Civilian in details : \n";
            for (int i = 0; i != tile.getCivilians().size(); i++) {
                details += "Civilian " + (i + 1) + ": from Lord " +
                        tile.getCivilians().get(i).getGovernment().getUser().getNickname() + "\n";
            }
        }
        details += "Military number : " + tile.getMilitaries().size() + "\n";
        for (int i = 0; i != tile.getMilitaries().size(); i++) {
            details += "Military " + (i + 1) + ": type: " + tile.getMilitaries().get(i).getName() + " | from Lord " +
                    tile.getMilitaries().get(i).getGovernment().getUser().getNickname() + "\n";
        }
        return details.substring(0, details.length() - 1);
    }

    public static String validateXAndY(int x, int y) {
        Map map = game.getMap();
        if (checkNullFields(x)) {
            return "x is required!";
        }
        if (checkNullFields(y)) {
            return "y is required!";
        }
        if (x < 1 || x > map.getWidth()) {
            return "invalid x!";
        }
        if (y < 1 || y > map.getLength()) {
            return "invalid y!";
        }
        return null;
    }

    public static String validatePatrol(int x1, int y1, int x2, int y2) {
        Map map = game.getMap();
        if (checkNullFields(x1)) {
            return "x1 is required!";
        }
        if (checkNullFields(y1)) {
            return "y1 is required!";
        }
        if (x1 < 1 || x1 > map.getWidth()) {
            return "invalid x1!";
        }
        if (y1 < 1 || y1 > map.getLength()) {
            return "invalid y!";
        }

        if (checkNullFields(x2)) {
            return "x2 is required!";
        }
        if (checkNullFields(y2)) {
            return "y2 is required!";
        }
        if (x2 < 1 || x2 > map.getWidth()) {
            return "invalid x2!";
        }
        if (y2 < 1 || y2 > map.getLength()) {
            return "invalid y2!";
        }
        return null;
    }

    private static boolean checkNullFields(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean checkNullFields(int input) {
        return input == -1;
    }
}
