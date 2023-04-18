package Controller;

import Enumeration.Answers.BuildingAnswers;

public class MapController {
    public static String setTexture(int x, int y, String type) {
        return "";
    }

    public static String clearLand(int x, int y) {
        return "";
    }

    public static String dropRock(int x, int y, String direction) {
        return "";
    }

    public static String dropTree(int x, int y, String type) {
        return "";
    }
    public static String dropBuilding(String x , String y , String type) {
        int xCoord , yCoord;
        try {
            xCoord = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_X_COORD_ERROR);
        }
        try {
            yCoord = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return BuildingAnswers.getMessage(BuildingAnswers.INVALID_Y_COORD_ERROR);
        }
        // TODO: dropping building in game and checking type of building
    }

    public static String dropCastleBuildings(int x, int y, String type) {
        return ""; // *********************************
    }

    public static String dropUnit(int x, int y, String type, int count) {
        return ""; // *********************************
    }

    public static String showMap(int x, int y) {
        return "";
    }

    public static String moveMap(int up, int left, int down, int right) {
        return "";
    }

    public static String showDetailsOfLand(int x, int y) {
        return "";
    }
}
