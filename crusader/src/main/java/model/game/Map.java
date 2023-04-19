package model.game;

public class Map {
    private int length, width;
    private Tile[][] mapTiles;

    public Map(int length, int width, Tile[][] mapTiles) {
        this.length = length;
        this.width = width;
        this.mapTiles = mapTiles;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Tile[][] getMapTiles() {
        return mapTiles;
    }

    public Tile getTile(int x, int y) {
        return mapTiles[x][y];
    }

    public void setMapTiles(Tile[][] mapTiles) {
        this.mapTiles = mapTiles;
    }
}
