package view.controllers;

import controller.gamestructure.GameBuildings;
import enumeration.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.building.Building;
import model.menugui.MiniMap;
import model.menugui.game.GameMap;
import view.menus.GameMenu;
import view.menus.LoginMenu;

import java.util.ArrayList;

public class GameViewController {

    public static String nameOfPageInBar;
    public static Timeline timeline;

    public static void createShortcutBars(Pane gamePane, Text text) {
        setCenterOfBar();
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
            GameMenu.createGameBar();
            setCenterToCastleBuildings();
            return;
        }
        switch (GameMenu.hoveringBarStateText.getText()) {
            case "Castle Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToCastleBuildings();
            }
            case "Towers" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToTowers();
            }
            case "Military Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToMilitaryBuildings();
            }
            case "Gatehouses" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToGatehouses();
            }
            case "Industry Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToIndustryBuildings();
            }
            case "Farm Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToFarmBuildings();
            }
            case "Town Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToTownBuildings();
            }
            case "Weapons Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToWeaponsBuildings();
            }
            case "Food Processing Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToFoodProcessingBuildings();
            }
        }
    }


    public static void setCenterOfBar(String destination) {
        switch (destination) {
            case "Castle Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToCastleBuildings();
            }
            case "Towers" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToTowers();
            }
            case "Military Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToMilitaryBuildings();
            }
            case "Gatehouses" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToGatehouses();
            }
            case "Industry Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToIndustryBuildings();
            }
            case "Farm Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToFarmBuildings();
            }
            case "Town Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToTownBuildings();
            }
            case "Weapons Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToWeaponsBuildings();
            }
            case "Food Processing Buildings" -> {
                GameMenu.menuBar.getChildren().clear();
                GameMenu.createGameBar();
                setCenterToFoodProcessingBuildings();
            }
        }
    }

    private static void setCenterToFoodProcessingBuildings() {
        putBuildingImageView("granaryIcon", "Granary", "granary", 280, 100, 1);
        putBuildingImageView("bakeryIcon", "Bakery", "bakery", 380, 100, 1);
        putBuildingImageView("breweryIcon", "Brewery", "brewery", 480, 100, 1);
        putBuildingImageView("millIcon", "Mill", "mill", 580, 80, 1);
        putBuildingImageView("innIcon", "Inn", "inn", 660, 90, 1);
    }

    private static void setCenterToWeaponsBuildings() {
        putBuildingImageView("fletcherWorkshopIcon", "Fletcher's Workshop", "fletcher", 300, 100, 1);
        putBuildingImageView("poleturnerWorkshopIcon", "Poleturner's Workshop", "poleTurner", 470, 100, 1);
        putBuildingImageView("armourerIcon", "Armourer", "armourer", 500, 30, 0.25);
    }

    private static void setCenterToTownBuildings() {
        putBuildingImageView("hovelIcon", "Hovel", "hovel", 300, 100, 1);
        putBuildingImageView("churchIcon", "Church", "church", 450, 100, 1);
        putBuildingImageView("cathedralIcon", "Cathedral", "cathedral", 600, 90, 1);
    }

    private static void setCenterToFarmBuildings() {
        putBuildingImageView("dairyIcon", "Dairy Farm", "dairyProducts", 180, 20, 0.25);
        putBuildingImageView("appleFarmIcon", "Apple Orchard", "appleGarden", 300, 20, 0.25);
        putBuildingImageView("wheatFarmIcon", "Wheat Farm", "wheatFarm", 460, 20, 0.25);
        putBuildingImageView("hopsFarmIcon", "Hops Farm", "hopFarm", 540, 20, 0.25);
    }

    private static void setCenterToIndustryBuildings() {
        putBuildingImageView("stockPileIcon", "Stockpile", "stockPile", 180, 70, 0.25);
        putBuildingImageView("woodCutterIcon", "Wood Cutter", "woodCutter", 220, 0, 0.25);
        putBuildingImageView("quarryIcon", "Quarry", "quarry", 310, -50, 0.25);
        putBuildingImageView("oxTetherIcon", "Ox Tether", "oxTether", 440, 30, 0.25);
        putBuildingImageView("ironMineIcon", "Iron Mine", "ironMine", 400, 10, 0.25);
        putBuildingImageView("pitchRigIcon", "Pitch Rig", "pitchRig", 465, 30, 0.25);
        putBuildingImageView("shopIcon", "Market Place", "shop", 590, 10, 0.25);
    }

    private static void setCenterToGatehouses() {
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
        putBuildingImageView("smallGatehouseIcon", "Small Gatehouse", "smallStoneGatehouse", 250, 0, 0.25);
        putBuildingImageView("bigGatehouseIcon", "Big Gatehouse", "bigStoneGatehouse", 290, -50, 0.25);
        putBuildingImageView("drawBridgeIcon", "Draw Bridge", "drawBridge", 400, 20, 0.25);
        putBuildingImageView("cageIcon", "Caged Dogs", "cagedWarDogs", 450, 30, 0.25);
        putBuildingImageView("pitchDitchIcon", "Pitch Ditch", "", 530, 50, 0.25); // TODO:
        putBuildingImageView("killingPitIcon", "Killing Pit", "", 530, 90, 0.25); // TODO:
        putBuildingImageView("braizerIcon", "Braizer", "", 650, 70, 0.25); // braizer will added to game buildings
    }

    private static void setCenterToMilitaryBuildings() {
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
        putBuildingImageView("engineerGuildIcon", "Engineer's Guild", "engineerGuild", 220, 20, 0.25);
        putBuildingImageView("stableIcon", "Stable", "stable", 300, -5, 0.25);
        putBuildingImageView("tunnelerGuildIcon", "Tunneler's Guild", "tunnelersGuild", 450, 5, 0.25);
        putBuildingImageView("oilSmelterIcon", "Oil Smelter", "oilSmelter", 540, 5, 0.25);
    }

    private static void setCenterToTowers() {
        putBuildingImageView("lookoutTowerIcon", "Lookout Tower", "lookoutTower", 290, -45, 0.25);
        putBuildingImageView("perimeterTurretIcon", "Perimeter Turret", "perimeterTurret", 340, 5, 0.30);
        putBuildingImageView("defenseTurretIcon", "Defense Turret", "defenseTurret", 415, -15, 0.3);
        putBuildingImageView("squareTowerIcon", "Square Tower", "squareTower", 495, -25, 0.3);
        putBuildingImageView("roundTowerIcon", "Round Tower", "roundTower", 595, -30, 0.3);
        putButtonImageViewWithDestination("backButtonIcon", "Back To Castles", "Castle Buildings", 225, 60, 0.2);
    }

    private static void setCenterToCastleBuildings() {
        putBuildingImageView("stairsIcon", "Stairs", "stairs", 240, 80, 0.4);
        putBuildingImageView("smallWallIcon", "Low Wall", "lowWall", 265, 80, 0.4);
        putBuildingImageView("bigWallIcon", "Stone Wall", "stoneWall", 310, 60, 0.4);
        putBuildingImageView("crenulatedWallIcon", "Crenulated Wall", "crenulatedWall", 370, 40, 0.4);
        putBuildingImageView("barracksIcon", "Barrack", "barrack", 450, 80, 0.4);
        putBuildingImageView("mercenrayIcon", "Mercenary Post", "mercenaryPost", 505, 100, 0.4);
        putBuildingImageView("armoryIcon", "Armoury", "armoury", 595, 70, 0.4);
        putImageView("towersIcon", "Towers", 652, 80);
        putImageView("militaryIcon", "Military Buildings", 692, 80);
        putImageView("gatehouseIcon", "Gatehouses", 692, 120);

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

    private static void putBuildingImageView(String fileName, String name, String buildingName, double x, double y, double size) {
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
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // TODO : select building
            }
        });
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

}
