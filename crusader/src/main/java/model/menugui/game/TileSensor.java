package model.menugui.game;

import controller.FileController;
import controller.gamestructure.GameImages;
import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import view.controllers.GameViewController;
import view.menus.GameMenu;

public class TileSensor extends ImageView {

    public GameTile gameTile;

    public TileSensor(Image image, GameTile gameTile) {
        super(image);
        this.gameTile = gameTile;
        setSensor();
    }

    public void setSensor() {
        this.setOnMouseEntered(mouseEvent -> {
            GameMenu.currentTile = gameTile;
        });

        this.setOnMouseClicked(mouseEvent -> {
            if (GameMenu.selectedUnit) {

            } else {
                gameTile.setTextureImage(GameImages.imageViews.get("red"));
            }
        });

        this.setOnDragDone(mouseEvent -> {
            GameMenu.currentTile = gameTile;
            gameTile.setTextureImage(new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath() + "textures/red.png").toExternalForm()));
        });

        this.setOnMouseReleased(mouseEvent -> {
            System.out.println("it released on tile " + gameTile.getTileX() + " " + gameTile.getTileY());
        });

    }
}
