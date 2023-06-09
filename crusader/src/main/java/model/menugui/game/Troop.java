package model.menugui.game;

import controller.GameController;
import controller.gamestructure.GameImages;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.game.Tile;
import model.human.military.Military;

public class Troop extends Rectangle {
    private Military military;
    private Tile tile;

    private GameTile gameTile;

    public Troop(Military military, Tile tile, GameTile gameTile) {
        super(60, 60);
        this.military = military;
        this.tile = tile;
        this.setViewOrder(-1);
        this.setTranslateX(gameTile.getTranslateX() - this.getWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTranslateY() - this.getHeight() / 2);
        setFill(new ImagePattern(GameImages.imageViews.get(military.getName())));
        setEventListener();
    }

    public void setEventListener() {
        this.setOnMouseClicked(mouseEvent -> {
            System.out.println(GameController.selectUnit(military.getX(),military.getY(),null));
        });
    }
}
