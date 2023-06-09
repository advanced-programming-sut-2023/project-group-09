package view.menus;

import client.Packet;
import client.PacketOnlineHandler;
import controller.DBController;
import controller.MainController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.menugui.MenuButton;
import view.controllers.ViewController;
import view.menus.profile.ProfileMenu;
import view.menus.profile.Scoreboard;

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
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, -1);
        setBackground();

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().getName().equals("C")) {
                //System.out.println("c pressed");
                Thread thread = new Thread(() -> {
                    Packet packet = null;
                    try {
                        packet = Packet.receivePacket();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        new PacketOnlineHandler(packet).handle();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Platform.runLater(() -> {
                        try {
                            new GameMenu().start(MainMenu.stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
                thread.start();
            }
        });

        MenuButton createMapButton = new MenuButton("Create Map", root, 0, -240, true);
        createMapButton.setOnMouseClicked(mouseEvent -> {
            try {
                new SharedMapMenu().start(stage);
            } catch (Exception e) {

            }
        });

        MenuButton startGameButton = new MenuButton("Start game", root, 0, -170, true);
        startGameButton.setOnMouseClicked(mouseEvent -> {
            CreateGameMenu createGameMenu = new CreateGameMenu();
            try {
                createGameMenu.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton menuButton = new MenuButton("Profile menu", root, 0, -100, true);
        menuButton.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        MenuButton scoreboard = new MenuButton("scoreboard", root, 0, -25, true);
        scoreboard.setOnMouseClicked(mouseEvent -> {
            try {
                new Scoreboard().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuButton logoutButton = new MenuButton("Logout", root, 0, 50, true);

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

        MenuButton exitButton = new MenuButton("Exit", root, 0, 125, true);
        exitButton.setOnMouseClicked(mouseEvent -> MainController.exitCrusader());

        MenuButton lobby = new MenuButton("lobby", root, 0, 200, true);
        lobby.setOnMouseClicked(mouseEvent -> {
            try {
                new LobbyMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        root.getChildren().add(menuButton);
        root.getChildren().add(logoutButton);
        root.getChildren().add(scoreboard);
        root.getChildren().add(exitButton);
        root.getChildren().add(startGameButton);
        root.getChildren().add(createMapButton);
        root.getChildren().add(lobby);

        stage.show();
    }

    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "01.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }


}
