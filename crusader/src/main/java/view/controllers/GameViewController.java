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
    public static void createShortcutBars(Pane gamePane , Text text) {
        ImageView castleBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/castleBuildingsIcon.png");
        castleBuildingsImage.setTranslateX(425);
        castleBuildingsImage.setTranslateY(765);
        gamePane.getChildren().add(castleBuildingsImage);
        setHoverEventForBar(castleBuildingsImage , "Castle Buildings");

        ImageView industryBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/industryBuildingsIcon.png");
        industryBuildingsImage.setTranslateX(460);
        industryBuildingsImage.setTranslateY(765);
        gamePane.getChildren().add(industryBuildingsImage);
        setHoverEventForBar(industryBuildingsImage , "Industry Buildings");

        ImageView farmBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/farmBuildingsIcon.png");
        farmBuildingsImage.setTranslateX(495);
        farmBuildingsImage.setTranslateY(765);
        gamePane.getChildren().add(farmBuildingsImage);
        setHoverEventForBar(farmBuildingsImage , "Farm Buildings");

        ImageView townBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/townBuildingsIcon.png");
        townBuildingsImage.setTranslateX(530);
        townBuildingsImage.setTranslateY(765);
        gamePane.getChildren().add(townBuildingsImage);
        setHoverEventForBar(townBuildingsImage , "Town Buildings");

        ImageView weaponsBuildingsImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/weaponsBuildingsIcon.png");
        weaponsBuildingsImage.setTranslateX(565);
        weaponsBuildingsImage.setTranslateY(765);
        gamePane.getChildren().add(weaponsBuildingsImage);
        setHoverEventForBar(weaponsBuildingsImage , "Weapons Buildings");

        ImageView foodProcessingImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/foodProcessingBuildingsIcon.png");
        foodProcessingImage.setTranslateX(600);
        foodProcessingImage.setTranslateY(765);
        gamePane.getChildren().add(foodProcessingImage);
        setHoverEventForBar(foodProcessingImage , "Food Processing Buildings");

        ImageView orderOfMeritImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/orderOfMeritIcon.png");
        orderOfMeritImage.setTranslateX(875);
        orderOfMeritImage.setTranslateY(765);
        gamePane.getChildren().add(orderOfMeritImage);
        setHoverEventForBar(orderOfMeritImage , "Order Of Merit");


        ImageView keyImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/keyIcon.png");
        keyImage.setTranslateX(915);
        keyImage.setTranslateY(680);
        gamePane.getChildren().add(keyImage);
        setHoverEventForBar(keyImage , "Game Options");

        ImageView deleteImage = new ImageView(LoginMenu.class.getResource(Paths.BAR_IMAGES.getPath())
                .toExternalForm() + "icons/deleteIcon.png");
        deleteImage.setTranslateX(918);
        deleteImage.setTranslateY(740);
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
    }
}
