package model.menugui.game;

import controller.gamestructure.GameImages;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.game.Tile;
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
            gameTile.setTextureImage(new Image(TileSensor.class.getResource(Paths.MAP_IMAGES.getPath() + "textures/red.png").toExternalForm()));
            if (GameMenu.selectedUnit) {
                GameViewController.doAction(true, gameTile);
                System.out.println("destination: " + gameTile.getTileX() + " " + gameTile.getTileY());
                GameMenu.selectedUnit = false;
                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
            }
        });

        this.setOnDragDone(mouseEvent -> {
            GameMenu.currentTile = gameTile;
            gameTile.setTextureImage(GameImages.imageViews.get("red"));
        });
    }
}
