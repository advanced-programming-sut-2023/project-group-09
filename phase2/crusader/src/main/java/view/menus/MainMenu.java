package view.menus;

import controller.DBController;
import controller.MainController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import model.menugui.MenuButton;
import view.controllers.ImageLoader;
import view.controllers.ViewController;
import view.menus.profile.ProfileMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainMenu extends Application {
    public static Stage stage;
    public static StackPane root;

    @Override
    public void start(Stage stage) throws Exception {
        MainMenu.stage = stage;
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/mainMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1250, 940);
        setBackground();

        String videoPath = "files/rs.mp4";

        Media media = new Media(new File(videoPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        // Set the size of the MediaView
        double videoWidth = 353;
        double videoHeight = 550;
        mediaView.setFitWidth(videoWidth);
        //mediaView.setFitHeight(videoHeight);
        new ImageLoader().start();
        // Set the translation (position) of the MediaView
        double translateX = 310;
        double translateY = -40;
        mediaView.setTranslateX(translateX);
        mediaView.setTranslateY(translateY);


        MenuButton startGameButton = new MenuButton("Start game", root, -100, -170, true);
        startGameButton.setOnMouseClicked(mouseEvent -> {
            CreateGameMenu createGameMenu = new CreateGameMenu();
            try {
                createGameMenu.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton menuButton = new MenuButton("Profile menu", root, -100, -100, true);
        menuButton.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton logoutButton = new MenuButton("Logout", root, -100, -25, true);

        logoutButton.setOnMouseClicked(mouseEvent -> {
            controller.Application.setCurrentUser(null);
            controller.Application.setStayLoggedIn(false);
            DBController.saveCurrentUser();
            try {
                new LoginMenu().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MenuButton exitButton = new MenuButton("Exit", root, -100, 50, true);
        exitButton.setOnMouseClicked(mouseEvent -> MainController.exitCrusader());

        root.getChildren().add(menuButton);
        root.getChildren().add(logoutButton);
        root.getChildren().add(exitButton);
        root.getChildren().add(startGameButton);
        root.getChildren().add(mediaView);
        mediaPlayer.play();
        stage.show();
    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "09.png"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }


}
