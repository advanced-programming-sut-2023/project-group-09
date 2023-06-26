package controller;

import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameTools;
import enumeration.Pair;
import enumeration.Textures;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
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
import model.tools.Tool;
import view.controllers.HumanViewController;

import java.util.ArrayList;

public class MapController {
    public static Map map;

    //TODO: complete setTexture conditions

    // Temporary solution start\
    public static boolean isRightSide;

    // Temporary solution end
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
            return "rock added in (" + (x) + ", " + (y) + ") with " + direction.getDirection() + " direction";
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
        if (building.getBuildingImpassableLength() != -1) {
            return checkCanDropSpecialBuilding(x, y, building);
        }
        ArrayList<Pair<Integer, Integer>> neighborTiles = GameController.getNeighborPairs(x, y, building.getWidth(), building.getLength());

        for (Pair<Integer, Integer> pair : neighborTiles) {
            int i = pair.getFirst();
            int j = pair.getSecond();
            Tile tile = map.getTile(i, j);
            if (tile.getTexture() == Textures.OIL && !building.getSuitableTextures().contains("oil")) {
                return false;
            }
            if (tile.getMilitaries().size() != 0 || tile.getCivilian().size() != 0) {
                return false;
            }
            if (building instanceof CastleBuilding && !(building instanceof Wall)) {
                if (!canPutCastleBuilding(i, j)) {
                    return false;
                }
            } else if (!map.getTile(i, j).getCanPutBuilding()) {
                return false;
            }
            if (building.getHasSpecialTexture()) {
                if (!building.getSuitableTextures().contains(map.getTile(i, j).getTexture().getName())) {
                    return false;
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
        if (building.getBuildingImpassableLength() >= 1) {
            dropSpecialBuilding(x, y, building);
            return;
        }
        ArrayList<Pair<Integer, Integer>> tiles = GameController.getNeighborPairs
                (x, y, building.getWidth(), building.getLength());

        for (Pair<Integer, Integer> pair : tiles) {
            int i = pair.getFirst();
            int j = pair.getSecond();
            System.out.println(++counter + " : x = " + i + " y = " + j);
            if (j >= map.getWidth() || i >= map.getLength()) {
                System.out.println("you can't put a building here");
            }
            Tile tile = map.getTile(i, j);
            tile.setCanPutBuilding(false);
            tile.setPassable(false);
            Textures textures = Textures.EARTH_AND_SAND;
            if (building.getHasSpecialTexture()) {
                textures = Textures.getTextureByName(building.getSuitableTextures().get(0));
            }

            tile.setBuilding(building);
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
            tile.setPassable(false);
            tile.setTexture(textures);
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

        if (building.isShouldBeOne()) {
            deleteOtherBuildingWithThisType(building);
        }

        if (building instanceof Gatehouse) {
            ((Gatehouse) building).setRightSide(isRightSide);
        }
    }

    public static void dropSpecialBuilding(int x, int y, Building building) {
        System.out.println("----:(");
        System.out.println(building.getBuildingImpassableLength());
        ArrayList<Tile> tiles = GameController.getNeighborTiles
                (x, y, building.getBuildingImpassableLength(), building.getBuildingImpassableLength());

        Tile lastTile = tiles.get(tiles.size() - 1);
        ArrayList<Tile> holeTiles = GameController.getNeighborTilesFromStart(lastTile.x, lastTile.y, building.getWidth(), building.getLength());

        for (Tile tile : holeTiles) {
            int i = tile.x;
            int j = tile.y;
            if (j >= map.getWidth() || i >= map.getLength()) {
                System.out.println("you can't put a building here");
                return;
            }
            tile.setCanPutBuilding(false);
            tile.setPassable(false);
            Textures textures = Textures.EARTH_AND_SAND;
            if (building.getHasSpecialTexture()) {
                textures = Textures.getTextureByName(building.getSuitableTextures().get(0));
            }

            tile.setBuilding(building);

            if (building.getBuildingImpassableLength() != -1) {
                if (!tiles.contains(tile)) {
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

        if (building.isShouldBeOne()) {
            deleteOtherBuildingWithThisType(building);
        }

    }

    public static boolean checkCanDropSpecialBuilding(int x, int y, Building building) {
        ArrayList<Tile> tiles = GameController.getNeighborTiles
                (x, y, building.getBuildingImpassableLength(), building.getBuildingImpassableLength());

        Tile lastTile = tiles.get(tiles.size() - 1);
        ArrayList<Tile> holeTiles = GameController.getNeighborTilesFromStart(lastTile.x, lastTile.y, building.getLength(), building.getWidth());
        for (Tile tile : holeTiles) {
            int i = tile.x;
            int j = tile.y;
            System.out.println("t: " + tile.getCanPutBuilding());
            if (tile.getTexture() == Textures.OIL && !building.getSuitableTextures().contains("oil")) {
                return false;
            }
            if (tile.getMilitaries().size() != 0 || tile.getCivilian().size() != 0) {
                return false;
            }
            if (building instanceof CastleBuilding && !(building instanceof Wall)) {
                if (!canPutCastleBuilding(i, j)) {
                    return false;
                }
            } else if (!map.getTile(i, j).getCanPutBuilding()) {
                return false;
            }
            if (building.getHasSpecialTexture()) {
                if (!building.getSuitableTextures().contains(map.getTile(i, j).getTexture().getName())) {
                    return false;
                }
            }
        }
        return true;
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
        HumanViewController.dropUnit(x, y, tile, military);
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


        Government government = GameController.getGame().getCurrentGovernment();
        BuildingCounter buildingCounter = government.getBuildingData(storageBuilding.getName());
        if (buildingCounter.getNumber() == 0) {
            return true;
        }
        ArrayList<Tile> storageTiles = GameController.getNeighborTiles(x, y, storageBuilding.getWidth(), storageBuilding.getLength());
        for (Building sb : buildingCounter.getBuildings()) {
            ArrayList<Tile> neighborTiles = GameController.getDirectNeighborTiles(sb);
            for (Tile tile : storageTiles) {
                for (Tile neighborTile : neighborTiles) {
                    if (tile.equals(neighborTile)) {
                        return true;
                    }
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
        if (buildingCounter.getNumber() <= 1) {
            System.out.println("returned!");
            return;
        }
        Building shouldDelete = buildingCounter.getBuildings().get(0);
        deleteBuilding(shouldDelete);
    }

    public static synchronized void deleteBuilding(Building building) {
        ArrayList<Pair<Integer, Integer>> tiles = GameController.getNeighborPairs
                (building.getEndX(), building.getEndY(), building.getWidth(), building.getLength());
        BuildingCounter buildingCounter = building.getGovernment().getBuildingData(building.getName());
        if (buildingCounter != null) {
            buildingCounter.deleteBuilding(building);
        }
        if (building instanceof StorageBuilding) {
            ((StorageBuilding) building).deleteStorage();
        }
        for (Pair<Integer, Integer> pair : tiles) {
            int i = pair.getFirst();
            int j = pair.getSecond();
            Tile tileOfBuilding = map.getTile(i, j);
            tileOfBuilding.setCanPutBuilding(true);
            tileOfBuilding.setPassable(true);
            tileOfBuilding.setBuilding(null);
        }
        GameMap.getGameTile(building.getEndX(), building.getEndY()).refreshTile();
    }

    public static void setDoorForCastleBuilding(Building building) {

        if (!(building instanceof CastleBuilding)) {
            return;
        }
        if (building.getName().equals("drawBridge")) {
            return;
        }
        int x = building.getEndX();
        int y = building.getEndY();
        int count = (int) Math.ceil((double) building.getLength() / 2);
        if (building instanceof MainCastle || (building instanceof Gatehouse gatehouse && !gatehouse.isRightSide())) {
            while (count != 0) {
                if (y % 2 == 1) x--;
                y--;
                count--;
            }
        } else if ((building instanceof Gatehouse gatehouse && gatehouse.isRightSide())) {
            while (count != 0) {
                if (y % 2 == 1) x--;
                y--;
                count--;
            }
        }
        if (building instanceof MainCastle mainCastle) {
            mainCastle.setDoor(map.getTile(x, y));
        }
        if (building instanceof Gatehouse gatehouse) {
            gatehouse.setDoor(map.getTile(x, y));
        }
    }
}
