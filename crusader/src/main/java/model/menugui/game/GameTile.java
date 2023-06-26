package model.menugui.game;

import controller.BuildingController;
import controller.FileController;
import controller.GovernmentController;
import controller.MapController;
import controller.gamestructure.GameImages;
import controller.human.HumanController;
import enumeration.Paths;
import enumeration.UnitMovingState;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.building.castlebuildings.MainCastle;
import model.game.Tile;
import model.human.military.Military;
import view.controllers.GameViewController;
import view.controllers.HumanViewController;
import view.menus.GameMenu;

import java.util.Random;

public class GameTile {
    private Tile tile;
    private double x;
    private double y;
    private int tileX;
    private int tileY;
    private double width;
    private double height;
    private ImageView textureImage;
    private ImageView buildingImage;
    private ImageView humanImage;
    private ImageView treeImage;
    private ImageView rockImage;
    private static int tileXOn, tileYOn;
    public boolean touch = false;

    public GameTile(Tile tile, double x, double y, int tileX, int tileY) {
        this.tileX = tileXOn = tileX;
        this.tileY = tileYOn = tileY;
        this.x = x;
        this.y = y;
        this.width = GameMap.tileWidth;
        this.height = GameMap.tileHeight;
        this.tile = tile;
        textureImage = new ImageView();
        textureImage.setFitWidth(width);
        textureImage.setFitHeight(height);
        textureImage.setTranslateX(x);
        textureImage.setTranslateY(y);
        textureImage.setViewOrder(1);
        refreshTile();
        setEventListener();
        setSensor();
    }

    private void setEventListener() {
        textureImage.setOnMouseClicked(mouseEvent -> {
            System.out.println(textureImage);
        });
    }

    public void refreshTile() {
        setTexture();
        setBuilding();
        setTree();
        setPit();
        setRock();
    }

    public void selectTile() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.4);
        if (textureImage != null) textureImage.setEffect(colorAdjust);
        if (buildingImage != null) buildingImage.setEffect(colorAdjust);
        if (humanImage != null) humanImage.setEffect(colorAdjust);
        if (treeImage != null) treeImage.setEffect(colorAdjust);
        HumanViewController.divideTroops(tile);
    }

    public void deselectTile() {
        if (textureImage != null) textureImage.setEffect(null);
        if (buildingImage != null) buildingImage.setEffect(null);
        if (humanImage != null) humanImage.setEffect(null);
        if (treeImage != null) treeImage.setEffect(null);
    }

    public void setTexture() {
        GameMenu.gameMap.getChildren().remove(textureImage);
        Image image = GameImages.imageViews.get(tile.getTexture().getName() + tile.getTextureNum());
        textureImage.setImage(image);
        GameMenu.gameMap.getChildren().add(textureImage);
    }

    public void setBuilding() {
        Building building = tile.getBuilding();
        if (building == null && buildingImage != null) {
            GameMenu.gameMap.getChildren().remove(buildingImage);
        }
        if (building != null && building.getEndX() == tileX && building.getEndY() == tileY) {
            Image image;
            if (building instanceof Gatehouse) {
                if (((Gatehouse) building).isRightSide()) {
                    if (((Gatehouse) building).isOpen())
                        image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                                + "buildings/" + building.getName() + "Right.png").toExternalForm());
                    else
                        image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                                + "buildings/" + building.getName() + "ClosedRight.png").toExternalForm());
                } else {
                    if (((Gatehouse) building).isOpen())
                        image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                                + "buildings/" + building.getName() + ".png").toExternalForm());
                    else
                        image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                                + "buildings/" + building.getName() + "Closed.png").toExternalForm());
                }
            } else {
                image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                        + "buildings/" + building.getName() + ".png").toExternalForm());
            }
            buildingImage = new ImageView(image);
            double translateX = image.getWidth() *
                    ((double) building.getLength() - building.getWidth()) / (building.getLength() + building.getWidth()) / 2;
            buildingImage.setTranslateX(translateX - image.getWidth() / 2 + textureImage.getFitWidth() / 2 + textureImage.getTranslateX());
            buildingImage.setTranslateY(-image.getHeight() + textureImage.getFitHeight() + textureImage.getTranslateY());
            buildingImage.setViewOrder(-tileY - 1);
            GameMenu.gameMap.getChildren().add(buildingImage);
            buildingImage.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY && building.getGovernment().equals
                        (GovernmentController.getCurrentGovernment()) && !GameMenu.selectedUnit) {
                    if (GameViewController.isDelete) {
                        if (!(building instanceof MainCastle)) {
                            MapController.deleteBuilding(building);
                            GameMenu.hoveringBarStateText.setText("Delete building was successful!");
                            this.refreshTile();
                            GameViewController.isDelete = false;
                            GameMenu.scene.setCursor(Cursor.DEFAULT);
                        } else {
                            GameMenu.hoveringBarStateText.setText("You cannot delete Main Castle!");
                        }
                    } else {
                        if (mouseEvent.getClickCount() == 2) {
                            FileController.copyBuildingNameToClipboard(tile.getBuilding().getName());
                            GameMenu.hoveringBarStateText.setText(GameViewController.buildingNameToName
                                    .get(tile.getBuilding().getName()) + " Copied!");
                        } else if (mouseEvent.getClickCount() == 1) {
                            GameViewController.selectedBuilding = building;
                            BuildingController.setBuilding(building);

                            GameViewController.setCenterOfBar(building.getName());
                        }
                    }
                } else if (GameMenu.selectedUnit) {
                    HumanViewController.doAction(true, this);
                    GameMenu.root.getChildren().remove(GameMenu.selectCursor);
                    GameMenu.movingState = UnitMovingState.NORMAL.getState();
                    GameViewController.unselectTilesWithOutUnits();
                }
            });
        }
    }

    public void setRock() {
        RockDirections rockDirections = tile.getRockDirection();
        if (rockDirections != null) {
            System.out.println("Yep!");
            String rockNumber = Integer.toString(new Random().nextInt(16) + 1);
            Image image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()).toExternalForm()
                    + "rocks/Image (" + rockNumber + ").png");
            rockImage = new ImageView(image);
            rockImage.setFitWidth(GameMap.tileWidth);
            rockImage.setFitHeight(GameMap.tileHeight);
            rockImage.setTranslateY(-rockImage.getFitHeight() + textureImage.getFitHeight() + textureImage.getTranslateY());
            rockImage.setTranslateX(textureImage.getTranslateX() - rockImage.getFitWidth() / 2 + textureImage.getFitWidth() / 2);
            rockImage.setViewOrder(-tileY - 1);
            GameMenu.gameMap.getChildren().add(rockImage);
        }
    }

    public void setTree() {
        Trees tree = tile.getTree();
        if (tree != null) {
            String shrubNumber = "";
            if (tree.equals(Trees.DESERT_SHRUB))
                shrubNumber = Integer.toString(new Random().nextInt(6) + 1);
            Image image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "trees/" + tree.getTreeName() + shrubNumber + ".png").toExternalForm());
            treeImage = new ImageView(image);
            treeImage.setTranslateY(-image.getHeight() + textureImage.getFitHeight() + textureImage.getTranslateY());
            treeImage.setTranslateX(textureImage.getTranslateX() - image.getWidth() / 2 + textureImage.getFitWidth() / 2);
            treeImage.setViewOrder(-tileY - 1);
            GameMenu.gameMap.getChildren().add(treeImage);
        }
    }

    public void setPit() {
        if (tile.isPit()) {
            System.out.println("is pit!");
            Image image;
            image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "buildings/killingPit.png").toExternalForm());
            buildingImage = new ImageView(image);
            buildingImage.setTranslateX(-image.getWidth() / 2 + textureImage.getFitWidth() / 2 + textureImage.getTranslateX());
            buildingImage.setTranslateY(-image.getHeight() + textureImage.getFitHeight() + textureImage.getTranslateY());
            buildingImage.setViewOrder(-tileY - 1);
            GameMenu.gameMap.getChildren().add(buildingImage);
            buildingImage.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (mouseEvent.getClickCount() == 2) {
                        FileController.copyBuildingNameToClipboard("killingPit");
                        GameMenu.hoveringBarStateText.setText("Killing Pit Copied!");
                    }
                }
            });
        }
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public ImageView getTextureImage() {
        return textureImage;
    }

    public void setTextureImage(Image image) {
        this.textureImage.setImage(image);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setSensor() {
        textureImage.setOnMouseEntered(mouseEvent -> {
            System.out.println(this.getTileX() + " " + this.getTileY());
            GameMenu.currentTile = this;
        });

        textureImage.setOnMouseClicked(mouseEvent -> {
            if (GameViewController.isDelete) {
                GameViewController.isDelete = false;
                GameMenu.scene.setCursor(Cursor.DEFAULT);
            } else if (mouseEvent.getButton() == MouseButton.PRIMARY && !GameMenu.selectedUnit && !GameViewController.isTextureSelected) {
                GameViewController.unselectTiles();
                GameMenu.startSelectionTile = this;
                GameMenu.endSelectionTile = this;
                GameMenu.selectedTiles.add(this);
                GameMenu.isSelected = true;
                this.selectTile();
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
                            System.out.println(HumanController.militaries);
                        }
                    }
                }
            } else if (GameMenu.isSelected && mouseEvent.getButton() == MouseButton.SECONDARY && !GameViewController.isTextureSelected) {
                GameViewController.unselectTiles();
            } else if (GameMenu.selectedUnit && !GameViewController.isTextureSelected) {
                HumanViewController.doAction(true, this);
                GameMenu.root.getChildren().remove(GameMenu.selectCursor);
                GameMenu.movingState = UnitMovingState.NORMAL.getState();
                GameViewController.unselectTilesWithOutUnits();
            } else if (GameMenu.isSelected && !GameViewController.isTextureSelected) {
                GameViewController.unselectTiles();
            }

            if (GameViewController.shopMenuPhase != -1) {
                GameViewController.setCenterOfBar(null);
                GameViewController.shopMenuPhase = -1;
                GameViewController.currentItem = null;
                GameViewController.currentCategory = null;
            }
        });

        textureImage.setOnMouseDragEntered(mouseDragEvent -> {

        });
    }
}