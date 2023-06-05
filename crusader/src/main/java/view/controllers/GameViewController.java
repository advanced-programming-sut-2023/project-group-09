package view.controllers;

import controller.gamestructure.GameBuildings;
import enumeration.Paths;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.building.Building;
import view.menus.GameMenu;
import view.menus.LoginMenu;

public class GameViewController{

    public static String nameOfPageInBar;
    public static void createShortcutBars(Pane gamePane , Text text) {
        setCenterToCastleBuildings();
        ImageView castleBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/castleBuildingsIcon.png");
        castleBuildingsImage.setTranslateX(275);
        castleBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(castleBuildingsImage);
        setHoverEventForBar(castleBuildingsImage , "Castle Buildings");

        ImageView industryBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/industryBuildingsIcon.png");
        industryBuildingsImage.setTranslateX(310);
        industryBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(industryBuildingsImage);
        setHoverEventForBar(industryBuildingsImage , "Industry Buildings");

        ImageView farmBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/farmBuildingsIcon.png");
        farmBuildingsImage.setTranslateX(345);
        farmBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(farmBuildingsImage);
        setHoverEventForBar(farmBuildingsImage , "Farm Buildings");

        ImageView townBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/townBuildingsIcon.png");
        townBuildingsImage.setTranslateX(380);
        townBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(townBuildingsImage);
        setHoverEventForBar(townBuildingsImage , "Town Buildings");

        ImageView weaponsBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/weaponsBuildingsIcon.png");
        weaponsBuildingsImage.setTranslateX(415);
        weaponsBuildingsImage.setTranslateY(185);
        gamePane.getChildren().add(weaponsBuildingsImage);
        setHoverEventForBar(weaponsBuildingsImage , "Weapons Buildings");

        ImageView foodProcessingImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/foodProcessingBuildingsIcon.png");
        foodProcessingImage.setTranslateX(450);
        foodProcessingImage.setTranslateY(185);
        gamePane.getChildren().add(foodProcessingImage);
        setHoverEventForBar(foodProcessingImage , "Food Processing Buildings");

        ImageView orderOfMeritImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/orderOfMeritIcon.png");
        orderOfMeritImage.setTranslateX(675);
        orderOfMeritImage.setTranslateY(185);
        gamePane.getChildren().add(orderOfMeritImage);
        setHoverEventForBar(orderOfMeritImage , "Order Of Merit");


        ImageView keyImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/keyIcon.png");
        keyImage.setTranslateX(760);
        keyImage.setTranslateY(85);
        gamePane.getChildren().add(keyImage);
        setHoverEventForBar(keyImage , "Game Options");

        ImageView deleteImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/deleteIcon.png");
        deleteImage.setTranslateX(760);
        deleteImage.setTranslateY(155);
        gamePane.getChildren().add(deleteImage);
        setHoverEventForBar(deleteImage , "Delete");

    }

    private static void setHoverEventForBar(ImageView imageView , String text) {
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

    public static void setCenterOfBar() {
        switch (GameMenu.hoveringBarStateText.getText()) {
            case "Castle Buildings" -> {
                setCenterToCastleBuildings();
            }
        }
    }

    private static void setCenterToCastleBuildings() {
        putImageView("stairsIcon" , "stairs" , "stairs", 240 , 80);
        putImageView("smallWallIcon" , "low wall"  , "lowWall" ,265 , 80);
        putImageView("bigWallIcon" , "stone wall" , "stoneWall", 310 , 60);
        putImageView("crenulatedWallIcon" , "crenulated wall" , "crenulatedWall", 370 , 40);
        putImageView("barracksIcon" , "barrack" , "barrack", 450 , 80);
        putImageView("mercenrayIcon" , "mercenary post" , "mercenaryPost", 505 , 100);
        putImageView("armoryIcon" , "armoury" , "armoury", 595 , 70);
        putImageView("towersIcon" , "" , "" , 652 , 80);
        putImageView("militaryIcon" , "" , "" , 692 , 80);
        putImageView("gatehouseIcon" , "" , "" , 692 , 120);



    }
    
    private static Text getNumberOfResourceNeededText() {
        Text text = new Text("");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));
        text.setStrokeWidth(0.5);
        text.setStroke(Color.BLACK);
        return text;
    }

    private static void putImageView(String fileName , String name , String buildingName, double x ,double y) {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        ImageView resourceIcon = new ImageView();
        Text text = getNumberOfResourceNeededText();
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(0.4);
        icon.setScaleY(0.4);
        GameMenu.menuBar.getChildren().add(icon);
        Building sampleBuilding = GameBuildings.getBuilding(buildingName);
        icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText(name);
                if (!buildingName.equals("")) {
                    String item = "";
                    for (String i : sampleBuilding.getCost().keySet()) {
                        item = i;
                        text.setText("(" + sampleBuilding.getCost().get(item).toString() + ")");
                        break;
                    }
                    if (item.equals("wood")) {
                        resourceIcon.setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).
                                toExternalForm() + "icons/woodIcon.png"));
                        resourceIcon.setTranslateY(GameMenu.hoveringBarStateText.getTranslateY() - 57);
                        resourceIcon.setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 95);
                    } else if (item.equals("stone")) {
                        resourceIcon.setImage(new Image(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath()).toExternalForm()
                                + "icons/stoneIcon.png"));
                        resourceIcon.setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 100);
                        resourceIcon.setTranslateY(GameMenu.hoveringBarStateText.getTranslateY() - 47);
                    }
                    resourceIcon.setScaleX(0.2);
                    resourceIcon.setScaleY(0.2);
                    text.setTranslateY(70);
                    text.setTranslateX(GameMenu.hoveringBarStateText.getTranslateX() + 155);
                    GameMenu.menuBar.getChildren().add(resourceIcon);
                    GameMenu.menuBar.getChildren().add(text);
                    }
            }
        });
        icon.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameMenu.hoveringBarStateText.setText("");
                GameMenu.menuBar.getChildren().remove(resourceIcon);
                GameMenu.menuBar.getChildren().remove(text);
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
