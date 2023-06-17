package model.menugui.game;

import controller.GameController;
import controller.gamestructure.GameImages;
import enumeration.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.controllers.GameViewController;
import view.menus.GameMenu;

import java.util.ArrayList;

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
                GameViewController.doAction(true, gameTile);
                System.out.println("destination: " + gameTile.getTileX() + " " + gameTile.getTileY());
                GameMenu.selectedUnit = false;
                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
            } else if (GameMenu.isSelected) {
                for (int i = 0; i < GameMenu.selectedTiles.size(); i++) {
                    GameMenu.selectedTiles.get(i).deselectTile();
                }
                GameMenu.startSelectionTile = null;
                GameMenu.endSelectionTile = null;
                GameMenu.isSelected = false;
                GameMenu.selectedTiles.clear();
            }
            GameMenu.startSelectionTile = gameTile;
            GameMenu.endSelectionTile = gameTile;
            GameMenu.selectedTiles.add(gameTile);
            gameTile.selectTile();
        });

        this.setOnDragDone(mouseEvent -> {
            GameMenu.currentTile = gameTile;
            gameTile.setTextureImage(GameImages.imageViews.get("red"));
        });
    }
}
