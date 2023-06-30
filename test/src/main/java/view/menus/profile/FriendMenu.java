package view.menus.profile;

import controller.network.FriendController;
import controller.network.UsersController;
import enumeration.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;
import model.menugui.*;
import view.controllers.ViewController;
import view.menus.LoginMenu;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class FriendMenu extends Application {
    public static Stage stage;
    public static StackPane root;
    public static MenuFingerBack back;
    public static BorderPane borderPane;
    public static Pane leftSide;
    public static Pane centerPane;
    User user;

    @Override
    public void start(Stage stage) throws Exception {
        FriendMenu.stage = stage;
        makeScene();
    }

    @FXML
    public void initialize() {
    }

    public void makeScene() throws IOException {
        BorderPane pane = FXMLLoader.load(
                new URL(Objects.requireNonNull(LoginMenu.class.getResource("/FXML/profile/friendMenu.fxml")).toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        root = ViewController.makeStackPaneScreen(stage, pane, 1000, 800);
        setBackground();
        borderPane = new BorderPane();
        leftSide = new Pane();
        centerPane = new Pane();

        leftSide.setMaxWidth(300);
        leftSide.setMinWidth(300);
        borderPane.setLeft(leftSide);
        borderPane.setCenter(centerPane);
        leftSide.setStyle("-fx-background-color: #fff");
        makeFriendPart();


        back = new MenuFingerBack(-400,300);
        back.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().addAll(borderPane);
        stage.show();
    }

    public void makeFriendPart() throws IOException {
        ArrayList<String> friends = FriendController.getFriends();
        VBox friendList= new VBox();
        for (String username: friends){
            VBox vBox = new VBox();
            ProfileView profileView = new ProfileView(username,80,300);
            vBox.getChildren().add(profileView);
            friendList.getChildren().add(vBox);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(friendList);
        scrollPane.setMaxHeight(700);
        scrollPane.setTranslateY(100);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        leftSide.getChildren().add(scrollPane);
    }


    public void setBackground() {
        BackgroundImage backgroundImage =
                new BackgroundImage(new Image(getClass().getResource
                        (Paths.BACKGROUND_IMAGES.getPath()).toExternalForm() + "03.jpg"),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));
        Background background = new Background(backgroundImage);
        root.setBackground(background);
    }
}