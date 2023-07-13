package view.controllers;

import enumeration.Paths;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ViewController {

    public static MediaPlayer musicPlayer;

    public static MediaPlayer attackMediaPlayer;
    public static MediaPlayer gameMediaPlayer;

    public static boolean menuIsPlaying;
    public static boolean attackIsPlaying;
    public static boolean gameIsPlaying;

    //give -1 if you want full height
    public static Pane makeScreen(Stage stage, BorderPane pane, double width, double height) {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        //stage.setFullScreenExitHint(null);

        pane.setStyle("-fx-background-color: #000");
        Pane newPane = new BorderPane();
        pane.setCenter(newPane);
        if (width > 0) {
            newPane.setMaxWidth(width);
            newPane.setMinWidth(width);
        }

        if (height > 0) {
            newPane.setMaxHeight(height);
            newPane.setMinHeight(height);
        }
        newPane.setStyle("-fx-background-color: #fff");
        return newPane;
    }

    public static Pane makePaneScreen(Stage stage, Pane pane, double width, double height) {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        pane.setStyle("-fx-background-color: #000");
        Pane newPane = new BorderPane();
        if (width > 0) {
            newPane.setMaxWidth(width);
            newPane.setMinWidth(width);
        }

        if (height > 0) {
            newPane.setMaxHeight(height);
            newPane.setMinHeight(height);
        }
        newPane.setStyle("-fx-background-color: #fff");
        pane.getChildren().add(newPane);
        return newPane;
    }

    public static Pane makePaneScreen(Stage stage, BorderPane pane, double width, double height) {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        pane.setStyle("-fx-background-color: #000");
        Pane newPane = new Pane();
        newPane.setMaxWidth(width);
        if (height != -1) {
            newPane.setMaxHeight(height);
        }
        newPane.setStyle("-fx-background-color: #fff");
        pane.setCenter(newPane);
        return newPane;
    }

    public static StackPane makeStackPaneScreen(Stage stage, BorderPane pane, double width, double height) {
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        pane.setStyle("-fx-background-color: #000");
        StackPane newPane = new StackPane();
        newPane.setMaxWidth(width);
        if (height != -1) {
            newPane.setMaxHeight(height);
        }
        pane.setCenter(newPane);
        return newPane;
    }

    public static void playMenuMusic() {
        if (menuIsPlaying) return;
        Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                .toExternalForm() + "menuMusic.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        musicPlayer = mediaPlayer;
        menuIsPlaying = true;
    }

    public static void playAttackMusic() {
        if (attackIsPlaying) return;
        Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                .toExternalForm() + "attack.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        attackMediaPlayer = mediaPlayer;
        attackIsPlaying = true;
    }

    public static void playGameMusic() {
        if (gameIsPlaying) return;
        Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                .toExternalForm() + "game.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        gameMediaPlayer= mediaPlayer;
        gameIsPlaying = true;

    }

    public static void stopMenuMusic(){
        if (musicPlayer != null){
            musicPlayer.stop();
            menuIsPlaying = false;
        }
    }

    public static void stopGameMusic(){
        if (gameMediaPlayer != null){
            gameMediaPlayer.stop();
            gameIsPlaying = false;
        }
    }


    public static void stopAttackMusic(){
        if (attackMediaPlayer != null){
            attackMediaPlayer.stop();
            attackIsPlaying = false;
        }
    }


    public static void createAndShowAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Button createExitButton() {
        Button button = new Button();
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(ViewController.class.getResource
                        (Paths.USER_ICONS.getPath()).toExternalForm() + "exitIcon.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        button.setBackground(background);
        button.setScaleX(3);
        button.setScaleY(3);

        button.setOnMouseExited(mouseEvent -> {
            BackgroundImage backgroundImage1 =
                    new BackgroundImage(new Image(ViewController.class.getResource
                            (Paths.USER_ICONS.getPath()).toExternalForm() + "exitIcon.png"),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(1.0, 1.0, true, true, false, false));
            Background background1 = new Background(backgroundImage1);
            button.setBackground(background1);
        });

        button.setOnMouseEntered(mouseEvent -> {
            Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                    .toExternalForm() + "exitSound.mp3");
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(1);
            mediaPlayer.play();
            BackgroundImage backgroundImage12 =
                    new BackgroundImage(new Image(ViewController.class.getResource
                            (Paths.USER_ICONS.getPath()).toExternalForm() + "exitHoverIcon.png"),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                            BackgroundPosition.DEFAULT,
                            new BackgroundSize(1.0, 1.0, true, true, false, false));
            Background background12 = new Background(backgroundImage12);
            button.setBackground(background12);
        });

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

        return button;

    }

    public synchronized static void playDeadSong() {
        Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                .toExternalForm() + "evil_laugh.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }

    public static double getScreenHeight() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double screenHeight = bounds.getHeight();
        return screenHeight;
    }
}
