package model.menugui.game;

import controller.gamestructure.GameImages;
import controller.human.HumanController;
import controller.human.MoveController;
import enumeration.Paths;
import enumeration.UnitMovingState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import model.building.castlebuildings.CastleBuilding;
import model.game.Tile;
import model.game.Tuple;
import model.human.military.Military;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;
import view.menus.GameMenu;

import java.util.ArrayList;

public class Troop extends ImageView {
    private Military military;
    private Tile tile;

    private GameTile gameTile;

    private Timeline move;
    private Timeline attack;
    private Timeline deadTimeline;
    int step = 0;
    int direction = 3;
    String color;
    int attackStep = 0;
    int attackDirection = 0;

    int airAttackStep = 0;
    int airAttackDirection = 0;
    int dead = 1;
    boolean overHead = false;

    public Troop(Military military, Tile tile, GameTile gameTile) {
        this.setFitWidth(40);
        this.setFitHeight(40);
        this.military = military;
        this.tile = tile;
        color = military.getGovernment().getColor();
        this.setViewOrder(-gameTile.getTileY() - 2);
        this.setTranslateX(gameTile.getTextureImage().getTranslateX() - this.getFitWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTextureImage().getTranslateY() - this.getFitHeight() / 2);
        this.gameTile = gameTile;
        setImage();
        setEventListener();
        setTimeLine();
    }

    public void changeGameTile(GameTile gameTile) {
        this.setViewOrder(-gameTile.getTileY() - 2);

        Tuple tuple = new Tuple(military.getY(), military.getX());
        tuple.setOverhead(overHead);
        if (MoveController.checkIsPathOverhead(gameTile.getTileX(), gameTile.getTileY(), military, tuple)) {
            if (tile.getBuilding() instanceof CastleBuilding castleBuilding) {
                this.setTranslateX(gameTile.getTextureImage().getTranslateX() - this.getFitWidth() / 2 + GameMap.tileWidth / 2);
                this.setTranslateY(gameTile.getTextureImage().getTranslateY()
                        - this.getFitHeight() / 2 - castleBuilding.getHeight() * GameMap.tileHeight);
            }
        }
        this.setTranslateX(gameTile.getTextureImage().getTranslateX() - this.getFitWidth() / 2 + GameMap.tileWidth / 2);
        this.setTranslateY(gameTile.getTextureImage().getTranslateY() - this.getFitHeight() / 2);
    }

    public void setEventListener() {
        this.setOnMouseEntered(mouseEvent -> GameMenu.currentTile = gameTile);
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && !GameMenu.selectedUnit) {
                GameViewController.unselectTiles();
                GameMenu.startSelectionTile = gameTile;
                GameMenu.endSelectionTile = gameTile;
                GameMenu.selectedTiles.add(gameTile);
                GameMenu.isSelected = true;
                gameTile.selectTile();
                if (GameMenu.selectedUnit) {
                    if (GameMenu.unitsCount.get("lord") == null || GameMenu.unitsCount.get("lord") != 1 || GameMenu.selectedTroops.size() != 1) {
                        if (GameMenu.unitsCount.get("lord") != null && GameMenu.unitsCount.get("lord") != 0) {
                            GameMenu.selectedTroops.removeIf(i -> i.getName().equals("lord"));
                            GameMenu.unitsCount.put("lord", 0);
                        }
                        GameMenu.hoveringBarStateText.setText("Unit Menu");
                        GameViewController.setCenterOfBar();
                    } else {
                        HumanViewController.addTypes();
                        if (GameMenu.selectedTroops.size() != 0) {
                            Military military = null;
                            for (Military m : GameMenu.selectedTroops) {
                                military = m;
                            }
                            HumanController.militaries.clear();
                            HumanController.militaries.add(military);
                        }
                    }
                }
            } else if (GameMenu.isSelected && mouseEvent.getButton() == MouseButton.SECONDARY) {
                GameViewController.unselectTiles();
            } else if (GameMenu.selectedUnit) {
                HumanViewController.doAction(true, gameTile);
                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
                GameMenu.movingState = UnitMovingState.NORMAL.getState();
                GameViewController.unselectTilesWithOutUnits();
            }
        });
    }

    public void setTimeLine() {
        move = new Timeline(new KeyFrame(Duration.millis((20 - military.getSpeed()) * 12), actionEvent -> {
            if (military.getMove() != null && military.getMove().isMoving()) {
                if (getDistance() < 7) {
                    if (GameMap.gameTroops[military.getY()][military.getX()] == null) {
                        GameMap.gameTroops[military.getY()][military.getX()] = new ArrayList<>();
                    }
                    doAttack();
                    GameMap.gameTroops[military.getY()][military.getX()].remove(this);
                    military.getMove().moveOneTurn();
                    changeGameTile(gameTile);
                    if (GameMap.gameTroops[military.getY()][military.getX()] == null) {
                        GameMap.gameTroops[military.getY()][military.getX()] = new ArrayList<>();
                    }
                    GameTile next = GameMap.getGameTile(military.getX(), military.getY());
                    direction = HumanViewController.getDirection(gameTile.getX(), gameTile.getY(), next.getX(), next.getY());
                    step = (step + 1) % 8;
                    gameTile = next;
                    tile = next.getTile();
                    GameMap.gameTroops[military.getY()][military.getX()].add(this);
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
            } else {
                doAttack();
            }
        }));
        GameMenu.timelines.add(move);
        move.setCycleCount(-1);
        move.play();
    }

    public void doAttack() {
        military.getAttack().doAttack();
        if (military.getGovernment() == null || military.getHealth() <= 0) {
            setDead();
        }
    }

    public void setDead() {
        move.stop();
        GameMenu.timelines.remove(move);
        deadTimeline = new Timeline(new KeyFrame(Duration.millis(50), actionEvent -> {
            try {
                Image troop = new Image(GameTile.class.getResource(Paths.TROOP_IMAGES.getPath()).toExternalForm() +
                        military.getName() + "/" + color + "/dead (" + dead + ").png");
                dead++;
                this.setImage(troop);
            } catch (Exception e) {
                deadTimeline.stop();
                GameMap.gameTroops[military.getY()][military.getX()].remove(this);
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
        setImage(GameImages.imageViews.get(
                military.getName() + "_" + military.getGovernment().getColor() + "_" + (step * 16 + direction + 1)));

    }


    public void setAttackStep() {
        if (military.getAttackStepCount() != 0) {
            this.attackStep = (attackStep + 1) % military.getAttackStepCount();
        }

    }

    public void setAttackDirection(int attackDirection) {
        this.attackDirection = attackDirection;
    }

    public void updateImageAttack() {
        setImage(GameImages.imageViews.get(
                military.getName() + "_" + military.getGovernment().getColor() + "_" + (attackStep * 16 + attackDirection + 129)));
    }

    public void setAirAttackStep() {
        if (military.getAirAttackStepCount() != 0) {
            this.attackStep = (attackStep + 1) % military.getAirAttackStepCount();
        }
    }

    public void setAirAttackDirection(int airAttackDirection) {
        this.airAttackDirection = airAttackDirection;
    }

    public void updateImageAirAttack() {
        setImage(GameImages.imageViews.get(
                military.getName() + "_" + military.getGovernment().getColor() + "_" + (attackStep * 16 + attackDirection + 257)));
    }
}
