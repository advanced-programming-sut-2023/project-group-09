package model.menugui.game;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.game.Map;
import model.game.Tile;

public class GameChunk extends Pane {
    private Map map;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public GameChunk(Map map, int startX, int startY, int endX, int endY) {
        this.map = map;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                Tile tile = map.getTile(x, y);
                GameTile gameTile = new GameTile(tile, x * 30, y * 18);
                this.getChildren().add(gameTile);
            }
        }
    }
}