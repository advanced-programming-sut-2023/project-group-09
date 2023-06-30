package model.menugui;

import controllers.gamestructure.GameMaps;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import model.game.Map;
import model.game.Tile;

public class PreviewMap extends StackPane {
    private final GraphicsContext graphicsContext;
    private final Canvas canvas;
    private int width;
    private int height;
    private Map map;

    public PreviewMap(Map map, int x, int y) {
        this.map = map;
        this.width = map.getWidth();
        this.height = map.getLength();
        this.setMaxWidth(this.width);
        this.setMinWidth(this.width);
        this.setMaxHeight(this.height);
        this.setMaxHeight(this.height);
        this.setTranslateX(x);
        this.setTranslateY(y);
        canvas = new Canvas(width, height);
        GameMaps.createMap1();
        this.getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        paintMap();
        this.setScaleX(300.0 / width);
        this.setScaleY(300.0 / height);
    }

    public void paintMap() {
        graphicsContext.clearRect(0, 0, width, height);
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == map.getWidth() || j == map.getLength()) {
                    return;
                }
                Tile tile = map.getTile(i, j);
                if (tile.getTree() != null) {
                    pixelWriter.setColor(i, j, Color.DARKGREEN);
                    continue;
                }

                if (tile.getRockDirection() != null) {
                    pixelWriter.setColor(i, j, Color.DARKGRAY);
                    continue;
                }
                pixelWriter.setColor(i, j, tile.getColor());
            }
        }
    }
}
