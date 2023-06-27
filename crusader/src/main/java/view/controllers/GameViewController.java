package view.controllers;

import controller.*;
import controller.gamestructure.GameBuildings;
import controller.gamestructure.GameGoods;
import controller.gamestructure.GameHumans;
import controller.gamestructure.GameImages;
import enumeration.Pair;
import enumeration.Paths;
import enumeration.Textures;
import enumeration.UnitMovingState;
import enumeration.dictionary.RockDirections;
import enumeration.dictionary.Trees;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Government;
import model.Trade;
import model.building.Building;
import model.building.castlebuildings.MainCastle;
import model.building.producerbuildings.Barrack;
import model.human.Human;
import model.human.civilian.Civilian;
import model.human.military.Military;
import model.menugui.*;
import model.menugui.game.GameMap;
import model.menugui.game.GameTile;
import view.menus.GameMenu;
import view.menus.LoginMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameViewController {

    public static String nameOfPageInBar;
    public static Timeline timeline;
    public static Timeline gameTimeLine;
    public static boolean isSelected = false;
    public static boolean isDelete = false;
    public static boolean isDropped = false;
    public static boolean isTextureSelected = false;
    public static int tileX, tileY;
    public static int shopMenuPhase = -1;
    public static String currentCategory;
    public static String currentItem, droppedBuildingName, droppedPicFileName;
    public static String currentTradeId;
    public static Textures selectedTexture;
    public static Building selectedBuilding;

    public static ImageView nowFace;
    public static Text popularityReporter , populationReporter , goldReporter;

    public static HashMap<String, String> buildingNameToFileName = new HashMap<>();
    public static HashMap<String, String> buildingNameToPicName = new HashMap<>();
    public static HashMap<String, String> buildingNameToName = new HashMap<>();
    public static HashMap<String, Double> buildingScales = new HashMap<>();
    public static HashMap<String, Double> buildingCoordinates = new HashMap<>();


    public static void setBarForCurrentGovernment() {
        ImageView emptyPage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                + "emptyPage.png");
        emptyPage.setTranslateX(868);
        emptyPage.setTranslateY(46);
        emptyPage.setScaleX(0.48);
        emptyPage.setScaleY(0.48);
        GameMenu.menuBar.getChildren().add(emptyPage);
        setEventForEmptyPage(emptyPage);

        int popularity = GovernmentController.getCurrentGovernment().getPopularity() + 37;
        Text popularityText = new Text(String.format("%d", popularity));
        popularityText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30));
        popularityText.setFill(Color.GREEN);
        popularityText.setTranslateY(140);
        popularityText.setTranslateX(980);
        GameMenu.menuBar.getChildren().add(popularityText);

        Text coinText = new Text(String.format("%d", GovernmentController.getCurrentGovernment().getGold()));
        coinText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        coinText.setFill(Color.GREEN);
        coinText.setTranslateY(160);
        coinText.setTranslateX(950);
        GameMenu.menuBar.getChildren().add(coinText);

        Text populationText = new Text(String.format("%d/%d", GovernmentController.getCurrentGovernment().getPopulation(),
                GovernmentController.getCurrentGovernment().getMaxPopulation()));
        populationText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 18));
        populationText.setFill(Color.GREEN);
        populationText.setTranslateY(180);
        populationText.setTranslateX(950);
        GameMenu.menuBar.getChildren().add(populationText);

        popularityReporter = popularityText;
        populationReporter = populationText;
        goldReporter = coinText;

        if (nowFace != null) {
            GameMenu.menuBar.getChildren().remove(nowFace);
        }
        nowFace = null;
        if (popularity < 21) {
            ImageView angryFace = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "angryFace.png");
            angryFace.setTranslateX(758);
            angryFace.setTranslateY(-160);
            angryFace.setScaleY(0.29);
            angryFace.setScaleX(0.25);
            GameMenu.menuBar.getChildren().add(angryFace);
            nowFace = angryFace;
        } else if (popularity < 42) {
            ImageView pokerFace = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "pokerFace.png");
            pokerFace.setTranslateX(750);
            pokerFace.setTranslateY(-160);
            pokerFace.setScaleX(0.25);
            pokerFace.setScaleY(0.29);
            GameMenu.menuBar.getChildren().add(pokerFace);
            nowFace = pokerFace;
        }


    }

    public static void updateFaceOfReporter() {
        int popularity = GovernmentController.getCurrentGovernment().getPopularity() + 37;
        if (nowFace != null) {
            GameMenu.menuBar.getChildren().remove(nowFace);
        }
        nowFace = null;
        if (popularity < 21) {
            ImageView angryFace = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "angryFace.png");
            angryFace.setTranslateX(758);
            angryFace.setTranslateY(-160);
            angryFace.setScaleY(0.29);
            angryFace.setScaleX(0.25);
            GameMenu.menuBar.getChildren().add(angryFace);
            nowFace = angryFace;
        } else if (popularity < 42) {
            ImageView pokerFace = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                    + "pokerFace.png");
            pokerFace.setTranslateX(750);
            pokerFace.setTranslateY(-160);
            pokerFace.setScaleX(0.25);
            pokerFace.setScaleY(0.29);
            GameMenu.menuBar.getChildren().add(pokerFace);
            nowFace = pokerFace;
        }
    }

    private static void setEventForEmptyPage(ImageView emptyPage) {
        emptyPage.setOnMouseClicked(e -> {
            System.out.println("Clicked!");
            setCenterOfBar("Government");
        });
    }

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
        setHoverEventForBar(keyImage, "Game Options"); // TODO : do it!

        ImageView deleteImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/deleteIcon.png");
        deleteImage.setTranslateX(760);
        deleteImage.setTranslateY(155);
        gamePane.getChildren().add(deleteImage);
        setEventForDeleteIcon(deleteImage);

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
        imageView.setOnMouseEntered(mouseEvent -> GameMenu.hoveringBarStateText.setText(text));

        imageView.setOnMouseExited(mouseEvent -> GameMenu.hoveringBarStateText.setText(""));

        imageView.setOnMouseClicked(mouseEvent -> {
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
        });
    }

    public static void setHoverEventForBar(ImageView imageView, String text) {
        imageView.setOnMouseEntered(mouseEvent -> GameMenu.hoveringBarStateText.setText(text));
        imageView.setOnMouseExited(mouseEvent -> GameMenu.hoveringBarStateText.setText(""));
        imageView.setOnMouseClicked(mouseEvent -> setCenterOfBar());
    }

    private static void setHoverEventForBar(ImageView imageView, String destination, String name) {
        imageView.setOnMouseEntered(mouseEvent -> GameMenu.hoveringBarStateText.setText(name));
        imageView.setOnMouseExited(mouseEvent -> GameMenu.hoveringBarStateText.setText(""));
        imageView.setOnMouseClicked(mouseEvent -> setCenterOfBar(destination));
    }

    public static void setCenterOfBar() {
        if (GameMenu.hoveringBarStateText == null || GameMenu.hoveringBarStateText.getText().startsWith("Lord")) {
            GameMenu.menuBar.getChildren().clear();
            GameMenu.createGameBar(0);
            setCenterToCastleBuildings();
            GameMenu.setShieldsForGovernments();
            return;
        }
        if (!GameMenu.selectedUnit) {
            GameMenu.barImage.setImage(GameImages.imageViews.get("bar"));
        }
        switch (GameMenu.hoveringBarStateText.getText()) {
            case "Castle Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToCastleBuildings();
                GameMenu.setShieldsForGovernments();
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
            case "Unit Menu" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(-1);
                HumanViewController.setCenterOfUnitMenu();
            }
            case "Small Gatehouse" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToSmallGatehouses();
            }
            case "Big Gatehouse" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToBigGatehouses();
            }
            case "Edit Land" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditLand();
            }
            case "Edit Water" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditWater();
            }
            case "Edit Features" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditFeatures();
            }
            case "Edit Vegetation" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditVegetation();
            }
            case "Government" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToGovernment();
            }
        }
    }

    private static void setCenterToBigGatehouses() {
        putBuildingImageView("bigGatehouseIcon", "Left Side", "bigStoneGatehouse", 275, -45, 0.3, "bigStoneGatehouse");
        putBuildingImageView("bigGatehouseRightIcon", "Right Side", "bigStoneGatehouse", 480, -45, 0.3, "bigStoneGatehouseRight");
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Gatehouses", 225, 60, 0.2);
    }

    private static void setCenterToSmallGatehouses() {
        putBuildingImageView("smallGatehouseIcon", "Left Side", "smallStoneGatehouse", 305, 0, 0.3, "smallStoneGatehouse");
        putBuildingImageView("smallGatehouseRightIcon", "Right Side", "smallStoneGatehouse", 520, 0, 0.3, "smallStoneGatehouseRight");
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Gatehouses", 225, 60, 0.2);
    }

    public static void setHoverEventForBarOnUnitMenu(ImageView imageView, String text) {
        imageView.setOnMouseEntered(mouseEvent -> GameMenu.hoveringBarStateText.setText(text));
        imageView.setOnMouseExited(mouseEvent -> GameMenu.hoveringBarStateText.setText(""));
    }

    public static void unselectTiles() {
        for (GameTile gameTile : GameMenu.selectedTiles) {
            gameTile.deselectTile();
        }
        GameMenu.startSelectionTile = null;
        GameMenu.endSelectionTile = null;
        GameMenu.isSelected = false;
        GameMenu.selectedUnit = false;
        GameMenu.selectDone = false;
        GameMenu.unitsCount = new HashMap<>();
        GameMenu.selectedTroops.clear();
        HumanViewController.selectedMilitaries.clear();
        HumanViewController.lastType = null;
        HumanViewController.typeBTNS = new ArrayList<>();
        GameMenu.selectedTilesTroop.clear();
        GameMenu.selectedArea.setVisible(false);
        GameMenu.selectedArea.setWidth(0);
        GameMenu.selectedArea.setHeight(0);
        GameMenu.selectedTiles.clear();
        GameMenu.movingState = UnitMovingState.NORMAL.getState();
        GameViewController.setCenterOfBar(null);
    }

    public static void unselectTilesWithOutUnits() {
        for (GameTile gameTile : GameMenu.selectedTiles) {
            gameTile.deselectTile();
        }
        GameMenu.startSelectionTile = null;
        GameMenu.endSelectionTile = null;
        GameMenu.isSelected = false;
        GameMenu.selectedUnit = false;
        GameMenu.selectDone = false;
        HumanViewController.lastType = null;
        HumanViewController.typeBTNS = new ArrayList<>();
        GameMenu.selectedArea.setVisible(false);
        GameMenu.selectedArea.setWidth(0);
        GameMenu.selectedArea.setHeight(0);
        GameMenu.selectedTiles.clear();
        GameViewController.setCenterOfBar(null);
    }


    public static void setCenterOfBar(String destination) {
        if (destination == null) {
            GameMenu.menuBar.getChildren().clear();
            GameMenu.createGameBar(0);
            setCenterToCastleBuildings();
            GameMenu.setShieldsForGovernments();
            return;
        }
        switch (destination) {
            case "Castle Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterToCastleBuildings();
                GameMenu.setShieldsForGovernments();
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
            case "Edit Landscape", "Edit Land" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditLand();
            }
            case "Edit Water" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditWater();
            }
            case "Edit Features" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditFeatures();
            }
            case "Edit Vegetation" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(1);
                setCenterOfEditVegetation();
            }
            case "Clipboard" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(0);
                setCenterOfClipboard();
            }
            case "shop" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToShopMenu();
            }
            case "foods" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToFoodsInShop();
            }
            case "rawMaterials" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToRawMaterialsInShop();
            }
            case "weapons" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToWeaponsInShop();
            }
            case "resources" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToAllResourcesInShop();
            }
            case "shopItem" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToShopItem(currentItem);
            }
            case "showUsers" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToShowUsers();
            }
            case "sendRequest" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToSendRequest(currentItem);
            }
            case "defenseTurret", "perimeterTurret", "lookoutTower",
                    "squareTower", "roundTower" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToSelectTowersBuilding();
            }
            case "smallStoneGatehouse", "bigStoneGatehouse", "drawBridge" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToSelectGatehouse();
            }
            case "mainCastle", "castleBuildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToSelectMainCastle();
            }
            case "tradeList" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToTradeList();
            }
            case "tradeHistory" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToTradeHistory();
            }
            case "showSentTrade" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToShowTrade(currentTradeId, true, false);
            }
            case "showReceivedTrade" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                Trade trade = TradeController.allTrades.get(currentTradeId);
                if (trade.isAccepted() || trade.isRejected())
                    setCenterToShowTrade(currentTradeId, false, false);
                else setCenterToShowTrade(currentTradeId, false, true);
            }
            case "acceptMessage" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToAcceptMessage(TradeController.allTrades.get(currentTradeId));
            }
            case "armoury" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToArmoury();
            }
            case "mercenaryPost" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(3);
                setCenterToMercenaryPost();
            }
            case "Government" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToGovernment();
            }
            case "Popularity" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToPopularity();
            }
            case "Change Rates" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToChangeRates();
            }
            case "barrack" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(4);
                setCenterToBarrack();
            }
            case "engineer" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar(2);
                setCenterToEngineerMenu();
            }
        }
    }

    private static void setCenterToPopularity() {
        ImageView twoMasks = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/twoMasks.png");
        twoMasks.setScaleY(0.25);
        twoMasks.setScaleX(0.25);
        twoMasks.setTranslateX(200);
        twoMasks.setTranslateY(-50);
        GameMenu.menuBar.getChildren().add(twoMasks);

        showPopularityOfOneFactor("Food", GovernmentController.getFoodPopularity(
                        GovernmentController.getCurrentGovernment().getFoodRate()),
                -8, 8, 500, 100);

        showPopularityOfOneFactor("Tax", GovernmentController.getTaxPopularity(
                        GovernmentController.getCurrentGovernment().getRealTaxRate()),
                -24, 7, 500, 125);

        showPopularityOfOneFactor("Fear Factor", GovernmentController.getCurrentGovernment().getFearRate(),
                -5, 5, 500, 150);

        showPopularityOfOneFactor("Religion", GovernmentController.getCurrentGovernment().getReligionRate(),
                0, 5, 500, 175);

        showPopularityOfOneFactor("In This Turn", GovernmentController.getCurrentGovernment().getPopularity() + 37,
                0, 62, 500, 200);
    }

    private static void showPopularityOfOneFactor(String nameOfFactor, int popularity, int minRange, int maxRange, double x, double y) {
        ImageView face;
        Text numberText = new Text(String.format("%d", popularity));
        numberText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        if (popularity < minRange + (maxRange - minRange) / 3) {
            face = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                    .toExternalForm() + "icons/sadMask.png");
            numberText.setFill(Color.RED);
        } else if (popularity < minRange + 2 * (maxRange - minRange) / 3) {
            face = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                    .toExternalForm() + "icons/pokerMask.png");
            numberText.setFill(Color.YELLOW);
        } else {
            face = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                    .toExternalForm() + "icons/happyMask.png");
            numberText.setFill(Color.GREEN);
        }

        Text name = new Text(nameOfFactor);
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));


        numberText.setTranslateX(x);
        numberText.setTranslateY(y);

        face.setTranslateX(x + 25);
        face.setTranslateY(y - 20);

        name.setTranslateY(y);
        name.setTranslateX(x + 60);

        GameMenu.menuBar.getChildren().add(face);
        GameMenu.menuBar.getChildren().add(numberText);
        GameMenu.menuBar.getChildren().add(name);
    }


    private static void setCenterToChangeRates() {
        Text text = new Text(String.format("Food Rate : %d",
                GovernmentController.getCurrentGovernment().getFoodRate()));
        Slider foodRate = new Slider(-2, 2, GovernmentController.
                getCurrentGovernment().getFoodRate());
        foodRate.setSnapToTicks(true);
        foodRate.setMajorTickUnit(1.0);
        foodRate.setMinorTickCount(0);
        foodRate.setShowTickMarks(true);
        foodRate.setShowTickLabels(true);
        GameMenu.menuBar.getChildren().add(foodRate);
        GameMenu.menuBar.getChildren().add(text);
        foodRate.setTranslateX(450);
        foodRate.setTranslateY(100);
        text.setTranslateX(300);
        text.setTranslateY(110);
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        foodRate.setOnMouseReleased(e -> {
            GovernmentController.getCurrentGovernment().setFoodRate((int) foodRate.getValue());
            text.setText(String.format("Food Rate : %d",
                    GovernmentController.getCurrentGovernment().getFoodRate()));
            int popularity = GovernmentController.getCurrentGovernment().getPopularity() + 37;
            GameViewController.popularityReporter.setText(String.format("%d", popularity));
            updateFaceOfReporter();
        });

        Text text2 = new Text(String.format("Fear Rate : %d",
                GovernmentController.getCurrentGovernment().getFearRate()));
        Slider fearRate = new Slider(-5, 5, GovernmentController.
                getCurrentGovernment().getFearRate());
        fearRate.setSnapToTicks(true);
        fearRate.setMajorTickUnit(1.0);
        fearRate.setMinorTickCount(0);
        fearRate.setShowTickMarks(true);
        fearRate.setShowTickLabels(true);
        GameMenu.menuBar.getChildren().add(fearRate);
        GameMenu.menuBar.getChildren().add(text2);
        fearRate.setTranslateX(450);
        fearRate.setTranslateY(155);
        text2.setTranslateX(300);
        text2.setTranslateY(165);
        text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        fearRate.setOnMouseReleased(e -> {
            GovernmentController.getCurrentGovernment().setFearRate((int) fearRate.getValue());
            text2.setText(String.format("Fear Rate : %d",
                    GovernmentController.getCurrentGovernment().getFearRate()));
            int popularity = GovernmentController.getCurrentGovernment().getPopularity() + 37;
            GameViewController.popularityReporter.setText(String.format("%d", popularity));
            updateFaceOfReporter();
        });
    }

    private static void setCenterToGovernment() {
        ImageView shieldIcon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/shieldIcon.png");
        shieldIcon.setScaleX(0.2);
        shieldIcon.setScaleY(0.2);
        shieldIcon.setTranslateX(-120);
        shieldIcon.setTranslateY(-140);
        GameMenu.menuBar.getChildren().add(shieldIcon);

        Text lordNameText = new Text("Lord " + GovernmentController.getCurrentGovernment().getUser().getNickname());
        lordNameText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        lordNameText.setTranslateX(340);
        lordNameText.setTranslateY(100);
        GameMenu.menuBar.getChildren().add(lordNameText);

        MenuButton popularity = new MenuButton("Popularity", GameMenu.menuBar,
                500, 90, false);
        GameMenu.menuBar.getChildren().add(popularity);
        popularity.setOnMouseClicked(e -> {
            setCenterOfBar("Popularity");
        });

        MenuButton changeRates = new MenuButton("Change Rates", GameMenu.menuBar, 500, 150, false);
        GameMenu.menuBar.getChildren().add(changeRates);
        changeRates.setOnMouseClicked(e -> {
            setCenterOfBar("Change Rates");
        });
    }

    private static void setCenterToSelectMainCastle() {

        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/taxIcon.png");
        GameMenu.menuBar.getChildren().add(icon);
        icon.setScaleX(0.3);
        icon.setScaleY(0.3);
        icon.setTranslateY(-20);
        icon.setTranslateX(120);

        Text numberOfPeople = new Text(String.format("%d            =            %d", GameController.getGame().getCurrentGovernment().getPopulation()
                , ((MainCastle) selectedBuilding).getTotalTax())); // TODO : update after update turn
        GameMenu.menuBar.getChildren().add(numberOfPeople);
        numberOfPeople.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        numberOfPeople.setTranslateX(510);
        numberOfPeople.setTranslateY(150);

        Text towerText = new Text("Main Castle");
        towerText.setTranslateX(270);
        towerText.setTranslateY(95);
        GameMenu.menuBar.getChildren().add(towerText);
        towerText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

        ImageView taxLine = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/lineOfTax.png");
        GameMenu.menuBar.getChildren().add(taxLine);
        taxLine.setScaleX(0.25);
        taxLine.setScaleY(0.25);
        taxLine.setTranslateY(50);
        taxLine.setTranslateX(250);

        ImageView rightButton = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/rightButton.png");
        GameMenu.menuBar.getChildren().add(rightButton);
        rightButton.setScaleX(0.25);
        rightButton.setScaleY(0.25);
        rightButton.setTranslateY(50);
        rightButton.setTranslateX(625);
        rightButton.setOnMouseClicked(e -> {
            GameMenu.hoveringBarStateText.setText(GovernmentController.changeTaxRate
                    (selectedBuilding.getGovernment().getTaxRate()+1));
            numberOfPeople.setText(String.format("%d            =            %d" ,GameController.getGame().getCurrentGovernment().getPopulation()
                    , ((MainCastle)selectedBuilding).getTotalTax()));
            int popularity = GovernmentController.getCurrentGovernment().getPopularity() + 37;
            GameViewController.popularityReporter.setText(String.format("%d", popularity));
            updateFaceOfReporter();

        });

        ImageView leftButton = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/leftButton.png");
        GameMenu.menuBar.getChildren().add(leftButton);
        leftButton.setScaleX(0.25);
        leftButton.setScaleY(0.25);
        leftButton.setTranslateY(50);
        leftButton.setTranslateX(440);
        leftButton.setOnMouseClicked(e -> {
            GameMenu.hoveringBarStateText.setText(GovernmentController.changeTaxRate
                    (selectedBuilding.getGovernment().getTaxRate() - 1));
            numberOfPeople.setText(String.format("%d            =            %d", GameController.getGame().getCurrentGovernment().getPopulation()
                    , ((MainCastle) selectedBuilding).getTotalTax()));
        });

        ImageView headIcon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/headIcon.png");
        GameMenu.menuBar.getChildren().add(headIcon);
        headIcon.setScaleX(0.25);
        headIcon.setScaleY(0.25);
        headIcon.setTranslateX(495);
        headIcon.setTranslateY(75);

        ImageView coinIcon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/coinIcon.png");
        GameMenu.menuBar.getChildren().add(coinIcon);
        coinIcon.setScaleY(0.25);
        coinIcon.setScaleX(0.25);
        coinIcon.setTranslateX(550);
        coinIcon.setTranslateY(105);

    }

    private static void setCenterToSelectGatehouse() {

        Text towerText = new Text("Gatehouse And Drawbridge");
        towerText.setTranslateX(270);
        towerText.setTranslateY(95);
        GameMenu.menuBar.getChildren().add(towerText);
        towerText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

        ProgressBar progressBar = new ProgressBar(1);
        progressBar.setTranslateX(600);
        progressBar.setTranslateY(95);
        GameMenu.menuBar.getChildren().add(progressBar); // TODO: live change

        Text hpOfTower = new Text(selectedBuilding.getHp() + " / " + selectedBuilding.getMaxHp());
        hpOfTower.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        hpOfTower.setTranslateX(620);
        hpOfTower.setTranslateY(85);
        GameMenu.menuBar.getChildren().add(hpOfTower);

        MenuButton repairButton = new MenuButton("Repair", GameMenu.menuBar, 520, 120, false);
        repairButton.setScaleX(0.4);
        repairButton.setScaleY(0.4);
        GameMenu.menuBar.getChildren().add(repairButton);
        repairButton.setOnMouseClicked(e -> {
            GameMenu.hoveringBarStateText.setText(BuildingController.repair());
            if (GameMenu.hoveringBarStateText.getText().equals("Successfully repaired!")) {
                progressBar.setProgress(1);
            }
        });

        ImageView closeIcon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/closeIcon.png");
        ImageView openIcon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/openIcon.png");
        closeIcon.setScaleY(0.25);
        openIcon.setScaleY(0.25);
        closeIcon.setScaleX(0.25);
        openIcon.setScaleX(0.25);
        closeIcon.setOnMouseClicked(e -> {
            GameMenu.hoveringBarStateText.setText(BuildingController.openOrCloseGatehouse("close"));
            GameMap.getGameTile(BuildingController.getBuilding().getEndX(), BuildingController.getBuilding().getEndY())
                    .refreshTile();
            System.out.println("pressed");
        });
        openIcon.setOnMouseClicked(e -> {
            GameMenu.hoveringBarStateText.setText(BuildingController.openOrCloseGatehouse("open"));
            GameMap.getGameTile(BuildingController.getBuilding().getEndX(), BuildingController.getBuilding().getEndY())
                    .refreshTile();
            System.out.println("pressed");
        });
        GameMenu.menuBar.getChildren().add(closeIcon);
        GameMenu.menuBar.getChildren().add(openIcon);
        closeIcon.setTranslateX(400);
        closeIcon.setTranslateY(80);
        openIcon.setTranslateX(490);
        openIcon.setTranslateY(80);
    }

    private static void setCenterToSelectTowersBuilding() {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/soldierIcon.png");
        GameMenu.menuBar.getChildren().add(icon);
        icon.setScaleX(0.3);
        icon.setScaleY(0.3);
        icon.setTranslateY(-30);
        icon.setTranslateX(140);

        Text towerText = new Text("Tower");
        towerText.setTranslateX(270);
        towerText.setTranslateY(95);
        GameMenu.menuBar.getChildren().add(towerText);
        towerText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));

        Text towerNameText = new Text(buildingNameToName.get(selectedBuilding.getName()));
        towerNameText.setTranslateX(450);
        towerNameText.setTranslateY(120);
        GameMenu.menuBar.getChildren().add(towerNameText);
        towerNameText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        ProgressBar progressBar = new ProgressBar(1);
        progressBar.setTranslateX(600);
        progressBar.setTranslateY(95);
        GameMenu.menuBar.getChildren().add(progressBar); // TODO: live change

        Text hpOfTower = new Text(selectedBuilding.getHp() + " / " + selectedBuilding.getMaxHp());
        hpOfTower.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        hpOfTower.setTranslateX(620);
        hpOfTower.setTranslateY(85);
        GameMenu.menuBar.getChildren().add(hpOfTower);

        MenuButton repairButton = new MenuButton("Repair", GameMenu.menuBar, 520, 120, false);
        repairButton.setScaleX(0.4);
        repairButton.setScaleY(0.4);
        GameMenu.menuBar.getChildren().add(repairButton);
        repairButton.setOnMouseClicked(e -> {
            GameMenu.hoveringBarStateText.setText(BuildingController.repair());
            if (GameMenu.hoveringBarStateText.getText().equals("Successfully repaired!")) {
                progressBar.setProgress(1);
            }
        });
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

    private static void setCenterOfEditFeatures() {
        putEditRockImageView("bigRockIcon", "Rock - South",
                RockDirections.SOUTH, 290, 85, 0.2);
        putEditRockImageView("bigRockIcon", "Rock - East",
                RockDirections.EAST, 325, 85, 0.2);
        putEditRockImageView("bigRockIcon", "Rock - North",
                RockDirections.NORTH, 360, 85, 0.2);
        putEditRockImageView("bigRockIcon", "Rock - West",
                RockDirections.WEST, 395, 85, 0.2);
        putEditRockImageView("bigRockIcon", "Rock - Random",
                RockDirections.getRandomDirection(), 430, 85, 0.2);

    }


    private static void setCenterOfEditLand() {
        putEditTextureImageView("earthAndStoneIcon", "Earth And Stones"
                , Textures.EARTH, 290, 85, 0.2);
        putEditTextureImageView("rocksIcon", "Rocks"
                , Textures.ROCK_TEXTURE, 270, 105, 0.2);
        putEditTextureImageView("ironTextureIcon", "Iron"
                , Textures.IRON_TEXTURE, 360, 90, 0.2);

        putEditTextureImageView("drivenSandIcon", "Driven Sand"
                , Textures.EARTH_AND_SAND, 360, 40, 0.2);
        putEditTextureImageView("scrubIcon", "Scrub"
                , Textures.GRASS, 450, 60, 0.2);
        putEditTextureImageView("thickScrubIcon", "Thick Scrub"
                , Textures.THICK_GRASS, 450, 120, 0.2);
        putEditTextureImageView("oasisGrassIcon", "Oasis Grass"
                , Textures.OASIS_GRASS, 520, 60, 0.2);
        putEditTextureImageView("bouldersIcon", "Boulder"
                , Textures.BOULDER, 500, 70, 0.2);
    }

    private static void setCenterOfEditWater() {
        putEditTextureImageView("seaIcon", "Sea"
                , Textures.SEA, 290, 65, 0.2);
        putEditTextureImageView("beachIcon", "Beach"
                , Textures.BEACH, 305, 95, 0.2);
        putEditTextureImageView("largePoundIcon", "Large Pound"
                , Textures.LARGE_POND, 350, 75, 0.2);

        putEditTextureImageView("smallPoundIcon", "Small Pound"
                , Textures.SMALL_POND, 360, 40, 0.2);
        putEditTextureImageView("riverIcon", "River"
                , Textures.RIVER, 475, 40, 0.2);
        putEditTextureImageView("fordIcon", "Ford"
                , Textures.LOW_DEPTH_WATER, 460, 100, 0.2);
        putEditTextureImageView("marshIcon", "Marsh"
                , Textures.MARSH, 550, 30, 0.2);
        putEditTextureImageView("oilIcon", "Oil"
                , Textures.OIL, 505, 90, 0.2);
    }

    private static void setCenterOfEditVegetation() {
        putEditTreeImageView("datePalmIcon", "Date Palm", Trees.DATE_PALM,
                300, 0, 0.2);
        putEditTreeImageView("coconutPalmIcon", "Coconut Palm", Trees.COCONUT_PALM,
                400, -20, 0.2);
        putEditTreeImageView("oliveTreeIcon", "Olive tree", Trees.OLIVE_TREE,
                430, 20, 0.2);
        putEditTreeImageView("desertShrubIcon", "Desert Shrub", Trees.DESERT_SHRUB,
                580, 50, 0.2);
        putEditTreeImageView("cherryPalmIcon", "Cherry Palm", Trees.CHERRY_PALM,
                600, 0, 0.2);
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
        setEventForDeleteIcon(deleteImage);

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

    private static void setEventForDeleteIcon(ImageView deleteImage) {
        deleteImage.setOnMouseEntered(mouseEvent -> GameMenu.hoveringBarStateText.setText("Delete"));
        deleteImage.setOnMouseExited(mouseEvent -> GameMenu.hoveringBarStateText.setText(""));
        deleteImage.setOnMouseClicked(e -> {
            isDelete = true;
            System.out.println("changed cursor!");
            Image image = new Image(LoginMenu.class.getResource(Paths.GAME_IMAGES.getPath())
                    .toExternalForm() + "cursor/crossCursor.png");
            GameMenu.scene.setCursor(new ImageCursor(image,
                    image.getWidth() / 2,
                    image.getHeight() / 2));
        });
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
        putGatehouseImageView("smallGatehouseIcon", "Small Gatehouse", "smallStoneGatehouse", 250, 0, 0.25, "smallStoneGatehouse");
        putGatehouseImageView("bigGatehouseIcon", "Big Gatehouse", "bigStoneGatehouse", 290, -50, 0.25, "bigStoneGatehouse");
        putBuildingImageView("drawBridgeIcon", "Draw Bridge", "drawBridge", 400, 20, 0.25, "drawBridge");
        putBuildingImageView("cageIcon", "Caged Dogs", "cagedWarDogs", 450, 30, 0.25, "cagedWarDogs");
        //putBuildingImageView("pitchDitchIcon", "Pitch Ditch", "", 530, 50, 0.25); // TODO:
        putBuildingImageView("killingPitIcon", "Killing Pit", "killingPit", 530, 90, 0.25, "killingPit");
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

    public static void setCenterToCastleBuildings() {
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
        currentCategory = null;
        shopMenuPhase = 0;
        setTitle("The Marketplace", 32, 275, 95);
        putShopIcon("foods", 340, 135, false, true);
        putShopIcon("rawMaterials", 440, 135, false, true);
        putShopIcon("weapons", 540, 130, false, true);
        putShopIcon("resources", 640, 132, false, true);
        putTradeHistoryButton();
        putTradeListButton();
    }

    private static void setCenterToFoodsInShop() {
        currentItem = null;
        shopMenuPhase = 1;
        setTitle("Food", 32, 275, 95);
        putShopIcon("meat", 320, 125, true, true);
        putShopIcon("cheese", 375, 125, true, true);
        putShopIcon("apple", 430, 120, true, true);
        putShopIcon("hops", 485, 120, true, true);
        putShopIcon("ale", 540, 120, true, true);
        putShopIcon("wheat", 595, 115, true, true);
        putShopIcon("flour", 645, 117, true, true);
        putShopIcon("bread", 700, 122, true, true);
        putBackButton("shop");
    }

    private static void setCenterToRawMaterialsInShop() {
        currentItem = null;
        shopMenuPhase = 1;
        setTitle("Raw Materials", 32, 275, 95);
        putShopIcon("wood", 375, 120, true, true);
        putShopIcon("stone", 475, 120, true, true);
        putShopIcon("iron", 575, 120, true, true);
        putShopIcon("pitch", 675, 120, true, true);
        putBackButton("shop");
    }

    private static void setCenterToWeaponsInShop() {
        currentItem = null;
        shopMenuPhase = 1;
        setTitle("Weapons", 32, 275, 95);
        putShopIcon("spear", 320, 120, true, true);
        putShopIcon("bow", 375, 120, true, true);
        putShopIcon("mace", 430, 120, true, true);
        putShopIcon("leatherArmour", 485, 120, true, true);
        putShopIcon("crossBow", 540, 120, true, true);
        putShopIcon("pike", 595, 120, true, true);
        putShopIcon("sword", 650, 120, true, true);
        putShopIcon("metalArmour", 705, 120, true, true);
        putBackButton("shop");
    }

    private static void setCenterToAllResourcesInShop() {
        currentItem = null;
        shopMenuPhase = 1;
        setTitle("Resources", 24, 270, 85);
        putShopIcon("meat", 300, 95, 145);
        putShopIcon("cheese", 350, 95, 145);
        putShopIcon("apple", 400, 90, 145);
        putShopIcon("hops", 450, 90, 145);
        putShopIcon("wood", 500, 90, 145);
        putShopIcon("stone", 550, 90, 145);
        putShopIcon("spear", 600, 90, 145);
        putShopIcon("bow", 650, 90, 145);
        putShopIcon("mace", 700, 90, 145);
        putShopIcon("leatherArmour", 750, 90, 145);
        putShopIcon("ale", 300, 155, 210);
        putShopIcon("wheat", 350, 155, 210);
        putShopIcon("bread", 400, 155, 210);
        putShopIcon("iron", 450, 155, 210);
        putShopIcon("pitch", 500, 155, 210);
        putShopIcon("crossBow", 550, 155, 210);
        putShopIcon("pike", 600, 155, 210);
        putShopIcon("sword", 650, 155, 210);
        putShopIcon("metalArmour", 700, 155, 210);
        putShopIcon("flour", 750, 155, 210);
        putBackButton("shop");
    }

    private static void setCenterToShopItem(String fileName) {
        shopMenuPhase = 2;
        setTitle(convertFileName(fileName), 32, 275, 85);
        putShopIcon(fileName, 320, 130, true, false);
        MenuTextField buyAmount = new MenuTextField(GameMenu.menuBar, "", "", 400, 90, 100);
        MenuTextField sellAmount = new MenuTextField(GameMenu.menuBar, "", "", 400, 160, 100);
        buyAmount.setText("1");
        buyAmount.getErrorLabel().setTranslateY(buyAmount.getErrorLabel().getTranslateY() + 15);
        sellAmount.setText("1");
        sellAmount.getErrorLabel().setTranslateY(sellAmount.getErrorLabel().getTranslateY() + 15);
        int buyPrice = GameGoods.goods.get(fileName).getPrice();
        int sellPrice = (int) Math.ceil(GameGoods.goods.get(fileName).getPrice() * 0.5);
        MenuButton buy = new MenuButton("Buy (" + buyPrice + " golds)", GameMenu.menuBar, 525, 90, false);
        MenuButton sell = new MenuButton("Sell (" + sellPrice + " golds)", GameMenu.menuBar, 525, 160, false);

        buy.setOnAction(actionEvent -> {
            buyAmount.clearErrorOrMessage();
            if (MarketController.buyItem(fileName, buyAmount)) {
                MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "success",
                        "buy process done successfully!");
                GameMenu.root.getChildren().add(popUp);
                setCenterOfBar("shopItem");
            }

        });
        sell.setOnAction(actionEvent -> {
            sellAmount.clearErrorOrMessage();
            if (MarketController.sellItem(fileName, sellAmount)) {
                MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, "success",
                        "sell process done successfully!");
                GameMenu.root.getChildren().add(popUp);
                setCenterOfBar("shopItem");
            }
        });

        buyAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[\\D]");
                Matcher matcher = pattern.matcher(newValue);
                if (matcher.find()) buyAmount.setText(oldValue);
                if (newValue.isEmpty() || newValue == null) return;
                int amount = Integer.parseInt(buyAmount.getText());
                if (amount <= 0) buyAmount.setText(oldValue);
                if (amount != 0) buy.setText("  Buy (" + (amount * buyPrice) + " golds)");
            }
        });
        sellAmount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[\\D]");
                Matcher matcher = pattern.matcher(newValue);
                if (matcher.find()) sellAmount.setText(oldValue);
                if (newValue.isEmpty() || newValue == null) return;
                int amount = Integer.parseInt(sellAmount.getText());
                if (amount <= 0) sellAmount.setText(oldValue);
                if (amount != 0) sell.setText("  Sell (" + (amount * sellPrice) + " golds)");
            }
        });

        putBackButton(currentCategory);
        putTradeButton();
        GameMenu.menuBar.getChildren().addAll(buyAmount, sellAmount, buy, sell);
    }

    private static void setCenterToShowUsers() {
        setTitle("Choose a government to trade with:", 28, 275, 100);

        ArrayList<String> castles = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < GameController.getGame().getGovernments().size(); i++) {
            Government government = GameController.getGame().getGovernments().get(i);
            if (!government.equals(GameController.getGame().getCurrentGovernment()))
                castles.add(count++ + ". " + government.getUser().getUsername());
        }
        MenuChoiceBox castleNumber = new MenuChoiceBox(GameMenu.menuBar, "", 375, 140,
                FXCollections.observableArrayList(castles), 300);
        castleNumber.setValue("Government");

        castleNumber.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                TradeController.setTargetGovernment(GameController.getGame().
                        getGovernmentByUsername(((String) newValue).substring(3)));
                setCenterOfBar("sendRequest");
            }
        });
        GameMenu.menuBar.getChildren().add(castleNumber);
    }

    private static void setCenterToSendRequest(String item) {
        setTitle("Send Request", 28, 275, 95);

        Button decreaseAmount = new Button("-");
        decreaseAmount.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        decreaseAmount.setTranslateX(310);
        decreaseAmount.setTranslateY(110);
        decreaseAmount.setMinWidth(40);
        decreaseAmount.setMaxWidth(40);
        decreaseAmount.setDisable(true);
        decreaseAmount.getStyleClass().add("addButton");

        MenuTextField amountField = new MenuTextField(GameMenu.menuBar, "", "", 365, 110, 80);
        amountField.getErrorLabel().setTranslateY(amountField.getErrorLabel().getTranslateY() + 8);
        amountField.setText("1");

        Button increaseAmount = new Button("+");
        increaseAmount.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        increaseAmount.setTranslateX(460);
        increaseAmount.setTranslateY(110);
        increaseAmount.setMinWidth(40);
        increaseAmount.setMaxWidth(40);
        increaseAmount.getStyleClass().add("addButton");

        increaseAmount.setOnMouseClicked(mouseEvent -> {
            if (amountField.getText().isEmpty() || amountField.getText() == null) return;
            int amount = Integer.parseInt(amountField.getText());
            amountField.setText(Integer.toString(++amount));
            if (amount != 1) decreaseAmount.setDisable(false);
        });
        decreaseAmount.setOnMouseClicked(mouseEvent -> {
            if (amountField.getText().isEmpty() || amountField.getText() == null) return;
            int amount = Integer.parseInt(amountField.getText());
            amountField.setText(Integer.toString(--amount));
            if (amount == 1) decreaseAmount.setDisable(true);
        });
        amountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[\\D]");
                Matcher matcher = pattern.matcher(newValue);
                if (matcher.find()) amountField.setText(oldValue);
                else {
                    if (newValue.isEmpty() || newValue == null) {
                        decreaseAmount.setDisable(true);
                        return;
                    }
                    int amount = Integer.parseInt(newValue);
                    if (amount <= 0) amountField.setText(oldValue);
                    else {
                        amountField.clearErrorOrMessage();
                        if (amount == 1) decreaseAmount.setDisable(true);
                        else decreaseAmount.setDisable(false);
                    }
                }
            }
        });

        MenuButton requestType = new MenuButton("Request", GameMenu.menuBar, 530, 110, false);
        MenuTextField priceField = new MenuTextField(GameMenu.menuBar, "Price", "", 310, 165, 120);
        priceField.getErrorLabel().setTranslateY(priceField.getErrorLabel().getTranslateY() + 8);
        MenuTextField messageField = new MenuTextField(GameMenu.menuBar, "Message", "", 445, 165, 285);
        messageField.getErrorLabel().setTranslateY(messageField.getErrorLabel().getTranslateY() + 8);

        requestType.setOnMouseClicked(mouseEvent -> {
            priceField.clearErrorOrMessage();
            if (requestType.getText().equals("  Denote")) {
                requestType.setText("  Request");
                priceField.setDisable(false);
            } else {
                requestType.setText("  Denote");
                priceField.setText("");
                priceField.setDisable(true);
            }
        });
        priceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Pattern pattern = Pattern.compile("[\\D]");
                Matcher matcher = pattern.matcher(newValue);
                if (matcher.find()) priceField.setText(oldValue);
                else {
                    if (newValue.isEmpty() || newValue == null) return;
                    int price = Integer.parseInt(newValue);
                    if (price <= 0) priceField.setText(oldValue);
                }
            }
        });

        GameMenu.menuBar.getChildren().addAll(increaseAmount, amountField, decreaseAmount,
                requestType, priceField, messageField);
        putSendRequestButton(amountField, priceField, messageField);
        putBackButton("shopItem");
    }

    private static void setCenterToTradeList() {
        setTitle("Trade List", 28, 275, 95);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(100);
        scrollPane.setPrefViewportWidth(150);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        scrollPane.setTranslateX(450);
        scrollPane.setTranslateY(100);
        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(10);
        scrollPane.setContent(pane);

        int count = 0;
        Government government = GameController.getGame().getCurrentGovernment();
        for (String id : government.getReceivedTrades().keySet()) {
            Trade trade = government.getReceivedTrades().get(id);
            if (!trade.isAccepted() && !trade.isRejected()) {
                Text idText = new Text("ID: " + id);
                idText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                GridPane.setHalignment(idText, HPos.LEFT);
                pane.add(idText, 0, count);
                putViewRequestButton(id, pane, count++, false);
            }
        }
        if (count == 0) {
            Text emptyText = new Text("Empty");
            emptyText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            GridPane.setHalignment(emptyText, HPos.CENTER);
            GridPane.setRowSpan(emptyText, 2);
            pane.add(emptyText, 0, count);
        }

        putBackButton("shop");
        GameMenu.menuBar.getChildren().add(scrollPane);
    }

    private static void setCenterToTradeHistory() {
        setTitle("History List", 28, 275, 95);
        setTitle("Sent", 22, 470, 90);
        ScrollPane sentScrollPane = new ScrollPane();
        sentScrollPane.setPrefViewportHeight(100);
        sentScrollPane.setPrefViewportWidth(150);
        sentScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sentScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        sentScrollPane.setTranslateX(420);
        sentScrollPane.setTranslateY(110);
        GridPane sentPane = new GridPane();
        sentPane.setHgap(20);
        sentPane.setVgap(10);
        sentScrollPane.setContent(sentPane);

        int count = 0;
        Government government = GameController.getGame().getCurrentGovernment();
        for (String id : government.getSentTrades().keySet()) {
            Trade trade = government.getSentTrades().get(id);
            Text idText = new Text("ID: " + id);
            idText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            GridPane.setHalignment(idText, HPos.LEFT);
            sentPane.add(idText, 0, count);
            putViewRequestButton(id, sentPane, count++, true);
        }
        if (count == 0) {
            Text emptyText = new Text("Empty");
            emptyText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            GridPane.setHalignment(emptyText, HPos.CENTER);
            GridPane.setRowSpan(emptyText, 2);
            sentPane.add(emptyText, 0, count);
        }

        setTitle("Received", 22, 650, 90);
        ScrollPane receivedScrollPane = new ScrollPane();
        receivedScrollPane.setPrefViewportHeight(100);
        receivedScrollPane.setPrefViewportWidth(150);
        receivedScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        receivedScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        receivedScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        receivedScrollPane.setTranslateX(600);
        receivedScrollPane.setTranslateY(110);
        GridPane receivedPane = new GridPane();
        receivedPane.setHgap(20);
        receivedPane.setVgap(10);
        receivedScrollPane.setContent(receivedPane);

        count = 0;
        for (String id : government.getReceivedTrades().keySet()) {
            Trade trade = government.getReceivedTrades().get(id);
            if (trade.isAccepted() || trade.isRejected()) {
                Text idText = new Text("ID: " + id);
                idText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                GridPane.setHalignment(idText, HPos.LEFT);
                receivedPane.add(idText, 0, count);
                putViewRequestButton(id, receivedPane, count++, false);
            }
        }
        if (count == 0) {
            Text emptyText = new Text("Empty");
            emptyText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
            GridPane.setHalignment(emptyText, HPos.CENTER);
            GridPane.setRowSpan(emptyText, 2);
            receivedPane.add(emptyText, 0, count);
        }

        putBackButton("shop");
        GameMenu.menuBar.getChildren().addAll(sentScrollPane, receivedScrollPane);
    }

    private static void setCenterToShowTrade(String id, boolean showReceiver, boolean showOptions) {
        Trade trade = TradeController.allTrades.get(id);
        showTradeType(trade);
        showTradeItem(trade);
        showTradeAmount(trade);
        showTradePrice(trade);
        if (showReceiver) showTradeReceiver(trade);
        else showTradeSender(trade);
        showTradeState(trade);
        MenuTextField message;
        if (trade.isAccepted()) message = showTradeAcceptMessage(trade);
        else message = showTradeRequestMessage(trade);
        if (showOptions) {
            putAcceptTradeButton(trade, false, message);
            putRejectTradeButton(trade);
        }
        putBackButton("shop");
    }

    private static void showTradeType(Trade trade) {
        Text tradeTypeText = new Text(275, 88, "Type:");
        tradeTypeText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField tradeType = new MenuTextField(GameMenu.menuBar, "", "", 330, 65, 100);
        tradeType.setText(trade.getTradeType());
        tradeType.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(tradeTypeText, tradeType);
    }

    private static void showTradeItem(Trade trade) {
        Text itemText = new Text(445, 88, "Item:");
        itemText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField item = new MenuTextField(GameMenu.menuBar, "", "", 495, 65, 200);
        item.setText(convertFileName(trade.getType()));
        item.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(itemText, item);
    }

    private static void showTradeAmount(Trade trade) {
        Text amountText = new Text(275, 125, "Amount:");
        amountText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField amount = new MenuTextField(GameMenu.menuBar, "", "", 355, 102, 80);
        amount.setText(Integer.toString(trade.getAmount()));
        amount.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(amountText, amount);
    }

    private static void showTradePrice(Trade trade) {
        Text priceText = new Text(450, 125, "Price:");
        priceText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField priceField = new MenuTextField(GameMenu.menuBar, "", "", 505, 102, 80);
        int price = trade.getPrice();
        priceField.setText(Integer.toString(trade.getPrice()));
        priceField.setEditable(false);
        if (price == 0) priceField.setDisable(true);
        GameMenu.menuBar.getChildren().addAll(priceText, priceField);
    }

    private static void showTradeSender(Trade trade) {
        Text senderText = new Text(275, 162, "Sender:");
        senderText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField sender = new MenuTextField(GameMenu.menuBar, "", "", 345, 139, 100);
        sender.setText(trade.getSender().getUser().getUsername());
        sender.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(senderText, sender);
    }

    private static void showTradeReceiver(Trade trade) {
        Text receiverText = new Text(275, 162, "Receiver:");
        receiverText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField receiver = new MenuTextField(GameMenu.menuBar, "", "", 360, 139, 100);
        receiver.setText(trade.getReceiver().getUser().getUsername());
        receiver.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(receiverText, receiver);
    }

    private static void showTradeState(Trade trade) {
        Text stateText = new Text(475, 162, "State:");
        stateText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField state = new MenuTextField(GameMenu.menuBar, "", "", 535, 139, 150);
        if (trade.isAccepted()) state.setText("Accepted");
        else if (trade.isRejected()) state.setText("Rejected");
        else state.setText("Not Checked");
        state.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(stateText, state);
    }

    private static MenuTextField showTradeRequestMessage(Trade trade) {
        Text requestMessageText = new Text(315, 199, "Request Message:");
        requestMessageText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField requestMessage = new MenuTextField(GameMenu.menuBar, "", "", 465, 176, 250);
        requestMessage.setText(trade.getRequestMessage());
        requestMessage.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(requestMessageText, requestMessage);
        return requestMessage;
    }

    private static MenuTextField showTradeAcceptMessage(Trade trade) {
        Text acceptMessageText = new Text(315, 199, "Accept Message:");
        acceptMessageText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        MenuTextField acceptMessage = new MenuTextField(GameMenu.menuBar, "", "", 465, 176, 250);
        acceptMessage.setText(trade.getAcceptMessage());
        acceptMessage.setEditable(false);
        GameMenu.menuBar.getChildren().addAll(acceptMessageText, acceptMessage);
        return acceptMessage;
    }

    private static void setCenterToAcceptMessage(Trade trade) {
        MenuTextField acceptMessage = new MenuTextField(GameMenu.menuBar,
                "Accept Message", "", 285, 100, 250);
        putAcceptTradeButton(trade, true, acceptMessage);
        GameMenu.menuBar.getChildren().add(acceptMessage);
    }

    private static void setCenterToArmoury() {
        setTitle("Armory", 32, 275, 95);
        putShopIcon("bow", 285, 120, true, false);
        putShopIcon("spear", 345, 120, true, false);
        putShopIcon("mace", 405, 120, true, false);
        putShopIcon("crossBow", 465, 120, true, false);
        putShopIcon("pike", 525, 120, true, false);
        putShopIcon("sword", 585, 120, true, false);
        putShopIcon("leatherArmour", 655, 120, true, false);
        putShopIcon("metalArmour", 705, 120, true, false);
    }

    private static void setCenterToMercenaryPost() {
        Text cost = new Text();
        cost.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        cost.setFill(new Color(0.93, 0.88, 0.61, 1));
        cost.setStrokeWidth(0.1);
        cost.setStroke(Color.BLACK);
        Text name = new Text();
        name.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        name.setFill(new Color(0.93, 0.88, 0.61, 1));
        name.setStrokeWidth(0.1);
        name.setStroke(Color.BLACK);
        cost.setTranslateX(275);
        cost.setTranslateY(207);
        name.setTranslateX(530);
        name.setTranslateY(207);

        ImageView coin = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "coin.png");
        coin.setTranslateX(337);
        coin.setTranslateY(192);

        putTroopImage("archerBow", 260, 74, cost, name, null, coin);
        putTroopImage("slave", 330, 65, cost, name, null, coin);
        putTroopImage("slinger", 412, 75, cost, name, null, coin);
        putTroopImage("assassin", 480, 85, cost, name, null, coin);
        putTroopImage("horseArcher", 553, 68, cost, name, null, coin);
        putTroopImage("arabianSwordsman", 645, 75, cost, name, null, coin);
        putTroopImage("fireThrower", 723, 92, cost, name, null, coin);

        Text peasantsNumber = new Text();
        peasantsNumber.setTranslateX(750);
        peasantsNumber.setTranslateY(207);
        peasantsNumber.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        peasantsNumber.setFill(new Color(0.93, 0.88, 0.61, 1));
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> {
            int count = 0;
            for (Human human : GameController.getGame().getCurrentGovernment().getSociety()) {
                if (human instanceof Civilian civilian && !civilian.isHasJob()) {
                    count++;
                }
            }
            peasantsNumber.setText(Integer.toString(count));
        }), new KeyFrame(Duration.millis(500), actionEvent -> {
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        GameMenu.menuBar.getChildren().addAll(cost, name, peasantsNumber);
    }

    private static void setCenterToBarrack() {
        Text cost = new Text();
        cost.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        cost.setFill(new Color(0.93, 0.88, 0.61, 1));
        Text name = new Text();
        name.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        name.setFill(new Color(0.93, 0.88, 0.61, 1));
        cost.setTranslateX(265);
        cost.setTranslateY(55);
        name.setTranslateX(530);
        name.setTranslateY(55);

        ImageView coin = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "coin.png");
        coin.setTranslateX(315);
        coin.setTranslateY(40);

        HashMap<String, ImageView> requirements = new HashMap<>();
        requirements.put("bow", putShopIcon("bow", 260, 175, false, false));
        requirements.put("spear", putShopIcon("spear", 325, 175, false, false));
        requirements.put("mace", putShopIcon("mace", 385, 173, false, false));
        requirements.put("crossBow", putShopIcon("crossBow", 435, 175, false, false));
        requirements.put("pike", putShopIcon("pike", 500, 173, false, false));
        requirements.put("sword", putShopIcon("sword", 555, 173, false, false));
        requirements.put("leatherArmour", putShopIcon("leatherArmour", 625, 175, false, false));
        requirements.put("metalArmour", putShopIcon("metalArmour", 670, 173, false, false));
        requirements.put("horse", putShopIcon("horse", 743, 173, false, false));

        putTroopImage("archer", 265, 54, cost, name, requirements, coin);
        putTroopImage("spearman", 332, 75, cost, name, requirements, coin);
        putTroopImage("maceman", 422, 70, cost, name, requirements, coin);
        putTroopImage("crossbowman", 500, 78, cost, name, requirements, coin);
        putTroopImage("pikeman", 578, 60, cost, name, requirements, coin);
        putTroopImage("swordsman", 650, 60, cost, name, requirements, coin);
        putTroopImage("knight", 718, 60, cost, name, requirements, coin);

        Text peasantsNumber = new Text();
        peasantsNumber.setTranslateX(750);
        peasantsNumber.setTranslateY(60);
        peasantsNumber.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        peasantsNumber.setFill(new Color(0.93, 0.88, 0.61, 1));
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, actionEvent -> {
            int count = 0;
            for (Human human : GameController.getGame().getCurrentGovernment().getSociety()) {
                if (human instanceof Civilian civilian && !civilian.isHasJob()) {
                    count++;
                }
            }
            peasantsNumber.setText(Integer.toString(count));
        }), new KeyFrame(Duration.millis(500), actionEvent -> {
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        GameMenu.menuBar.getChildren().addAll(cost, name, peasantsNumber);
    }

    private static void setCenterToEngineerMenu() {

    }

    private static void setTitle(String title, int fontSize, double x, double y) {
        Text menuTitle = new Text(title);
        menuTitle.setTranslateX(x);
        menuTitle.setTranslateY(y);
        menuTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, fontSize));
        GameMenu.menuBar.getChildren().add(menuTitle);
    }

    private static String convertFileName(String fileName) {
        char firstChar = fileName.charAt(0);
        fileName = fileName.replaceFirst(Character.toString(firstChar),
                "" + Character.toString(firstChar - 'a' + 'A'));
        for (int i = 1; i < fileName.length(); i++) {
            char character = fileName.charAt(i);
            if (character >= 'A' && character <= 'Z') {
                fileName = fileName.replaceFirst(Character.toString(character),
                        " " + Character.toString(character));
                i++;
            }
        }
        return fileName;
    }

    private static ImageView putShopIcon(String fileName, double x, double y, boolean count, boolean canHover) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        ColorAdjust colorAdjust = new ColorAdjust();
        if (canHover) {
            icon.setOnMouseEntered(mouseEvent -> {
                colorAdjust.setSaturation(0.2);
                colorAdjust.setBrightness(-0.1);
                icon.setEffect(colorAdjust);
                if (shopMenuPhase == 0) currentCategory = fileName;
                else if (shopMenuPhase == 1) currentItem = fileName;
            });
            icon.setOnMouseExited(mouseEvent -> {
                colorAdjust.setSaturation(0);
                colorAdjust.setBrightness(0);
                icon.setEffect(colorAdjust);
            });
        }
        icon.setOnMouseClicked(mouseEvent -> {
            if (shopMenuPhase == 1) setCenterOfBar("shopItem");
            else setCenterOfBar(fileName);
        });
        if (count) {
            String numberOfResource = Integer.toString(GameController.getGame().getCurrentGovernment().getPropertyAmount(fileName));
            Text resourceCount = new Text(numberOfResource);
            resourceCount.setFont(Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.REGULAR, 24));
            resourceCount.setTranslateX(x + icon.getImage().getWidth() / 2 - 5 * numberOfResource.length());
            resourceCount.setTranslateY(195);
            GameMenu.menuBar.getChildren().add(resourceCount);
        }
        GameMenu.menuBar.getChildren().add(icon);
        return icon;
    }

    private static void putShopIcon(String fileName, double x, double y, double countY) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(0.8);
        icon.setScaleY(0.8);
        ColorAdjust colorAdjust = new ColorAdjust();

        String costs = Integer.toString(GameGoods.goods.get(fileName).getPrice()) + "/"
                + Integer.toString((int) Math.ceil(GameGoods.goods.get(fileName).getPrice() / 2));
        Text costsText = new Text(costs);
        costsText.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 14));
        costsText.setTranslateX(x + icon.getImage().getWidth() / 2 - 3 * costs.length());
        costsText.setTranslateY(countY);
        GameMenu.menuBar.getChildren().add(costsText);

        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putBackButton(String destination) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "back.png");
        icon.setTranslateX(262);
        icon.setTranslateY(182);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "backHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "back.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            setCenterOfBar(destination);
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putTradeButton() {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "trade.png");
        icon.setTranslateX(262);
        icon.setTranslateY(182);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "tradeHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "trade.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            setCenterOfBar("showUsers");
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putTradeHistoryButton() {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "history.png");
        icon.setTranslateX(262);
        icon.setTranslateY(142);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "historyHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "history.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            setCenterOfBar("tradeHistory");
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putTradeListButton() {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "list.png");
        icon.setTranslateX(262);
        icon.setTranslateY(182);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "listHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "list.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            setCenterOfBar("tradeList");
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putViewRequestButton(String id, GridPane pane, int row, boolean showReceiver) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "view.png");
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "viewHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "view.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            currentTradeId = id;
            if (showReceiver) setCenterOfBar("showSentTrade");
            else setCenterOfBar("showReceivedTrade");
        });
        GridPane.setHalignment(icon, HPos.CENTER);
        pane.add(icon, 1, row);
    }

    private static void putSendRequestButton(MenuTextField amountField, MenuTextField priceField, MenuTextField messageField) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "check.png");
        icon.setTranslateX(745);
        icon.setTranslateY(165);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "checkHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "check.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            amountField.clearErrorOrMessage();
            priceField.clearErrorOrMessage();
            messageField.clearErrorOrMessage();
            boolean error = false;
            if (amountField.getText().isEmpty() || amountField.getText() == null) {
                amountField.handlingError("required!");
                error = true;
            }
            if (!priceField.isDisable()) {
                if (priceField.getText().isEmpty() || priceField.getText() == null) {
                    priceField.handlingError("required!");
                    error = true;
                }
            }
            if (messageField.getText().isEmpty() || messageField.getText() == null) {
                messageField.handlingError("message is required!");
                error = true;
            }
            if (error) return;

            int price = (priceField.isDisable()) ? 0 : Integer.parseInt(priceField.getText());
            String message = TradeController.tradeGoods(currentItem, Integer.parseInt(amountField.getText()),
                    price, messageField.getText());
            String popUpType = (message.charAt(0) == 'y') ? "error" : "success";
            MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400, popUpType, message);
            if (message.charAt(0) == 'r') setCenterOfBar("shopItem");
            GameMenu.root.getChildren().add(popUp);
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putAcceptTradeButton(Trade trade, boolean isAcceptMessageAdded, MenuTextField message) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "check.png");
        icon.setTranslateX(752);
        icon.setTranslateY(180);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "checkHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "check.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            if (!isAcceptMessageAdded) {
                if (TradeController.acceptTrade(trade.getId())) setCenterOfBar("acceptMessage");
                else return;
            } else {
                if (message.getText().isEmpty() || message.getText() == null) {
                    MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400,
                            "error", "accept message is required!");
                    GameMenu.root.getChildren().add(popUp);
                } else {
                    MenuPopUp popUp = new MenuPopUp(GameMenu.root, 400, 400,
                            "success", "trade accepted successfully");
                    GameMenu.root.getChildren().add(popUp);
                }
            }
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putRejectTradeButton(Trade trade) {
        ImageView icon = new ImageView(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                .toExternalForm() + "reject.png");
        icon.setTranslateX(752);
        icon.setTranslateY(140);
        icon.setOnMouseEntered(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "rejectHovered.png"));
        });
        icon.setOnMouseExited(mouseEvent -> {
            icon.setImage(new Image(GameViewController.class.getResource(Paths.RESOURCE_IMAGES.getPath())
                    .toExternalForm() + "reject.png"));
        });
        icon.setOnMouseClicked(mouseEvent -> {
            trade.reject();
            setCenterOfBar("shop");
        });
        GameMenu.menuBar.getChildren().add(icon);
    }

    private static void putTroopImage(String troop, int x, int y, Text cost, Text name,
                                      HashMap<String, ImageView> requirements, ImageView coin) {
        ImageView image = new ImageView(GameViewController.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "troops/" + troop + ".png");
        image.setTranslateX(x);
        image.setTranslateY(y);
        ColorAdjust colorAdjust = new ColorAdjust();
        image.setOnMouseEntered(mouseEvent -> {
            colorAdjust.setSaturation(0.2);
            colorAdjust.setBrightness(-0.1);
            if (requirements != null) {
                Military human = (Military) GameHumans.getUnit(troop);
                requirements.get(human.getWeapon()).setEffect(colorAdjust);
                for (String armour : human.getArmours())
                    requirements.get(armour).setEffect(colorAdjust);
                if (human.isUsesHorse())
                    requirements.get("horse").setEffect(colorAdjust);
            }
            image.setEffect(colorAdjust);
            cost.setText("Cost " + GameHumans.getUnit(troop).getPrice());
            String troopName = convertFileName(troop);
            name.setText(troopName);
            name.setTranslateX(name.getTranslateX() - 5 * troopName.length());
            GameMenu.menuBar.getChildren().add(coin);
        });
        image.setOnMouseExited(mouseEvent -> {
            colorAdjust.setSaturation(0);
            colorAdjust.setBrightness(0);
            if (requirements != null) {
                Military human = (Military) GameHumans.getUnit(troop);
                System.out.println(human.getWeapon());
                requirements.get(human.getWeapon()).setEffect(colorAdjust);
                for (String armour : human.getArmours())
                    requirements.get(armour).setEffect(colorAdjust);
                if (human.isUsesHorse())
                    requirements.get("horse").setEffect(colorAdjust);
            }
            image.setEffect(colorAdjust);
            String troopName = convertFileName(troop);
            name.setTranslateX(name.getTranslateX() + 5 * troopName.length());
            cost.setText("");
            name.setText("");
            GameMenu.menuBar.getChildren().remove(coin);
        });
        image.setOnMouseClicked(mouseEvent -> {
            ((Barrack) selectedBuilding).makeUnit(troop);
        });
        GameMenu.menuBar.getChildren().add(image);
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

    private static void putEditRockImageView(String fileName, String name, RockDirections rockDirection, double x, double y, double size) {
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


        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isTextureSelected = true;
                GameMenu.gameMap.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && isTextureSelected) {
                            Pair<Integer, Integer> pair = tileCoordinateWithMouseEvent(mouseEvent);
                            tileX = pair.getFirst();
                            tileY = pair.getSecond();
                            System.out.println("Tile founded at : " + tileX + " " + tileY);
                            GameMenu.hoveringBarStateText.setText(MapController.dropRock(tileX, tileY, rockDirection));
                            GameMap.getGameTile(tileX, tileY).refreshTile();
                        } else {
                            isTextureSelected = false;
                        }
                    }
                });

                GameMenu.gameMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            isTextureSelected = false;
                        }
                    }
                });

            }
        });
    }

    private static void putEditTreeImageView(String fileName, String name, Trees tree, double x, double y, double size) {
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


        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isTextureSelected = true;
                GameMenu.gameMap.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && isTextureSelected) {
                            Pair<Integer, Integer> pair = tileCoordinateWithMouseEvent(mouseEvent);
                            tileX = pair.getFirst();
                            tileY = pair.getSecond();
                            System.out.println("Tile founded at : " + tileX + " " + tileY);
                            GameMenu.hoveringBarStateText.setText(MapController.dropTree(tileX, tileY, tree));
                            GameMap.getGameTile(tileX, tileY).refreshTile();
                        } else {
                            isTextureSelected = false;
                        }
                    }
                });

                GameMenu.gameMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            isTextureSelected = false;
                        }
                    }
                });

            }
        });
    }

    private static void putEditTextureImageView(String fileName, String name, Textures texture, double x, double y, double size) {
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


        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                isTextureSelected = true;
                GameMenu.gameMap.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY && isTextureSelected) {
                            Pair<Integer, Integer> pair = tileCoordinateWithMouseEvent(mouseEvent);
                            tileX = pair.getFirst();
                            tileY = pair.getSecond();
                            System.out.println("Tile founded at : " + tileX + " " + tileY);
                            GameMenu.hoveringBarStateText.setText(MapController.setTexture(tileX, tileY, texture));
                            GameMap.getGameTile(tileX, tileY).refreshTile();
                        } else {
                            isTextureSelected = false;
                        }
                    }
                });

                GameMenu.gameMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            isTextureSelected = false;
                        }
                    }
                });

            }
        });
    }

    private static void putGatehouseImageView(String fileName, String name, String buildingName, double x, double y, double size, String picFileName) {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(size);
        icon.setScaleY(size);
        GameMenu.menuBar.getChildren().add(icon);
        Building sampleBuilding = GameBuildings.getBuilding(buildingName);
        if (sampleBuilding != null) {
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
            icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    setCenterOfBar();
                }
            });
        }
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
                        droppedPicFileName = picFileName;
                        droppedBuildingName = buildingName;
                        isDropped = true;
                        dropBuildingAfterSelectingTile(mouseEvent);
                        isDropped = false;
                    }
                });
            }
        });

    }

    public static void dropBuildingAfterSelectingTile(MouseEvent mouseEvent) {
        Pair<Integer, Integer> pair = tileCoordinateWithMouseEvent(mouseEvent);
        tileX = pair.getFirst();
        tileY = pair.getSecond();
        tileY = pair.getSecond();
        System.out.println("Tile founded at : " + tileX + " " + tileY);
        String side = getSideOfGatehouseFromFilename(droppedPicFileName);
        GameMenu.hoveringBarStateText.setText(GameController.dropBuilding(tileX, tileY, droppedBuildingName, side));
        GameMap.getGameTile(tileX, tileY).refreshTile();
    }

    private static String getSideOfGatehouseFromFilename(String fileName) {
        if (fileName.contains("Right"))
            return "right";
        if (fileName.contains("Gatehouse"))
            return "left";
        return null;
    }

    public static Pair<Integer, Integer> tileCoordinateWithMouseEvent(MouseEvent mouseEvent) {
        int halfTileX = (int) ((mouseEvent.getScreenX() -
                (GameMenu.scene.getWidth() - 1200) / 2) / ((double) GameMap.tileWidth / 2));
        int halfTileY = (int) ((mouseEvent.getScreenY() -
                (GameMenu.scene.getHeight() - 800) / 2) / ((double) GameMap.tileHeight / 2));
        Pair<Integer, Integer> pair;
        if (halfTileX % 2 == 0) {
            pair = checkNearestTile(mouseEvent, halfTileX / 2, halfTileX / 2, halfTileY, halfTileY - 1);
        } else {
            if (halfTileY % 2 == 1) {
                pair = checkNearestTile(mouseEvent, (halfTileX - 1) / 2, (halfTileX + 1) / 2, halfTileY - 1, halfTileY);
            } else {
                pair = checkNearestTile(mouseEvent, (halfTileX + 1) / 2, (halfTileX - 1) / 2, halfTileY - 1, halfTileY);
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


        GameMenu.root.getChildren().addAll(downRight, downLeft, upRight,
                upLeft, right, left, up, down);

        downRight.setViewOrder(-3000);
        downLeft.setViewOrder(-3000);
        down.setViewOrder(-3000);
        left.setViewOrder(-3000);
        right.setViewOrder(-3000);

        setEventForRectangles(downRight, 1, -1, gameMap, miniMap);
        setEventForRectangles(downLeft, -1, -1, gameMap, miniMap);
        setEventForRectangles(upRight, 1, 1, gameMap, miniMap);
        setEventForRectangles(upLeft, -1, 1, gameMap, miniMap);

        setEventForRectangles(down, 0, -1, gameMap, miniMap);
        setEventForRectangles(left, -1, 0, gameMap, miniMap);
        setEventForRectangles(up, 0, 1, gameMap, miniMap);
        setEventForRectangles(right, 1, 0, gameMap, miniMap);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO , actionEvent -> {
            GameController.nextTurn();
            System.out.println("next turn!");
        }) , new KeyFrame(Duration.seconds(30) , actionEvent -> {}));
        timeline.setCycleCount(-1);
        timeline.play();
        gameTimeLine = timeline;


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


    public static void showWorkerStateOfBuilding(Building building) {
        if (building.getNumberOfRequiredWorkers() + building.getNumberOfRequiredEngineers() >= 0) {
            GameMenu.hoveringBarStateText.setText(String.format("%d/%d Engineers And %d/%d Workers" ,
                    building.getNumberOfEngineers() , building.getNumberOfRequiredEngineers()
                    ,building.getNumberOfWorkers()
                    , building.getNumberOfRequiredWorkers()));
        }
    }
}
