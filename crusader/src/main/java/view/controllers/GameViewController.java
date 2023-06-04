package view.controllers;

import enumeration.Paths;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import view.menus.GameMenu;
import view.menus.LoginMenu;

public class GameViewController{

    public static String nameOfPageInBar;
    public static void createShortcutBars(Pane gamePane , Text text) {
        setCenterToCastleBuildings();
        ImageView castleBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/castleBuildingsIcon.png");
        castleBuildingsImage.setTranslateX(375);
        castleBuildingsImage.setTranslateY(825);
        gamePane.getChildren().add(castleBuildingsImage);
        setHoverEventForBar(castleBuildingsImage , "Castle Buildings");

        ImageView industryBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/industryBuildingsIcon.png");
        industryBuildingsImage.setTranslateX(410);
        industryBuildingsImage.setTranslateY(825);
        gamePane.getChildren().add(industryBuildingsImage);
        setHoverEventForBar(industryBuildingsImage , "Industry Buildings");

        ImageView farmBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/farmBuildingsIcon.png");
        farmBuildingsImage.setTranslateX(445);
        farmBuildingsImage.setTranslateY(825);
        gamePane.getChildren().add(farmBuildingsImage);
        setHoverEventForBar(farmBuildingsImage , "Farm Buildings");

        ImageView townBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/townBuildingsIcon.png");
        townBuildingsImage.setTranslateX(480);
        townBuildingsImage.setTranslateY(825);
        gamePane.getChildren().add(townBuildingsImage);
        setHoverEventForBar(townBuildingsImage , "Town Buildings");

        ImageView weaponsBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/weaponsBuildingsIcon.png");
        weaponsBuildingsImage.setTranslateX(515);
        weaponsBuildingsImage.setTranslateY(825);
        gamePane.getChildren().add(weaponsBuildingsImage);
        setHoverEventForBar(weaponsBuildingsImage , "Weapons Buildings");

        ImageView foodProcessingImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/foodProcessingBuildingsIcon.png");
        foodProcessingImage.setTranslateX(550);
        foodProcessingImage.setTranslateY(825);
        gamePane.getChildren().add(foodProcessingImage);
        setHoverEventForBar(foodProcessingImage , "Food Processing Buildings");

        ImageView orderOfMeritImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/orderOfMeritIcon.png");
        orderOfMeritImage.setTranslateX(775);
        orderOfMeritImage.setTranslateY(825);
        gamePane.getChildren().add(orderOfMeritImage);
        setHoverEventForBar(orderOfMeritImage , "Order Of Merit");


        ImageView keyImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/keyIcon.png");
        keyImage.setTranslateX(852);
        keyImage.setTranslateY(730);
        gamePane.getChildren().add(keyImage);
        setHoverEventForBar(keyImage , "Game Options");

        ImageView deleteImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/deleteIcon.png");
        deleteImage.setTranslateX(852);
        deleteImage.setTranslateY(790);
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
        putImageView("stairsIcon" , "" , 325 , 720);
        putImageView("smallWallIcon" , "" , 355 , 720);
        putImageView("bigWallIcon" , "" , 400 , 700);
        putImageView("crenulatedWallIcon" , "" , 460 , 680);
        putImageView("barracksIcon" , "" , 545 , 720);
        putImageView("mercenrayIcon" , "" , 605 , 740);
        putImageView("armoryIcon" , "" , 690 , 710);
        putImageView("towersIcon" , "" , 745 , 720);
        putImageView("militaryIcon" , "" , 785 , 720);
        putImageView("gatehouseIcon" , "" , 785 , 760);



    }

    private static void putImageView(String fileName , String name , double x ,double y) {
        ImageView icon = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/" + fileName + ".png");
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(0.4);
        icon.setScaleY(0.4);
        GameMenu.menuBar.getChildren().add(icon);
    }
}
