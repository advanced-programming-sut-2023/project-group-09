package model.menugui.game;

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
        this.setViewOrder(-1000);
        setSensor();
    }

    public void setSensor() {
        this.setOnMouseEntered(mouseEvent -> {
            GameMenu.currentTile = gameTile;
        });

        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && !GameMenu.selectedUnit) {
                GameViewController.unselectTiles();
                GameMenu.startSelectionTile = gameTile;
                GameMenu.endSelectionTile = gameTile;
                GameMenu.selectedTiles.add(gameTile);
                GameMenu.isSelected = true;
                gameTile.selectTile();
                if (GameMenu.selectedUnit) {
                    GameMenu.hoveringBarStateText.setText("Unit Menu");
                    GameViewController.setCenterOfBar();
                }
            }else if (GameMenu.isSelected && mouseEvent.getButton() == MouseButton.SECONDARY) {
                GameViewController.unselectTiles();
            } else if (GameMenu.selectedUnit) {
                GameViewController.doAction(true, gameTile);
                System.out.println("destination: " + gameTile.getTileX() + " " + gameTile.getTileY());
                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
                GameViewController.unselectTilesWithOutUnits();
            } else if (GameMenu.isSelected) {
                GameViewController.unselectTiles();
            }

            if (GameViewController.shopMenuPhase != -1) {
                GameViewController.setCenterOfBar(null);
                GameViewController.shopMenuPhase = -1;
                GameViewController.currentItem = null;
                GameViewController.currentCategory = null;
            }


        });
    }
}