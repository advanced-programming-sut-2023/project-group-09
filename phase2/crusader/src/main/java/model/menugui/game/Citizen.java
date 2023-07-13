package model.menugui.game;

import controller.gamestructure.GameImages;
import enumeration.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.game.Tile;
import model.human.civilian.Civilian;
import view.controllers.HumanViewController;
import view.menus.GameMenu;

import java.util.ArrayList;
import java.util.HashSet;

public class Citizen extends ImageView {
    private final Civilian civilian;
    private Tile tile;

    private GameTile gameTile;

    private Timeline move;
    private Timeline deadTimeline;
    int step = 0;
    int direction = 3;
    int dead = 1;
    boolean overHead = false;

    public Citizen(Civilian civilian, Tile tile, GameTile gameTile) {
        this.setFitWidth(40);
        this.setFitHeight(40);
        this.civilian = civilian;
        this.tile = tile;
        this.setViewOrder(-1000);
        this.setTranslateX(gameTile.getTextureImage().getTranslateX() - this.getFitWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTextureImage().getTranslateY() - this.getFitHeight() / 2);
        this.gameTile = gameTile;
        setImage();
        setEventListener();
        setTimeLine();
    }

    public void changeGameTile(GameTile gameTile) {
        this.setTranslateX(gameTile.getTextureImage().getTranslateX() - this.getFitWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTextureImage().getTranslateY() - this.getFitHeight() / 2);
    }

    public void setEventListener() {
        this.setOnMouseEntered(mouseEvent -> GameMenu.currentTile = gameTile);
    }

    public void setTimeLine() {
        move = new Timeline(new KeyFrame(Duration.millis((20 - civilian.getSpeed()) * 10), actionEvent -> {
            if (civilian.getMove() != null && civilian.getMove().isMoving()) {
                if (getDistance() < 7) {
                    civilian.getMove().moveOneTurn();
                    changeGameTile(gameTile);
                    GameTile next = GameMap.getGameTile(civilian.getX(), civilian.getY());
                    direction = HumanViewController.getDirection(gameTile.getX(), gameTile.getY(), next.getX(), next.getY());
                    step = (step + 1) % 8;
                    gameTile = next;
                    tile = next.getTile();
                } else {
                    double disX = this.getTranslateX() + this.getFitWidth() / 2 - (gameTile.getX() + GameMap.tileWidth / 2);
                    double disY = this.getTranslateY() + this.getFitHeight() / 2 - gameTile.getY();
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
            if (civilian.getGovernment() == null || civilian.getHealth() <= 0){
                setDead();
            }
        }));
        GameMenu.timelines.add(move);
        move.setCycleCount(-1);
        move.play();
    }


    public void setDead() {
        move.stop();
        GameMenu.timelines.remove(move);
        deadTimeline = new Timeline(new KeyFrame(Duration.millis(50), actionEvent -> {
            try {
                Image troop;
                if (civilian.isHasJob()) {
                    troop = new Image(GameTile.class.getResource(Paths.GAME_IMAGES.getPath()).toExternalForm() +
                            "civilian/worker/dead (" + dead + ").png");
                } else {
                    troop = new Image(GameTile.class.getResource(Paths.GAME_IMAGES.getPath()).toExternalForm() +
                            "civilian/peasant/dead (" + dead + ").png");
                }
                dead++;
                this.setImage(troop);
            } catch (Exception e) {
                deadTimeline.stop();
                GameMenu.gameMap.getChildren().remove(this);
            }
        }));
        deadTimeline.setCycleCount(-1);
        deadTimeline.play();
    }

    public double getDistance() {
        return Math.sqrt(Math.pow(this.getTranslateX() + this.getFitWidth() / 2 - (gameTile.getX() + GameMap.tileWidth / 2), 2)
                + Math.pow(this.getTranslateY() + this.getFitHeight() / 2 - gameTile.getY(), 2));
    }


    public Civilian getMilitary() {
        return civilian;
    }

    public Tile getTile() {
        return tile;
    }

    public GameTile getGameTile() {
        return gameTile;
    }

    public void setImage() {
        if (civilian.isHasJob()) {
            setImage(GameImages.imageViews.get(
                    "worker_" + (step * 16 + direction + 1)));
        } else {
            setImage(GameImages.imageViews.get(
                    "peasant_" + (step * 16 + direction + 1)));
        }
    }

}
