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
        Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                .toExternalForm() + "menuMusic.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(-1);
        musicPlayer = mediaPlayer;
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

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BackgroundImage backgroundImage =
                        new BackgroundImage(new Image(ViewController.class.getResource
                                (Paths.USER_ICONS.getPath()).toExternalForm() + "exitIcon.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false));
                Background background = new Background(backgroundImage);
                button.setBackground(background);
            }
        });

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Media media = new Media(ViewController.class.getResource(Paths.MENU_IMAGES.getPath())
                        .toExternalForm() + "exitSound.mp3");
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(1);
                mediaPlayer.play();
                BackgroundImage backgroundImage =
                        new BackgroundImage(new Image(ViewController.class.getResource
                                (Paths.USER_ICONS.getPath()).toExternalForm() + "exitHoverIcon.png"),
                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                new BackgroundSize(1.0, 1.0, true, true, false, false));
                Background background = new Background(backgroundImage);
                button.setBackground(background);
            }
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
