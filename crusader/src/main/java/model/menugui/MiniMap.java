package model.menugui;

import controller.gamestructure.GameMaps;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.game.Map;
import model.game.Tile;
import view.menus.GameMenu;

public class MiniMap extends StackPane {
    private final GraphicsContext graphicsContext;

    private final Canvas canvas;
    private int startX;
    private int startY;

    private final int width;
    private final int height;
    private final int pointerWidth = 50;
    private final int pointerHeight = 60;
    private Rectangle pointer;

    private final Map map;

    public MiniMap(int width, int height, int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.setMaxWidth(width);
        this.setMinWidth(width);
        this.setMaxHeight(width);
        this.setMaxHeight(width);
        canvas = new Canvas(width, height);
        GameMaps.createMap1();
        setPointer();
        this.getChildren().addAll(canvas, pointer);
        map = GameMaps.largeMaps.get(0);
        graphicsContext = canvas.getGraphicsContext2D();
        this.setOnMouseClicked(mouseEvent -> {
            this.requestFocus();
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
        this.setOnMouseExited(mouseEvent -> {
            GameMenu.menuBar.requestFocus();
            this.setOnKeyPressed(null);
        });
        paintMap();
    }

    public void setPointer() {
        pointer = new Rectangle(pointerWidth, pointerHeight);
        pointer.setStyle("-fx-fill: transparent");
        pointer.setStroke(Color.WHITE);
        pointer.setStrokeWidth(2);
        pointer.setTranslateX(startX - (double) width / 2 + (double) pointerWidth / 2);
        pointer.setTranslateY(startY - (double) height / 2 + (double) pointerHeight / 2);
    }

    private synchronized void paintMap() {
        graphicsContext.clearRect(0, 0, width, height);
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                if (i == map.getWidth() || j == map.getLength()) {
                    return;
                }
                Tile tile = map.getTile(i, j);
                if (tile.getTree() != null) {
                    pixelWriter.setColor(i - startX, j - startY, Color.DARKGREEN);
                    continue;
                }

                if (tile.getRockDirection() != null) {
                    pixelWriter.setColor(i - startX, j - startY, Color.DARKGRAY);
                    continue;
                }
                pixelWriter.setColor(i - startX, j - startY, tile.getColor());
            }
        }
    }


    private void moveLeft() {
        if (startX + getX() == 0) {
            return;
        }
        if (Math.abs(getX() - 25) < 0.5 && startX + getX() <= 25) {
            pointer.setTranslateX(pointer.getTranslateX() - 1);
            return;
        }

        if (Math.abs(getX() - 25) < 0.5) {
            startX -= 1;
            paintMap();
            return;
        }

        pointer.setTranslateX(pointer.getTranslateX() - 1);
    }

    private void moveRight() {
        if (startX + getX() == map.getWidth() - pointerWidth) {
            return;
        }
        if (Math.abs(getX() - (width - (pointerWidth + 25))) < 0.5 && map.getWidth() - (startX + getX()) <= (pointerWidth + 25)) {
            pointer.setTranslateX(pointer.getTranslateX() + 1);
            return;
        }

        if (Math.abs(getX() - (width - (pointerWidth + 25))) < 0.5) {
            startX += 1;
            paintMap();
            return;
        }

        pointer.setTranslateX(pointer.getTranslateX() + 1);
    }

    private void moveUp() {
        if (startY + getY() == 0) {
            return;
        }
        if (Math.abs(getY() - 25) < 0.5 && startY + getY() <= 25) {
            pointer.setTranslateY(pointer.getTranslateY() - 1);
            return;
        }

        if (Math.abs(getY() - 25) < 0.5) {
            startY -= 1;
            paintMap();
            return;
        }

        pointer.setTranslateY(pointer.getTranslateY() - 1);
    }

    private void moveDown() {
        if (startY + getY() == map.getLength() - pointerHeight) {
            return;
        }
        if (Math.abs(getY() - (height - (pointerHeight + 25))) < 0.5 && map.getLength() - (startY + getY()) <= (pointerHeight + 25)) {
            pointer.setTranslateY(pointer.getTranslateY() + 1);
            return;
        }

        if (Math.abs(getY() - (height - (pointerHeight + 25))) < 0.5) {
            startY += 1;
            paintMap();
            return;
        }

        pointer.setTranslateY(pointer.getTranslateY() + 1);
    }

    private double getX() {
        return pointer.getTranslateX() + (double) width / 2 - (double) pointerWidth / 2;
    }

    private double getY() {
        return pointer.getTranslateY() + (double) height / 2 - (double) pointerHeight / 2;
    }
}