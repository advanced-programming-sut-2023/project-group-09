package model.menugui.game;

import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.building.Building;
import model.game.Tile;

public class GameTile extends StackPane {
    private Tile tile;
    private double x;
    private double y;
    private int tileX;
    private int tileY;
    private double width;
    private double height;
    private ImageView textureImage;
    private ImageView buildingImage;

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
        textureImage = new ImageView();
        textureImage.setFitWidth(width);
        textureImage.setFitHeight(height);
        textureImage.setTranslateX(x);
        textureImage.setTranslateY(y);
        textureImage.setViewOrder(0);
        refreshTile();
        this.getChildren().addAll(textureImage);
    }

    public void refreshTile() {
        setTexture();
        setBuilding();
    }

    public void setTexture() {
        textureImage.setImage(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                + "textures/" + tile.getTexture().getName() + "/" + tile.getTextureNum() + ".png").toExternalForm()));
    }

    public void setBuilding() {
        if (tileX == 10 && tileY == 20) {
            Building building = tile.getBuilding();
            textureImage.setImage(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/red.png").toExternalForm()));
            buildingImage = new ImageView(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "buildings/stockPile.png").toExternalForm()));
            buildingImage.setTranslateX(x);
            buildingImage.setTranslateY(y - buildingImage.getImage().getHeight() / 2 + textureImage.getFitHeight() / 2);
//            buildingImage.setViewOrder(-1);
            this.setViewOrder(-1);
            this.getChildren().add(buildingImage);
        }

        if (tileX == 20 && tileY == 20) {
            Building building = tile.getBuilding();
            textureImage.setImage(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "textures/red.png").toExternalForm()));
            buildingImage = new ImageView(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "buildings/engineerGuild.png").toExternalForm()));
            buildingImage.setTranslateX(x - buildingImage.getImage().getWidth() * (-1.0 / 6));
            buildingImage.setTranslateY(y - buildingImage.getImage().getHeight() / 2 + textureImage.getFitHeight() / 2);
//            buildingImage.setViewOrder(-1);
            this.setViewOrder(-1);
            this.getChildren().add(buildingImage);
        }
    }
}
