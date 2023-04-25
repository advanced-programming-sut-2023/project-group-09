package controller;

import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameHumans;
import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.CastleBuilding;
import model.building.castlebuildings.Wall;
import model.building.storagebuildings.StorageBuilding;
import model.buildinghandler.BuildingCounter;
import model.game.Map;
import model.game.Tile;
import model.human.military.Military;

import java.util.ArrayList;
import java.util.Objects;

public class MapController {

    private static Map map;

    public static String setTexture(int x, int y, Textures type) {
        GameController.getGame().getMap().getTile(x, y).setTexture(type);
        return "texture of tile (" + x + ", " + y + ") changed to " + type.getTextureName() + " successfully";
    }

    public static String clearTile(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        tile.clearCivilian();
        tile.clearMilitary();
        tile.setTexture(Textures.EARTH);
        if (tile.getBuilding() != null) {
            Building building = tile.getBuilding();
            deleteBuilding(building);
        }

        return "tile (" + x + ", " + y + ") cleared successfully";
    }

    public static String dropRock(int x, int y, RockDirections direction) {
        return "";
    }

    public static String dropTree(int x, int y, Trees tree) {
        return "";
    }


    public static boolean checkCanPutBuilding(int x, int y, String type, Government government) {
        Building building = GameBuildings.getBuilding(type, government, x, y);
        if (building == null) {
            return false;
        }

        if (x + building.getWidth() >= map.getWidth()) {
            return false;
        }
        if (y + building.getLength() >= map.getLength()) {
            return false;
        }
        if (building instanceof StorageBuilding && !checkCanPutStorage(x, y, (StorageBuilding) building)) {
            return false;
        }
        for (int i = y - 1; i < y + building.getLength(); i++) {
            for (int j = x - 1; j < x + building.getWidth(); j++) {
                if (building instanceof CastleBuilding) {
                    if (!canPutCastleBuilding(x, y, (CastleBuilding) building)) {
                        return false;
                    }
                } else if (!map.getTile(i, j).getCanPutBuilding()) {
                    return false;
                }
                if (building.getHasSpecialTexture()) {
                    if (!building.getSuitableTextures().contains(map.getTile(i, j).getTexture())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void dropBuilding(int x, int y, String type, Government government) {
        Building building = GameBuildings.getBuilding(type, government, x, y);
        for (int i = y - 1; i < y + Objects.requireNonNull(building).getLength(); i++) {
            for (int j = x - 1; j < x + building.getWidth(); j++) {
                Tile tile = map.getTile(j, i);
                tile.setCanPutBuilding(false);
                Textures textures = Textures.EARTH_AND_SAND;
                if (building.getHasSpecialTexture()) {
                    textures = building.getSuitableTextures().get(0);
                }
                tile.setBuilding(building);
                if (building.getBuildingImpassableLength() != -1) {
                    if (i >= building.getBuildingImpassableLength() || j >= building.getBuildingImpassableLength()) {
                        tile.setPassable(true);
                    } else {
                        tile.setPassable(false);
                        tile.setTexture(textures);
                    }
                } else {
                    tile.setPassable(false);
                    tile.setTexture(textures);
                }
            }
        }
    }

    public static ArrayList<Military> getMilitariesOfGovernment(int x, int y, Government government) {
        ArrayList<Military> militaries = new ArrayList<>();
        Tile tile = map.getTile(x, y);
        for (Military military : tile.getMilitaries()) {
            if (military.getGovernment().getColor().equals(government.getColor())) {
                militaries.add(military);
            }
        }
        return militaries;
    }

    public static boolean checkCanPutMilitary(int x, int y, String type, Government government) {
        Military military = GameHumans.getUnit(type, government, x, y);
        Tile tile = map.getTile(x - 1, y - 1);
        return tile.isPassable();
    }

    public static void dropMilitary(int x, int y, String type, Government government) {
        Military military = GameHumans.getUnit(type, government, x, y);
        Tile tile = map.getTile(x - 1, y - 1);
        government.addMilitary(military);
        tile.addMilitary(military);
    }

    public static void dropMilitary(int x, int y, Military military) {
        Tile tile = map.getTile(x - 1, y - 1);
        tile.addMilitary(military);
    }


    public static boolean checkCanPutStorage(int x, int y, StorageBuilding storageBuilding) {
        int startX = x - 1;
        int startY = y - 1;
        int endX = x + storageBuilding.getWidth() - 1;
        int endY = y + storageBuilding.getLength() - 1;


        if (startY != 0) {
            for (int j = startX; j <= endX; j++) {
                Tile tile = map.getTile(j, startY - 1);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }

        if (startX != 0) {
            for (int i = startY; i <= endY; i++) {
                Tile tile = map.getTile(startX - 1, i);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }


        if (endX != map.getWidth() - 1) {
            for (int i = startY; i <= endY; i++) {
                Tile tile = map.getTile(endX + 1, i);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }

        if (endY != map.getLength() - 1) {
            for (int j = startX; j <= endX; j++) {
                Tile tile = map.getTile(j, endY + 1);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canPutCastleBuilding(int x, int y, CastleBuilding building) {
        Tile tile = map.getTile(x, y);
        if (tile.getCanPutBuilding()) {
            return true;
        } else return tile.getBuilding() instanceof Wall;
    }

    public static void deleteOtherBuildingWithThisType(Building building) {
        Government government = GameController.getGame().getCurrentGovernment();
        BuildingCounter buildingCounter = government.getBuildingData(building.getName());
        if(buildingCounter.getNumber() == 0){
            return;
        }

        Building shouldDelete = buildingCounter.getBuildings().get(0);
        buildingCounter.deleteBuilding(shouldDelete);
        deleteBuilding(shouldDelete);
    }

    public static void deleteBuilding(Building building){
        int xx = building.getStartX();
        int yy = building.getStartY();
        for (int i = yy - 1; i < yy + building.getLength(); i++) {
            for (int j = xx - 1; j < xx + building.getWidth(); j++) {
                Tile tileOfBuilding = map.getTile(j,i);
                tileOfBuilding.setCanPutBuilding(true);
                tileOfBuilding.setPassable(true);
                tileOfBuilding.setBuilding(null);
            }
        }
        BuildingCounter buildingCounter = building.getGovernment().getBuildingData(building.getName());
        buildingCounter.deleteBuilding(building);
    }
}
