package view.controllers;

import controller.FileController;
import controller.GameController;
import controller.MapController;
import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameImages;
import enumeration.Pair;
import enumeration.Paths;
import enumeration.Textures;
import enumeration.UnitMovingState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.building.Building;
import model.game.Tile;
import model.human.military.Military;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import model.menugui.game.Troop;
import view.animations.MoveTroop;
import view.menus.GameMenu;
import view.menus.LoginMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class GameViewController {

    public static String nameOfPageInBar;
    public static Timeline timeline;
    public static boolean isSelected = false;
    public static boolean isTextureSelected = false;
    public static int tileX, tileY;

    public static HashMap<String, String> buildingNameToFileName = new HashMap<>();
    public static HashMap<String, String> buildingNameToPicName = new HashMap<>();
    public static HashMap<String, String> buildingNameToName = new HashMap<>();
    public static HashMap<String, Double> buildingScales = new HashMap<>();
    public static HashMap<String, Double> buildingCoordinates = new HashMap<>();

    public static void createShortcutBars(Pane gamePane, Text text) {
        setCenterOfBar();

        ImageView clipboardSign = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                + "icons/clipboardIcon.png");
        clipboardSign.setTranslateX(657);
        clipboardSign.setTranslateY(20);
        clipboardSign.setScaleX(0.08);
        clipboardSign.setScaleY(0.08);
        gamePane.getChildren().add(clipboardSign);
        setEventsForClipboardIcon(clipboardSign);

        ImageView castleBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/castleBuildingsIcon.png");
        castleBuildingsImage.setTranslateX(275);
        castleBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(castleBuildingsImage);
        setHoverEventForBar(castleBuildingsImage, "Castle Buildings");

        ImageView industryBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/industryBuildingsIcon.png");
        industryBuildingsImage.setTranslateX(310);
        industryBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(industryBuildingsImage);
        setHoverEventForBar(industryBuildingsImage, "Industry Buildings");

        ImageView farmBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/farmBuildingsIcon.png");
        farmBuildingsImage.setTranslateX(345);
        farmBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(farmBuildingsImage);
        setHoverEventForBar(farmBuildingsImage, "Farm Buildings");

        ImageView townBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/townBuildingsIcon.png");
        townBuildingsImage.setTranslateX(380);
        townBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(townBuildingsImage);
        setHoverEventForBar(townBuildingsImage, "Town Buildings");

        ImageView weaponsBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/weaponsBuildingsIcon.png");
        weaponsBuildingsImage.setTranslateX(415);
        weaponsBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(weaponsBuildingsImage);
        setHoverEventForBar(weaponsBuildingsImage, "Weapons Buildings");

        ImageView foodProcessingImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/foodProcessingBuildingsIcon.png");
        foodProcessingImage.setTranslateX(450);
        foodProcessingImage.setTranslateY(185);
        gamePane.getChildren().add(foodProcessingImage);
        setHoverEventForBar(foodProcessingImage, "Food Processing Buildings");

        ImageView orderOfMeritImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/orderOfMeritIcon.png");
        orderOfMeritImage.setTranslateX(675);
        orderOfMeritImage.setTranslateY(185);
        gamePane.getChildren().add(orderOfMeritImage);
        setHoverEventForBar(orderOfMeritImage, "Order Of Merit");


        ImageView keyImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/keyIcon.png");
        keyImage.setTranslateX(760);
        keyImage.setTranslateY(85);
        gamePane.getChildren().add(keyImage);
        setHoverEventForBar(keyImage, "Game Options");

        ImageView deleteImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/deleteIcon.png");
        deleteImage.setTranslateX(760);
        deleteImage.setTranslateY(155);
        gamePane.getChildren().add(deleteImage);
        setHoverEventForBar(deleteImage, "Delete");

        ImageView buildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/buildingsIcon.png");
        buildingsImage.setTranslateX(750);
        buildingsImage.setTranslateY(-55);
        buildingsImage.setScaleX(0.2);
        buildingsImage.setScaleY(0.2);
        gamePane.getChildren().add(buildingsImage);


        ImageView editLandImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/editLandScapeActiveOffIcon.png");
        editLandImage.setTranslateX(785);
        editLandImage.setTranslateY(-55);
        editLandImage.setScaleX(0.2);
        editLandImage.setScaleY(0.2);
        gamePane.getChildren().add(editLandImage);
        setHoverEventForMainBarState(editLandImage, "Edit Landscape", "Edit Landscape", buildingsImage);
        setHoverEventForMainBarState(buildingsImage, "Buildings", "Castle Buildings", editLandImage);
    }

    private static void setEventsForClipboardIcon(ImageView clipboardSign) {
        clipboardSign.setOnMouseEntered(e -> {
            GameMenu.hoveringBarStateText.setText("Clipboard");
        });
        clipboardSign.setOnMouseExited(e -> {
            GameMenu.hoveringBarStateText.setText("");
        });
        clipboardSign.setOnMouseClicked(e -> {
            setCenterOfBar();
        });
    }

    private static void setHoverEventForMainBarState(ImageView imageView, String text, String destination, ImageView anotherIcon) {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText(text);
            }
        });

        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText("");
            }
        });

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameViewController.setCenterOfBar(destination);
                if (destination.equals("Edit Landscape")) {
                    imageView.setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                            .toExternalForm() + "icons/editLandscapeIcon.png"));
                    anotherIcon.setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                            .toExternalForm() + "icons/buildingsActiveOffIcon.png"));
                } else {
                    imageView.setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                            .toExternalForm() + "icons/buildingsIcon.png"));
                    anotherIcon.setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                            .toExternalForm() + "icons/editLandScapeActiveOffIcon.png"));
                }
            }
        });
    }

    private static void setHoverEventForBar(ImageView imageView, String text) {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText(text);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText("");
            }
        });
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCenterOfBar();
            }
        });
    }

    private static void setHoverEventForBar(ImageView imageView, String destination, String name) {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText(name);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText("");
            }
        });
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCenterOfBar(destination);
            }
        });
    }

    public static void setCenterOfBar() {
        if (GameMenu.hoveringBarStateText == null) {
            GameMenu.menuBar.getChildren().clear();
            GameMenu.createGameBar(0);
            setCenterToCastleBuildings();
            return;
        }
        switch (GameMenu.hoveringBarStateText.getText()) {
            case "Castle Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToCastleBuildings();
            }
            case "Towers" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToTowers();
            }
            case "Military Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToMilitaryBuildings();
            }
            case "Gatehouses" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToGatehouses();
            }
            case "Industry Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToIndustryBuildings();
            }
            case "Farm Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToFarmBuildings();
            }
            case "Town Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToTownBuildings();
            }
            case "Weapons Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToWeaponsBuildings();
            }
            case "Food Processing Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToFoodProcessingBuildings();
            }
            case "Clipboard" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterOfClipboard();
            }
        }
    }


    public static void setCenterOfBar(String destination) {
        switch (destination) {
            case "Castle Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToCastleBuildings();
            }
            case "Towers" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToTowers();
            }
            case "Military Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToMilitaryBuildings();
            }
            case "Gatehouses" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToGatehouses();
            }
            case "Industry Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToIndustryBuildings();
            }
            case "Farm Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToFarmBuildings();
            }
            case "Town Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToTownBuildings();
            }
            case "Weapons Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToWeaponsBuildings();
            }
            case "Food Processing Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToFoodProcessingBuildings();
            }
            case "Edit Landscape" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditLand();
            }
            case "Edit Land" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditLand();
            }
            case "Edit Water" -> {

            }
            case "Edit Features" -> {

            }
            case "Edit Vegetation" -> {

            }
            case "Clipboard" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterOfClipboard();
            }
            case "Shop" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToShopMenu();
            }
        }
    }

    private static void setCenterOfClipboard() {
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
        String buildingName = FileController.getClipboard();
        putBuildingFromClipboard(buildingName);
    }

    private static void putBuildingFromClipboard(String buildingName) {
        String fileName = buildingNameToFileName.get(buildingName);
        String name = buildingNameToName.get(buildingName);
        String picFileName = buildingNameToPicName.get(buildingName);
        if (fileName != null && name != null && picFileName != null) {
            putBuildingImageView(fileName, name, buildingName,
                    coordinateOfBuildingIconsInClipboardPage(buildingName).getFirst(),
                    coordinateOfBuildingIconsInClipboardPage(buildingName).getSecond(),
                    buildingScales.get(buildingName), picFileName);
        }
    }

    private static Pair<Double, Double> coordinateOfBuildingIconsInClipboardPage(String buildingName) {
        double x, y;
        Image image = new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + buildingNameToFileName.get(buildingName) + ".png");
        x = image.getWidth();
        y = image.getHeight();
        //x *= buildingScales.get(buildingName);
        //y *= buildingScales.get(buildingName);
        return new Pair<>((765 - 270 - x) / 2 + 270, (225 - 80 - y) / 2 + 65);
    }

    private static void setCenterOfEditLand() {
        putEditTextureImageView("bouldersIcon", "Boulder"
                , Textures.BOULDER, 200, 20, 0.2, "boulder");
    }

    public static void createShortcutBars2(Pane gamePane, Text text) {

        ImageView keyImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/keyIcon.png");
        keyImage.setTranslateX(760);
        keyImage.setTranslateY(85);
        gamePane.getChildren().add(keyImage);
        setHoverEventForBar(keyImage, "Game Options");

        ImageView deleteImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/deleteIcon.png");
        deleteImage.setTranslateX(760);
        deleteImage.setTranslateY(155);
        gamePane.getChildren().add(deleteImage);
        setHoverEventForBar(deleteImage, "Delete");

        ImageView buildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/buildingsActiveOffIcon.png");
        buildingsImage.setTranslateX(750);
        buildingsImage.setTranslateY(-55);
        buildingsImage.setScaleX(0.2);
        buildingsImage.setScaleY(0.2);
        gamePane.getChildren().add(buildingsImage);


        ImageView editLandImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/editLandscapeIcon.png");
        editLandImage.setTranslateX(785);
        editLandImage.setTranslateY(-55);
        editLandImage.setScaleX(0.2);
        editLandImage.setScaleY(0.2);
        gamePane.getChildren().add(editLandImage);
        setHoverEventForMainBarState(editLandImage, "Edit Landscape", "Edit Landscape", buildingsImage);
        setHoverEventForMainBarState(buildingsImage, "Buildings", "Castle Buildings", editLandImage);

        ImageView editLandShortcut = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/editLandShortcutIcon.png");
        editLandShortcut.setTranslateX(220);
        editLandShortcut.setTranslateY(30);
        editLandShortcut.setScaleX(0.2);
        editLandShortcut.setScaleY(0.2);
        gamePane.getChildren().add(editLandShortcut);
        setHoverEventForBar(editLandShortcut, "Edit Land");

        ImageView editWaterShortcut = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/editWaterIcon.png");
        editWaterShortcut.setTranslateX(250);
        editWaterShortcut.setTranslateY(30);
        editWaterShortcut.setScaleX(0.2);
        editWaterShortcut.setScaleY(0.2);
        gamePane.getChildren().add(editWaterShortcut);
        setHoverEventForBar(editWaterShortcut, "Edit Water");

        ImageView editVegetationShortcut = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/editVegetationIcon.png");
        editVegetationShortcut.setTranslateX(217);
        editVegetationShortcut.setTranslateY(65);
        editVegetationShortcut.setScaleX(0.2);
        editVegetationShortcut.setScaleY(0.2);
        gamePane.getChildren().add(editVegetationShortcut);
        setHoverEventForBar(editVegetationShortcut, "Edit Vegetation");

        ImageView editFeaturesShortcut = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/editFeaturesIcon.png");
        editFeaturesShortcut.setTranslateX(250);
        editFeaturesShortcut.setTranslateY(67);
        editFeaturesShortcut.setScaleX(0.2);
        editFeaturesShortcut.setScaleY(0.2);
        gamePane.getChildren().add(editFeaturesShortcut);
        setHoverEventForBar(editFeaturesShortcut, "Edit Features");
    }

    private static void setCenterToFoodProcessingBuildings() {
        putBuildingImageView("granaryIcon", "Granary", "granary", 280, 100, 1, "granary");
        putBuildingImageView("bakeryIcon", "Bakery", "bakery", 380, 100, 1, "bakery");
        putBuildingImageView("breweryIcon", "Brewery", "brewery", 480, 100, 1, "brewery");
        putBuildingImageView("millIcon", "Mill", "mill", 580, 80, 1, "mill");
        putBuildingImageView("innIcon", "Inn", "inn", 660, 90, 1, "inn");
    }

    private static void setCenterToWeaponsBuildings() {
        putBuildingImageView("fletcherWorkshopIcon", "Fletcher's Workshop", "fletcher", 300, 100, 1, "fletcher");
        putBuildingImageView("poleturnerWorkshopIcon", "Poleturner's Workshop", "poleTurner", 470, 100, 1, "poleTurner");
        putBuildingImageView("armourerIcon", "Armourer", "armourer", 500, 30, 0.25, "armourer");
    }

    private static void setCenterToTownBuildings() {
        putBuildingImageView("hovelIcon", "Hovel", "hovel", 300, 100, 1, "hovel");
        putBuildingImageView("churchIcon", "Church", "church", 450, 100, 1, "church");
        putBuildingImageView("cathedralIcon", "Cathedral", "cathedral", 600, 90, 1, "cathedral");
    }

    private static void setCenterToFarmBuildings() {
        putBuildingImageView("dairyIcon", "Dairy Farm", "dairyProducts", 180, 20, 0.25, "dairyProducts");
        putBuildingImageView("appleFarmIcon", "Apple Orchard", "appleGarden", 300, 20, 0.25, "appleGarden");
        putBuildingImageView("wheatFarmIcon", "Wheat Farm", "wheatFarm", 460, 20, 0.25, "wheatFarm");
        putBuildingImageView("hopsFarmIcon", "Hops Farm", "hopFarm", 540, 20, 0.25, "hopFarm");
    }

    private static void setCenterToIndustryBuildings() {
        putBuildingImageView("stockPileIcon", "Stockpile", "stockPile", 180, 70, 0.25, "stockPile");
        putBuildingImageView("woodCutterIcon", "Wood Cutter", "woodCutter", 220, 0, 0.25, "woodCutter");
        putBuildingImageView("quarryIcon", "Quarry", "quarry", 310, -50, 0.25, "quarry");
        putBuildingImageView("oxTetherIcon", "Ox Tether", "oxTether", 440, 30, 0.25, "oxTether");
        putBuildingImageView("ironMineIcon", "Iron Mine", "ironMine", 400, 10, 0.25, "ironMine");
        putBuildingImageView("pitchRigIcon", "Pitch Rig", "pitchRig", 465, 30, 0.25, "pitchRig");
        putBuildingImageView("shopIcon", "Market Place", "shop", 590, 10, 0.25, "shop");
    }

    private static void setCenterToGatehouses() {
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
        putBuildingImageView("smallGatehouseIcon", "Small Gatehouse", "smallStoneGatehouse", 250, 0, 0.25, "smallStoneGatehouse");
        putBuildingImageView("bigGatehouseIcon", "Big Gatehouse", "bigStoneGatehouse", 290, -50, 0.25, "bigStoneGatehouse");
        putBuildingImageView("drawBridgeIcon", "Draw Bridge", "drawBridge", 400, 20, 0.25, "drawBridge");
        putBuildingImageView("cageIcon", "Caged Dogs", "cagedWarDogs", 450, 30, 0.25, "cagedWarDogs");
        //putBuildingImageView("pitchDitchIcon", "Pitch Ditch", "", 530, 50, 0.25); // TODO:
        putBuildingImageView("killingPitIcon", "Killing Pit", "", 530, 90, 0.25, "killing");
        //putBuildingImageView("braizerIcon", "Braizer", "", 650, 70, 0.25); // braizer will added to game buildings
    }

    private static void setCenterToMilitaryBuildings() {
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
        putBuildingImageView("engineerGuildIcon", "Engineer's Guild", "engineerGuild", 220, 20, 0.25, "engineerGuild");
        putBuildingImageView("stableIcon", "Stable", "stable", 300, -5, 0.25, "stable");
        putBuildingImageView("tunnelerGuildIcon", "Tunneler's Guild", "tunnelersGuild", 450, 5, 0.25, "tunnelersGuild");
        putBuildingImageView("oilSmelterIcon", "Oil Smelter", "oilSmelter", 540, 5, 0.25, "oilSmelter");
    }

    private static void setCenterToTowers() {
        putBuildingImageView("lookoutTowerIcon", "Lookout Tower", "lookoutTower", 290, -45, 0.25, "lookoutTower");
        putBuildingImageView("perimeterTurretIcon", "Perimeter Turret", "perimeterTurret", 340, 5, 0.30, "perimeterTurret");
        putBuildingImageView("defenseTurretIcon", "Defense Turret", "defenseTurret", 415, -15, 0.3, "defenseTurret");
        putBuildingImageView("squareTowerIcon", "Square Tower", "squareTower", 495, -25, 0.3, "squareTower");
        putBuildingImageView("roundTowerIcon", "Round Tower", "roundTower", 595, -30, 0.3, "roundTower");
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
    }

    private static void setCenterToCastleBuildings() {
        putBuildingImageView("stairsIcon", "Stairs", "stairs", 240, 80, 0.4, "stairs");
        putBuildingImageView("smallWallIcon", "Low Wall", "lowWall", 265, 80, 0.4, "lowWall");
        putBuildingImageView("bigWallIcon", "Stone Wall", "stoneWall", 310, 60, 0.4, "stoneWall");
        putBuildingImageView("crenulatedWallIcon", "Crenulated Wall", "crenulatedWall", 370, 40, 0.4, "crenulatedWall");
        putBuildingImageView("barracksIcon", "Barrack", "barrack", 450, 80, 0.4, "barrack");
        putBuildingImageView("mercenrayIcon", "Mercenary Post", "mercenaryPost", 505, 100, 0.4, "mercenaryPost");
        putBuildingImageView("armoryIcon", "Armoury", "armoury", 595, 70, 0.4, "armoury");
        putImageView("towersIcon", "Towers", 652, 80);
        putImageView("militaryIcon", "Military Buildings", 692, 80);
        putImageView("gatehouseIcon", "Gatehouses", 692, 120);
    }

    private static void setCenterToShopMenu() {
        setTitle("The Marketplace", 275, 95);
        putShopIcon("foods", 340, 135);
        putShopIcon("rawMaterials", 440, 135);
        putShopIcon("weapons", 540, 130);
        putShopIcon("resources", 640, 132);
    }

    private static void putImageView(String fileName, String name, double x, double y) {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(0.4);
        icon.setScaleY(0.4);
        GameMenu.menuBar.getChildren().add(icon);
        setHoverEventForBar(icon, name);
    }

    private static void setTitle(String title, double x, double y) {
        Text menuTitle = new Text("The Marketplace");
        menuTitle.setTranslateX(x);
        menuTitle.setTranslateY(y);
        menuTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 32));
        GameMenu.menuBar.getChildren().add(menuTitle);
    }

    private static void putShopIcon(String fileName, double x, double y) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        ColorAdjust colorAdjust = new ColorAdjust();
        icon.setOnMouseEntered(mouseEvent -> {
            colorAdjust.setSaturation(0.2);
            colorAdjust.setBrightness(-0.1);
            icon.setEffect(colorAdjust);
        });
        icon.setOnMouseExited(mouseEvent -> {
            colorAdjust.setSaturation(0);
            colorAdjust.setBrightness(0);
            icon.setEffect(colorAdjust);
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putButtonImageViewWithDestination
            (String fileName, String name, String destination, double x, double y, double size) {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(size);
        icon.setScaleY(size);
        GameMenu.menuBar.getChildren().add(icon);
        setHoverEventForBar(icon, destination, name);
    }

    private static Text getNumberOfResourceNeededText() {
        Text text = new Text("");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        text.setStrokeWidth(0.5);
        text.setStroke(Color.BLACK);
        return text;
    }

    private static void putEditTextureImageView(String fileName, String name, Textures texture, double x, double y, double size, String picFileName) {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(size);
        icon.setScaleY(size);
        GameMenu.menuBar.getChildren().add(icon);
        icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText(name);
            }
        });
        icon.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText("");
            }
        });

        Image image = new Image(GameViewController.class.getResource(
                Paths.TEXTURE_IMAGES.getPath()).toExternalForm() + picFileName + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setViewOrder(-10);

        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!GameMenu.gameMap.getChildren().contains(imageView))
                    GameMenu.gameMap.getChildren().add(imageView);
                isTextureSelected = true;
                imageView.setTranslateX(GameMenu.gameMap.getCameraX() * GameMap.tileWidth +
                        mouseEvent.getScreenX() - (GameMenu.scene.getWidth() - 1200) / 2 - image.getWidth() * 0.5);
                imageView.setTranslateY(GameMenu.gameMap.getCameraY() * GameMap.tileHeight / 2 +
                        mouseEvent.getScreenY() - (GameMenu.scene.getHeight() - 800) / 2 - image.getHeight());
                imageView.setOpacity(0.6);
                GameMenu.gameMap.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && isTextureSelected) {
                            GameMenu.gameMap.getChildren().remove(imageView);
                            Pair<Integer, Integer> pair = tileCoordinateWithMouseEvent(mouseEvent);
                            tileX = pair.getFirst();
                            tileY = pair.getSecond();
                            System.out.println("Tile founded at : " + tileX + " " + tileY);
                            GameMenu.hoveringBarStateText.setText(MapController.setTexture(tileX, tileY, texture));
                            GameMap.getGameTile(tileX, tileY).refreshTile();
                        } else {
                            isTextureSelected = false;
                            GameMenu.gameMap.getChildren().remove(imageView);
                        }
                    }
                });

                GameMenu.gameMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            isTextureSelected = false;
                            GameMenu.gameMap.getChildren().remove(imageView);
                        }
                    }
                });

            }
        });
    }

    private static void putBuildingImageView(String fileName, String name, String buildingName, double x, double y, double size, String picFileName) {
        buildingNameToFileName.put(buildingName, fileName);
        buildingNameToName.put(buildingName, name);
        buildingNameToPicName.put(buildingName, picFileName);
        buildingScales.put(buildingName, size);
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        ArrayList<ImageView> resourceIcons = new ArrayList<>();
        ArrayList<Text> resourceTexts = new ArrayList<>();
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(size);
        icon.setScaleY(size);
        GameMenu.menuBar.getChildren().add(icon);
        Building sampleBuilding = GameBuildings.getBuilding(buildingName);
        if (sampleBuilding != null) {
            for (int i = 0; i != sampleBuilding.getCost().size(); i++) {
                resourceIcons.add(new ImageView());
                resourceTexts.add(getNumberOfResourceNeededText());
            }
            if (sampleBuilding.getPrice() != 0) {
                resourceIcons.add(new ImageView());
                resourceTexts.add(getNumberOfResourceNeededText());
            }
            icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMenu.hoveringBarStateText.setText(name);
                    int index = 0;
                    if (!buildingName.equals("")) {
                        for (String i : sampleBuilding.getCost().keySet()) {
                            resourceTexts.get(index).setText
                                    (sampleBuilding.getCost().get(i).toString());
                            if (i.equals("wood")) {
                                resourceIcons.get(index).setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).
                                        toExternalForm() + "icons/woodIcon.png"));
                                resourceIcons.get(index).setTranslateY(GameMenu.hoveringBarStateText.getTranslateY() - 57);
                                resourceIcons.get(index).setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 125 + index * 70);
                            } else if (i.equals("stone")) {
                                resourceIcons.get(index).setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                                        + "icons/stoneIcon.png"));
                                resourceIcons.get(index).setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 130 + index * 70);
                                resourceIcons.get(index).setTranslateY(GameMenu.hoveringBarStateText.getTranslateY() - 50);
                            } else if (i.equals("iron")) {
                                resourceIcons.get(index).setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                                        + "icons/ironIcon.png"));
                                resourceIcons.get(index).setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 130 + index * 70);
                                resourceIcons.get(index).setTranslateY(GameMenu.hoveringBarStateText.getTranslateY() - 56);
                            }
                            resourceIcons.get(index).setScaleX(0.2);
                            resourceIcons.get(index).setScaleY(0.2);
                            resourceTexts.get(index).setTranslateY(70);
                            resourceTexts.get(index).setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 145 + index * 70);
                            GameMenu.menuBar.getChildren().add(resourceIcons.get(index));
                            GameMenu.menuBar.getChildren().add(resourceTexts.get(index));
                            index++;
                        }
                        if (sampleBuilding.getPrice() != 0) {
                            resourceTexts.get(index).setText
                                    ("" + sampleBuilding.getPrice());
                            resourceIcons.get(index).setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                                    + "icons/coinIcon.png"));
                            resourceIcons.get(index).setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 130 + index * 70);
                            resourceIcons.get(index).setTranslateY(GameMenu.hoveringBarStateText.getTranslateY() - 47);
                            resourceIcons.get(index).setScaleX(0.2);
                            resourceIcons.get(index).setScaleY(0.2);
                            resourceTexts.get(index).setTranslateY(70);
                            resourceTexts.get(index).setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 145 + index * 70);
                            GameMenu.menuBar.getChildren().add(resourceIcons.get(index));
                            GameMenu.menuBar.getChildren().add(resourceTexts.get(index));
                            index++;
                        }

                    }
                }
            });
            icon.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMenu.hoveringBarStateText.setText("");
                    for (Text text : resourceTexts) {
                        GameMenu.menuBar.getChildren().remove(text);
                    }
                    for (ImageView imageView : resourceIcons) {
                        GameMenu.menuBar.getChildren().remove(imageView);
                    }
                }
            });
        }

        if (buildingName.equals("killingPit")) {
            icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMenu.hoveringBarStateText.setText(name);

                }
            });
            icon.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMenu.hoveringBarStateText.setText("");
                }
            });
        }

        Image image = new Image(GameViewController.class.getResource(
                Paths.BUILDING_IMAGES.getPath()).toExternalForm() + picFileName + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setViewOrder(-500);


        icon.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!GameMenu.gameMap.getChildren().contains(imageView))
                    GameMenu.gameMap.getChildren().add(imageView);
                if (sampleBuilding != null) {
                    imageView.setTranslateX(GameMenu.gameMap.getCameraX() * GameMap.tileWidth +
                            mouseEvent.getScreenX() - (GameMenu.scene.getWidth() - 1200) / 2 - image.getWidth() *
                            ((double) sampleBuilding.getWidth() / (sampleBuilding.getWidth() + sampleBuilding.getLength())));
                    imageView.setTranslateY(GameMenu.gameMap.getCameraY() * GameMap.tileHeight / 2 +
                            mouseEvent.getScreenY() - (GameMenu.scene.getHeight() - 800) / 2 - image.getHeight());
                } else {
                    imageView.setTranslateX(GameMenu.gameMap.getCameraX() * GameMap.tileWidth +
                            mouseEvent.getScreenX() - (GameMenu.scene.getWidth() - 1200) / 2 - image.getWidth() *
                            (0.5));
                    imageView.setTranslateY(GameMenu.gameMap.getCameraY() * GameMap.tileHeight / 2 +
                            mouseEvent.getScreenY() - (GameMenu.scene.getHeight() - 800) / 2 - image.getHeight());
                }
                imageView.setOpacity(0.6);
                icon.setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        GameMenu.gameMap.getChildren().remove(imageView);
                        Pair<Integer, Integer> pair = tileCoordinateWithMouseEvent(mouseEvent);
                        tileX = pair.getFirst();
                        tileY = pair.getSecond();
                        System.out.println("Tile founded at : " + tileX + " " + tileY);
                        GameMenu.hoveringBarStateText.setText(GameController.dropBuilding(tileX, tileY, buildingName, null));
                        GameMap.getGameTile(tileX, tileY).refreshTile();
                    }
                });
            }
        });

    }

    public static Pair<Integer, Integer> tileCoordinateWithMouseEvent(MouseEvent mouseEvent) {
        int halfTileX = (int) ((mouseEvent.getScreenX() -
                (GameMenu.scene.getWidth() - 1200) / 2) / ((double) GameMap.tileWidth / 2));
        int halfTileY = (int) ((mouseEvent.getScreenY() -
                (GameMenu.scene.getHeight() - 800) / 2) / ((double) GameMap.tileHeight / 2));
        Pair<Integer, Integer> pair;
        if (halfTileX % 2 == 1) {
            pair = checkNearestTile(mouseEvent, (halfTileX - 1) / 2, (halfTileX - 1) / 2, halfTileY, halfTileY - 1);
        } else {
            if (halfTileY % 2 == 1) {
                pair = checkNearestTile(mouseEvent, halfTileX / 2 - 1, halfTileX / 2, halfTileY, halfTileY - 1);
            } else {
                pair = checkNearestTile(mouseEvent, halfTileX / 2 - 1, halfTileX / 2, halfTileY - 1, halfTileY);
            }
        }
        return new Pair<>(pair.getFirst() + GameMenu.gameMap.getCameraX(),
                pair.getSecond() + GameMenu.gameMap.getCameraY());
    }

    private static Pair<Integer, Integer> checkNearestTile(MouseEvent mouseEvent, int x1, int x2, int y1, int y2) {
        double dis1, dis2;
        dis1 = distanceOfTile(mouseEvent, x1, y1);
        dis2 = distanceOfTile(mouseEvent, x2, y2);
        if (dis1 <= dis2)
            return new Pair<>(x1, y1);
        return new Pair<>(x2, y2);
    }

    private static double distanceOfTile(MouseEvent mouseEvent, int tileX, int tileY) {
        double x = (mouseEvent.getScreenX() -
                (GameMenu.scene.getWidth() - 1200) / 2);
        double y = (mouseEvent.getScreenY() -
                (GameMenu.scene.getHeight() - 800) / 2);
        GameTile tile = GameMap.getGameTile(tileX, tileY);
        double distance = Math.sqrt(Math.pow(tile.getX() - x, 2) +
                Math.pow(tile.getY() - y, 2));
        return distance;
    }

    public static void createBorderRectangles(GameMap gameMap, MiniMap miniMap) {
        Rectangle downRight = new Rectangle(10, 10);
        Rectangle upRight = new Rectangle(10, 10);
        Rectangle upLeft = new Rectangle(10, 10);
        Rectangle downLeft = new Rectangle(10, 10);
        Rectangle right = new Rectangle(10, 780);
        Rectangle up = new Rectangle(1180, 10);
        Rectangle left = new Rectangle(10, 780);
        Rectangle down = new Rectangle(1180, 10);

        setTranslateOfRectangle(downRight, 595, 395);
        setTranslateOfRectangle(upRight, 595, -395);
        setTranslateOfRectangle(upLeft, -595, -395);
        setTranslateOfRectangle(downLeft, -595, 395);
        setTranslateOfRectangle(right, 595, 0);
        setTranslateOfRectangle(left, -595, 0);
        setTranslateOfRectangle(up, 0, -395);
        setTranslateOfRectangle(down, 0, 395);


        GameMenu.root.getChildren().addAll(downRight, downLeft, upRight, upLeft, right, left, up, down);

        setEventForRectangles(downRight, 1, -1, gameMap, miniMap);
        setEventForRectangles(downLeft, -1, -1, gameMap, miniMap);
        setEventForRectangles(upRight, 1, 1, gameMap, miniMap);
        setEventForRectangles(upLeft, -1, 1, gameMap, miniMap);

        setEventForRectangles(down, 0, -1, gameMap, miniMap);
        setEventForRectangles(left, -1, 0, gameMap, miniMap);
        setEventForRectangles(up, 0, 1, gameMap, miniMap);
        setEventForRectangles(right, 1, 0, gameMap, miniMap);


    }

    private static void setEventForRectangles(Rectangle rectangle, int horizontal, int vertical, GameMap gameMap, MiniMap miniMap) {
        rectangle.setOnMouseEntered(e -> {
            timeline = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> {
                if (horizontal == 1 && vertical == 0) {
                    miniMap.moveRight(true);
                } else if (horizontal == -1 && vertical == 0) {
                    miniMap.moveLeft(true);
                } else if (horizontal == 0 && vertical == 1) {
                    miniMap.moveUp(true);
                } else if (horizontal == 1 && vertical == 1) {
                    miniMap.moveRightUp();
                } else if (horizontal == -1 && vertical == 1) {
                    miniMap.moveLeftUp();
                } else if (horizontal == 0 && vertical == -1) {
                    miniMap.moveDown(true);
                } else if (horizontal == 1 && vertical == -1) {
                    miniMap.moveRightDown();
                } else if (horizontal == -1 && vertical == -1) {
                    miniMap.moveLeftDown();
                }

            }), new KeyFrame(Duration.millis(100), actionEvent -> {
            }));
            timeline.setCycleCount(-1);
            timeline.play();
        });

        rectangle.setOnMouseExited(e -> {
            timeline.stop();
        });

    }

    private static void setTranslateOfRectangle(Rectangle rectangle, double x, double y) {
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        rectangle.setFill(Color.TRANSPARENT);
    }

    private static void setEventOfOkButton(Button button, Stage popupStage) {
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    popupStage.close();
                } catch (Exception e) {
                    System.out.println("an error occurred");
                }
            }
        });
    }

    public static void dropUnit(int x, int y, Tile tile, Military military) {
        GameTile gameTile = GameMap.getGameTile(x, y);
        GameMap gameMap = GameMenu.gameMap;
        Troop troop = new Troop(military, tile, gameTile);
        gameMap.getChildren().add(troop);
        if (GameMap.gameTroops[y][x] == null) {
            GameMap.gameTroops[y][x] = new ArrayList<>();
        }
        GameMap.gameTroops[y][x].add(troop);
    }

    public static void selectUnits(int x, int y) {
        GameMenu.selectedTilesTroop.clear();
        Tile tile = GameController.getGame().getMap().getTile(x, y);
        GameMenu.selectedTilesTroop.add(tile);
        GameMenu.selectedUnit = true;
    }

    public static void setSelectCursorImage(String state) {
        GameMenu.selectCursor.setFill(new ImagePattern(GameImages.imageViews.get(state)));
    }

    public static void setSelectCursorState(GameTile endTile) {
        String state = GameMenu.movingState;
        if (UnitMovingState.NORMAL.getState().equals(state)) {
            canDoAction(true, endTile);
        }
    }


    public static boolean canDoAction(boolean changeCursor, GameTile endTile) {
        String state = GameMenu.movingState;
        if (Objects.equals(state, UnitMovingState.NORMAL.getState())) {
            if (checkCanAttack(endTile) || checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return true;
            }
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }
        if (Objects.equals(state, UnitMovingState.MOVE.getState())) {
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }
        if (Objects.equals(state, UnitMovingState.AIR_ATTACK.getState())) {
            if (checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }

        if (Objects.equals(state, UnitMovingState.ATTACK.getState())) {
            if (checkCanAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return true;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return false;
        }

        return false;
    }


    public static boolean checkCanAttack(GameTile endTile) {
        if (GameController.validateAttackEnemy(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        if (GameController.validateAttackBuilding(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        return GameController.validateAttackTool(endTile.getTileX(), endTile.getTileY());
    }


    public static boolean checkCanAirAttack(GameTile endTile) {
        if (GameController.validateAirAttack(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        if (GameController.validateAirAttackBuilding(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        return GameController.validateAirAttackTool(endTile.getTileX(), endTile.getTileY());
    }

    public static void doAction(boolean changeCursor, GameTile endTile) {
        String state = GameMenu.movingState;
        if (Objects.equals(state, UnitMovingState.NORMAL.getState())) {
            if (checkCanAttack(endTile) || checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                attack();
                return;
            }
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                moveUnits(endTile);
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return;
        }
        if (Objects.equals(state, UnitMovingState.MOVE.getState())) {
            if (GameController.validateMoveUnit(endTile.getTileX(), endTile.getTileY())) {
                if (changeCursor) {
                    setSelectCursorImage("selectMove");
                }
                moveUnits(endTile);
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return;
        }
        if (Objects.equals(state, UnitMovingState.AIR_ATTACK.getState())) {
            if (checkCanAirAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                    attack();
                }
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
            return;
        }

        if (Objects.equals(state, UnitMovingState.ATTACK.getState())) {
            if (checkCanAttack(endTile)) {
                if (changeCursor) {
                    setSelectCursorImage("attack");
                }
                return;
            }
            if (changeCursor) {
                setSelectCursorImage("cannot");
            }
        }

    }


    public static boolean doAttack(GameTile endTile) {
        if (GameController.validateAttackEnemy(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        if (GameController.validateAttackBuilding(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        return GameController.validateAttackTool(endTile.getTileX(), endTile.getTileY());
    }


    public static boolean doAirAttack(GameTile endTile) {
        if (GameController.validateAirAttack(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        if (GameController.validateAirAttackBuilding(endTile.getTileX(), endTile.getTileY())) {
            return true;
        }
        return GameController.validateAirAttackTool(endTile.getTileX(), endTile.getTileY());
    }


    public static void moveUnits(GameTile end) {
        for (Tile tile : GameMenu.selectedTilesTroop) {
            GameController.selectUnit(tile.x, tile.y, null);
            GameController.moveUnit(end.getTileX(), end.getTileY());
            if (GameMap.gameTroops[tile.y][tile.x] != null) {

                Iterator<Troop> it = GameMap.gameTroops[tile.y][tile.x].iterator();
                while (it.hasNext()) {
                    Troop troop = it.next();
                    Military military = troop.getMilitary();
                    System.out.println("this is a troop!");
                    if (military.getMove() != null && military.getMove().isMoving()) {
                        MoveTroop moveTroop = new MoveTroop(troop);
                        moveTroop.play();
                        GameMenu.transitions.add(moveTroop);
                        it.remove();
                    }
                }
            }

        }
    }

    public static void attack() {
        //
    }

    public static void airAttack() {
        //
    }
}
