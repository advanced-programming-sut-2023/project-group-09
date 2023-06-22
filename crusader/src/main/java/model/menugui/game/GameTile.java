package model.menugui.game;

import controller.FileController;
import controller.gamestructure.GameImages;
import enumeration.Paths;
import enumeration.dictionary.Trees;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.building.Building;
import model.building.castlebuildings.Gatehouse;
import model.game.Tile;
import model.human.Human;
import view.controllers.GameViewController;
import view.menus.GameMenu;

import java.util.ArrayList;
import java.util.Random;

public class GameTile extends StackPane {
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
    private static int tileXOn, tileYOn;

    public TileSensor tileSensor;


    public GameTile(Tile tile, double x, double y, int tileX, int tileY) {
        this.tileX = tileXOn = tileX;
        this.tileY = tileYOn = tileY;
        this.x = x;
        this.y = y;
        this.width = GameMap.tileWidth;
        this.height = GameMap.tileHeight;
        this.tile = tile;
        this.setMaxHeight(height);
        this.setMinHeight(height);
        this.setMaxWidth(width);
        this.setMinWidth(width);
        textureImage = new ImageView();
        textureImage.setFitWidth(width);
        textureImage.setFitHeight(height);
        this.setTranslateX(x);
        this.setTranslateY(y);
        textureImage.setViewOrder(1);
        refreshTile();
        setEventListener();
    }

    private void setEventListener() {
        textureImage.setOnMouseClicked(mouseEvent -> {
            System.out.println(this);
        });
    }

    public void refreshTile() {
        setTexture();
        setBuilding();
        //setTroop();
        setTree();
    }

    public void selectTile() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.4);
        if (textureImage != null) textureImage.setEffect(colorAdjust);
        if (buildingImage != null) buildingImage.setEffect(colorAdjust);
        if (humanImage != null) humanImage.setEffect(colorAdjust);
        if (treeImage != null) treeImage.setEffect(colorAdjust);
        GameViewController.divideTroops(tile);
    }

    public void deselectTile() {
        if (textureImage != null) textureImage.setEffect(null);
        if (buildingImage != null) buildingImage.setEffect(null);
        if (humanImage != null) humanImage.setEffect(null);
        if (treeImage != null) treeImage.setEffect(null);
    }

    public void setTexture() {
        this.getChildren().remove(textureImage);
        Image image = GameImages.imageViews.get(tile.getTexture().getName() + tile.getTextureNum());
        textureImage.setImage(image);
        this.getChildren().add(textureImage);
    }

    public void setBuilding() {
        Building building = tile.getBuilding();
        if (building == null && buildingImage != null) {
            this.getChildren().remove(buildingImage);
        }
        if (building != null && building.getEndX() == tileX && building.getEndY() == tileY) {
            Image image;
            if (building instanceof Gatehouse && ((Gatehouse)building).isRightSide()) {
                image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                        + "buildings/" + building.getName() + "Right.png").toExternalForm());
            } else {
                image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                        + "buildings/" + building.getName() + ".png").toExternalForm());
            }
            buildingImage = new ImageView(image);
            buildingImage.setTranslateX(image.getWidth() *
                    ((double) building.getLength() - building.getWidth()) / (building.getLength() + building.getWidth()) / 2);
            buildingImage.setTranslateY(-image.getHeight() / 2 + textureImage.getFitHeight() / 2);
            buildingImage.setViewOrder(-1);
            this.setViewOrder(-tileY);
            this.getChildren().add(buildingImage);
            buildingImage.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (mouseEvent.getClickCount() == 1) {
                        GameViewController.setCenterOfBar(building.getName());
                    } else if (mouseEvent.getClickCount() == 2) {
                        FileController.copyBuildingNameToClipboard(tile.getBuilding().getName());
                        GameMenu.hoveringBarStateText.setText(GameViewController.buildingNameToName
                                .get(tile.getBuilding().getName()) + " Copied!");
                    }
                }
            });
        }
    }

    public void setTroop() {
        ArrayList<Human> humans = tile.getHumans();
        if (humans.size() != 0) {
            Image image = new Image(GameTile.class.getResource(Paths.MAP_IMAGES.getPath()
                    + "troops/" + humans.get(0).getName() + ".png").toExternalForm());
            humanImage = new ImageView(image);
            humanImage.setTranslateY(-image.getHeight() / 2 + textureImage.getFitHeight() / 2);
            humanImage.setViewOrder(-2);
            this.setViewOrder(-tileY);
            this.getChildren().add(humanImage);
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
            treeImage.setTranslateY(-image.getHeight() / 2 + textureImage.getFitHeight() / 2);
            treeImage.setViewOrder(-1);
            this.setViewOrder(-tileY);
            this.getChildren().add(treeImage);
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
}
