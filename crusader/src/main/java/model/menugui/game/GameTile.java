package model.menugui.game;

import enumeration.Paths;
import enumeration.Textures;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.game.Tile;

public class GameTile extends StackPane {
    Tile tile;
    double width;
    double height;
    Rectangle image;

    public GameTile(Tile tile, double x, double y) {
        this.width = GameMap.tileWidth;
        this.height = GameMap.tileHeight;
        this.tile = tile;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        image = new Rectangle(width, height);
        image.setTranslateX(x);
        image.setTranslateY(y);
        setTexture();

        this.getChildren().addAll(image);
    }

    public void refreshTile() {
        setTexture();
    }

    public void setTexture() {
        image.setFill(new ImagePattern(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
        + "textures/" + tile.getTexture().getName() + ".png").toExternalForm())));
    }
}
