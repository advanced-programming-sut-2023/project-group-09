package model.menugui;

import controllers.gamestructure.GameMaps;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.game.Map;
import model.game.Tile;
import model.human.military.Military;
import model.menugui.game.GameMap;
import view.menus.GameMenu;

public class MiniMap extends StackPane {
    private final GraphicsContext graphicsContext;

    private final Canvas canvas;
    private int startX;
    private int startY;

    private final int width;
    private final int height;
    private int pointerWidth = (int) Math.ceil((double) 1200 / GameMap.tileWidth) + 1;
    private int pointerHeight = (int) Math.ceil((double) 800 / (GameMap.tileHeight/2));
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
                    moveRight(true);
                }

                if (keyName.equals("Left")) {
                    moveLeft(true);
                }

                if (keyName.equals("Up")) {
                    moveUp(true);
                }

                if (keyName.equals("Down")) {
                    moveDown(true);
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

    public void updatePointer(double zoom) {
        double width = pointer.getWidth();
        double height = pointer.getHeight();
        pointerWidth = (int) Math.ceil((double) 1200 / (GameMap.tileWidth * zoom)) + 1;
        pointerHeight = (int) Math.ceil((double) 800 / (GameMap.tileHeight * zoom/2));
        pointer.setWidth(pointerWidth);
        pointer.setHeight(pointerHeight);

        double translateX = (pointerWidth - width)/2;
        double translateY = (pointerHeight - height)/2;

        pointer.setTranslateX(pointer.getTranslateX() + translateX);
        pointer.setTranslateY(pointer.getTranslateY() + translateY);
    }

    public void setStartX(int startX) {
        while (startX + getX() > startX){
            moveLeft(false);
        }
        while (startX + getX() < startX){
            moveRight(false);
        }
    }

    public void setStartY(int startY) {
        while (startY + getY() > startY){
            moveUp(false);
        }
        while (startY + getY() < startY){
            moveDown(false);
        }
    }

    public void setPointerWidth(int pointerWidth) {
        this.pointerWidth = pointerWidth;
    }

    public void setPointerHeight(int pointerHeight) {
        this.pointerHeight = pointerHeight;
    }


    public synchronized void paintMap() {
        graphicsContext.clearRect(0, 0, width, height);
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                if (i == map.getWidth() || j == map.getLength()) {
                    return;
                }
                Tile tile = map.getTile(i, j);
                if (tile.getMilitaries().size() > 0){
                    Military military = tile.getMilitaries().get(0);
                    switch (military.getGovernment().getColor()){
                        case "red":
                            pixelWriter.setColor(i - startX, j - startY, Color.RED);
                            break;
                        case "blue":
                            pixelWriter.setColor(i - startX, j - startY, Color.BLUE);
                            break;
                        case "skyBlue":
                            pixelWriter.setColor(i - startX, j - startY, Color.SKYBLUE);
                            break;
                        case "orange":
                            pixelWriter.setColor(i - startX, j - startY, Color.ORANGE);
                            break;
                        case "yellow":
                            pixelWriter.setColor(i - startX, j - startY, Color.YELLOW);
                            break;
                        case "purple":
                            pixelWriter.setColor(i - startX, j - startY, Color.PURPLE);
                            break;
                        case "grey":
                            pixelWriter.setColor(i - startX, j - startY, Color.BLACK);
                            break;
                        case "green":
                            pixelWriter.setColor(i - startX, j - startY, Color.GREEN);
                            break;
                    }
                    continue;
                }
                if (tile.getBuilding() != null){
                    pixelWriter.setColor(i - startX, j - startY, Color.BROWN);
                    continue;
                }





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


    public void moveLeft(boolean moveMap) {
        if (startX + getX() == 0) {
            return;
        }
        if (moveMap) {
            GameMenu.gameMap.moveLeft();
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

    public void moveRight(boolean moveMap) {
        if (startX + getX() == map.getWidth() - pointerWidth) {
            return;
        }
        if (moveMap) {
            GameMenu.gameMap.moveRight();
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

    public void moveUp(boolean moveMap) {
        if (startY + getY() == 0) {
            return;
        }
        if (moveMap) {
            GameMenu.gameMap.moveUp();
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

    public void moveDown(boolean moveMap) {
        if (startY + getY() == map.getLength() - pointerHeight) {
            return;
        }
        if (moveMap) {
            GameMenu.gameMap.moveDown();
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


    public void moveLeftUp() {
        if (startY + getY() == 0) {
            return;
        }
        if (startX + getX() == 0) {
            return;
        }
        moveLeft(false);
        moveUp(false);
        GameMenu.gameMap.moveLeftUp();
    }

    public void moveRightUp() {
        if (startY + getY() == 0) {
            return;
        }
        if (startX + getX() == map.getWidth() - pointerWidth) {
            return;
        }
        moveRight(false);
        moveUp(false);
        GameMenu.gameMap.moveRightUp();
    }

    public void moveRightDown() {
        if (startY + getY() == map.getLength() - pointerHeight) {
            return;
        }
        if (startX + getX() == map.getWidth() - pointerWidth) {
            return;
        }
        moveRight(false);
        moveDown(false);
        GameMenu.gameMap.moveRightDown();
    }

    public void moveLeftDown() {
        if (startY + getY() == map.getLength() - pointerHeight) {
            return;
        }
        if (startX + getX() == 0) {
            return;
        }
        moveLeft(false);
        moveDown(false);
        GameMenu.gameMap.moveLeftDown();
    }

    private double getX() {
        return pointer.getTranslateX() + (double) width / 2 - (double) pointerWidth / 2;
    }

    private double getY() {
        return pointer.getTranslateY() + (double) height / 2 - (double) pointerHeight / 2;
    }
}