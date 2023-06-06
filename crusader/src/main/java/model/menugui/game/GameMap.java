package model.menugui.game;

import javafx.scene.layout.Pane;
import model.game.Map;
import model.game.Tile;

public class GameMap extends Pane {
    private Map map;
    private double width;
    private double height;
    public static double tileWidth;
    public static double tileHeight;
    private int screenWidth;
    private int screenHeight;
    private int cameraX;
    private int cameraY;
    private int tilesLoaded;
    private static GameTile[][] gameTiles;
    private final boolean[][] load;

    public GameMap(Map map, double cameraX, double cameraY, double tileWidth, double tileHeight) {
        GameMap.tileWidth = tileWidth;
        GameMap.tileHeight = tileHeight;
        this.screenWidth = (int) Math.ceil(1200 / tileWidth) + 1;
        this.screenHeight = (int) Math.ceil(800 / (tileHeight/2));
        width = map.getWidth() * tileWidth;
        height = map.getLength() * (tileHeight/2);
        this.map = map;
        gameTiles = new GameTile[map.getWidth()][map.getWidth()];
        this.cameraX = (int) cameraX;
        this.cameraY = (int) cameraY;
        this.tilesLoaded = 0;
        load = new boolean[map.getLength()][map.getWidth()];
        loadMap();
        this.setOnMouseEntered(mouseEvent -> {
            this.requestFocus();
            this.setOnKeyPressed(null);
            this.setOnKeyReleased(keyEvent -> {
                String keyName = keyEvent.getCode().getName();
                if (keyName.equals("Right")) {
                    moveRight();
                }

                if (keyName.equals("Left")) {
                    moveLeft();
                }

                if (keyName.equals("Up")) {
                    moveUp();
                }

                if (keyName.equals("Down")) {
                    moveDown();
                }
            });
        });
    }

    private void loadMap() {
        if (tilesLoaded == map.getWidth() * map.getLength())
            return;
        for (int y = cameraY; y < Math.min(cameraY + screenHeight, map.getLength()); y++) {
            for (int x = cameraX; x < Math.min(cameraX + screenWidth, map.getWidth()); x++) {
                if (!load[y][x]) {
                    Tile tile = map.getTile(x, y);
                    if (y % 2 == 1) {
                        GameTile gameTile = new GameTile(tile, x * tileWidth - (tileWidth / 2),
                                y * (tileHeight / 2) - (tileHeight / 2), x, y);
                        gameTiles[y][x] = gameTile;
                        this.getChildren().add(gameTile);
                    } else {
                        GameTile gameTile = new GameTile(tile, x * tileWidth, y * (tileHeight / 2) - (tileHeight / 2), x, y);
                        gameTiles[y][x] = gameTile;
                        this.getChildren().add(gameTile);
                    }
                    tilesLoaded++;
                    load[y][x] = true;
                }
            }
        }
    }

    public void moveRight() {
        if (cameraX == map.getWidth() - screenWidth) return;
        cameraX++;
        loadMap();
        this.setTranslateX(this.getTranslateX() - tileWidth);
    }

    public void moveLeft() {
        if (cameraX == 0) return;
        cameraX--;
        loadMap();
        this.setTranslateX(this.getTranslateX() + tileWidth);
    }

    public void moveUp() {
        if (cameraY == 0) return;
        cameraY--;
        loadMap();
        this.setTranslateY(this.getTranslateY() + tileHeight / 2);
    }

    public void moveDown() {
        if (cameraY == map.getLength() - screenHeight) return;
        cameraY++;
        loadMap();
        this.setTranslateY(this.getTranslateY() - tileHeight / 2);
    }

    public void moveLeftUp() {
        if (cameraX == 0 || cameraY == 0) return;
        cameraX--;
        cameraY--;
        loadMap();
        this.setTranslateX(this.getTranslateX() + tileWidth);
        this.setTranslateY(this.getTranslateY() + tileHeight / 2);
    }

    public void moveRightUp() {
        if (cameraX == map.getWidth() - screenWidth || cameraY == 0) return;
        if (cameraX != map.getWidth() - screenWidth) cameraX++;
        if (cameraY != 0) cameraY--;
        loadMap();
        this.setTranslateX(this.getTranslateX() - tileWidth);
        this.setTranslateY(this.getTranslateY() + tileHeight / 2);
    }

    public void moveRightDown() {
        if (cameraX == map.getWidth() - screenWidth || cameraY == map.getLength() - screenHeight) return;
        if (cameraX != map.getWidth() - screenWidth) cameraX++;
        if (cameraY != map.getLength() - screenHeight) cameraY++;
        loadMap();
        this.setTranslateX(this.getTranslateX() - tileWidth);
        this.setTranslateY(this.getTranslateY() - tileHeight / 2);
    }

    public void moveLeftDown() {
        if (cameraX == 0 || cameraY == map.getLength() - screenHeight) return;
        if (cameraX != 0) cameraX--;
        if (cameraY != map.getLength() - screenHeight) cameraY++;
        loadMap();
        this.setTranslateX(this.getTranslateX() + tileWidth);
        this.setTranslateY(this.getTranslateY() - tileHeight / 2);
    }

    public static GameTile getGameTile(int x, int y) {
        return gameTiles[y][x];
    }
}