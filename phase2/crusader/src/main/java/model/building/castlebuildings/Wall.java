package model.building.castlebuildings;

import controller.GameController;
import model.game.Map;

public class Wall extends CastleBuilding {
    private String type; // wall or stairs

    public Wall(int numberOfRequiredWorkers, int numberOfRequiredEngineers,
                String type, int maxHp, int width, int length, int height) {
        super(numberOfRequiredWorkers, numberOfRequiredEngineers, type, maxHp, width, length);
        setHeight(height);
        this.capacity = 0;
    }
    public Wall(Wall wall) {
        super(wall.getNumberOfRequiredWorkers(), wall.getNumberOfRequiredEngineers(), wall.getName(), wall.getMaxHp(), wall.getWidth(), wall.getLength());
        this.setHeight(wall.getHeight());
        this.capacity = 0;
        this.type = wall.type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static int heightOfStairs(int x, int y) { // if returned 0 or -1, we can't create wall over there.
        Map map = GameController.getGame().getMap();
        int heightOfTallestWallOrStairs = findHeightOfTallestWallOrStairs(x , y);
        return heightOfTallestWallOrStairs-1;
    }

    private static int findHeightOfTallestWallOrStairs(int x , int y) {
        int height = 0;
        Map map = GameController.getGame().getMap();
        if (x+1 < map.getLength() && map.getTile(x+1 , y).getBuilding() instanceof Wall) {
            Wall wall = (Wall)map.getTile(x+1 , y).getBuilding();
            if (wall.getHeight() > height && height != 8) {
                height = wall.getHeight();
            }
        }
        if (y+1 < map.getWidth() && map.getTile(x , y+1).getBuilding() instanceof Wall) {
            Wall wall = (Wall)map.getTile(x , y+1).getBuilding();
            if (wall.getHeight() > height && height != 8) {
                height = wall.getHeight();
            }
        }
        if (x-1 >= 0 && map.getTile(x-1 , y).getBuilding() instanceof Wall) {
            Wall wall = (Wall)map.getTile(x-1 , y).getBuilding();
            if (wall.getHeight() > height && height != 8) {
                height = wall.getHeight();
            }
        }
        if (y-1 >= 0 && map.getTile(x , y-1).getBuilding() instanceof Wall) {
            Wall wall = (Wall)map.getTile(x , y-1).getBuilding();
            if (wall.getHeight() > height && height != 8) {
                height = wall.getHeight();
            }
        }
        return height;
    }

    public static boolean canDropCrenulatedWall(int x , int y) {
        Map map = GameController.getGame().getMap();
        if (x+1 < map.getWidth() && map.getTile(x+1 , y).getBuilding() instanceof Wall) {
            return true;
        }
        if (y+1 < map.getLength() && map.getTile(x , y+1).getBuilding() instanceof Wall) {
            return true;
        }
        if (x-1 >= 0 && map.getTile(x-1 , y).getBuilding() instanceof Wall) {
            return true;
        }
        if (y-1 >= 0 && map.getTile(x , y-1).getBuilding() instanceof Wall) {
            return true;
        }
        return false;
    }

}
