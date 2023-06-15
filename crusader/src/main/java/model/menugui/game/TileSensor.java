package model.menugui.game;

import controller.gamestructure.GameImages;
import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

            }
        });

        this.setOnDragDone(mouseEvent -> {
            GameMenu.currentTile = gameTile;
            gameTile.setTextureImage(GameImages.imageViews.get("red"));
        });
    }
}
