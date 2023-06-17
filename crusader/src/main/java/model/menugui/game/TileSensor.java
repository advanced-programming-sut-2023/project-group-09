package model.menugui.game;

import controller.gamestructure.GameImages;
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
                GameViewController.doAction(true, gameTile);
                System.out.println("destination: " + gameTile.getTileX() + " " + gameTile.getTileY());
                GameMenu.selectedUnit = false;
                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
            } else if (GameMenu.isSelected && !GameMenu.selectedUnit) {
                for (int i = 0; i < GameMenu.selectedTiles.size(); i++) {
                    GameMenu.selectedTiles.get(i).deselectTile();
                }
                GameMenu.startSelectionTile = null;
                GameMenu.endSelectionTile = null;
                GameMenu.isSelected = false;
                GameMenu.selectedTiles.clear();
            } else if (GameMenu.isSelected && mouseEvent.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < GameMenu.selectedTiles.size(); i++) {
                    GameMenu.selectedTiles.get(i).deselectTile();
                }
                GameMenu.startSelectionTile = null;
                GameMenu.endSelectionTile = null;
                GameMenu.isSelected = false;
                GameMenu.selectedTiles.clear();
            }

            if (GameViewController.shopMenuPhase != -1) {
                GameViewController.setCenterOfBar(null);
                GameViewController.shopMenuPhase = -1;
                GameViewController.currentItem = null;
                GameViewController.currentCategory = null;
            }

            if (mouseEvent.getSource() == MouseButton.PRIMARY) {
                GameMenu.startSelectionTile = gameTile;
                GameMenu.endSelectionTile = gameTile;
                GameMenu.selectedTiles.add(gameTile);
                gameTile.selectTile();
            }
        });

        this.setOnDragDone(mouseEvent -> {
            GameMenu.currentTile = gameTile;
            gameTile.setTextureImage(GameImages.imageViews.get("red"));
        });
    }
}
