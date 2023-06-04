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
    double width = 30;
    double height  = 18;
    Rectangle image;

    public GameTile(Tile tile,double x,double y) {
        this.tile = tile;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        image = new Rectangle(width,height);
        image.setTranslateX(x);
        image.setTranslateY(y);
        if (tile.getTexture() == Textures.EARTH){
            image.setFill(new ImagePattern(
                    new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()).toExternalForm()+"textures/earth.png")));
        }else{
            image.setFill(new ImagePattern(
                    new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()).toExternalForm()+"textures/other.png")));
        }

        this.getChildren().addAll(image);

    }
}
