package model.menugui.game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.game.Map;
import model.game.Tile;
import view.controllers.GameViewController;

public class GameMap extends Pane {
    private final Map map;
    public static double tileWidth;
    public static double tileHeight;
    private final int screenWidth;
    private final int screenHeight;
    private int cameraX;
    private int cameraY;
    private static GameTile[][] gameTiles;

    public GameMap(Map map, double cameraX, double cameraY, double tileWidth, double tileHeight) {
        GameMap.tileWidth = tileWidth;
        GameMap.tileHeight = tileHeight;
        this.screenWidth = (int) Math.ceil(1200 / tileWidth) + 1;
        this.screenHeight = (int) Math.ceil(800 / (tileHeight/2));
        this.map = map;
        gameTiles = new GameTile[map.getWidth()][map.getWidth()];
        this.cameraX = (int) cameraX;
        this.cameraY = (int) cameraY;
        loadMap();
    }

    private void loadMap() {
        for (int y = 0; y < map.getLength(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Tile tile = map.getTile(x, y);
                if (y % 2 == 1) {
                    GameTile gameTile = new GameTile(tile, x * tileWidth - (tileWidth / 2),
                            y * (tileHeight / 2) - (tileHeight / 2), x, y);
                    gameTiles[y][x] = gameTile;
                    this.getChildren().add(0,gameTile);
                } else {
                    GameTile gameTile = new GameTile(tile, x * tileWidth, y * (tileHeight / 2) - (tileHeight / 2), x, y);
                    gameTiles[y][x] = gameTile;
                    this.getChildren().add(gameTile);
                }
            }
        }
    }


    public void moveRight() {
        if (cameraX == map.getWidth() - screenWidth) return;
        cameraX++;
        this.setTranslateX(this.getTranslateX() - tileWidth);
    }

    public void moveLeft() {
        if (cameraX == 0) return;
        cameraX--;
        this.setTranslateX(this.getTranslateX() + tileWidth);
    }

    public void moveUp() {
        if (cameraY == 0) return;
        cameraY--;
        this.setTranslateY(this.getTranslateY() + tileHeight / 2);
    }

    public void moveDown() {
        if (cameraY == map.getLength() - screenHeight) return;
        cameraY++;
        this.setTranslateY(this.getTranslateY() - tileHeight / 2);
    }

    public void moveLeftUp() {
        if (cameraX == 0 || cameraY == 0) return;
        cameraX--;
        cameraY--;
        this.setTranslateX(this.getTranslateX() + tileWidth);
        this.setTranslateY(this.getTranslateY() + tileHeight / 2);
    }

    public void moveRightUp() {
        if (cameraX == map.getWidth() - screenWidth || cameraY == 0) return;
        if (cameraX != map.getWidth() - screenWidth) cameraX++;
        if (cameraY != 0) cameraY--;
        this.setTranslateX(this.getTranslateX() - tileWidth);
        this.setTranslateY(this.getTranslateY() + tileHeight / 2);
    }

    public void moveRightDown() {
        if (cameraX == map.getWidth() - screenWidth || cameraY == map.getLength() - screenHeight) return;
        if (cameraX != map.getWidth() - screenWidth) cameraX++;
        if (cameraY != map.getLength() - screenHeight) cameraY++;
        this.setTranslateX(this.getTranslateX() - tileWidth);
        this.setTranslateY(this.getTranslateY() - tileHeight / 2);
    }

    public void moveLeftDown() {
        if (cameraX == 0 || cameraY == map.getLength() - screenHeight) return;
        if (cameraX != 0) cameraX--;
        if (cameraY != map.getLength() - screenHeight) cameraY++;
        this.setTranslateX(this.getTranslateX() + tileWidth);
        this.setTranslateY(this.getTranslateY() - tileHeight / 2);
    }

    public static GameTile getGameTile(int x, int y) {
        return gameTiles[y][x];
    }

    public int getCameraX() {
        return cameraX;
    }


    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

    public void setCameraY(int cameraY) {
        this.cameraY = cameraY;
    }
}