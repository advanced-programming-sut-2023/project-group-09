package controller;

import controller.gamestructure.GameHumans;
import controller.human.HumanController;
import enumeration.HumanStates;
import javafx.util.Pair;
import model.building.Building;
import model.building.castlebuildings.Wall;
import model.game.Game;
import model.game.Map;
import model.game.Tile;
import model.human.military.Military;
import view.UnitMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class GameController {
    private static Game game;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameController.game = game;
    }

    //TODO handel empty field
    public static String selectUnit(int x, int y, String type, Scanner scanner) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        ArrayList<Military> militaries;
        if (type == null) {
            militaries = MapController.getMilitariesOfGovernment(x, y, game.getCurrentGovernment());
        } else if (GameHumans.getUnit(type) == null) {
            return "invalid type!";
        } else {
            militaries = MapController.getOneTypeOfMilitariesOfGovernment(x, y, type, game.getCurrentGovernment());
        }

        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }
        HumanController.militaries = militaries;
        UnitMenu.x = x;
        UnitMenu.y = y;
        UnitMenu.run(scanner);
        return "";
    }

    public static String moveUnit(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        Pair<Integer, Integer> destination = new Pair<>(y, x);
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

        boolean check = HumanController.patrolUnit(x1, y1, x2, y2);
        if (!check) {
            return "can't start patrol, no path to destination!";
        }
        return "patrol started successfully!";
    }

    public static String setStateOfMilitary(int x, int y, String state) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }

        ArrayList<Military> militaries = MapController.getMilitariesOfGovernment(x, y, game.getCurrentGovernment());
        if (militaries.size() == 0) {
            return "There is no troop in this place!";
        }


        if (state.equals(HumanStates.STAND_GROUND.getState())) {
            HumanController.setState(HumanStates.STAND_GROUND.getState(), militaries);
        }

        if (state.equals(HumanStates.DEFENSIVE_STANCE.getState())) {
            HumanController.setState(HumanStates.DEFENSIVE_STANCE.getState(), militaries);
        }

        if (state.equals(HumanStates.AGGRESSIVE_STANCE.getState())) {
            HumanController.setState(HumanStates.AGGRESSIVE_STANCE.getState(), militaries);
        }

        return "invalid state!";
    }

    public static String attackEnemy(int x, int y) {
        String message = validateXAndY(x, y);
        if (message != null) {
            return message;
        }
        return "";
    }

    public static String airAttack(int x, int y) {
        return "";
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

    public static String dropBuilding(String x, String y, String type) {
//        int xCoord, yCoord;
//        try {
//            xCoord = Integer.parseInt(x);
//        } catch (NumberFormatException e) {
//            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_X_COORD_ERROR);
//        }
//        try {
//            yCoord = Integer.parseInt(x);
//        } catch (NumberFormatException e) {
//            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_Y_COORD_ERROR);
//        }
//        Building building = getInstanceOfBuilding(xCoord, yCoord, type); // TODO: get instance of supposed building.
//        if (building == null)
//            return BuildingAnswers.getMessage(BuildingAnswers.ERROR_FOR_DROP_BUILDING);
//        GameController.getGame().getMap().getTile(xCoord, yCoord).setBuilding(building);
//        return BuildingAnswers.getMessage(BuildingAnswers.DROP_BUILDING_SUCCESSFULLY_DONE);
        return null;
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
                        Tile tile = map.getTile(i, k);
                        String sign = " ";
                        if (tile.getMilitaries().size() != 0) sign = "\uE54E";
                        else if (tile.getBuilding() != null && !(tile.getBuilding() instanceof Wall)) sign = "B";
                        else if (tile.getBuilding() != null && tile.getBuilding() instanceof Wall) sign = "W";
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

        String details = "tile (" + x + ", " + y + ") details:\n";
//        details += "texture type: " + tile.getTexture().getTextureName();
//
//        if (tile.getBuilding() != null) {
//            details += "building " + tile.getBuilding().getName() + " from government " + tile.getBuilding().getGovernment() +
//                    " | HP: " + tile.getBuilding().getHp() + "/" + tile.getBuilding().getMaxHp() + "\n";
//        } else details += "there is no building on this tile\n";
//
//        for (int i = 0; i < tile.getHuman().size(); i++) {
//            Human human = tile.getHuman().get(i);
////            TODO: add human type and government
//        }
//        if (tile.getHuman().size() > 0) details += "total number of humans: " + tile.getHuman().size();
//        else details += "there are no humans on this tile";

        return details;
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
