package model.menugui.game;

import controller.human.HumanController;
import enumeration.UnitMovingState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import model.human.military.Military;
import view.controllers.GameViewController;
import view.menus.GameMenu;

public class TileSensor extends ImageView {

    public GameTile gameTile;
    public boolean touch = false;

    public TileSensor(Image image, GameTile gameTile) {
        super(image);
        this.gameTile = gameTile;
        this.setViewOrder(-1000);
        setSensor();
    }

    public void setSensor() {
        this.setOnMouseEntered(mouseEvent -> GameMenu.currentTile = gameTile);

        this.setOnMouseClicked(mouseEvent -> {
//            if (mouseEvent.getButton() == MouseButton.PRIMARY && !GameMenu.selectedUnit) {
//                GameViewController.unselectTiles();
//                GameMenu.startSelectionTile = gameTile;
//                GameMenu.endSelectionTile = gameTile;
//                GameMenu.selectedTiles.add(gameTile);
//                GameMenu.isSelected = true;
//                gameTile.selectTile();
//                if (GameMenu.selectedUnit) {
//                    if (GameMenu.unitsCount.get("lord") == null || GameMenu.unitsCount.get("lord") != 1 || GameMenu.selectedTroops.size() != 1) {
//                        if (GameMenu.unitsCount.get("lord") != null && GameMenu.unitsCount.get("lord") != 0) {
//                            GameMenu.selectedTroops.removeIf(i -> i.getName().equals("lord"));
//                            GameMenu.unitsCount.put("lord", 0);
//                        }
//                        GameMenu.hoveringBarStateText.setText("Unit Menu");
//                        GameViewController.setCenterOfBar();
//                    } else {
//                        GameViewController.addTypes();
//                        if (GameMenu.selectedTroops.size() != 0) {
//                            Military military = null;
//                            for (Military m : GameMenu.selectedTroops) {
//                                military = m;
//                            }
//                            HumanController.militaries.clear();
//                            HumanController.militaries.add(military);
//                            System.out.println(HumanController.militaries);
//                        }
//                    }
//                }
//            } else if (GameMenu.isSelected && mouseEvent.getButton() == MouseButton.SECONDARY) {
//                GameViewController.unselectTiles();
//            } else if (GameMenu.selectedUnit) {
//                GameViewController.doAction(true, gameTile);
//                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
//                GameMenu.movingState = UnitMovingState.NORMAL.getState();
//                GameViewController.unselectTilesWithOutUnits();
//            } else if (GameMenu.isSelected) {
//                GameViewController.unselectTiles();
//            }
//
//            if (GameViewController.shopMenuPhase != -1) {
//                GameViewController.setCenterOfBar(null);
//                GameViewController.shopMenuPhase = -1;
//                GameViewController.currentItem = null;
//                GameViewController.currentCategory = null;
//            }


        });
    }
}