package model.game;

import enumeration.Pair;

import java.util.ArrayList;

public class Map {
    private int length, width;
    private Tile[][] mapTiles;
    private ArrayList<Pair<Integer, Integer>> defaultCastles = new ArrayList<>();

    public Map(int length, int width) {
        this.length = length;
        this.width = width;
        this.mapTiles = new Tile[length][width];
        for (int i = 0; i != length; i++) {
            for (int j = 0; j != width; j++)
             this.mapTiles[i][j] = new Tile();
        }
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
        return mapTiles[y][x];
    }

    public void setTile(int x, int y, Tile tile) {
        mapTiles[y][x] = tile;
    }

    public void setMapTiles(Tile[][] mapTiles) {
        this.mapTiles = mapTiles;
    }

    public ArrayList<Pair<Integer, Integer>> getDefaultCastles() {
        return defaultCastles;
    }

    public void setDefaultCastles(ArrayList<Pair<Integer, Integer>> defaultCastles) {
        this.defaultCastles = defaultCastles;
    }

    public void addDefaultCastle(int x , int y) {
        this.defaultCastles.add(new Pair<>(x, y));
        this.getTile(x , y).setDefaultCastle(true);
    }
}
