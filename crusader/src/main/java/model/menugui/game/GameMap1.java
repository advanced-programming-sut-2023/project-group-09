package model.menugui.game;

import javafx.scene.layout.Pane;
import model.game.Map;
import model.game.Tile;

import java.util.ArrayList;
import java.util.List;

public class GameMap1 extends Pane {
    private static final int CHUNK_SIZE = 100;

    private Map map;
    private int numChunksX;
    private int numChunksY;

    private int tileWidth;
    private int tileHeight;
    private double width;
    private double height;
    private int screenWidth;
    private int screenHeight;
    private int cameraX;
    private int cameraY;

    private static GameTile[][] gameTiles;
    private List<GameTile[][]> chunks;

    public GameMap1(Map map,int tileWidth, int tileHeight, int cameraX, int cameraY) {
        this.map = map;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.cameraX = cameraX;
        this.cameraY = cameraY;
        this.numChunksX = (int) Math.ceil((double) map.getWidth() / CHUNK_SIZE);
        this.numChunksY = (int) Math.ceil((double) map.getLength() / CHUNK_SIZE);
        this.screenWidth = (int) Math.ceil(1200 / tileWidth) + 1;
        this.screenHeight = (int) Math.ceil(800 / (tileHeight/2));
        width = map.getWidth() * tileWidth;
        height = map.getLength() * (tileHeight/2);
        this.chunks = new ArrayList<>();
        loadInitialChunks();
    }

    private void loadInitialChunks() {
        for (int chunkY = 0; chunkY < numChunksY; chunkY++) {
            for (int chunkX = 0; chunkX < numChunksX; chunkX++) {
                int startX = chunkX * CHUNK_SIZE;
                int startY = chunkY * CHUNK_SIZE;
                int endX = Math.min(startX + CHUNK_SIZE, map.getWidth());
                int endY = Math.min(startY + CHUNK_SIZE, map.getLength());
            }
        }
    }

    public void loadChunksInRange(int startX, int startY, int endX, int endY) {
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                Tile tile = map.getTile(x, y);
                if (y % 2 == 1) {
                    GameTile gameTile = new GameTile(tile, x * tileWidth - (tileWidth / 2),
                            y * (tileHeight / 2) - (tileHeight / 2), x, y);
                    gameTiles[y][x] = gameTile;
                    this.getChildren().add(0, gameTile);
                } else {
                    GameTile gameTile = new GameTile(tile, x * tileWidth, y * (tileHeight / 2) - (tileHeight / 2), x, y);
                    gameTiles[y][x] = gameTile;
                    this.getChildren().add(gameTile);
                }
            }
        }
    }

}




