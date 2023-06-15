package model.menugui.game;

import controller.GameController;
import controller.gamestructure.GameImages;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.game.Game;
import model.game.Tile;
import model.human.military.Military;
import view.menus.GameMenu;

public class Troop extends Rectangle {
    private Military military;
    private Tile tile;

    private GameTile gameTile;


    public Troop(Military military, Tile tile, GameTile gameTile) {
        super(40, 40);
        this.military = military;
        this.tile = tile;
        this.setViewOrder(-1);
        this.setTranslateX(gameTile.getTranslateX() - this.getWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTranslateY() - this.getHeight() / 2);
        this.gameTile = gameTile;
        gameTile.setTextureImage(GameImages.imageViews.get("red"));
        setFill(new ImagePattern(GameImages.imageViews.get(military.getName())));
        setEventListener();
    }

    public void changeGameTile(GameTile gameTile){
        this.setViewOrder(-1);
        this.setTranslateX(gameTile.getTranslateX() - this.getWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTranslateY() - this.getHeight() / 2);
        gameTile.setTextureImage(GameImages.imageViews.get("red"));
        this.gameTile = gameTile;
    }
    public void setEventListener() {
        this.setOnMouseClicked(mouseEvent -> {
            GameController.selectUnit(military.getX(),military.getY(),null);
            GameMenu.setMouseCursorOnSelect();
        });
    }

    public Military getMilitary() {
        return military;
    }

    public Tile getTile() {
        return tile;
    }

    public GameTile getGameTile() {
        return gameTile;
    }
}
