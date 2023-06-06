package model.menugui.game;

import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.game.Tile;

public class GameTile extends StackPane {
    private Tile tile;
    private double x;
    private double y;
    private int tileX;
    private int tileY;
    private double width;
    private double height;
    private Rectangle textureImage;
    private Rectangle buildingImage;

    public GameTile(Tile tile, double x, double y, int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.x = x;
        this.y = y;
        this.width = GameMap.tileWidth;
        this.height = GameMap.tileHeight;
        this.tile = tile;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        textureImage = new Rectangle(width, height);
        textureImage.setViewOrder(0);
        textureImage.setTranslateX(x);
        textureImage.setTranslateY(y);
        refreshTile();
        this.getChildren().addAll(textureImage);
    }

    public void refreshTile() {
        setTexture();
        setBuilding();
    }

    public void setTexture() {
        textureImage.setFill(new ImagePattern(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                + "textures/" + tile.getTexture().getName() + ".png").toExternalForm())));
    }

    public void setBuilding() {
        if (tileX == 20 && tileY == 20) {
            buildingImage = new Rectangle(width * 4, height * 7);
            buildingImage.setFill(new ImagePattern(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "buildings/tunnelersGuild.png").toExternalForm())));
            buildingImage.setTranslateX(x);
            buildingImage.setTranslateY(y);
            buildingImage.setViewOrder(-1);
            this.getChildren().add(buildingImage);
        }
    }
}
