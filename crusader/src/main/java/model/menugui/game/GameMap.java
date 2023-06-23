package model.menugui.game;

import controller.gamestructure.GameImages;
import javafx.scene.layout.Pane;
import model.game.Map;
import model.game.Tile;
import view.menus.GameMenu;

import java.util.ArrayList;

public class GameMap extends Pane {
    private final Map map;
    private double width;
    private double height;
    public static double tileWidth;
    public static double tileHeight;
    private final int screenWidth;
    private final int screenHeight;
    private int cameraX;
    private int cameraY;
    private int tilesLoaded;
    private static GameTile[][] gameTiles;
    public static ArrayList<Troop>[][] gameTroops;
    private final boolean[][] load;

    private static final double ZOOM_FACTOR = 1.1;

    public GameMap(Map map, double cameraX, double cameraY, double tileWidth, double tileHeight) {
        GameMap.tileWidth = (tileWidth * this.getScaleX());
        GameMap.tileHeight = (tileHeight * this.getScaleY());
        this.screenWidth = (int) Math.ceil(1200 / (tileWidth * this.getScaleX())) + 1;
        this.screenHeight = (int) Math.ceil(800 / ((tileHeight * this.getScaleY()) / 2));
        width = map.getWidth() * (tileWidth * this.getScaleX());
        height = map.getLength() * ((tileHeight * this.getScaleY()) / 2);
        this.map = map;
        gameTiles = new GameTile[map.getWidth()][map.getWidth()];
        gameTroops = new ArrayList[map.getWidth()][map.getWidth()];
        this.cameraX = (int) cameraX;
        this.cameraY = (int) cameraY;
        this.tilesLoaded = 0;
        load = new boolean[map.getLength()][map.getWidth()];
        this.setOnScroll(event -> {
            double zoomFactor = event.getDeltaY() > 0 ? ZOOM_FACTOR : 1 / ZOOM_FACTOR;
            zoom(zoomFactor);
            event.consume();
        });
    }

    public void loadMap() {
        if (tilesLoaded == map.getWidth() * map.getLength())
            return;
        for (int y = 0; y < map.getLength(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (!load[y][x]) {
                    Tile tile = map.getTile(x, y);
                    double xx;
                    double yy = y * ((tileHeight * this.getScaleY()) / 2) - ((tileHeight * this.getScaleY()) / 2);
                    if (y % 2 == 1) {
                        xx = x * (tileWidth * this.getScaleX()) - ((tileWidth * this.getScaleX()) / 2);
                        GameTile gameTile = new GameTile(tile, xx, yy, x, y);
                        gameTiles[y][x] = gameTile;
                    } else {
                        xx = x * (tileWidth * this.getScaleX());
                        GameTile gameTile = new GameTile(tile, xx, yy, x, y);
                        gameTiles[y][x] = gameTile;
                    }
                    tilesLoaded++;
                    load[y][x] = true;
                }
            }
        }
    }

    public void zoom(double zoomFactor) {
        if (this.getScaleX() * zoomFactor > 1 && this.getScaleX() * zoomFactor < 1.5) {
            // Apply the scale and translation adjustments
            double newWidth = this.getWidth() * this.getScaleX() * zoomFactor;
            double newHeight = this.getHeight() * this.getScaleY() * zoomFactor;
            double lastWidth = this.getWidth() * this.getScaleX();
            double lastHeight = this.getHeight() * this.getScaleY();
            double translateX = (newWidth - lastWidth) / 2;
            double translateY = (newHeight - lastHeight) / 2;
            this.setScaleX(this.getScaleX() * zoomFactor);
            this.setScaleY(this.getScaleY() * zoomFactor);
            this.setTranslateX((this.getTranslateX() + translateX));
            this.setTranslateY((this.getTranslateY() + translateY));
            GameMenu.miniMap.updatePointer(this.getScaleX());
        }
    }

    public Map getMap() {
        return map;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setCameraX(int cameraX) {
        this.cameraX = cameraX;
    }

    public void setCameraY(int cameraY) {
        this.cameraY = cameraY;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void moveRight() {
        if (cameraX == map.getWidth() - screenWidth) return;
        cameraX++;
        this.setTranslateX(this.getTranslateX() - (tileWidth * this.getScaleX()));
    }

    public void moveLeft() {
        if (cameraX == 0) return;
        cameraX--;
        this.setTranslateX(this.getTranslateX() + (tileWidth * this.getScaleX()));
    }

    public void moveUp() {
        if (cameraY == 0) return;
        cameraY--;
        this.setTranslateY(this.getTranslateY() + (tileHeight * this.getScaleY()) / 2);
    }

    public void moveDown() {
        if (cameraY == map.getLength() - screenHeight) return;
        cameraY++;
        this.setTranslateY(this.getTranslateY() - (tileHeight * this.getScaleY()) / 2);
    }

    public void moveLeftUp() {
        if (cameraX == 0 || cameraY == 0) return;
        cameraX--;
        cameraY--;
        this.setTranslateX(this.getTranslateX() + (tileWidth * this.getScaleX()));
        this.setTranslateY(this.getTranslateY() + (tileHeight * this.getScaleY()) / 2);
    }

    public void moveRightUp() {
        if (cameraX == map.getWidth() - screenWidth || cameraY == 0) return;
        if (cameraX != map.getWidth() - screenWidth) cameraX++;
        if (cameraY != 0) cameraY--;
        this.setTranslateX(this.getTranslateX() - (tileWidth * this.getScaleX()));
        this.setTranslateY(this.getTranslateY() + (tileHeight * this.getScaleY()) / 2);
    }

    public void moveRightDown() {
        if (cameraX == map.getWidth() - screenWidth || cameraY == map.getLength() - screenHeight) return;
        if (cameraX != map.getWidth() - screenWidth) cameraX++;
        if (cameraY != map.getLength() - screenHeight) cameraY++;
        this.setTranslateX(this.getTranslateX() - (tileWidth * this.getScaleX()));
        this.setTranslateY(this.getTranslateY() - (tileHeight * this.getScaleY()) / 2);
    }

    public void moveLeftDown() {
        if (cameraX == 0 || cameraY == map.getLength() - screenHeight) return;
        if (cameraX != 0) cameraX--;
        if (cameraY != map.getLength() - screenHeight) cameraY++;
        this.setTranslateX(this.getTranslateX() + (tileWidth * this.getScaleX()));
        this.setTranslateY(this.getTranslateY() - (tileHeight * this.getScaleY()) / 2);
    }

    public static GameTile getGameTile(int x, int y) {
        return gameTiles[y][x];
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }

}