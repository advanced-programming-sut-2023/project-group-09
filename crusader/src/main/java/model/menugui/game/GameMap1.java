package model.menugui.game;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.game.Map;
import model.game.Tile;

public class GameMap1 extends Pane {
    Map map;
    double width;
    double height;
    int screenWidth = 41;
    int screenHeight = 90;
    private int cameraX;
    private int cameraY;

    private boolean[][] load;

    public GameMap1(Map map, double cameraX, double cameraY) {
        width = map.getWidth() * 30;
        height = map.getLength() * 18;
        this.map = map;
        this.cameraX = (int) cameraX;
        this.cameraY = (int) cameraY;
        load = new boolean[map.getLength()][map.getWidth()];
        loadMap();
        this.setOnMouseEntered(mouseEvent -> {
            this.requestFocus();
            this.setOnKeyPressed(null);
            this.setOnKeyPressed(keyEvent -> {
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
        for (int y = cameraY; y < Math.min(cameraY + screenHeight, map.getLength()); y++) {
            for (int x = cameraX; x < Math.min(cameraX + screenWidth, map.getWidth()); x++) {
                if (!load[y][x]) {
                    Tile tile = map.getTile(x, y);
                    if (y % 2 == 1) {
                        GameTile gameTile = new GameTile(tile, x * 30 - 15, y * 9 - 9);
                        this.getChildren().add(gameTile);
                    } else {
                        GameTile gameTile = new GameTile(tile, x * 30, y * 9 - 9);
                        this.getChildren().add(gameTile);
                    }
                    load[y][x] = false;
                }

            }
        }
    }

    private void moveRight() {
        System.out.println("move right");
        if (cameraX == map.getWidth()) {
            return;
        }
        cameraX++;
        loadMap();
        this.setTranslateX(this.getTranslateX() - 30);
    }

    private void moveLeft() {
        System.out.println("move left");
        if (cameraX == 0) return;
        cameraX--;
        loadMap();
        this.setTranslateX(this.getTranslateX() + 30);
    }

    private void moveUp() {
        System.out.println("move up");
        if (cameraY == 0) return;
        cameraY--;
        loadMap();
        this.setTranslateX(this.getTranslateY() - 9);
    }

    private void moveDown() {
        System.out.println("move down");
        if (cameraY == map.getLength()) return;
        cameraY++;
        loadMap();
        this.setTranslateX(this.getTranslateY() + 9);
    }
}