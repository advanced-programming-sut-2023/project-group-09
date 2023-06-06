package model.menugui.game;

import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.game.Tile;

public class GameTile extends StackPane {
    private Tile tile;
    private double width;
    private double height;
    private Rectangle textureImage;
    private Rectangle buildingImage;

    public GameTile(Tile tile, double x, double y) {
        this.width = GameMap.tileWidth;
        this.height = GameMap.tileHeight;
        this.tile = tile;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        textureImage = new Rectangle(width, height);
        textureImage.setTranslateX(x);
        textureImage.setTranslateY(y);
        setTexture();
        this.getChildren().addAll(textureImage);
    }

    public void refreshTile() {
        setTexture();
    }

    public void setTexture() {
        textureImage.setFill(new ImagePattern(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                + "textures/" + tile.getTexture().getName() + ".png").toExternalForm())));
    }

//    public void setBuilding() {
//        buildingImage = new Rectangle()
//    }
}
