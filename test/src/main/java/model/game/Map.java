package model.game;

import enumeration.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable {

    private int index = 0;
    private int length, width;
    private Tile[][] mapTiles;
    private String name;
    private String owner;
    private transient ArrayList<Pair<Integer, Integer>> defaultCastles = new ArrayList<>();

    private int[] defaultCastlesX = new int[8];
    private int[] defaultCastlesY = new int[8];

    public Map(int length, int width) {
        this.length = length;
        this.width = width;
        this.mapTiles = new Tile[length][width];
        for (int i = 0; i != length; i++) {
            for (int j = 0; j != width; j++){
                this.mapTiles[i][j] = new Tile();
                this.mapTiles[i][j].x = j;
                this.mapTiles[i][j].y = i;
            }
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
        defaultCastles = new ArrayList<>();
        defaultCastles.clear();
        for (int i = 0; i != index; i++) {
            defaultCastles.add(new Pair<>(defaultCastlesX[i] , defaultCastlesY[i]));
        }
        return defaultCastles;
    }


    public void addDefaultCastle(int x , int y) {
        this.defaultCastles.add(new Pair<>(x, y));
        this.getTile(x , y).setDefaultCastle(true);
        this.defaultCastlesX[index] = x;
        this.defaultCastlesY[index] = y;
        index++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getIndex() {
        return this.index;
    }

    public int getDefaultCastleX(int ind) {
        return this.defaultCastlesX[ind];
    }

    public int getDefaultCastleY(int ind) {
        return this.defaultCastlesY[ind];
    }

    public void removeDefaultCastle(int tileX, int tileY) {
        getDefaultCastles();
        for (int i = 0; i != defaultCastles.size(); i++) {
            if (defaultCastles.get(i).getFirst() == tileX && defaultCastles.get(i).getSecond() == tileY) {
                defaultCastles.remove(i);
                break;
            }
        }
        index = 0;
        for (Pair<Integer , Integer> pair : defaultCastles) {
            defaultCastlesX[index] = pair.getFirst();
            defaultCastlesY[index] = pair.getSecond();
            index++;
        }
    }
}
