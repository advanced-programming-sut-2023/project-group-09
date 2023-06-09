package controller;

import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameMaps;
import controller.gamestructure.GameTools;
import enumeration.Pair;
import enumeration.Paths;
import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.scene.image.Image;
import model.Government;
import model.building.Building;
import model.building.castlebuildings.CastleBuilding;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.Wall;
import model.building.storagebuildings.StorageBuilding;
import model.buildinghandler.BuildingCounter;
import model.game.Map;
import model.game.Tile;
import model.human.civilian.Civilian;
import model.human.military.Engineer;
import model.human.military.Military;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import model.menugui.game.Troop;
import model.tools.Tool;
import view.controllers.GameViewController;
import view.menus.GameMenu;

import java.util.ArrayList;

public class MapController {
    public static Map map;

    //TODO: complete setTexture conditions
    public static String setTexture(int x, int y, Textures type) {
        Tile tile = map.getTile(x, y);
        if ((tile.getTree() != null && !type.equals(Textures.EARTH) && !type.equals(Textures.EARTH_AND_SAND) && !type.equals(Textures.GRASS) &&
                !type.equals(Textures.THICK_GRASS) && !type.equals(Textures.OASIS_GRASS) && !type.equals(Textures.BEACH)) ||
                (tile.getRockDirection() != null && (type.equals(Textures.SMALL_POND) || type.equals(Textures.LARGE_POND))))
            return "you can't change this tile's texture to " + type.getTextureName();
        tile.setTexture(type);
        return "texture of tile (" + (x + 1) + ", " + (y + 1) + ") changed to " + type.getTextureName() + " successfully";
    }

    public static String setTexture(int x1, int x2, int y1, int y2, Textures type) {
        String result = "";
        for (int i = y1; i <= y2; i++) {
            for (int j = x1; j <= x2; j++) {
                Tile tile = map.getTile(j, i);
                if ((tile.getTree() != null && !type.equals(Textures.EARTH) && !type.equals(Textures.EARTH_AND_SAND) && !type.equals(Textures.GRASS) &&
                        !type.equals(Textures.THICK_GRASS) && !type.equals(Textures.OASIS_GRASS) && !type.equals(Textures.BEACH)) ||
                        (tile.getRockDirection() != null && (type.equals(Textures.SMALL_POND) || type.equals(Textures.LARGE_POND))))
                    result += "you can't change texture of tile (" + (j + 1) + ", " + (i + 1) + ") to " + type.getTextureName() + "\n";
                tile.setTexture(type);
            }
        }
        return result + "texture of tiles from (" + (x1 + 1) + ", " + (y1 + 1) + ") to (" + (x2 + 1) + ", " + (y2 + 1) + ") changed to "
                + type.getTextureName() + " successfully (except the mentioned ones)";
    }

    public static String clearTile(int x, int y) {
        Tile tile = map.getTile(x, y);
        tile.clearCivilian();
        tile.clearMilitary();
        tile.setTexture(Textures.EARTH);
        if (tile.getBuilding() != null) {
            Building building = tile.getBuilding();
            deleteBuilding(building);
        }
        tile.setTree(null);
        return "tile (" + (x + 1) + ", " + (y + 1) + ") cleared successfully";
    }

    public static String dropRock(int x, int y, RockDirections direction) {
        Tile tile = map.getTile(x, y);
        if (!tile.getTexture().equals(Textures.SMALL_POND) && !tile.getTexture().equals(Textures.LARGE_POND) && tile.getTree() == null && tile.getBuilding() == null
                && tile.getMilitaries().size() == 0 && tile.getBuilding() == null && tile.getTree() == null) {
            tile.setRockDirection(direction);
            tile.setCanPutBuilding(false);
            tile.setPassable(false);
            return "rock added in (" + (x + 1) + ", " + (y + 1) + ") with " + direction.getDirection() + " direction";
        }
        return "you can't drop a rock here";
    }

    public static String dropTree(int x, int y, Trees tree) {
        Tile tile = map.getTile(x, y);
        if ((tile.getTexture().equals(Textures.EARTH) || tile.getTexture().equals(Textures.EARTH_AND_SAND) || tile.getTexture().equals(Textures.GRASS) ||
                tile.getTexture().equals(Textures.THICK_GRASS) || tile.getTexture().equals(Textures.OASIS_GRASS) || tile.getTexture().equals(Textures.BEACH)) &&
                tile.getBuilding() == null && tile.getRockDirection() == null) {
            tile.setTree(tree);
            tile.setCanPutBuilding(false);
            return tree.getTreeName() + " added to (" + (x + 1) + ", " + (y + 1) + ") successfully";
        }
        return "you can't drop a tree here";
    }


    public static boolean checkCanPutBuilding(int x, int y, String type, Government government) {
        Building building = GameBuildings.getBuilding(type, government, x, y);
        if (type.equals("killingPit")) {
            return checkKillingPit(x, y);
        }
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
            System.out.println("reason: this tile is pit or is moat or the texture is not suitable!");
            return false;
        }

        if (building.getName().equals("stairs") && building instanceof Wall) {
            int height = Wall.heightOfStairs(x, y);
            if (height == 0 || height == -1) {
                return false;
            }
        }


        if (building.getName().equals("crenulatedWall") && building instanceof Wall) {
            if (!Wall.canDropCrenulatedWall(x, y)) {
                return false;
            }
        }

        if (building.getName().equals("drawBridge")) {
            if (Gatehouse.canDropDrawBridge(x, y) == null) {
                return false;
            }
        }

        for (int i = y; i < y + building.getLength(); i++) {
            for (int j = x; j < x + building.getWidth(); j++) {
                Tile tile = map.getTile(j, i);
                if (tile.getTexture() == Textures.OIL && !building.getSuitableTextures().contains("oil")) {
                    return false;
                }
                if (tile.getMilitaries().size() != 0 || tile.getCivilian().size() != 0) {
                    return false;
                }

                if (building instanceof CastleBuilding && !(building instanceof Wall)) {
                    if (!canPutCastleBuilding(j, i)) {
                        return false;
                    }
                } else if (!map.getTile(j, i).getCanPutBuilding()) {
                    return false;
                }

                if (building.getHasSpecialTexture()) {
                    if (!building.getSuitableTextures().contains(map.getTile(j, i).getTexture().getName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkKillingPit(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (tile.getBuilding() == null && !tile.isPit() && !tile.isMoat() &&
                (tile.getTexture().equals(Textures.EARTH)) || (tile.getTexture().equals(Textures.EARTH_AND_SAND))
                || (tile.getTexture().equals(Textures.BOULDER)) || (tile.getTexture().equals(Textures.GRASS))
                || (tile.getTexture().equals(Textures.THICK_GRASS)) || (tile.getTexture().equals(Textures.OASIS_GRASS))
                || (tile.getTexture().equals(Textures.BEACH)))
            return true;
        return false;
    }

    public static void dropKillingPit(int x, int y) {
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        if (tile.getBuilding() == null && !tile.isPit() && !tile.isMoat() &&
                (tile.getTexture().equals(Textures.EARTH)) || (tile.getTexture().equals(Textures.EARTH_AND_SAND))
                || (tile.getTexture().equals(Textures.BOULDER)) || (tile.getTexture().equals(Textures.GRASS))
                || (tile.getTexture().equals(Textures.THICK_GRASS)) || (tile.getTexture().equals(Textures.OASIS_GRASS))
                || (tile.getTexture().equals(Textures.BEACH)))
            tile.setPit(true);
        tile.setPitGovernment(GameController.getGame().getCurrentGovernment());
    }


    public static void dropBuilding(int x, int y, String type, Government government) {
        Building building = GameBuildings.getBuilding(type, government, x, y);
        if (type.equals("killingPit")) {
            dropKillingPit(x, y);
            return;
        }
        if (type.equals("mainCastle") && government.getBuildingData("mainCastle").getNumber() != 0) {
            System.out.println("a government just can have one main Castle!");
            return;
        }
        int counter = 0;
        assert building != null;
        ArrayList<Pair<Integer, Integer>> tiles = GameController.getNeighborTiles
                (x, y, building.getWidth(), building.getLength());
        for (Pair<Integer, Integer> pair : tiles) {
            int i = pair.getFirst();
            int j = pair.getSecond();
            System.out.println(++counter + " : x = " + i + " y = " + j);
            if (j >= map.getWidth() || i >= map.getLength()) {
                System.out.println("you can't put a building here");
            }
            Tile tile = map.getTile(i, j);
            if (building.isShouldBeOne()) {
                deleteOtherBuildingWithThisType(building);
            }

            tile.setCanPutBuilding(false);
            Textures textures = Textures.EARTH_AND_SAND;
            if (building.getHasSpecialTexture()) {
                textures = Textures.getTextureByName(building.getSuitableTextures().get(0));
            }

            tile.setBuilding(building);
            System.out.println(map.getTile(x, y).getBuilding() != null);
            if (building.getName().equals("stairs") && building instanceof Wall) {
                ((Wall) building).setHeight(Wall.heightOfStairs(x, y));
                tile.setPassable(false);
                tile.setTexture(textures);
                continue;
            }

            if (building.getName().equals("drawBridge")) {
                Gatehouse gatehouse = Gatehouse.canDropDrawBridge(x, y);
                assert gatehouse != null;
                gatehouse.setDrawBridge_gatehouse((Gatehouse) building);
                tile.setMoat(true);
                tile.setPassable(true);
                tile.setBuilding(building);
                continue;
            }


            if (building.getBuildingImpassableLength() != -1) {
                if (i >= building.getBuildingImpassableLength() + y || j >= building.getBuildingImpassableLength() + x) {
                    tile.setPassable(true);
                    //tile.setBuilding(null);
                } else {
                    tile.setPassable(false);
                    tile.setTexture(textures);
                }
            } else {
                tile.setPassable(false);
                tile.setTexture(textures);
            }
        }
        Pair<Integer, Integer> lastPair = tiles.get(tiles.size() - 1);
        building.setStartX(lastPair.getFirst());
        building.setStartY(lastPair.getSecond());

        government.getBuildingData(type).addBuilding(building);
        if (building.getName().equals("hovel")) {
            government.updateMaxPopulation();
        }

        if (building instanceof StorageBuilding) {
            government.checkFirstStorage(building);
        }
    }

    public static void dropTool(int x, int y, String type, Government government) {
        Tool tool = GameTools.getTool(type, government, x, y);
        Tile tile = map.getTile(x, y);
        tile.setTool(tool);
        government.addTool(tool);
    }

    public static boolean checkCanPutTool(int x, int y, String type) {
        Tool tool = GameTools.getTool(type);
        if (tool == null) {
            return false;
        }
        Tile tile = map.getTile(x, y);
        return tile.isPassable();
    }

    public static ArrayList<Military> getMilitariesOfGovernment(int x, int y, Government government) {
        ArrayList<Military> militaries = new ArrayList<>();
        Tile tile = map.getTile(x, y);
        for (Military military : tile.getMilitaries()) {
            if (military.getGovernment().getColor().equals(government.getColor())) {
                if (!(military.getName().equals("lord")) && !((military instanceof Engineer engineer) && engineer.isInTool())) {
                    militaries.add(military);
                }
            }
        }
        return militaries;
    }

    public static ArrayList<Military> getMilitariesOfOtherGovernment(int x, int y, Government government) {
        ArrayList<Military> militaries = new ArrayList<>();
        Tile tile = map.getTile(x, y);
        for (Military military : tile.getMilitaries()) {
            if (!military.getGovernment().getColor().equals(government.getColor())) {
                if (!military.isInvisible()) {
                    militaries.add(military);
                }
            }
        }
        return militaries;
    }

    public static ArrayList<Civilian> getCiviliansOfOtherGovernment(int x, int y, Government government) {
        ArrayList<Civilian> civilians = new ArrayList<>();
        Tile tile = map.getTile(x, y);
        for (Civilian civilian : tile.getCivilian()) {
            if (!civilian.getGovernment().getColor().equals(government.getColor())) {
                civilians.add(civilian);
            }
        }
        return civilians;
    }

    public static ArrayList<Military> getOneTypeOfMilitariesOfGovernment(int x, int y, String type, Government government) {
        ArrayList<Military> militaries = new ArrayList<>();
        Tile tile = map.getTile(x, y);
        for (Military military : tile.getMilitaries()) {
            if (military.getGovernment().equals(government) && military.getName().equals(type)) {
                militaries.add(military);
            }
        }
        return militaries;
    }

    public static boolean checkCanPutMilitary(int x, int y, String type) {
        Military military = GameHumans.getUnit(type);
        if (military == null) {
            System.out.println("reason : military with this name doesn't exist!");
            return false;
        }
        Tile tile = map.getTile(x, y);
        Building building = tile.getBuilding();
        if (building instanceof CastleBuilding && !building.getName().equals("drawBridge")) {
            return true;
        }
        if (!tile.isPassable()) {
            System.out.println("reason : tile is not passable");
        }
        return tile.isPassable();
    }

    public static void dropMilitary(int x, int y, String type, Government government) {
        Military military = GameHumans.getUnit(type, government, x, y);
        Tile tile = map.getTile(x, y);
        government.addMilitary(military);
        tile.addMilitary(military);
        GameViewController.dropUnit(x,y,tile,military);
    }

    public static void deleteMilitary(int x, int y, Military military) {
        Tile tile = map.getTile(x, y);
        military.getGovernment().removeMilitary(military);
        tile.removeMilitary(military);
    }

    public static void addMilitary(int x, int y, Military military) {
        Tile tile = map.getTile(x, y);
        military.setX(x);
        military.setY(y);
        military.getGovernment().addMilitary(military);
        tile.addMilitary(military);
    }

    public static void deleteTool(int x, int y, Tool tool) {
        Tile tile = map.getTile(x, y);
        tool.getGovernment().removeTool(tool);
        tile.setTool(null);
    }

    public static void addTool(int x, int y, Tool tool) {
        Tile tile = map.getTile(x, y);
        tool.setY(y);
        tool.setX(x);
        tool.getGovernment().addTool(tool);
        tile.setTool(tool);
    }

    public static void moveTool(int x, int y, Tool tool) {
        deleteTool(tool.getX(), tool.getY(), tool);
        addTool(x, y, tool);
        tool.setX(x);
        tool.setY(y);
        for (Engineer engineer : tool.getEngineers()) {
            moveMilitary(x, y, engineer);
        }
    }

    public static void moveMilitary(int x, int y, Military military) {
        deleteMilitary(military.getX(), military.getY(), military);
        addMilitary(x, y, military);
        military.setX(x);
        military.setY(y);
    }

    public static void deleteHuman(int x, int y, Civilian civilian) {
        Tile tile = map.getTile(x, y);
        civilian.getGovernment().removeHuman(civilian);
        tile.removeHuman(civilian);
    }

    public static void addHuman(int x, int y, Civilian civilian) {
        Tile tile = map.getTile(x, y);
        civilian.getGovernment().addHuman(civilian);
        tile.addHuman(civilian);
    }

    public static void moveHuman(int x, int y, Civilian civilian) {
        deleteHuman(civilian.getX(), civilian.getY(), civilian);
        addHuman(x, y, civilian);
        civilian.setX(x);
        civilian.setY(y);
    }

    public static boolean checkCanPutStorage(int x, int y, StorageBuilding storageBuilding) {
        int endX = x + storageBuilding.getWidth();
        int endY = y + storageBuilding.getLength();
        Government government = GameController.getGame().getCurrentGovernment();
        BuildingCounter buildingCounter = government.getBuildingData(storageBuilding.getName());
        if (buildingCounter.getNumber() == 0) {
            return true;
        }
        if (y != 0) {
            for (int j = x; j <= endX; j++) {
                Tile tile = map.getTile(j, y - 1);
                Building building = tile.getBuilding();
                if (building != null && building.getName().equals(storageBuilding.getName()) && building.getGovernment().equals(government)) {
                    return true;
                }
            }
        }

        if (x != 0) {
            for (int i = y; i <= endY; i++) {
                Tile tile = map.getTile(x - 1, i);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }


        if (endX != map.getWidth() - 1) {
            for (int i = y; i <= endY; i++) {
                Tile tile = map.getTile(endX + 1, i);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }

        if (endY != map.getLength() - 1) {
            for (int j = x; j <= endX; j++) {
                Tile tile = map.getTile(j, endY + 1);
                if (tile.getBuilding() != null && tile.getBuilding().getName().equals(storageBuilding.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean canPutCastleBuilding(int x, int y) {
        Tile tile = map.getTile(x, y);
        if (tile.getCanPutBuilding()) {
            return true;
        } else return tile.getBuilding() instanceof Wall;
    }

    public static void deleteOtherBuildingWithThisType(Building building) {
        Government government = GameController.getGame().getCurrentGovernment();
        BuildingCounter buildingCounter = government.getBuildingData(building.getName());
        if (buildingCounter.getNumber() == 0) {
            return;
        }
        // TODO : delete with refresh
        Building shouldDelete = buildingCounter.getBuildings().get(0);
        buildingCounter.deleteBuilding(shouldDelete);
        deleteBuilding(shouldDelete);
    }

    public static void deleteBuilding(Building building) {
        int xx = building.getStartX();
        int yy = building.getStartY();
        for (int i = yy; i < yy + building.getLength(); i++) {
            for (int j = xx; j < xx + building.getWidth(); j++) {
                Tile tileOfBuilding = map.getTile(j, i);
                tileOfBuilding.setCanPutBuilding(true);
                tileOfBuilding.setPassable(true);
                tileOfBuilding.setBuilding(null);
            }
        }
        BuildingCounter buildingCounter = building.getGovernment().getBuildingData(building.getName());
        if (buildingCounter != null) {
            buildingCounter.deleteBuilding(building);
        }
        if (building instanceof StorageBuilding) {
            ((StorageBuilding) building).deleteStorage();
        }
    }

}
