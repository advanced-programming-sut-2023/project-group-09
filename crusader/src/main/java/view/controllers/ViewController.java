package view.controllers;

import enumeration.Paths;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.menugui.MenuTextField;

public class ViewController {

    public static MediaPlayer musicPlayer;

    //give -1 if you want full height
    public static BorderPane makeScreen(Stage stage,BorderPane pane,double width,double height){
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.setFullScreenExitHint(null);

        pane.setStyle("-fx-background-color: #000");
        BorderPane borderPane = new BorderPane();
        pane.setCenter(borderPane);
        borderPane.setMaxWidth(width);
        if (height != -1){
            borderPane.setMaxHeight(height);
        }
        borderPane.setStyle("-fx-background-color: #fff");
        return borderPane;
    }

    public static Pane makePaneScreen(Stage stage, Pane pane, double width, double height){
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.setFullScreenExitHint(null);
        pane.setStyle("-fx-background-color: #000");
        Pane borderPane = new Pane();
        borderPane.setMaxWidth(width);
        if (height != -1){
            borderPane.setMaxHeight(height);
        }
        borderPane.setStyle("-fx-background-color: #fff");
        return borderPane;
    }

    public static void playMenuMusic() {
        Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                .toExternalForm() + "menuMusic.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        musicPlayer = mediaPlayer;
    }
}
