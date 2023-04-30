package controller;

import enumeration.answers.BuildingAnswers;
import model.building.Building;
import model.building.castlebuildings.Wall;
import model.game.Game;
import model.game.Map;
import model.game.Tile;
import model.human.Human;

public class GameController {
    private static Game game;

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        GameController.game = game;
    }

    public static String selectUnit(int x, int y) {
        return "";
    }

    public static String moveUnit(int x, int y) {
        return "";
    }

    public static String patrolUnit(int x1, int y1, int x2, int y2) {
        return "";
    }

    public static String setStateOfMilitary(int x, int y, String state) {
        return "";
    }

    public static String attackEnemy(int x, int y) {
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

    public static String createUnit(String type, int count) {
        return "";
    }

    public static String repairCastleBuildings() {
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
}
