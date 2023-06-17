package model.menugui.game;

import controller.GameController;
import controller.gamestructure.GameImages;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.game.Tile;
import model.human.military.Military;
import view.controllers.GameViewController;
import view.menus.GameMenu;

import java.util.ArrayList;

public class Troop extends Rectangle {
    private Military military;
    private Tile tile;

    private GameTile gameTile;

    private Timeline move;
    private Timeline attack;

    int step = 0;
    int direction = 3;

    public Troop(Military military, Tile tile, GameTile gameTile) {
        super(40, 40);
        this.military = military;
        this.tile = tile;
        this.setViewOrder(-1);
        this.setTranslateX(gameTile.getTranslateX() - this.getWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTranslateY() - this.getHeight() / 2);
        this.gameTile = gameTile;
        setImage();
        setEventListener();
        setTimeLine();
    }

    public void changeGameTile(GameTile gameTile) {
        this.setViewOrder(-1);
        this.setTranslateX(gameTile.getTranslateX() - this.getWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTranslateY() - this.getHeight() / 2);
    }

    public void setEventListener() {
        this.setOnMouseClicked(mouseEvent -> {
            GameController.selectUnit(military.getX(), military.getY(), null);
            GameMenu.setMouseCursorOnSelect();
            GameMenu.hoveringBarStateText.setText("Unit Menu");
            GameViewController.setCenterOfBar();
        });
    }

    public void setTimeLine() {
        move = new Timeline(new KeyFrame(Duration.millis(military.getSpeed() * 25), actionEvent -> {
            if (military.getMove() != null && military.getMove().isMoving()) {
                if (getDistance() < 7) {
                    if (GameMap.gameTroops[military.getY()][military.getX()] == null) {
                        GameMap.gameTroops[military.getY()][military.getX()] = new ArrayList<>();
                    }
                    GameMap.gameTroops[military.getY()][military.getX()].remove(this);
                    military.getMove().moveOneTurn();
                    changeGameTile(gameTile);
                    if (GameMap.gameTroops[military.getY()][military.getX()] == null) {
                        GameMap.gameTroops[military.getY()][military.getX()] = new ArrayList<>();
                    }
                    GameTile next = GameMap.getGameTile(military.getX(), military.getY());
                    direction = getDirection(gameTile.getX(), gameTile.getY(), next.getX(), next.getTileY());
                    step = (step + 1) % 8;
                    gameTile = next;
                    GameMap.gameTroops[military.getY()][military.getX()].add(this);
                } else {
                    double disX = this.getTranslateX() + this.getWidth() / 2 - (gameTile.getX() + GameMap.tileWidth / 2);
                    double disY = this.getTranslateY() + this.getHeight() / 2 - gameTile.getY();
                    if (Math.abs(disX) >= 5) {
                        int sign = (int) Math.signum(disX);
                        this.setTranslateX(this.getTranslateX() + (sign * (-1) * 5));
                    }
                    if (Math.abs(disY) >= 5) {
                        int sign = (int) Math.signum(disY);
                        this.setTranslateY(this.getTranslateY() + (sign * (-1) * 5));
                    }
                    step = (step + 1) % 8;
                }
                setImage();
            }
        }));
        GameMenu.timelines.add(move);
        move.setCycleCount(-1);
        move.play();
    }

    public double getDistance() {
        return Math.sqrt(Math.pow(this.getTranslateX() + this.getWidth() / 2 - (gameTile.getX() + GameMap.tileWidth / 2), 2)
                + Math.pow(this.getTranslateY() + this.getHeight() / 2 - gameTile.getY(), 2));
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

    public void setImage() {
        setFill(new ImagePattern(GameImages.imageViews.get(
                military.getName() + "_" + military.getGovernment().getColor() + "_" + (step * 16 + direction))));
    }

    public int getDirection(double x1, double y1, double x2, double y2) {
        if (Math.abs(x1 - x2) < 0.5) {
            if ((y2 - y1) > 0) {
                return 3;
            }
            return 7;
        }
        double slop = (y2 - y1) / (x2 - x1);
        if (slop < 0.5) {
            if ((y2 - y1) > 0) {
                return 5;
            }
            return 1;
        }
        if (slop >= 0) {
            if ((y2 - y1) > 0) {
                return 0;
            }
            return 4;
        } else {
            if ((y2 - y1) > 0) {
                return 2;
            }
            return 6;
        }
    }
}
